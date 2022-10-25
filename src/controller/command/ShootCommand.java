package controller.command;

import model.Board;

public class ShootCommand extends ACommand{

  public ShootCommand(int row, int col) {
    super(row, col);
  }

  @Override
  public void apply(Board b) {
    b.shootAtSpace(row, col);
  }
}
