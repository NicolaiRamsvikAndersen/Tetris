package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {
    
    /** Move the tetromino.
    * 
    * @param deltaRow The number of rows to move the tetromino
    * @param deltaCol The number of columns to move the tetromino
    * @return True if the tetromino was moved, false otherwise
    */
    boolean moveTetromino(int deltaRow, int deltaCol);

    /**
    * Rotate the tetromino.
    * 
    * @return True if the tetromino was rotated, false otherwise
    */
    boolean rotateTetromino();

    /** Move the tetromino down until no longer possible then place it */
    void dropTetromino();

    /**
    * Get the current game state.
    * 
    * @return The current game state
    */
    GameState getGameState();

    /**
    * Set the current game state. 
    * 
    * @param state The new game state
    */
    void setGameState(GameState state);

    /**
    * Get the number of milliseconds per tick.
    *
    * @return The number of milliseconds per tick 
    */
    int getMillisPerTick();

    /** Perform an action every tick. */
    void clockTick();

    /** Reset the board, tetromino, level, score and lines cleared. */
    void resetBoard();
    
    /**
    * Adds to the score.
    * 
    * @param score the score to add
    * @return the new score
    */
    int addToScore(int score);

    /** Switches the current tetromino with the hold tetromino. */
    void switchTetromino();
}
