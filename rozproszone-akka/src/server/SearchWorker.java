package server;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchWorker extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String fileName;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println("SearchWorker: " + s);
                    AtomicBoolean found = new AtomicBoolean(false);
                    Files.lines(Paths.get(fileName)).forEach(line -> {
                        String[] book = line.split(" ");
                        if (s.equals(book[0])) {
                            found.set(true);
                            getSender().tell(Integer.parseInt(book[1]), getSelf());
                        }
                    });
                    if (!found.get()) getSender().tell(-1, getSelf());

                })
                .match(Integer.class, integer -> fileName = String.format("books%d.txt", integer))
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
