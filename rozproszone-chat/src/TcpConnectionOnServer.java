import java.io.BufferedReader;
import java.io.IOException;

public class TcpConnectionOnServer extends Thread {
  private BufferedReader in;
  private int id;

  public TcpConnectionOnServer(BufferedReader in, int id) {
    this.in = in;
    this.id = id;
  }

  @Override
  public void run() {
    super.run();
    while (true) {
      String msg = null;
      try {
        do {
          msg = in.readLine();
        } while (msg == null);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("received msg: " + msg);
      String messageToClients = String.format("client id %d: %s", id, msg);
      Server.sendMessageToClientsByTcp(messageToClients, id);
    }
  }
}
