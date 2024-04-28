package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  public int getWidth() {
    if (board != null && board.length > 0) {
      return board[0].length;
    } else {
      return 0;
    }
  }

  public int getHeight() {
    if (board != null) {
      return board.length;
    } else {
      return 0;
    }
  }

  public CellType getCellType(int r, int c) {
    if (r > board.length || c > board[0].length) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }

    int boardVal = board[r][c];
    if (boardVal <= 4) {
      return CellType.CLUE;
    } else if (boardVal == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  public int getClue(int r, int c) {
    if (r > board.length || c > board[0].length) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell must be of type clue.");
    }
    return board[r][c];
  }
}
