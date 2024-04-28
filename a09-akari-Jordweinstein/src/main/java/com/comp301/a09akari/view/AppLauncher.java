package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.model.*;
import com.comp301.a09akari.controller.*;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary library = new PuzzleLibraryImpl();

    // create PuzzleImpls
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    // populate library
    library.addPuzzle(puzzle1);
    library.addPuzzle(puzzle2);
    library.addPuzzle(puzzle3);
    library.addPuzzle(puzzle4);
    library.addPuzzle(puzzle5);

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);

    View view = new View(controller, model);

    view.getScene().getStylesheets().add("main.css");
    stage.setScene(view.getScene());
    stage.sizeToScene();

    stage.setTitle("Akari Game");
    stage.show();
  }
}
