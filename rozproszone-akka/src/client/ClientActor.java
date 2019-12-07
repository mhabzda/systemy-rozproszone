package client;

import akka.Done;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (getSender().path().toString().contains("client")) {
                        context().actorSelection("akka.tcp://server_system@127.0.0.1:3552/user/server")
                                .tell(s, getSelf());
                    } else {
                        System.out.println(s);
                    }
                })
                .match(Done.class, done -> System.out.println("Received lines"))
                .match(Integer.class, System.out::println)
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
