package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Board.space;

public class ViewImpl extends JFrame implements BattleShipView {

  private JComboBox<String> b;
  private JPanel p;
  private JButton ready;

  public ViewImpl(String name, ActionListener a){
    setSize(1920, 1080);
    setName(name);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    b = new JComboBox<String>();
    b.setPrototypeDisplayValue("select ship to place");
    b.addItem("BattleShip");
    b.addItem("Destroyer");
    ready = new JButton("ready");
    ready.addActionListener(a);
    ready.setPreferredSize(new Dimension(100, 25));
    p = new JPanel();
    p.setBounds(800, 800, 100, 100);
    b.setBounds(800, 800, 100, 100);
    b.setPreferredSize(new Dimension(100, 25));
    p.add(b);
    p.add(ready);
    this.add(p);
    setVisible(true);
  }



  @Override
  public void updateMouseListener(MouseListener a) {
    this.addMouseListener(a);
  }

  @Override
  public void updateSpace(int row, int col, space s) throws IllegalArgumentException {
    paint(this.getGraphics(), row, col, s);
  }

  @Override
  public void displayMessage(String s) {
    JOptionPane.showMessageDialog(null, s, "", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public boolean rotate() {
    return false;
  }

  public void paint(Graphics g, int row, int col, space s){
    Color c = Color.BLUE;
    switch (s){
      case Hit:
        c = Color.RED;
        break;
      case Miss:
        c = Color.WHITE;
        break;
      case Ship:
        c = Color.GRAY;
        break;
      case Empty:
        c = Color.BLUE;
        break;
    }
    g.setColor(c);
    g.fillRect(200 + (col * 60), 200 + (row * 60), 60, 60);
    g.setColor(Color.BLACK);
    g.drawRect(200 + (col * 60), 200 + (row * 60), 60, 60);
  }

  @Override
  public String getShipVal(){
    return (String) b.getSelectedItem();
  }

  @Override
  public int getGameVals(String message) {
    int val =  Integer.parseInt(JOptionPane.showInputDialog(null,message));
    return val;
  }
}
