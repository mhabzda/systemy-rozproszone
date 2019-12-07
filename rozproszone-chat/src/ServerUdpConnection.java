import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerUdpConnection extends Thread {
  private final List<ClientUdpData> clients = new ArrayList<>();
  private final DatagramSocket serverSocketUdp;
  private final byte[] receiveBuffer;

  public ServerUdpConnection(int portNumber) throws SocketException {
    serverSocketUdp = new DatagramSocket(portNumber);
    receiveBuffer = new byte[1024];
  }

  @Override
  public void run() {
    super.run();
    int clientNumber = 0;
    int currentClientNumber;

    try {
      while (true) {
        Arrays.fill(receiveBuffer, (byte) 0);
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        String message = getMessage(receivePacket);

        if (clientDoesNotExists(receivePacket.getPort())) {
          ClientUdpData clientUdpData = new ClientUdpData(receivePacket.getAddress(), receivePacket.getPort(), clientNumber);
          clients.add(clientUdpData);
          currentClientNumber = clientNumber;
          clientNumber++;
        } else {
          currentClientNumber = findClientByPortNumber(receivePacket.getPort());
        }

        if (!message.contains(ClientUdp.CLIENT_HELLO)) {
          sendMessageToClients(message, currentClientNumber);
        }
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    } finally {
      serverSocketUdp.close();
    }
  }

  private String getMessage(DatagramPacket receivePacket) throws IOException {
    serverSocketUdp.receive(receivePacket);
    String msg = new String(receivePacket.getData());
    System.out.println("received msg: " + msg);
    return msg;
  }

  private boolean clientDoesNotExists(int portNumber) {
    for (ClientUdpData clientUdpData : clients) {
      if (clientUdpData.getPortNumber() == portNumber) {
        return false;
      }
    }
    return true;
  }

  private int findClientByPortNumber(int portNumber) {
    for (ClientUdpData clientUdpData : clients) {
      if (clientUdpData.getPortNumber() == portNumber) {
        return clientUdpData.getId();
      }
    }
    throw new IllegalArgumentException();
  }

  private void sendMessageToClients(String msg, int excludedClientId) throws IOException {
    String message = String.format("client id %d: %s", excludedClientId, msg);
    byte[] sendBuffer = message.getBytes();
    for (ClientUdpData client : clients) {
      if (client.getId() != excludedClientId) {
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, client.getAddress(), client.getPortNumber());
        serverSocketUdp.send(sendPacket);
      }
    }
  }
}
