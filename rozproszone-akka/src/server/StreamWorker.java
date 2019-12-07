package server;

import akka.Done;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.concurrent.duration.FiniteDuration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamWorker extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println("StreamWorker: " + s);

                    AtomicInteger lineCounter = new AtomicInteger();
                    List<String> lines = new ArrayList<>();
                    Files.lines(Paths.get(s)).forEach(line -> {
                        if (lineCounter.get() >= 100) {
                            lineCounter.set(0);
                            streamLines(lines);

                            lines.clear();
                            collectLine(lineCounter, lines, line);
                        } else {
                            collectLine(lineCounter, lines, line);
                        }
                    });
                    if (!lines.isEmpty()) {
                        streamLines(lines);
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private void streamLines(List<String> lines) {
        final Source source = Source.from(lines)
                .throttle(1, FiniteDuration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping());
        final Sink sink = Sink.actorRef(getSender(), Done.getInstance());

        source.runWith(sink, Server.MATERIALIZER);
    }

    private void collectLine(AtomicInteger lineCounter, List<String> lines, String line) {
        lines.add(line);
        lineCounter.getAndIncrement();
    }
}
