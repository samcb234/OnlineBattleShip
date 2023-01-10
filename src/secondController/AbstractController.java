package secondController;

import View.BattleShipView;
import View.ViewImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import model.Board;

public abstract class AbstractController{

  protected Socket s;
  protected boolean turn, r1, r2;
  protected DataInputStream dis;
  protected DataOutputStream dos;
  protected Scanner sc = new Scanner(System.in);
  protected boolean read, write;
  protected boolean phase;
  protected BattleShipView view;
  protected MessageListener m;
  protected String args;
  protected GUIListener g;

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

  protected AbstractController(Socket s, String name) throws IOException{
    this.read = true;
    this.s = s;
    this.r1 = false;
    this.r2 = false;
    this.phase = false;
    this.dis = new DataInputStream(s.getInputStream());
    this.dos = new DataOutputStream(s.getOutputStream());
    this.m = new MessageListener();
    this.g = new GUIListener();
    setStandardCommands();
    r.start();
  }

  protected void sendData(String data){
    try{
      dos.writeUTF(data);
    }
    catch(IOException e){
      e.printStackTrace();
    }
    System.out.println("sent");
    return;
  }

  protected abstract void setStandardCommands();

  protected abstract void updateView(Board b);

  protected abstract void clearView();


}
