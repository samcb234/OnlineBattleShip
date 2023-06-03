package secondController;

import View.ViewImpl;
import controller.command.BattleshipCommand;
import controller.command.DestroyerCommand;
import controller.command.ShootCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
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
    int size = view.getGameVals("enter the size of the board");
    int b = view.getGameVals("enter the number of battleships");
    int d = view.getGameVals("enter the number of destroyers");
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
      try {
        new ShootCommand(r, c).apply(p1);
        if (p1.getSpaceAt(r, c) == space.Hit) {
          sendData("hit#" + r + "," + c);
        } else {
          sendData("miss#" + r + "," + c);
        }
        this.turn = true;
        if(p1.allSunk()) {
          sendData("gameover#");
          view.displayMessage("Game Over!");
        }
      }
      catch (IllegalArgumentException e){
        sendData("error#"+e.getMessage());
        if(e.getMessage().equals("you can't shoot at the same spot twice")){
          this.turn = false;
        }
      }
    });
    m.addCommand("des", () ->{
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      try {
        new DestroyerCommand(r, c).apply(p2);
        sendData("des#" + r + "," + c);
      }
      catch (IllegalArgumentException e) {
        sendData("error#" + e.getMessage());
      }
    });
    m.addCommand("bat", () ->{
      String[] i = args.split(",");
      int r = Integer.parseInt(i[0]);
      int c = Integer.parseInt(i[1]);
      try {
        new BattleshipCommand(r, c, true).apply(p2);
        sendData("bat#" + r + "," + c);
      }
      catch (IllegalArgumentException e) {
        sendData("error#" + e.getMessage());
      }
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
      try {
        switch (view.getShipVal()) {
          case "BattleShip":
            p1.placeBattleShip(i[1], i[0], true); //THIS NEEDS TO CHANGE LATER!!!!
            break;
          case "Destroyer":
            p1.placeDestroyer(i[1], i[0]);
            break;
        }
        updateView(p1);
      }
      catch (IllegalArgumentException e) {
        view.displayMessage(e.getMessage());
      }
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
    sendData("ready#,");
    this.r1 = true;
    if(r1 && r2){
      this.phase = true;
      this.turn = false;
      g.updateRunnable(() ->{
        try {
          if (turn) {
            int[] i = g.getCoord();
            p2.shootAtSpace(i[1], i[0]);
            if (p2.getSpaceAt(i[1], i[0]) == space.Hit) {
              view.updateSpace(i[1], i[0], space.Hit);
            } else {
              view.updateSpace(i[1], i[0], space.Miss);
            }
          }
          this.turn = false;
        }
        catch (IllegalArgumentException k){
          view.displayMessage(k.getMessage());
        }

        if (p2.allSunk()){
          sendData("gameover#");
          view.displayMessage("Game Over!");
        }
      });
      clearView();
    }
  }

  public static void main(String[] args) throws IOException{
    new Host();
  }
}
