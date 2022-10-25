package model;

/**
 * represents the board that a player uses to place their ships
 * and shoot at the other player's ships
 */
public interface Board {

  /**
   * checks if every boat is sunk
   * @return true if every boat is sunk
   */
  boolean allSunk();

  enum space {Empty, Ship, Hit, Miss}

  /**
   * returns a space on a player's board
   * @param row the row that the space is on
   * @param col the column that the space is on
   * @return the selected column
   * @throws IllegalArgumentException if the given coordinate (row, col) is outside of the
   * board area.
   */
  space getSpaceAt(int row, int col) throws IllegalArgumentException;

  /**
   * changes an empty space to a miss or a ship space to a hit
   * @param row the row containing the space being shot at
   * @param col the column containing the space being shot at
   * @return true if a ship space is hit, false if it is missed
   * @throws IllegalArgumentException if the space being shot at is outside of the board,
   * or if the space has already been shot at
   */
  boolean shootAtSpace(int row, int col) throws IllegalArgumentException;

  /**
   * places a new destroyer ship (one space) on the board
   * @param row the row that the center of the sip is on
   * @param col the column that the center of the ship is on
   * @throws IllegalArgumentException if any part of the ship is out of bounds,
   * or if a part of it is being placed on an existing ship space
   */
  void placeDestroyer(int row, int col) throws IllegalArgumentException;

  /**
   * places a new battleship (three spaces) on the board
   * @param row the row containing the center space of the battleship
   * @param col the column containing the center space of the battleship
   * @param orientation boolean representing the orientation of the ship
   *                    true if ship is vertical, false if it is horizontal
   * @throws IllegalArgumentException if any part of the ship is placed outside the board,
   * or of any space of the ship is placed ontop of an existing ship
   */
  void placeBattleShip(int row, int col, boolean orientation) throws IllegalArgumentException;

  /**
   * resets the game board, allowing the size and the number of ships to be changed
   * @param size the length of an edge of the square board
   * @param destroyers the number of destroyers to be placed on the new board
   * @param battleships the number of battleships to be placed on the new board
   * @throws IllegalArgumentException if any of the fields are negative, or if no ships are
   * to be added
   */
  void restart(int size, int destroyers, int battleships) throws IllegalArgumentException;

  /**
   * gets the size of the board
   * @return the size of the board as an integer
   */
  int boardSize();

  /**
   * gets the number of remaining destroyers
   * @return the number of remaining destroyers
   */
  int numDesRemaining();

  /**
   * gets the number of remaining battleships
   * @return the number of remaining battleships
   */
  int numBattleShipsRemaining();
}
