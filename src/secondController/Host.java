package secondController;

import View.ViewImpl;
import controller.command.BattleshipCommand;
import controller.command.DestroyerCommand;
import controller.command.ShootCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.Board;
import model.Board.space;
import model.BoardImpl;

public class Host extends AbstractController implements ActionListener {

  private ServerSocket ss;
  private Board p1, p2;

  public Host() throws IOException {
    super(new ServerSocket(1234).accept(), "Host");
    this.view = new ViewImpl("Host", this);
    view.updateMouseListener(g);
    System.out.println("enter board size, number of battle ships, and number of destroyers");
    int size = sc.nextInt();
    int b = sc.nextInt();
    int d = sc.nextInt();
    this.p1 = new BoardImpl(size, b, d);
    this.p2 = new BoardImpl(size, b, d);
    this.turn = false;

    sendData("set#"+size);
    updateView(p1);
  }

  @Override
  protected void setStandardCommands() {
    m.addCommand("shoot", () ->{
      if(turn){
        return;
      }
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      new ShootCommand(r, c).apply(p1);
      if(p1.getSpaceAt(r, c) == space.Hit){
        sendData("hit#" + r + "," + c);
      }
      else{
        sendData("miss#" + r + "," + c);
      }
      this.turn = true;
    });
    m.addCommand("des", () ->{
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      new DestroyerCommand(r, c).apply(p2);
      sendData("des#" + r + "," +c);
    });
    m.addCommand("bat", () ->{
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      new BattleshipCommand(r, c, true).apply(p2);
      sendData("bat#" + r + "," +c);
    });
    m.addCommand("ready", () -> {
      this.r2 = true;
      if(r1 && r2){
        this.phase = true;
        g.updateRunnable(() ->{
          int[] i = g.getCoord();
          p2.shootAtSpace(i[1], i[0]);
          if(p2.getSpaceAt(i[1], i[0]) == space.Hit) {
            view.updateSpace(i[1], i[0], space.Hit);
          }else{
            view.updateSpace(i[1], i[0], space.Miss);
          }
        });
        clearView();
      }
    });

    g.updateRunnable(() -> {
      int[] i = g.getCoord();
      switch (view.getShipVal()){
        case "BattleShip":
          p1.placeBattleShip(i[1], i[0], true); //THIS NEEDS TO CHANGE LATER!!!!
          break;
        case "Destroyer":
          p1.placeDestroyer(i[1], i[0]);
          break;
      }
      updateView(p1);
    });
  }

  @Override
  protected void updateView(Board b) {
    for(int i = 0; i < p1.boardSize(); i ++) {
      for (int j = 0; j < p1.boardSize(); j ++) {
        view.updateSpace(i, j, p1.getSpaceAt(i, j));
      }
    }
  }

  @Override
  protected void clearView() {
    for(int i = 0; i < p1.boardSize(); i ++) {
      for (int j = 0; j < p1.boardSize(); j ++) {
        view.updateSpace(i, j, space.Empty);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("pressed");
    this.r1 = true;
    if(r1 && r2){
      this.phase = true;
      g.updateRunnable(() ->{
        int[] i = g.getCoord();
        p2.shootAtSpace(i[1], i[0]);
        if(p2.getSpaceAt(i[1], i[0]) == space.Hit) {
          view.updateSpace(i[1], i[0], space.Hit);
        }else{
          view.updateSpace(i[1], i[0], space.Miss);
        }
      });
      clearView();
    }
  }

  public static void main(String[] args) throws IOException{
    new Host();
  }
}
