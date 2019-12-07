import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.*;

public class Executor extends Thread implements DataMonitor.DataMonitorListener, Watcher {
    private DataMonitor dataMonitor;
    private ZooKeeper zooKeeper;
    private Process child;
    private String exec[];
    private static StructurePrinter structurePrinter;
    public Executor(String exec[]) throws IOException {
        this.exec = exec;
        this.zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, this);
        this.dataMonitor = new DataMonitor(zooKeeper, this);
        structurePrinter = new StructurePrinter(zooKeeper);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("USAGE: Executor program [args ...]");
            System.exit(2);
        }
        String exec[] = new String[args.length];
        System.arraycopy(args, 0, exec, 0, exec.length);
        try {
            new Executor(exec).start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (input.readLine().equals("structure")) {
                try {
                    structurePrinter.printStructure(DataMonitor.ZNODE);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dataMonitor.dead) {
                    wait();
                }
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void created(String path) {
        if (child == null) {
            try {
                System.out.println("Starting program");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void deleted(String path) {
        if (child != null) {
            System.out.println("Stopping program");
            child.destroy();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            child = null;
        }
    }

    @Override
    public void childrenChanged(int size) {
        System.out.println("Offspring count: " + size);
    }

    @Override
    public void closing() {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        dataMonitor.process(event);
    }

    static class StreamWriter extends Thread {
        private OutputStream outputStream;
        private InputStream inputStream;

        StreamWriter(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            start();
        }

        public void run() {
            byte buffer[] = new byte[80];
            int bytesRead;
            try {
                while ((bytesRead = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
