package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

    private TetrisBoard board;
    private TetrominoFactory factory;
    private Tetromino current;
    private Tetromino hold;
    private GameState state = GameState.START;
    private int linesCleared = 0;
    private int level = 1;
    private int score = 0;

    /**
    * Create a new TetrisModel.
    * 
    * @param board the board to use
    * @param factory the factory to use
    */
    public TetrisModel(TetrisBoard board, TetrominoFactory factory) {
        this.board = board;
        this.factory = factory;
        this.current = factory.getNext().shiftedToTopCenterOf(board);
    }

    @Override
    public GridDimension getDimension() {
        return board;
    }

    @Override
    public Iterable<GridCell<Character>> getCells() {
        return board;
    }

    @Override
    public Iterable<GridCell<Character>> fallingTetromino() {
        return current;
    }

    @Override
    public Iterable<GridCell<Character>> heldTetromino() {
        return hold;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        Tetromino moved = current.shiftedBy(deltaRow, deltaCol);
        return this.isLegalMove(moved);
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino rotated = current.rotateClock();
        return this.isLegalMove(rotated);
    }

    @Override
    public void dropTetromino() {
        while (this.moveTetromino(1, 0)) {
            score += 2;
        }
        this.placeTetromino();
    }

    private boolean isLegalMove(Tetromino moved) {
        for (GridCell<Character> cell : moved) {
            if(!board.positionIsOnGrid(cell.pos()) || board.get(cell.pos()) !=  '-') return false;
        }
        current = moved;
        return true;
    }

    private void placeTetromino() {
        for (GridCell<Character> cell : current) {
            board.set(cell.pos(), cell.value());
        }
        calculateScore();
        getNewTetromino();
    }

    private void getNewTetromino() {
        Tetromino next = factory.getNext().shiftedToTopCenterOf(board);
        if (!this.isLegalMove(next)) {
            state = GameState.GAME_OVER;
        } else current = next;
    }

    private void calculateScore() {
        int removedLines = board.removeFullRows();
        if(!checkPerfectClear()) {
            if(removedLines == 1) this.score += 100 * level;
            else if(removedLines == 2) this.score += 300 * level;
            else if(removedLines == 3) this.score += 500 * level;
            else if(removedLines == 4) this.score += 800 * level;
        } else {
            if(removedLines == 1) this.score += 800 * level;
            else if(removedLines == 2) this.score += 1200 * level;
            else if(removedLines == 3) this.score += 1800 * level;
            else if(removedLines == 4) this.score += 2000 * level;
        }
        this.linesCleared += removedLines;
        if(canLevelUp()) this.level++;
    }

    private boolean checkPerfectClear() {
        for (GridCell<Character> cell : board) {
            if (cell.value() != '-') return false;
        }
        return true;
    }

    private boolean canLevelUp() {
        if (this.linesCleared >= 10) {
            this.linesCleared -= 10;
            return true;
        }
        return false;
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public int getMillisPerTick() {
        double g = Math.pow(0.8-((level - 1) * 0.007), level - 1);
        return (int) (1000 * g);
    }

    @Override
    public void clockTick() {
        if (state == GameState.ACTIVE_GAME) {
            if (!this.moveTetromino(1, 0)) {
                this.placeTetromino();
            }
        }
    }

    @Override
    public void setGameState(GameState state) {
        this.state = state;
    }

    @Override
    public void resetBoard() {
        this.board = new TetrisBoard(board.rows(), board.cols());
        this.linesCleared = 0;
        this.level = 1;
        this.score = 0;
        this.current = factory.getNext().shiftedToTopCenterOf(board);
    }

    @Override
    public int addToScore(int score) {
        this.score += score;
        return this.score;
    }

    @Override
    public void switchTetromino() {
        if (hold == null) {
            hold = current.shiftedToZero(board).defaultRotation();
            getNewTetromino();
        } else {
            Tetromino temp = current.shiftedToZero(board).defaultRotation();
            current = hold.shiftedToTopCenterOf(board);
            hold = temp;
        }
    }

}
