package com.bdelhommer.jfxdemo;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a single cell in the Whack-A-Mole game grid.
 * Contains the visual components (mole image, whack button) and tracks active state.
 * 
 * @author Brant
 */
public class Cell {
    
    private final VBox container;
    private final Button button;
    private boolean active; // flag for the cell that contains the mole
    private final Image mole = new Image(getClass().getResourceAsStream("/mole.png"));
    private final Image hitImg = new Image(getClass().getResourceAsStream("/hit.png"));
    private final ImageView moleView;
    
    Cell(double width, double height) {
        this.container = new VBox();
        this.button = new Button("Whack!");
        
        this.moleView = new ImageView(this.mole);
        imageViewSetup(this.moleView, container);
        this.moleView.setVisible(false);        
        
        this.container.getChildren().addAll(this.moleView, button);
        this.container.setAlignment(Pos.CENTER);
        this.container.setMinWidth(width);
        this.container.setMinHeight(height);
    }
    
    /**
     * Configures an ImageView to scale responsively within its container.
     */
    private void imageViewSetup(ImageView iv, VBox container) {
        iv.fitWidthProperty().bind(container.widthProperty().multiply(0.6));
        iv.fitHeightProperty().bind(container.heightProperty().multiply(0.6));
        iv.setPreserveRatio(true);
    }
    
    public VBox getContainer() { return this.container; }
    
    public Button getButton() { return this.button; }
    
    public boolean getActive() { return this.active; }
    
    public void setActive(boolean a) { this.active = a; }
    
    public Image getMoleImg() { return this.mole; }
    
    public Image getHitImg() { return this.hitImg; }
    
    public ImageView getMoleView() { return this.moleView; }
    
}
