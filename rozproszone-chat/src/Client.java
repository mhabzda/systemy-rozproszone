import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;

public class Client {
  private static ClientUdp udpClient;

  public static void main(String[] args) throws IOException {
    System.out.println("JAVA TCP/UDP CLIENT");
    String hostName = "localhost";
    int portNumber = 12345;

    Socket socketTcp = null;
    DatagramSocket socketUdp = null;

    try {
      socketTcp = new Socket(hostName, portNumber);

      socketUdp = new DatagramSocket();
      socketUdp.setSoTimeout(500);
      udpClient = new ClientUdp(socketUdp);
      udpClient.start();

      PrintWriter out = new PrintWriter(socketTcp.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socketTcp.getInputStream()));

      BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

      while (true) {
        if (userIn.ready()) {
          String msg = userIn.readLine();
          switch (msg) {
            case "U":
              udpClient.putElementToUnicastQueue(ASCIIArt.ASCII_ART);
              break;
            case "M":
              udpClient.putElementToMulticastQueue(ASCIIArt.ASCII_ART);
              break;
            default:
              out.println(msg);
              break;
          }
        }

        if (in.ready()) {
          String msg = in.readLine();
          System.out.println(msg);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        udpClient.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (socketTcp != null) {
        socketTcp.close();
      }
      if (socketUdp != null) {
        socketUdp.close();
      }
    }
  }
}
