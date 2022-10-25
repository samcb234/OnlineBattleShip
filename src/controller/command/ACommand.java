package controller.command;

import model.Board;

public abstract class ACommand {

  protected final int row, col;

  protected ACommand(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public abstract void apply(Board b)throws IllegalArgumentException;
}
