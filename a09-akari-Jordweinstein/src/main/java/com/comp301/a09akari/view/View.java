package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class View implements FXComponent, ModelObserver {
  private final FXComponent gameView;
  private final FXComponent textView;
  private final FXComponent buttonView;

  private final Scene scene;

  public View(ClassicMvcController controller, Model model) {
    model.addObserver(this);
    // creating sub Views.
    gameView = new GameView(controller, model);
    textView = new TextView(controller, model);
    buttonView = new ButtonsView(controller, model);
    scene = new Scene(this.render());
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public Parent render() {
    Pane layout = new VBox();
    layout.setMinWidth(700);
    layout.setMinHeight(700);
    layout.getChildren().add(gameView.render());
    layout.getChildren().add(textView.render());
    layout.getChildren().add(buttonView.render());

    return layout;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }
}
