import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import javax.swing.JOptionPane;
import secondController.Client;
import secondController.Host;

public class Main {

  public static void main(String[] args) throws IOException {
    String[] inputs = {"host", "client"};
    int role = JOptionPane.showOptionDialog(null, "select your role",
        "select one", 0, 3, null, inputs, inputs[0]);
    switch(role){
      case 0:
        JOptionPane.showMessageDialog(null, InetAddress.getLocalHost().getHostAddress(),
            "", JOptionPane.PLAIN_MESSAGE);
        new Host();
        break;
      case 1:
        new Client(JOptionPane.showInputDialog(null,"please enter address of host"));
        break;
      default:
        System.out.println("please select either host or client");
    }
  }
}
