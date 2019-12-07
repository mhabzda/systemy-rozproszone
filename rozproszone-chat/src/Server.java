import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  private static List<PrintWriter> printWriters = new ArrayList<>();
  private static List<Thread> threads = new ArrayList<>();

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("JAVA TCP/UDP SERVER");
    int portNumber = 12345;
    ServerSocket serverSocketTcp = null;
    int clientNumber = 0;

    try {
      Thread thread = new ServerUdpConnection(portNumber);
      threads.add(thread);
      thread.start();

      serverSocketTcp = new ServerSocket(portNumber);
      while (true) {
        Socket clientSocket = acceptClient(serverSocketTcp);

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        printWriters.add(out);

        thread = new TcpConnectionOnServer(in, clientNumber);
        thread.start();
        clientNumber++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (serverSocketTcp != null) {
        for (Thread thread : threads) {
          thread.join();
        }
        serverSocketTcp.close();
      }
    }
  }

  private static Socket acceptClient(ServerSocket serverSocketTcp) throws IOException {
    Socket clientSocket = serverSocketTcp.accept();
    System.out.println("client connected");
    return clientSocket;
  }

  public synchronized static void sendMessageToClientsByTcp(String msg, int id) {
    for (int i = 0; i < printWriters.size(); i++) {
      if (i != id) {
        printWriters.get(i).println(msg);
      }
    }
  }
}