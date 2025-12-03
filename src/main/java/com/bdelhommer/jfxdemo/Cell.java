/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdelhommer.jfxdemo;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Brant
 */
public class Cell {
    
    // Container for the cell's visual components
    private final VBox container;
    // Button for whacking the mole
    private final Button button;
    // Tracks whether this cell currently has an active mole
    private boolean active;
    // Mole image loaded from resources
    private final Image mole = new Image(getClass().getResourceAsStream("/mole.png"));
    private final Image hitImg = new Image(getClass().getResourceAsStream("/hit.png"));
    // ImageView to display the mole image
    private final ImageView moleView;
    
    /**
     * Constructs a Cell with the specified dimensions
     * @param width The width of the cell
     * @param height The height of the cell
     */
    Cell(double width, double height) {
        
        // Initialize UI components
        this.container = new VBox();
        this.button = new Button("Whack!");
        
        // Create mole image view (initially hidden)
        this.moleView = new ImageView(this.mole);
        this.moleView.setVisible(false);        
        
        // Add components to container and center them
        this.container.getChildren().addAll(this.moleView, button);
        this.container.setAlignment(Pos.CENTER);
        
        // Set container dimensions
        this.container.setMinWidth(width);
        this.container.setMinHeight(height);
        
    }
    
    /** @return The VBox container holding this cell's components */
    public VBox getContainer() { return this.container; }
    
    /** @return The whack button for this cell */
    public Button getButton() { return this.button; }
    
    /** @return True if this cell currently has an active mole */
    public boolean getActive() { return this.active; }
    
    /** 
     * Sets the active state of this cell
     * @param a True if the cell should be active, false otherwise
     */
    public void setActive(boolean a) { this.active = a; }
    
    public Image getMoleImg() {
        return this.mole;
    }
    
    public Image getHitImg() {
        return this.hitImg;
    }
    
    /** @return The ImageView displaying the mole */
    public ImageView getMoleView() { return this.moleView; }
    
}
