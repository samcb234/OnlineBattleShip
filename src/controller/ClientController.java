package controller;


import View.ViewImpl;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import model.BoardImpl;

public class ClientController extends Communicator{

  Scanner sc = new Scanner(System.in);
  InetAddress ip;

  public ClientController()throws IOException, UnknownHostException {
    super(new Socket(InetAddress.getByName("localhost"), 1234));
    this.ip = InetAddress.getByName("localhost");
    this.turn = true;
    m.addCommand("set", ()->{
      String[] i = args.split(",");
      this.friend = new BoardImpl(Integer.parseInt(i[0]), Integer.parseInt(i[1]), Integer.parseInt(i[2]));
      this.foe = new BoardImpl(Integer.parseInt(i[0]), Integer.parseInt(i[1]), Integer.parseInt(i[2]));
      displayBoard(friend);
    });
    view = new ViewImpl("client", this);
    view.updateMouseListener(g);
  }

  public static void main(String[] args) throws IOException{
    new ClientController();
  }
}
