package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {

    String pattern;
    int index = 0;

    public PatternedTetrominoFactory(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Tetromino getNext() {
        Tetromino t = Tetromino.newTetromino(pattern.charAt(index));
        index = (index + 1) % pattern.length();
        return t;
    }
}
