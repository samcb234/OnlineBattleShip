import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import javax.swing.JOptionPane;
import secondController.Client;
import secondController.Host;

public class Main {

  public static void main(String[] args) throws IOException {
    if(args.length != 1){
      System.out.println("please select either host or client");
    }
    switch(args[0]){
      case "host":
        JOptionPane.showMessageDialog(null, InetAddress.getLocalHost().getHostAddress(),
            "", JOptionPane.PLAIN_MESSAGE);
        new Host();
        break;
      case "client":
        new Client(JOptionPane.showInputDialog(null,"please enter address of host"));
        break;
      default:
        System.out.println("please select either host or client");
    }
  }
}
