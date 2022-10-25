package View;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import model.Board.space;

public interface BattleShipView {

  void updateMouseListener(MouseListener a);


  void updateSpace(int row, int col, space s) throws IllegalArgumentException;

  void displayMessage(String s);

  boolean rotate();

  String getShipVal();
}
