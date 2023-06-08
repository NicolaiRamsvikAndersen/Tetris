package no.uib.inf101.tetris.model.tetromino;

import java.util.Iterator;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public final class Tetromino implements Iterable<GridCell<Character>> {

    private char symbol;
    private boolean[][] shape;
    private CellPosition pos;

    private Tetromino(char symbol, boolean[][] shape, CellPosition pos) {
        this.symbol = symbol;
        this.shape = shape;
        this.pos = pos;
    }

    /**
    * Gets the symbol of the tetromino.
    * 
    * @return a char representing the tetromino
    */
    public char getSymbol() {
        return symbol;
    }

    /**
    * Makes a new tetromino with the correct shape.
    * 
    * @param symbol the symbol of the tetromino
    * @return a new tetromino with the given symbol and correct shape at
    *         position (0,0)
    * @throws IllegalArgumentException if the symbol is not a valid tetromino
    *         symbol
    */
    static Tetromino newTetromino(char symbol) {

        if(symbol == 'L') return new Tetromino(symbol, lTetromino, new CellPosition(0, 0));
        if(symbol == 'J') return new Tetromino(symbol, jTetromino, new CellPosition(0, 0));
        if(symbol == 'S') return new Tetromino(symbol, sTetromino, new CellPosition(0, 0));
        if(symbol == 'Z') return new Tetromino(symbol, zTetromino, new CellPosition(0, 0));
        if(symbol == 'T') return new Tetromino(symbol, tTetromino, new CellPosition(0, 0));
        if(symbol == 'I') return new Tetromino(symbol, iTetromino, new CellPosition(0, 0));
        if(symbol == 'O') return new Tetromino(symbol, oTetromino, new CellPosition(0, 0));

        throw new IllegalArgumentException("Invalid symbol");

    }

    /**
     * Makes a copy of the tetromino with a new position and replaces the
     * old one.
     * 
     * @param deltaRow the number of rows to shift the tetromino
     * @param deltaCol the number of columns to shift the tetromino
     * @return a new tetromino with the same shape and symbol at the new
     *         position
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        CellPosition shiftedPos = new CellPosition(pos.row() + deltaRow, pos.col() + deltaCol);
        Tetromino shifted = new Tetromino(symbol, shape, shiftedPos);
        return shifted;
    }

    /**
    * Makes a copy of the tetromino at the top center of the grid and replaces
    * the old one.
    * 
    * @param gd the grid dimension
    * @return a new tetromino with the same shape and symbol at the top center
    */
    public Tetromino shiftedToTopCenterOf(GridDimension gd) {
        int middleCol = (gd.cols() / 2) - shape[0].length / 2;
        
        return shiftedBy(-1 - pos.row(), middleCol - pos.col());
    }

    public Tetromino shiftedToZero(GridDimension gd) {
        return shiftedBy(-pos.row(), -pos.col());
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        ArrayList<GridCell<Character>> cells = new ArrayList<GridCell<Character>>();
        for(int row = 0; row < shape.length; row++) {
            for(int col = 0; col < shape[row].length; col++) {
                if(shape[row][col]) {
                    CellPosition pos = new CellPosition(this.pos.row() + row, this.pos.col() + col);
                    GridCell<Character> cell = new GridCell<Character>(pos, symbol);
                    cells.add(cell);
                }
            }
        }
        return cells.iterator();
    }

    /**
    * Generates a hashcode for the tetromino.
    *
    * @return the hashcode of the tetromino
    */
    public int hashCode() {
        return Objects.hash(this.symbol, Arrays.deepHashCode(this.shape), this.pos);
    }

    /**
    * Checks if two tetrominos are equal.
    * 
    * @param o the object to compare to
    * @return true if the objects are equal, false otherwise
    */
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Tetromino)) return false;
        Tetromino t = (Tetromino) o;
        return this.symbol == t.symbol && Arrays.deepEquals(this.shape, t.shape) && this.pos.equals(t.pos);
    }

    /**
     * Rotates the tetromino 90 degrees clockwise.
     * 
     * @return a new tetromino with the same sympol and position but the shape
     *         is rotated 90 degrees clockwise
     */
    public Tetromino rotateClock() {
        boolean[][] rotatedShape = new boolean[shape[0].length][shape.length];
        for(int row = 0; row < shape.length; row++) {
            for(int col = 0; col < shape[row].length; col++) {
                rotatedShape[col][shape.length - 1 - row] = shape[row][col];
            }
        }
        Tetromino rotated = new Tetromino(symbol, rotatedShape, pos);
        return rotated;
    }

    /**
     * Sets the rotation of the tetromino to the default rotation.
     * 
     * @return a new tetromino with the same symbol and position but the shape
     *         is set to the default rotation
     */
    public Tetromino defaultRotation() {
        return newTetromino(symbol);
    }
    
    //Shapes
    private static boolean[][] lTetromino = new boolean[][] {
        { false, false, false },
        {  true,  true,  true },
        {  true, false, false }
    };
    
    private static boolean[][] jTetromino = new boolean[][] {
        { false, false, false },
        {  true,  true,  true },
        { false, false,  true }
    };

    private static boolean[][] sTetromino = new boolean[][] {
        { false, false, false },
        { false,  true,  true },
        {  true,  true, false }
    };

    private static boolean[][] zTetromino = new boolean[][] {
        { false, false, false },
        {  true,  true, false },
        { false,  true,  true }
    };

    private static boolean[][] tTetromino = new boolean[][] {
        { false, false, false },
        {  true,  true,  true },
        { false,  true, false }
    };

    private static boolean[][] iTetromino = new boolean[][] {
        { false, false, false, false },
        {  true,  true,  true,  true },
        { false, false, false, false },
        { false, false, false, false }
    };

    private static boolean[][] oTetromino = new boolean[][] {
        { false, false, false, false },
        { false,  true,  true, false },
        { false,  true,  true, false },
        { false, false, false, false }
    };

    
}
