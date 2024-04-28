package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ButtonsView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public ButtonsView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {

    HBox pane = new HBox(5);

    pane.getChildren().clear();
    pane.getChildren().add(renderButtons());
    Label puzzleNum =
        new Label((model.getActivePuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize());
    puzzleNum.getStyleClass().add("puzzleNum");
    pane.getChildren().add(puzzleNum);
    return pane;
  }

  private HBox renderButtons() {
    HBox layout = new HBox(5);
    // previous button
    Button prev = new Button("Previous");
    prev.getStyleClass().add("prevButton");
    prev.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    layout.getChildren().add(prev);

    // next
    Button next = new Button("Next");
    next.getStyleClass().add("nextButton");
    next.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    layout.getChildren().add(next);

    // random
    Button rand = new Button("Random");
    rand.getStyleClass().add("randButton");

    rand.setOnAction(
        (ActionEvent event) -> {
          int before = model.getActivePuzzleIndex();
          controller.clickRandPuzzle();
          int after = model.getActivePuzzleIndex();

          while (before == after) {
            before = model.getActivePuzzleIndex();
            controller.clickRandPuzzle();
            after = model.getActivePuzzleIndex();
          }
        });
    layout.getChildren().add(rand);

    // reset
    Button resetButton = new Button("Reset");
    resetButton.getStyleClass().add("reset");
    resetButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    layout.getChildren().add(resetButton);
    return layout;
  }
}
