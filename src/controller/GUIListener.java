package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import model.Board;

public class GUIListener implements MouseListener {

  private int[] coord;
  private Runnable r;

  public GUIListener(){
  }

  public void updateRunnable(Runnable r) throws IllegalArgumentException{
    this.r = r;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    coord = new int[]{(e.getX() - 200) / 60, (e.getY() - 200) / 60};
    r.run();
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  public int[] getCoord(){
    return coord;
  }
}
