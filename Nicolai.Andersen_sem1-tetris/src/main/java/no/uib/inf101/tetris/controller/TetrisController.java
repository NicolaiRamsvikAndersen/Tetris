package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

public class TetrisController implements KeyListener {

    private ControllableTetrisModel model;
    private TetrisView view;
    private Timer timer;
    private TetrisSong song = new TetrisSong();

    /**
     * Create a new TetrisController.
     * 
     * @param model The model to control
     * @param view The view to control
     */
    public TetrisController(ControllableTetrisModel model, TetrisView view) {
        this.model = model;
        this.view = view;
        this.timer = new Timer(model.getMillisPerTick(), this::clockTick);
        view.setFocusable(true);
        view.addKeyListener(this);
        //TODO: add song.run();
        //song.run();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(model.getGameState() == GameState.ACTIVE_GAME) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                model.moveTetromino(0, -1);
                // Left arrow was pressed
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                model.moveTetromino(0, 1);
                // Right arrow was pressed
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                model.moveTetromino(1, 0);
                model.addToScore(1);
                timer.restart();
                // Down arrow was pressed
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                model.rotateTetromino();
                // Up arrow was pressed
            }
            else if (e.getKeyCode() == KeyEvent.VK_Z) {
                for (int i = 0; i < 3; i++) {
                    model.rotateTetromino();
                }
                // Z was pressed
            }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                model.dropTetromino();
                timer.restart();
                // Space was pressed
            }
            else if (e.getKeyCode() == KeyEvent.VK_C) {
                model.switchTetromino();
                // C was pressed
            }
        }
        if(model.getGameState() == GameState.START) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                model.setGameState(GameState.ACTIVE_GAME);
                timer.start();
                // Down arrow was pressed
            }
        }
        if(model.getGameState() == GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                timer.stop();
                model.resetBoard();
                model.setGameState(GameState.START);
                // Down arrow was pressed
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(model.getGameState() == GameState.PAUSED) {
                model.setGameState(GameState.ACTIVE_GAME);
                timer.start();
            } else if(model.getGameState() == GameState.ACTIVE_GAME) {
                model.setGameState(GameState.PAUSED);
                timer.stop();
            }
            // Enter was pressed
        }
        
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    private void clockTick(ActionEvent e) {
        model.clockTick();
        delay();
        view.repaint();
    }

    private void delay() {
        int millis = model.getMillisPerTick();
        timer.setDelay(millis);
        timer.setInitialDelay(millis);
    }
    
}
