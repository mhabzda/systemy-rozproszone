import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientUdp extends Thread {
  public static final String CLIENT_HELLO = "CliHello";
  private static final String MULTICAST_ADDRESS = "230.0.0.0";
  private static final int MULTICAST_PORT_NUMBER = 4446;

  private final DatagramSocket datagramSocket;
  private final MulticastSocket multicastSocket;
  private final List<String> unicastQueue = new ArrayList<>();
  private final List<String> multicastQueue = new ArrayList<>();

  public ClientUdp(DatagramSocket datagramSocket) throws IOException {
    this.datagramSocket = datagramSocket;
    multicastSocket = new MulticastSocket(MULTICAST_PORT_NUMBER);
    InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
    multicastSocket.joinGroup(group);
    multicastSocket.setSoTimeout(500);
  }

  @Override
  public void run() {
    super.run();
    String hostName = "localhost";
    int portNumber = 12345;

    try {
      InetAddress address = InetAddress.getByName(hostName);
      byte[] receiveBuffer = new byte[1024];
      DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

      sendHelloToServer(address, portNumber);
      while (true) {
        if (!unicastQueue.isEmpty()) {
          sendDatagram(address, portNumber, ASCIIArt.ASCII_ART);
          unicastQueue.clear();
        }
        if (!multicastQueue.isEmpty()) {
          sendMulticastDatagram();
          multicastQueue.clear();
        }

        Arrays.fill(receiveBuffer, (byte) 0);

        receiveMessageIfPossible(datagramSocket, receivePacket, receiveBuffer.length);
        receiveMessageIfPossible(multicastSocket, receivePacket, receiveBuffer.length);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void putElementToUnicastQueue(String msg) {
    unicastQueue.add(msg);
  }

  public void putElementToMulticastQueue(String msg) {
    multicastQueue.add(msg);
  }

  private void sendHelloToServer(InetAddress address, int portNumber) throws IOException {
    sendDatagram(address, portNumber, CLIENT_HELLO);
  }

  private void sendDatagram(InetAddress address, int portNumber, String datagram) throws IOException {
    byte[] sendBuffer = datagram.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
    datagramSocket.send(sendPacket);
  }

  private void sendMulticastDatagram() throws IOException {
    byte[] sendBuffer = ASCIIArt.ASCII_ART.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(MULTICAST_ADDRESS),
            MULTICAST_PORT_NUMBER);
    datagramSocket.send(sendPacket);
  }

  private void receiveMessageIfPossible(DatagramSocket socket, DatagramPacket receivePacket, int bufferLength)
          throws IOException {
    boolean isUdpDatagramReceived = true;
    try {
      socket.receive(receivePacket);
    } catch (SocketTimeoutException exception) {
      isUdpDatagramReceived = false;
    }
    if (isUdpDatagramReceived) {
      String msg = new String(receivePacket.getData());
      System.out.println(msg);
      receivePacket.setLength(bufferLength);
    }
  }
}
