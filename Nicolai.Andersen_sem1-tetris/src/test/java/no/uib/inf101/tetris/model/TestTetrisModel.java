package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TestTetrisModel {
    
    @Test
    public void initialPositionOfO() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }

    @Test
    public void initialPositionOfI() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
    }

    @Test
    public void canMoveTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            tetroCells.add(gc);
        }

        assertFalse(model.moveTetromino(100, 100));
        assertTrue(model.moveTetromino(1, 1));

        List<GridCell<Character>> movedTetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            movedTetroCells.add(gc);
        }

        assertNotEquals(movedTetroCells, tetroCells);

        assertEquals(4, movedTetroCells.size());
        assertTrue(movedTetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(movedTetroCells.contains(new GridCell<>(new CellPosition(1, 6), 'O')));
        assertTrue(movedTetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));
        assertTrue(movedTetroCells.contains(new GridCell<>(new CellPosition(2, 6), 'O')));

    }

    @Test
    public void canRotateTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            tetroCells.add(gc);
        }

        assertFalse(model.rotateTetromino());
        assertTrue(model.moveTetromino(1, 0));
        assertTrue(model.rotateTetromino());

        List<GridCell<Character>> rotatedTetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            rotatedTetroCells.add(gc);
        }

        assertNotEquals(rotatedTetroCells, tetroCells);

        assertEquals(4, rotatedTetroCells.size());
        assertTrue(rotatedTetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'T')));
        assertTrue(rotatedTetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'T')));
        assertTrue(rotatedTetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'T')));
        assertTrue(rotatedTetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'T')));
    }

    @Test
    public void canDropTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        model.dropTetromino();

        List<GridCell<Character>> gridCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getCells()) {
            gridCells.add(gc);
        }
        
        assertEquals(200, gridCells.size());
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(18, 5), 'T')));
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(18, 4), 'T')));
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(18, 5), 'T')));
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(19, 5), 'T')));
    }

    @Test
    public void testClockTick() {
        TetrisBoard board = new TetrisBoard(3, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);
        model.setGameState(GameState.ACTIVE_GAME);

        model.clockTick();

        List<GridCell<Character>> TetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            TetroCells.add(gc);
        }

        assertEquals(4, TetroCells.size());
        assertTrue(TetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(TetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(TetroCells.contains(new GridCell<>(new CellPosition(2, 4), 'O')));
        assertTrue(TetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));
        
        model.clockTick();

        List<GridCell<Character>> gridCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getCells()) {
            gridCells.add(gc);
        }
        
        assertEquals(30, gridCells.size());
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));
        assertTrue(gridCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));
    }
}
