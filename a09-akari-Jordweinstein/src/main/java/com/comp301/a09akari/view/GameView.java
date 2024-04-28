package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public class GameView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;
  private Image lightbulbImage;
  private Image incorrectImage;
  private ImagePattern lightbulbPattern;
  private ImagePattern incorrectPattern;

  public GameView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
    loadImages();
  }

  private void loadImages() {
    lightbulbImage = new Image("lightbulb.png");
    incorrectImage = new Image("incorrect.png");
    lightbulbPattern = new ImagePattern(lightbulbImage);
    incorrectPattern = new ImagePattern(incorrectImage);
  }

  @Override
  public Parent render() {
    GridPane board = new GridPane();
    board.setHgap(10);
    board.setVgap(10);
    int cellSize = 50;

    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        switch (model.getActivePuzzle().getCellType(i, j)) {
          case CLUE:
            handleClueCell(board, i, j, cellSize);
            break;
          case WALL:
            handleWallCell(board, i, j, cellSize);
            break;
          case CORRIDOR:
            handleCorridorCell(board, i, j, cellSize);
            break;
        }
      }
    }
    return board;
  }

  private void handleClueCell(GridPane board, int i, int j, int size) {
    StackPane stp = new StackPane();
    Rectangle rectangle = new Rectangle(size, size);
    Text text = new Text("" + model.getActivePuzzle().getClue(i, j));
    text.getStyleClass().add("white-text");
    text.setFill(Color.WHITE);
    rectangle.setFill(Color.BLACK);
    if (model.isClueSatisfied(i, j)) {
      rectangle.setFill(Color.rgb(73, 196, 106));
    }
    stp.getChildren().addAll(rectangle, text);
    board.add(stp, j, i);
  }

  private void handleWallCell(GridPane board, int i, int j, int size) {
    Rectangle cell = new Rectangle(size, size);
    cell.setFill(Color.BLACK);
    board.add(cell, j, i);
  }

  private void handleCorridorCell(GridPane board, int i, int j, int size) {
    Rectangle cell = new Rectangle(size, size);
    cell.setFill(Color.rgb(230, 230, 230));

    if (model.isLamp(i, j) && model.isLampIllegal(i, j)) {
      cell.setFill(incorrectPattern);
    } else if (model.isLamp(i, j)) {
      cell.setFill(lightbulbPattern);
    } else if (model.isLit(i, j)) {
      cell.setFill(Color.rgb(252, 204, 52));
    }

    cell.setOnMouseClicked(
        mouseEvent -> {
          toggleLamp(cell, i, j);
        });
    board.add(cell, j, i);
  }

  private void toggleLamp(Rectangle cell, int i, int j) {
    if (!model.isLamp(i, j)) {
      model.addLamp(i, j);
    } else {
      model.removeLamp(i, j);
      cell.setFill(Color.WHITE);
    }
  }
}
