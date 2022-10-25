package controller.command;

import model.Board;

public class DestroyerCommand extends ACommand{

  public DestroyerCommand(int row, int col) {
    super(row, col);
  }

  @Override
  public void apply(Board b) {
    b.placeDestroyer(row, col);
  }
}
