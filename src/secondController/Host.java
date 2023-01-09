package secondController;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.Board;
import model.BoardImpl;

public class Host extends AbstractController{

  private ServerSocket ss;
  private Board p1, p2;

  protected Host() throws IOException {
    super(new ServerSocket(1234).accept(), "Host");
    System.out.println("enter board size, number of battle ships, and number of destroyers");
    int size = sc.nextInt();
    int b = sc.nextInt();
    int d = sc.nextInt();
    this.p1 = new BoardImpl(size, b, d);
    this.p2 = new BoardImpl(size, b, d);

  }

  @Override
  protected void setStandardCommands() {

  }

  @Override
  protected void updateView() {

  }

  @Override
  protected void clearView() {

  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
