package no.uib.inf101.tetris.model.tetromino;

public class RandomTetrominoFactory implements TetrominoFactory{

    @Override
    public Tetromino getNext() {
        String symbol = "LJSZTIO";
        int index = (int) (Math.random() * symbol.length());
        char c = symbol.charAt(index);
        return Tetromino.newTetromino(c);
    }

}
