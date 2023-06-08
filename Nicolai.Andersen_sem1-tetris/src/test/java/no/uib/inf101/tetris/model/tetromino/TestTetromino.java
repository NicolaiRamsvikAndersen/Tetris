package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.TetrisBoard;

public class TestTetromino {

    @Test
    public void testHashCodeAndEquals() {
        Tetromino t1 = Tetromino.newTetromino('T');
        Tetromino t2 = Tetromino.newTetromino('T');
        Tetromino t3 = Tetromino.newTetromino('T').shiftedBy(1, 0);
        Tetromino s1 = Tetromino.newTetromino('S');
        Tetromino s2 = Tetromino.newTetromino('S').shiftedBy(0, 0);

        assertEquals(t1, t2);
        assertEquals(s1, s2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1, s1);
    }

    @Test
    public void tetrominoIterationOfT() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 100), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'T')));
    }

    @Test
    public void tetrominoIterationOfS() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('S');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'S')));
    }

    @Test
    public void shiftedByTwice() {
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(21, 200), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(21, 201), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(21, 202), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(22, 201), 'T')));
    }

    @Test
    public void shiftedToTopCenterOf() {
        Tetromino tetro = Tetromino.newTetromino('T');
        Tetromino tetro2 = Tetromino.newTetromino('I');
        TetrisBoard board = new TetrisBoard(10, 100);
        tetro = tetro.shiftedToTopCenterOf(board);
        tetro2 = tetro2.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }
        for (GridCell<Character> gc2 : tetro2) {
            objs.add(gc2);
        }

        // Check that we got the expected GridCell objects
        assertEquals(8, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 49), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 50), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 51), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 50), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 48), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 49), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 50), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 51), 'I')));

        tetro = Tetromino.newTetromino('T');
        tetro2 = Tetromino.newTetromino('I');
        board = new TetrisBoard(10, 99);
        tetro = tetro.shiftedToTopCenterOf(board);
        tetro2 = tetro2.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }
        for (GridCell<Character> gc2 : tetro2) {
            objs.add(gc2);
        }

        // Check that we got the expected GridCell objects
        assertEquals(8, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 48), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 49), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 50), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 49), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 47), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 48), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 49), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 50), 'I')));
    }

}
