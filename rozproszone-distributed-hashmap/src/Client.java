import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    try {
      DistributedMap map = new DistributedMap();

      String in;
      Scanner scan = new Scanner(System.in);
      while (true) {
        in = scan.nextLine();
        String[] input = in.split(" ");
        switch (input[0]) {
          case "get":
            System.out.println(map.get(input[1]));
            break;
          case "put":
            System.out.println(map.put(input[1], input[2]));
            break;
          case "remove":
            System.out.println(map.remove(input[1]));
            break;
          case "containsKey":
            System.out.println(map.containsKey(input[1]));
            break;
          case "mem":
            System.out.println(map.getChannel().getView().getMembers());
            break;
        }
        System.out.println(map.getHashMap());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
