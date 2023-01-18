package model;

import java.util.ArrayList;

/**
 * represents a board for a single player in a game of battleship
 */
public class BoardImpl implements Board {

  private space[][] board;
  private int destroyers;
  private int battleships;

  private ArrayList<Battleship> battleshipsHealth;
  private ArrayList<Destroyer> destroyerHealth;

  public BoardImpl(int size, int destroyers, int battleships) throws IllegalArgumentException {
    restart(size, destroyers, battleships);
  }

  @Override
  public boolean allSunk() {
    int n = 0;
    for(space[] i : board){
      for(space j : i){
        if(j.equals(space.Hit)){
          n += 1;
        }
      }
    }
    return n == destroyers + (3 * battleships);
  }

  @Override
  public space getSpaceAt(int row, int col) throws IllegalArgumentException {
    validSpaceCoords(row, col);
    return board[row][col];
  }

  @Override
  public boolean shootAtSpace(int row, int col) throws IllegalArgumentException {
    validSpaceCoords(row, col);
    if(board[row][col].equals(space.Hit) || board[row][col].equals(space.Miss)){
      throw new IllegalArgumentException("you can't shoot at the same spot twice");
    }

    if(board[row][col].equals(space.Empty)){
      board[row][col] = space.Miss;
      return false;
    }
    else{
      board[row][col] = space.Hit;
      return true;
    }
  }

  @Override
  public void placeDestroyer(int row, int col) throws IllegalArgumentException {
    validSpaceCoords(row, col);
    if(!board[row][col].equals(space.Empty)){
      throw new IllegalArgumentException("ship can't be placed there!");
    }
    if(destroyerHealth.size() >= destroyers){
      throw new IllegalArgumentException("destroyer limit reached");
    }
    board[row][col] = space.Ship;
    destroyerHealth.add(new Destroyer(row, col));
  }

  @Override
  public void placeBattleShip(int row, int col, boolean orientation) throws IllegalArgumentException {
    if(battleshipsHealth.size() >= battleships) {
      throw new IllegalArgumentException("battleship limit reached");
    }
    int[] rows;
    int[]cols;
    if(orientation){
      rows = new int[] {row-1, row, row + 1};
      cols = new int[]{col, col, col};
    }
    else{
      rows = new int[]{row, row, row};
      cols = new int[] {col - 1, col, col + 1};
    }
    battleshipPlacementHelper(rows, cols);
  }

  @Override
  public void restart(int size, int destroyers, int battleships) throws IllegalArgumentException {
    if(size <= 3 || destroyers < 0 || battleships < 0 || destroyers + battleships < 1) {
      throw new IllegalArgumentException("please enter valid arguments");
    }
    this.board = new space[size][size];
    for(space[] i : board){
      for(int j = 0; j < i.length; j ++) {
        i[j] = space.Empty;
      }
    }
    this.destroyers = destroyers;
    this.battleships = battleships;
    battleshipsHealth = new ArrayList<>();
    destroyerHealth = new ArrayList<>();
  }

  @Override
  public int boardSize() {
    return board.length;
  }

  @Override
  public int numDesRemaining() {
    int i = 0;
    for(Destroyer d : destroyerHealth){
      if(d.sunk()){
        i += 1;
      }
    }
    return destroyers - i;
  }

  @Override
  public int numBattleShipsRemaining() {
    int i = 0;
    for(Battleship d : battleshipsHealth){
      if(d.sunk()){
        i += 1;
      }
    }
    return battleships - i;
  }

  private void validSpaceCoords(int row, int col) throws IllegalArgumentException{
    if(row < 0 || col < 0 || row >= board.length || col >= board.length) {
      throw new IllegalArgumentException("please enter a valid space");
    }
    return;
  }

  private void battleshipPlacementHelper(int[] rows, int[] cols) throws IllegalArgumentException{
    for(int i = 0; i < 3; i ++) {
      validSpaceCoords(rows[i], cols[i]);
    }

    for(int i = 0; i < 3; i++) {
      if (!getSpaceAt(rows[i], cols[i]).equals(space.Empty)){
        throw new IllegalArgumentException("can't place ship here");
      }
      board[rows[i]][cols[i]] = space.Ship;
    }
    battleshipsHealth.add(new Battleship(rows, cols));
  }

  private class Battleship{
    private final int[] rowCoords;
    private final int[] colCoords;

    private Battleship(int[] rowCoords, int[] colCoords) {
      this.rowCoords = rowCoords;
      this.colCoords = colCoords;
    }

    protected boolean sunk(){
      boolean sunk = true;
      for(int i = 0; i < 3; i ++){
        sunk = sunk && board[rowCoords[i]][colCoords[i]].equals(space.Hit);
      }
      return sunk;
    }
  }

  private class Destroyer{
    private final int row;
    private final int col;

    private Destroyer(int row, int col) {
      this.row = row;
      this.col = col;
    }

    protected boolean sunk(){
      return board[row][col].equals(space.Hit);
    }
  }
}
