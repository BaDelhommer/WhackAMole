/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdelhommer.jfxdemo;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Brant
 */
public final class Game {
    
    // 3x3 grid of game cells
    private final Cell[][] board;
    // Observable property for score binding to UI
    private final IntegerProperty score = new SimpleIntegerProperty();
    // Timeline for spawning moles at regular intervals
    private final Timeline spawnTimeLine;
    // Tracks the previously active cell
    private Cell prevCell;
    // Random number generator for mole positions
    private final Random random;
    // Label displaying the current score
    private final Label scoreLabel = new Label();
    // Current point total
    private int points;
    
    private int misses;
    private final int maxMisses = 5;
    private final IntegerProperty missProp = new SimpleIntegerProperty();
    private final Label missLabel = new Label();
    
    private HBox labelContainer;
    
    private VBox startMenu;
    private VBox gameOverMenu;
    private GridPane boardPane;
    
    /**
     * Constructs a new Game instance and initializes game components
     */
    Game() {
        
        // Initialize 3x3 board
        this.board = new Cell[3][3];
        this.points = 0;
        this.misses = 0;
        this.score.set(points);
        this.missProp.set(misses);
        this.prevCell = null;
        this.random = new Random();
        // Bind score label text to score property
        this.scoreLabel.textProperty().bind(Bindings.format("Score: %d", score));
        this.missLabel.textProperty().bind(Bindings.format("Misses: %d", missProp));
        
        // Set up the timeline for mole spawning
        KeyFrame kf = createKeyFrame();
        this.spawnTimeLine = new Timeline(kf);
        
        this.misses = 0;
        
    }
    
    /**
     * Creates the visual game board with cells and score label
     * @param root The GridPane container for the game board
     * @param width The width of each cell
     * @param height The height of each cell
     */
    public void createView(GridPane boardPane, double width, double height) {

        // Create and position cells in a 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {                
                Cell cell = new Cell(width, height);
                cell.getContainer().prefWidthProperty().bind(boardPane.widthProperty().divide(3));
                cell.getContainer().prefHeightProperty().bind(boardPane.heightProperty().divide(4));
                this.board[row][col] = cell;
                
                // Attach click handler to each cell's button
                cell.getButton().setOnMousePressed(event -> handleClicks(cell));
                
                boardPane.add(cell.getContainer(), col, row);
            }
        }
        
        // Add and position the score label
        this.labelContainer = new HBox(10);
        this.scoreLabel.setId("scoreLabel");
        this.missLabel.setId("missLabel");
        this.labelContainer.getChildren().addAll(this.scoreLabel, this.missLabel);
        this.labelContainer.setPadding(new Insets(5));
        this.labelContainer.setTranslateY(50);
        this.boardPane = boardPane;
        this.boardPane.add(this.labelContainer, 0, 3, 3, 1);
        this.boardPane.setPadding(new Insets(10, 10, 0, 10));
        this.boardPane.setVisible(true);
    }
    
    /**
     * Handles button clicks on cells
     * Awards points if the cell is currently active (has a visible mole)
     * @param c The cell that was clicked
     */
    public void handleClicks(Cell c) {
        
        // Only award points if the cell is currently active
        if (c.getActive()) {
            increment(c);
            c.getMoleView().setImage(c.getHitImg());
            c.setActive(false);
            
            FadeTransition ft = new FadeTransition(Duration.millis(100), c.getContainer());
            ft.setFromValue(1.0);
            ft.setToValue(0.7);
            ft.setAutoReverse(true);
            ft.setCycleCount(2);
            ft.play();
        } else {
            this.misses++;
            this.missProp.set(this.misses);
            if (this.misses >= this.maxMisses) {
                endGame();
            }
        }
        
    }
    
    /**
     * Creates a KeyFrame that spawns a mole in a random cell every second
     * @return KeyFrame to be used in the game timeline
     */
    public KeyFrame createKeyFrame() {
        
        KeyFrame kf = new KeyFrame(Duration.millis(1000), event -> { 
            // Hide the previous mole if it exists
            if (this.prevCell != null) {
                this.prevCell.getMoleView().setVisible(false);
                this.prevCell.getMoleView().setImage(this.prevCell.getMoleImg());
                this.prevCell.setActive(false);
            }
            
            // Generate random position for new mole
            int row = this.random.nextInt(3);
            int col = this.random.nextInt(3);
            
            // Ensure the new position is different from the previous one
            while (this.board[row][col] == this.prevCell) {
                row = this.random.nextInt(3);
                col = this.random.nextInt(3);
            }
            
            // Activate the new cell and show the mole
            Cell curCell = this.board[row][col];
            
            curCell.getMoleView().setVisible(true);
            curCell.setActive(true);
            this.prevCell = curCell;
        });
        
        return kf;
    }
    
    /**
     * Starts the game by initiating the mole spawn timeline
     */
    public void startGame() {
        
        // Set timeline to repeat indefinitely
        this.spawnTimeLine.setCycleCount(Animation.INDEFINITE);
        this.spawnTimeLine.play();
        
    }
    
    /**
     * Increments the player's score by 10 points
     * @param c The cell that was successfully whacked
     */
    public void increment(Cell c) {
        
        this.points += 10;
        this.score.set(points);
        
    }
    
    public void createStartMenu(StackPane root) {
        
        this.startMenu = new VBox(10);
        this.startMenu.setAlignment(Pos.CENTER);
        this.startMenu.setId("startMenu");
        
        Label title = new Label("Whack-A-Mole");
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startPlaying());
        
        this.startMenu.getChildren().addAll(title, startButton);
        root.getChildren().add(startMenu);
        this.startMenu.setVisible(true);
        
    }
    
    public void createGameOverMenu(StackPane root) {
        
        this.gameOverMenu = new VBox(10);
        this.gameOverMenu.setAlignment(Pos.CENTER);
        
        Label gameOverLabel = new Label("Game Over!");
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> restartGame());
        
        Button quitButton = new Button("Quit?");
        quitButton.setOnAction(e -> Platform.exit());
        
        this.gameOverMenu.getChildren().addAll(gameOverLabel, restartButton, quitButton);
        this.gameOverMenu.setVisible(false);
        root.getChildren().add(gameOverMenu);
        
    }
    
    public void startPlaying() {
        
        this.startMenu.setVisible(false);
        this.boardPane.setVisible(true);
        startGame();
        
    }
    
    private void endGame() {
        
        this.spawnTimeLine.stop();
        this.boardPane.setVisible(false);
        this.gameOverMenu.setVisible(true);
        
    }
    
    private void restartGame() {
        
        this.misses = 0;
        this.points = 0;
        this.score.set(this.points);
        this.missProp.set(this.misses);
        this.gameOverMenu.setVisible(false);
        this.boardPane.setVisible(true);
        startPlaying();
        
    }
}
