package server;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import scala.concurrent.duration.Duration;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import static akka.actor.SupervisorStrategy.restart;

public class ServerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private static final int SEARCH_WORKER_PAIRS_NUMBER = 6;
    private static final int ORDER_WORKERS_NUMBER = 6;
    private static final int STREAM_WORKERS_NUMBER = 6;

    private final List<WorkersEntity> workersEntities = new ArrayList<>(SEARCH_WORKER_PAIRS_NUMBER);
    private final Router orderWorkersRouter;
    private final Router streamWorkersRouter;

    public ServerActor() {
        orderWorkersRouter = new Router(new RoundRobinRoutingLogic(),
                initializeActors(ORDER_WORKERS_NUMBER, OrderWorker.class));

        streamWorkersRouter = new Router(new RoundRobinRoutingLogic(),
                initializeActors(STREAM_WORKERS_NUMBER, StreamWorker.class));
    }

    private <T> List<Routee> initializeActors(int workersNumber, Class<T> kClass) {
        List<Routee> routees = new ArrayList<>();
        for (int i = 0; i < workersNumber; i++) {
            ActorRef worker = getContext().actorOf(Props.create(kClass));
            getContext().watch(worker);
            routees.add(new ActorRefRoutee(worker));
        }
        return routees;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println("Server: " + s);
                    String title = s.split(" ")[1];
                    if (s.contains("search")) {
                        WorkersEntity workersEntity = getWorkersEntity();
                        if (workersEntity == null) {
                            getSelf().tell(s, getSelf());
                        } else {
                            workersEntity.setClient(getSender());

                            workersEntity.getFirstWorker().tell(1, getSelf());
                            workersEntity.getFirstWorker().tell(title, getSelf());

                            workersEntity.getSecondWorker().tell(2, getSelf());
                            workersEntity.getSecondWorker().tell(title, getSelf());
                        }
                    } else if (s.contains("order")) {
                        orderWorkersRouter.route(title, getSender());
                    } else if (s.contains("stream")) {
                        streamWorkersRouter.route(title, getSender());
                    }
                })
                .match(Integer.class, integer -> {
                    WorkersEntity workersEntity = findWorkersEntity(getSender());
                    if (!workersEntity.isTaskDone()) {
                        tryToSendResult(integer, workersEntity);
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private void tryToSendResult(Integer integer, WorkersEntity workersEntity) {
        if (integer == -1) {
            if (workersEntity.isFirstActorDone()) {
                workersEntity.getClient().tell("There is no such a book", getSelf());
                setTaskDone(workersEntity);
            } else {
                workersEntity.setFirstActorDone(true);
            }
        } else {
            workersEntity.getClient().tell(integer, getSelf());
            setTaskDone(workersEntity);
        }
    }

    private void setTaskDone(WorkersEntity workersEntity) {
        workersEntity.setTaskDone(true);
        workersEntity.setFirstActorDone(false);
    }

    private WorkersEntity findWorkersEntity(ActorRef sender) {
        return workersEntities.stream()
                .filter(it -> it.getFirstWorker().equals(sender) || it.getSecondWorker().equals(sender))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no such an worker"));
    }

    private WorkersEntity getWorkersEntity() {
        if (workersEntities.size() < SEARCH_WORKER_PAIRS_NUMBER) {
            return createWorkers();
        }
        return searchForFreeWorkers();
    }

    private WorkersEntity createWorkers() {
        WorkersEntity workersEntity = new WorkersEntity(
                context().actorOf(Props.create(SearchWorker.class)),
                context().actorOf(Props.create(SearchWorker.class))
        );
        workersEntities.add(workersEntity);
        return workersEntity;
    }

    private WorkersEntity searchForFreeWorkers() {
        for (WorkersEntity workersEntity : workersEntities) {
            if (workersEntity.isTaskDone()) {
                workersEntity.setTaskDone(false);
                return workersEntity;
            }
        }
        return null;
    }

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"),
            DeciderBuilder
                    .match(NoSuchFileException.class, ex -> SupervisorStrategy.resume())
                    .match(FileNotFoundException.class, ex -> SupervisorStrategy.resume())
                    .matchAny(o -> restart())
                    .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
