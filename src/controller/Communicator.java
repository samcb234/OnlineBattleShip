package controller;

import View.BattleShipView;
import View.ViewImpl;
import controller.command.ACommand;
import controller.command.BattleshipCommand;
import controller.command.DestroyerCommand;
import controller.command.ShootCommand;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import model.Board;

public abstract class Communicator {

  protected Socket s;
  protected DataInputStream dis;
  protected DataOutputStream dos;
  protected Scanner sc = new Scanner(System.in);
  protected Boolean read, write;
  protected Board friend, foe;
  protected boolean turn; //true = friend, false = foe
  private boolean phase; //true = attacking, false = placing ships
  protected BattleShipView view;
  protected MessageListener m;
  protected String args;
  protected GUIListener g;

  protected Communicator(Socket s) throws IOException{
    this.s = s;
    this.dis = new DataInputStream(s.getInputStream());
    this.dos = new DataOutputStream(s.getOutputStream());
    this.read = true;
    this.write = true;
    this.m = new MessageListener();
    this.g = new GUIListener();
    setStandardCommands();
    this.phase = false;
    r.start();
  }

  protected Thread r = new Thread(new Runnable() {
    @Override
    public void run() {
      while (read) {
        try {
          String recieved = dis.readUTF();
          String[] i = recieved.split("#");
          args = i[1];
          m.runCommand(i[0]);
          System.out.println(recieved);
        }
        catch(EOFException i){
          read = false;
          write = false;
          break;
        }
        catch (IOException e) {
          System.out.println("deez nuts");
        }
      }
    }
  });

  private Board findRightBoard(){
    if(turn){
      return friend;
    }
    else return foe;
  }
  private void switchSides(){
    turn = !turn;
  }
  protected void sendData(String data){
    try{
      dos.writeUTF(data);
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }
  private void setStandardCommands(){
    m.addCommand("shoot", () ->{
      String[] i = args.split(",");
      new ShootCommand(Integer.parseInt(i[0]), Integer.parseInt(i[1])).apply(friend);
      switchSides();
    });
    m.addCommand("des", () ->{
      String[] i = args.split(",");
      new DestroyerCommand(Integer.parseInt(i[0]), Integer.parseInt(i[1])).apply(foe);
      switchSides();
    });
    m.addCommand("bat", () ->{
      String[] i = args.split(",");
      new BattleshipCommand(Integer.parseInt(i[0]), Integer.parseInt(i[1]), Boolean.parseBoolean(i[2])).apply(foe);
      switchSides();
    });

    g.updateRunnable(() -> {
      int[] i = g.getCoord();
      switch (view.getShipVal()){
        case "BattleShip":
          friend.placeBattleShip(i[1], i[0], true); //THIS NEEDS TO CHANGE LATER!!!!
          sendData("bat#" + i[1] + "," + i[0]);
          break;
        case "Destroyer":
          friend.placeDestroyer(i[1], i[0]);
          sendData("des#" + i[1] + "," + i[0]);
          break;
      }
      displayBoard(friend);
    });
  }
  protected void displayBoard(Board b){
    for(int i = 0; i < b.boardSize(); i ++){
      for(int j = 0; j < b.boardSize(); j ++) {
        view.updateSpace(i, j, b.getSpaceAt(i, j));
      }
    }
  }
}
