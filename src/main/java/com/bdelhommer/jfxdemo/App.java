package com.bdelhommer.jfxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */

public class App extends Application {
    
    // Game instance shared across the application
    final static Game game = new Game();

    /**
     * Initializes and displays the primary stage for the Whack-A-Mole game
     * @param stage The primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        
        // Create the root layout as a Stack Pane
        StackPane root = new StackPane();
        GridPane boardPane = new GridPane();
        boardPane.setId("boardPane");
        
        root.getChildren().add(boardPane);
                
        // Create the scene with dimensions and apply CSS stylesheet
        Scene scene = new Scene(root, 360, 480);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        // Calculate cell dimensions based on scene size
        double cellWidth = scene.getWidth() / 3.0;
        double cellHeight = scene.getHeight() / 4.0;

        // Initialize the game board view
        game.createView(boardPane, cellWidth, cellHeight);
        
        game.createStartMenu(root);
        game.createGameOverMenu(root);
        
        boardPane.setVisible(false);
        
        // Configure and display the stage
        stage.setScene(scene);
        stage.setTitle("Whack-A-Mole");
        stage.show();
        
    }

    /**
     * Application entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

}