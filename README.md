# Whack-A-Mole

A classic Whack-A-Mole game built with JavaFX for LSUA Intro to Object Oriented Design(CSCI 2340) final project.

## Description

Click the "Whack!" buttons to hit moles as they randomly appear on a 3x3 grid. Each successful hit earns 10 points. Moles appear every second in a random cell, so stay alert!

## Requirements

- Java 11 or higher
- Maven

## How to Run

```bash
mvn clean javafx:run
```

## How to Play

1. Watch the 3x3 grid for moles to appear
2. Click the "Whack!" button under a visible mole to score points
3. Each successful whack earns 10 points
4. Try to get the highest score you can!

## Project Structure

```
src/main/java/com/bdelhommer/jfxdemo/
├── App.java    - Application entry point and UI setup
├── Game.java   - Game logic, scoring, and mole spawning
└── Cell.java   - Individual grid cell with button and mole display

src/main/resources/
├── styles.css  - CSS styling
├── mole.png    - Mole image
└── hit.png     - Hit animation image
```

## Built With

- Java 11
- JavaFX 13
- Maven
