package server;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class OrderWorker extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final File file;
    private final RandomAccessFile stream;
    private final FileChannel channel;

    public OrderWorker() throws FileNotFoundException {
        this.file = new File("orders.txt");
        this.stream = new RandomAccessFile(file, "rw");
        this.channel = stream.getChannel();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println("OrderWorker: " + s);

                    FileLock lock = channel.lock();
                    long fileLength = file.length();
                    stream.seek(fileLength);
                    stream.writeChars(String.format("%s\n", s));
                    lock.release();

                    getSender().tell("Book ordered", getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
