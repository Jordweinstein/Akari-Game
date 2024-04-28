package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.w3c.dom.Text;

public class TextView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public TextView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  public Parent render() {
    StackPane layout = new StackPane();
    if (model.isSolved()) {
      int puzzle = model.getActivePuzzleIndex() + 1;
      Label instructions = new Label("You solved puzzle number " + puzzle + "!");
      instructions.getStyleClass().add("instructions");
      instructions.setAlignment(Pos.CENTER);
      layout.getChildren().add(instructions);
    }
    return layout;
  }
}
