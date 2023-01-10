import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import secondController.Client;
import secondController.Host;

public class Main {

  public static void main(String[] args) throws IOException {
    if(args.length != 1){
      System.out.println("please select either host or client");
    }
    switch(args[0]){
      case "host":
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        new Host();
        break;
      case "client":
        System.out.println("please enter address of host");
        Scanner t = new Scanner(System.in);
        new Client(t.next());
        break;
      default:
        System.out.println("please select either host or client");
    }
  }
}
