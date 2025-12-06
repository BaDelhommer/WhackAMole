# Whack-A-Mole

A classic Whack-A-Mole game built with JavaFX for LSUA Intro to Object Oriented Design (CSCI 2340) final project.

## Description

Click the "Whack!" buttons to hit moles as they randomly appear on a 3x3 grid. Each successful hit earns 10 points, but be careful — clicking an empty cell counts as a miss. After 5 misses, the game ends.

## Features

- Start menu with a "Start" button to begin gameplay
- 3x3 game grid with randomly spawning moles
- Score tracking (+10 points per successful hit)
- Miss counter (game over after 5 misses)
- Hit animation feedback
- Game over screen with restart and quit options

## Requirements

- Java 11 or higher
- Maven

## How to Run

```bash
mvn clean javafx:run
```

## How to Play

1. Click "Start" on the main menu
2. Watch the 3x3 grid for moles to appear
3. Click the "Whack!" button under a visible mole to score points
4. Each successful whack earns 10 points
5. Avoid clicking empty cells — 5 misses ends the game
6. Try to get the highest score you can!

## Project Structure

```
src/main/java/com/bdelhommer/jfxdemo/
├── App.java    - Application entry point and UI setup
├── Game.java   - Game logic, scoring, mole spawning, and menu management
└── Cell.java   - Individual grid cell with button and mole display

src/main/resources/
├── styles.css  - CSS styling
├── grass.png   - Background image
├── mole.png    - Mole image
└── hit.png     - Hit animation image
```

## Built With

- Java 11
- JavaFX 13
- Maven
