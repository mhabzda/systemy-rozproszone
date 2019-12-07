import java.net.InetAddress;

public class ClientUdpData {
  private final InetAddress address;
  private final int portNumber;
  private final int id;

  public ClientUdpData(InetAddress address, int portNumber, int id) {
    this.address = address;
    this.portNumber = portNumber;
    this.id = id;
  }

  public InetAddress getAddress() {
    return address;
  }

  public int getPortNumber() {
    return portNumber;
  }

  public int getId() {
    return id;
  }
}
