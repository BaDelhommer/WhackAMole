package com.bdelhommer.jfxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
        
        // Create the root layout as a GridPane
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: lightgreen;");
                
        // Create the scene with dimensions and apply CSS stylesheet
        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        // Calculate cell dimensions based on scene size
        double cellWidth = scene.getWidth() / 4;
        double cellHeight = scene.getHeight() / 4;

        // Initialize the game board view
        game.createView(root, cellWidth, cellHeight);

        // Start the game loop
        game.startGame();
        
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