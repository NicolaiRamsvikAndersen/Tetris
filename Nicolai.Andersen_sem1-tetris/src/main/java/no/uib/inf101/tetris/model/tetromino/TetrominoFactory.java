package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {

    /**
    * Get the next tetromino.
    * 
    * @return a new tetromino
    */
    public Tetromino getNext();
}
