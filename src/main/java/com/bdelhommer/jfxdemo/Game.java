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
 * Main game logic class for Whack-A-Mole.
 * Manages the game board, scoring, mole spawning, and game state transitions.
 * 
 * @author Brant
 */
public final class Game {
    
    private final Cell[][] board;
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final Timeline spawnTimeLine;
    private Cell prevCell;
    private final Random random;
    private final Label scoreLabel = new Label();
    private int points;
    
    private int misses;
    private final int maxMisses = 5;
    private final IntegerProperty missProp = new SimpleIntegerProperty();
    private final Label missLabel = new Label();
    
    private HBox labelContainer;
    private VBox startMenu;
    private VBox gameOverMenu;
    private GridPane boardPane;
    
    Game() {
        this.board = new Cell[3][3];
        this.points = 0;
        this.misses = 0;
        this.score.set(points);
        this.missProp.set(misses);
        this.prevCell = null;
        this.random = new Random();
        this.scoreLabel.textProperty().bind(Bindings.format("Score: %d", score));
        this.missLabel.textProperty().bind(Bindings.format("Misses: %d", missProp));
        
        KeyFrame kf = createKeyFrame();
        this.spawnTimeLine = new Timeline(kf);
    }
    
    /**
     * Creates the visual game board with cells and score display.
     */
    public void createView(GridPane boardPane, double width, double height) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {                
                Cell cell = new Cell(width, height);
                cell.getContainer().prefWidthProperty().bind(boardPane.widthProperty().divide(3));
                cell.getContainer().prefHeightProperty().bind(boardPane.heightProperty().divide(4));
                this.board[row][col] = cell;
                cell.getButton().setOnMousePressed(event -> handleClicks(cell));
                boardPane.add(cell.getContainer(), col, row);
            }
        }
        
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
     * Handles cell clicks. Awards points for hitting an active mole,
     * otherwise increments miss count.
     */
    public void handleClicks(Cell c) {
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
     * Creates a KeyFrame that spawns a mole in a random cell every second,
     * ensuring the new position differs from the previous one.
     */
    public KeyFrame createKeyFrame() {
        KeyFrame kf = new KeyFrame(Duration.millis(1000), event -> { 
            if (this.prevCell != null) {
                this.prevCell.getMoleView().setVisible(false);
                this.prevCell.getMoleView().setImage(this.prevCell.getMoleImg());
                this.prevCell.setActive(false);
            }
            
            int row = this.random.nextInt(3);
            int col = this.random.nextInt(3);
            
            while (this.board[row][col] == this.prevCell) {
                row = this.random.nextInt(3);
                col = this.random.nextInt(3);
            }
            
            Cell curCell = this.board[row][col];
            curCell.getMoleView().setVisible(true);
            curCell.setActive(true);
            this.prevCell = curCell;
        });
        
        return kf;
    }
    
    public void startGame() {
        this.spawnTimeLine.setCycleCount(Animation.INDEFINITE);
        this.spawnTimeLine.play();
    }
    
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
