package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid<Character> {

    /**
    * Create a new TetrisBoard.
    * 
    * @param rows Number of rows
    * @param cols Number of columns
    */
    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
    * Makes a string representation of the board.
    * 
    * @return String representation of the board.
    */
    public String prettyString() {
        String prettyString = "";
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                CellPosition pos = new CellPosition(i, j);
                prettyString += this.get(pos);
            }
            if (i < rows() - 1) prettyString += "\n";
        }
        return prettyString;
    }

    /**
    * Removes all full rows and gives the number of rows removed.
    *  
    * @return Number of rows removed.
    */
    public int removeFullRows() {
        int count = 0;
        int a = rows() - 1;
        int b = rows() - 1;
        while(a >= 0) {
            while(b >= 0 && !elementOnRow(b, '-')) {
                count++;
                b--;
            }
            if (b >= 0) {
                copyPasteRow(b, a);
            } else {
                setRowToElement(a, '-');
            }
            a--;
            b--;
        }
        return count;
    }

    private boolean elementOnRow(int row, Character element) {
        for (int col = 0; col < cols(); col++) {
            CellPosition pos = new CellPosition(row, col);
            if (get(pos) == element) {
                return true;
            }
        }
        return false;
    }

    private void setRowToElement(int row, Character element) {
        for (int col = 0; col < cols(); col++) {
            CellPosition pos = new CellPosition(row, col);
            set(pos, element);
        }
    }

    private void copyPasteRow(int copyRow, int pasteRow) {
        for (int col = 0; col < cols(); col++) {
            CellPosition copyPos = new CellPosition(copyRow, col);
            CellPosition pastePos = new CellPosition(pasteRow, col);
            set(pastePos, get(copyPos));
        }
    }
}
