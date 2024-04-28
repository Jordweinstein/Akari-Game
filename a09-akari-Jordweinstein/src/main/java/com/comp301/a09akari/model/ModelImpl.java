package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private int activePuzzleIndex;
  private int[][] lampPlacements; // 1 means lit, 0 means not lit
  List<ModelObserver> activeObservers;
  private Puzzle activePuzzle;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException("Enter valid input.");
    }
    this.library = library;
    this.activePuzzleIndex = 0;
    this.activePuzzle = library.getPuzzle(activePuzzleIndex);
    activeObservers = new ArrayList<>();
    lampPlacements = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    this.resetPuzzle();
  }

  public void addLamp(int r, int c) {
    checkIndexBounds(r, c, true);
    if (!isLamp(r, c)) {
      lampPlacements[r][c] = 1;
      notifyObservers();
    }
  }

  public void removeLamp(int r, int c) {
    checkIndexBounds(r, c, true);
    if (lampPlacements[r][c] == 0) {
      throw new IllegalArgumentException("There is no lamp here");
    }
    lampPlacements[r][c] = 0;
    notifyObservers();
  }

  public boolean isLit(int r, int c) {
    checkIndexBounds(r, c, true);
    if (isLamp(r, c)) return true;

    // check horizontally for a lamp
    for (int i = c - 1; i >= 0; i--) {
      if (library.getPuzzle(activePuzzleIndex).getCellType(r, i) != CellType.CORRIDOR) break;
      if (isLamp(r, i)) return true;
    }
    for (int i = c + 1; i < library.getPuzzle(activePuzzleIndex).getWidth(); i++) {
      if (library.getPuzzle(activePuzzleIndex).getCellType(r, i) != CellType.CORRIDOR) break;
      if (isLamp(r, i)) return true;
    }

    // Check vertically for a lamp
    for (int i = r - 1; i >= 0; i--) {
      if (library.getPuzzle(activePuzzleIndex).getCellType(i, c) != CellType.CORRIDOR) break;
      if (isLamp(i, c)) return true;
    }
    for (int i = r + 1; i < library.getPuzzle(activePuzzleIndex).getHeight(); i++) {
      if (library.getPuzzle(activePuzzleIndex).getCellType(i, c) != CellType.CORRIDOR) break;
      if (isLamp(i, c)) return true;
    }

    return false;
  }

  public boolean isLamp(int r, int c) {
    checkIndexBounds(r, c, true);
    return lampPlacements[r][c] == 1;
  }

  public boolean isLampIllegal(int r, int c) {
    checkIndexBounds(r, c, false);
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("This cell does not currently contain a lamp");
    }

    int width = activePuzzle.getWidth();
    int height = activePuzzle.getHeight();

    // left
    for (int i = c - 1; i >= 0; i--) {
      if (!isCorridor(r, i)) break;
      if (isLamp(r, i)) return true;
    }
    // right
    for (int i = c + 1; i < width; i++) {
      if (!isCorridor(r, i)) break;
      if (isLamp(r, i)) return true;
    }

    // up
    for (int i = r - 1; i >= 0; i--) {
      if (!isCorridor(i, c)) break;
      if (isLamp(i, c)) return true;
    }
    // down
    for (int i = r + 1; i < height; i++) {
      if (!isCorridor(i, c)) break;
      if (isLamp(i, c)) return true;
    }

    return false;
  }

  private boolean isCorridor(int r, int c) {
    return activePuzzle.getCellType(r, c) == CellType.CORRIDOR;
  }

  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activePuzzleIndex);
  }

  public int getActivePuzzleIndex() {
    return this.activePuzzleIndex;
  }

  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    this.activePuzzleIndex = index;
    activePuzzle = library.getPuzzle(activePuzzleIndex);
    resetPuzzle();
  }

  public int getPuzzleLibrarySize() {
    return library.size();
  }

  public void resetPuzzle() {
    lampPlacements = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    notifyObservers();
  }

  public boolean isSolved() {
    for (int r = 0; r < activePuzzle.getHeight(); r++) {
      for (int c = 0; c < activePuzzle.getWidth(); c++) {
        CellType cellType = activePuzzle.getCellType(r, c);
        switch (cellType) {
          case CORRIDOR:
            if (!isLit(r, c) || (isLamp(r, c) && isLampIllegal(r, c))) return false;
            break;
          case CLUE:
            if (!isClueSatisfied(r, c)) return false;
            break;
        }
      }
    }
    return true;
  }

  public void addObserver(ModelObserver observer) {
    activeObservers.add(observer);
  }

  public void removeObserver(ModelObserver observer) {
    activeObservers.remove(observer);
  }

  public boolean isClueSatisfied(int r, int c) {
    checkIndexBounds(r, c, false);
    if (library.getPuzzle(activePuzzleIndex).getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell must be of type clue");
    }
    int maxLamps = library.getPuzzle(activePuzzleIndex).getClue(r, c);

    int numSurroundingLamps = 0;

    if (r > 0 && lampPlacements[r - 1][c] == 1) {
      numSurroundingLamps++;
    }
    if (r < activePuzzle.getHeight() - 1 && lampPlacements[r + 1][c] == 1) {
      numSurroundingLamps++;
    }
    if (c > 0 && lampPlacements[r][c - 1] == 1) {
      numSurroundingLamps++;
    }
    if (c < activePuzzle.getWidth() - 1 && lampPlacements[r][c + 1] == 1) {
      numSurroundingLamps++;
    }

    return maxLamps == numSurroundingLamps;
  }

  private void checkIndexBounds(int r, int c, boolean checkCorridor) {
    if (r >= library.getPuzzle(activePuzzleIndex).getHeight()
        || c >= library.getPuzzle(activePuzzleIndex).getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (checkCorridor) {
      if (library.getPuzzle(activePuzzleIndex).getCellType(r, c) != CellType.CORRIDOR) {
        throw new IllegalArgumentException("Cell is not of type corridor");
      }
    }
  }

  private void notifyObservers() {
    for (ModelObserver o : activeObservers) {
      o.update(this);
    }
  }
}
