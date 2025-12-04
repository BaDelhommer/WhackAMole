package com.bdelhommer.jfxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main application entry point for the Whack-A-Mole game.
 * 
 * @author Brant
 */
public class App extends Application {
    
    final static Game game = new Game();

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        GridPane boardPane = new GridPane();
        boardPane.setId("boardPane");
        
        root.getChildren().add(boardPane);
                
        Scene scene = new Scene(root, 360, 480);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        double cellWidth = scene.getWidth() / 3.0;
        double cellHeight = scene.getHeight() / 4.0;

        game.createView(boardPane, cellWidth, cellHeight);
        game.createStartMenu(root);
        game.createGameOverMenu(root);
        
        boardPane.setVisible(false);

        stage.setScene(scene);
        stage.setTitle("Whack-A-Mole");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
