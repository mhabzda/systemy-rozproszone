import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class DataMonitor implements Watcher {
    static final String ZNODE = "/znode_testowy";

    private ZooKeeper zooKeeper;
    private DataMonitorListener listener;
    private List<String> offspring = new ArrayList<>();
    boolean dead;

    public DataMonitor(ZooKeeper zooKeeper, DataMonitorListener listener) {
        this.listener = listener;
        this.zooKeeper = zooKeeper;
    }

    public interface DataMonitorListener {
        void created(String path);

        void deleted(String path);

        void childrenChanged(int size);

        void closing();
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case Expired:
                    dead = true;
                    listener.closing();
                    break;
            }
        } else if (event.getType() == Event.EventType.NodeCreated) {
            if (path.equals(ZNODE)) {
                try {
                    zooKeeper.getChildren(ZNODE, this);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
                listener.created(path);
            }
        } else if (event.getType() == Event.EventType.NodeDeleted) {
            if (path.equals(ZNODE)) {
                listener.deleted(path);
            } else if (path.contains(ZNODE)) {
                offspring.remove(path);
            }
        } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
            if (path.contains(ZNODE)) {
                try {
                    List<String> children = zooKeeper.getChildren(path, this);
                    addNewOffSpring(children, path);

                    listener.childrenChanged(offspring.size());
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        getSubscriptions();
    }

    private void addNewOffSpring(List<String> newOffspring, String path) {
        newOffspring.stream()
                .map(it -> path + "/" + it)
                .forEach(it -> {
                    if (!offspring.contains(it)) {
                        offspring.add(it);
                    }
                });
    }

    private void getSubscriptions() {
        try {
            zooKeeper.exists(ZNODE, this);
            for (String name : offspring) {
                zooKeeper.exists(name, this);
                zooKeeper.getChildren(name, this);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
