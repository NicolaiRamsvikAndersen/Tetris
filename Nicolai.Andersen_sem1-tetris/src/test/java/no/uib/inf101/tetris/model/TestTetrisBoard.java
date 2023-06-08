package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class TestTetrisBoard {
    
    @Test
    public void testPrettyString() {
        TetrisBoard board = new TetrisBoard(3, 4);
        board.set(new CellPosition(0, 0), 'g');
        board.set(new CellPosition(0, 3), 'y');
        board.set(new CellPosition(2, 0), 'r');
        board.set(new CellPosition(2, 3), 'b');
        String expected = String.join("\n", new String[] {
            "g--y",
            "----",
            "r--b"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void testRemoveFullRows() {
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "-T",
            "TT",
            "LT",
            "L-",
            "LL"
        });
        assertEquals(3, board.removeFullRows());
        String expected = String.join("\n", new String[] {
            "--",
            "--",
            "--",
            "-T",
            "L-"
        });
        assertEquals(expected, board.prettyString());
    }
    
    @Test
    public void testRemoveTopNotBottom() {
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "TTT",
            "T-T"
        });
        assertEquals(1, board.removeFullRows());
        String expected = String.join("\n", new String[] {
            "---",
            "T-T"
        });
        assertEquals(expected, board.prettyString());
    }
    

    private TetrisBoard getTetrisBoardWithContents(String[] content) {
        TetrisBoard board = new TetrisBoard(content.length, content[0].length());
        for (int row = 0; row < content.length; row++) {
            for (int col = 0; col < content[row].length(); col++) {
                board.set(new CellPosition(row, col), content[row].charAt(col));
            }
        }
        return board;
    }
}
