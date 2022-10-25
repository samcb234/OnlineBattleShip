package controller.command;

import model.Board;

public class BattleshipCommand extends ACommand{

  private final boolean orientation;
  public BattleshipCommand(int row, int col, boolean orientation) {
    super(row, col);
    this.orientation = orientation;
  }

  @Override
  public void apply(Board b) {
    b.placeBattleShip(row, col, orientation);
  }
}
