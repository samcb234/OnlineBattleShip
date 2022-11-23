package controller;

import View.ViewImpl;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import model.BoardImpl;

public class ServerController extends Communicator{

  private ServerSocket ss;
  Scanner sc = new Scanner(System.in);
  public ServerController() throws IOException {
    super(new ServerSocket(1234).accept());
    System.out.println("connected");
    view = new ViewImpl("server", this);
    view.updateMouseListener(g);
    int size = sc.nextInt();
    int des = sc.nextInt();
    int bat = sc.nextInt();
    this.friend = new BoardImpl(size, des, bat);
    this.foe = new BoardImpl(size, des, bat);
    displayBoard(friend);
    sendData("set#" + size + "," + des + "," + bat);
    this.turn = false;
    System.out.println("done");
  }

  public static void main(String[] args) throws IOException{
    new ServerController();
  }
}
