package secondController;

import View.ViewImpl;
import controller.command.BattleshipCommand;
import controller.command.DestroyerCommand;
import controller.command.ShootCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import model.Board;
import model.Board.space;
import model.BoardImpl;

public class Client extends AbstractController implements ActionListener {

  InetAddress ip;
  protected int size;
  public Client() throws IOException {
    super(new Socket(InetAddress.getByName("localhost"), 1234), "Client");
    this.ip = InetAddress.getByName("localhost");
    this.turn = true;
    this.view = new ViewImpl("Client", this);
    view.updateMouseListener(g);
  }

  @Override
  protected void setStandardCommands() {
    m.addCommand("set", () ->{
      System.out.println(args);
      System.out.println(size);
      this.size = Integer.parseInt(args);
      clearView();
    });
    m.addCommand("des", () ->{
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      view.updateSpace(r, c, space.Ship);
    });
    m.addCommand("bat", () ->{
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      view.updateSpace(r - 1, c, space.Ship);
      view.updateSpace(r + 1, c, space.Ship);
      view.updateSpace(r, c, space.Ship);
    });
    m.addCommand("ready", () -> {
      this.r2 = true;
      if(r1 && r2){
        this.phase = true;
        g.updateRunnable(() ->{
          int[] i = g.getCoord();
          sendData("shoot#"+i[1]+","+i[0]);
        });
        clearView();
      }
    });

    g.updateRunnable(() -> {
      int[] i = g.getCoord();
      switch (view.getShipVal()){
        case "BattleShip":
          sendData("bat#"+i[1]+","+i[0]); //THIS NEEDS TO CHANGE LATER!!!!
          break;
        case "Destroyer":
          sendData("des#"+i[1]+","+i[0]);
          break;
      }
    });
  }

  @Override
  protected void updateView(Board b) {
    return;
  }

  @Override
  protected void clearView() {
    for(int i = 0; i < this.size; i ++) {
      for (int j = 0; j < this.size; j ++) {
        view.updateSpace(i, j, space.Empty);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.r1 = true;
    sendData("ready#");
    if(r1 && r2){
      this.phase = true;
      g.updateRunnable(() ->{
        int[] i = g.getCoord();
        sendData("shoot#"+i[1]+","+i[0]);
      });
      clearView();
    }
  }

  public static void main(String[] args) throws IOException{
    new Client();
  }
}
