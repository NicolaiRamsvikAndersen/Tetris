package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {

    /**
    * Gets the dimension of the grid.
    * 
    * @return the dimension of the grid as a GridDimension object
    */
    GridDimension getDimension();

    /**
    * Gives all the cells in the grid
    * 
    * @return an Iterable of GridCell objects in the grid
    */
    Iterable<GridCell<Character>> getCells();

    /**
    * Gives the cells belonging to the falling tetromino.
    * 
    * @return an Iterable of GridCell objects belonging to the falling tetromino
    */
    Iterable<GridCell<Character>> fallingTetromino();

    /**
    * Gives the cells belonging to the held tetromino.
    * 
    * @return an Iterable of GridCell objects belonging to the held tetromino
    */
    Iterable<GridCell<Character>> heldTetromino();

    /**
    * Gets the current state of the game.
    * 
    * @return the current game state
    */
    GameState getGameState();

    /**
    * Gets the level.
    * 
    * @return the current level
    */
    int getLevel();

    /**
    * Gets the score.
    *
    * @return the current score
    */
    int getScore();
}
