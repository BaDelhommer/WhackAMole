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
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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
    
    /**
     * Constructs a new Game instance and initializes game components
     */
    Game() {
        
        // Initialize 3x3 board
        this.board = new Cell[3][3];
        this.points = 0;
        this.score.set(points);
        this.prevCell = null;
        this.random = new Random();
        // Bind score label text to score property
        this.scoreLabel.textProperty().bind(Bindings.format("Score: %d", score));
        
        // Set up the timeline for mole spawning
        KeyFrame kf = createKeyFrame();
        this.spawnTimeLine = new Timeline(kf);
        
    }
    
    /**
     * Creates the visual game board with cells and score label
     * @param root The GridPane container for the game board
     * @param width The width of each cell
     * @param height The height of each cell
     */
    public void createView(GridPane root, double width, double height) {

        // Create and position cells in a 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {                
                Cell cell = new Cell(width, height);
                cell.getContainer().prefWidthProperty().bind(root.widthProperty().divide(3));
                cell.getContainer().prefHeightProperty().bind(root.heightProperty().divide(4));
                this.board[row][col] = cell;
                
                // Attach click handler to each cell's button
                cell.getButton().setOnMousePressed(event -> handleClicks(cell));
                
                root.add(cell.getContainer(), col, row);
            }
        }
        
        // Add and position the score label
        this.scoreLabel.setId("scoreLabel");
        this.scoreLabel.setTranslateY(50);
        root.add(this.scoreLabel, 0, 400);
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
    
}
