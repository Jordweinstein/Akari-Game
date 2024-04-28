package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  public void clickNextPuzzle() {
    if (model.getActivePuzzleIndex() < model.getPuzzleLibrarySize() - 1) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
  }

  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() != 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    }
  }

  public void clickRandPuzzle() {
    Random rand = new Random();
    int r = rand.nextInt(model.getPuzzleLibrarySize());
    model.setActivePuzzleIndex(r);
  }

  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      System.out.println("This is not a corridor");
    } else if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }
}
