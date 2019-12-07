import org.jgroups.*;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributedMap implements SimpleStringMap {
  private JChannel channel;
  private final Map<String, String> hashMap = new HashMap<>();

  public Map<String, String> getHashMap() {
    return hashMap;
  }

  public JChannel getChannel() {
    return channel;
  }

  public DistributedMap() throws Exception {
    initializeJChannel();
    channel.getState(null, 0);
  }

  private void initializeJChannel() throws Exception {
    channel = new JChannel(false);
    ProtocolStack stack = new ProtocolStack();
    channel.setProtocolStack(stack);
    stack.addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.0.0.19")))
            .addProtocol(new PING())
            .addProtocol(new MERGE3())
            .addProtocol(new FD_SOCK())
            .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
            .addProtocol(new VERIFY_SUSPECT())
            .addProtocol(new BARRIER())
            .addProtocol(new NAKACK2())
            .addProtocol(new UNICAST3())
            .addProtocol(new STABLE())
            .addProtocol(new GMS())
            .addProtocol(new UFC())
            .addProtocol(new MFC())
            .addProtocol(new FRAG2())
            .addProtocol(new SEQUENCER())
            .addProtocol(new FLUSH())
            .addProtocol(new STATE_TRANSFER());

    stack.init();

    channel.setReceiver(new ReceiverAdapter() {
      @Override
      public void getState(OutputStream output) throws Exception {
        synchronized (hashMap) {
          Util.objectToStream(hashMap, new DataOutputStream(output));
        }
      }

      @Override
      public void setState(InputStream input) throws Exception {
        Map<String, String> map;
        map = (Map<String, String>) Util.objectFromStream(new DataInputStream(input));
        synchronized (hashMap) {
          hashMap.clear();
          hashMap.putAll(map);
        }
        System.out.println(hashMap);
      }

      @Override
      public void receive(Message msg) {
        System.out.println("received message " + msg);

        Map<String, String> map = (Map<String, String>) msg.getObject(Map.class.getClassLoader());
        synchronized (hashMap) {
          hashMap.clear();
          hashMap.putAll(map);
        }
      }

      @Override
      public void viewAccepted(View view) {
        System.out.println("received view " + view.getMembers());
        handleView(view);
      }
    });

    channel.connect("hashmap");
  }

  @Override
  public boolean containsKey(String key) {
    return hashMap.containsKey(key);
  }

  @Override
  public String get(String key) {
    return hashMap.get(key);
  }

  @Override
  public String put(String key, String value) {
    String element = hashMap.put(key, value);
    sendTable();

    return element;
  }

  @Override
  public String remove(String key) {
    String element = hashMap.remove(key);
    sendTable();

    return element;
  }

  private void sendTable() {
    try {
      Message msg = new Message(null, null, hashMap);
      channel.send(msg);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleView(View newView) {
    if (newView instanceof MergeView) {
      ViewHandler handler = new ViewHandler(channel, (MergeView) newView);
      handler.start();
    }
  }

  private class ViewHandler extends Thread {
    private JChannel jChannel;
    private MergeView view;

    private ViewHandler(JChannel jChannel, MergeView view) {
      this.jChannel = jChannel;
      this.view = view;
    }

    public void run() {
      List<View> subgroups = view.getSubgroups();
      View temporaryView = subgroups.get(0);
      Address localAddress = jChannel.getAddress();
      System.out.println(localAddress);
      if (!temporaryView.getMembers().contains(localAddress)) {
        System.out.println("I am not a member of the primary partition ("
                + temporaryView + "), will re-acquire the state");
        try {
          jChannel.getState(null, 30000);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      } else {
        System.out.println("I am a member of the primary partition ("
                + temporaryView + "), will do nothing");
      }
    }
  }
}
