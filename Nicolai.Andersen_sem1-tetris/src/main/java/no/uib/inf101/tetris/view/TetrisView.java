package no.uib.inf101.tetris.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.TetrisBoard;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class TetrisView extends JPanel {

    private ViewableTetrisModel model;
    private ColorTheme theme;

    /**
     * Create a new TetrisView.
     * 
     * @param model
     *            The model to view
     * @param theme
     *            The color theme to use
     */
    public TetrisView(ViewableTetrisModel model, DefaultColorTheme theme) {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(500, 600));
        this.model = model;
        this.theme = theme;
        this.setBackground(theme.getBackgroundColor());
    }

    // The paintComponent method is called by the Java Swing framework every time
    // either the window opens or resizes, or we call .repaint() on this object.
    // Note: NEVER call paintComponent directly yourself
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    // The drawGame method is called by paintComponent, and is responsible for
    // drawing the entire game.
    private void drawGame(Graphics2D g2) {
        // ansvar å tegne et fullstendig rutenett, inkludert alle rammer og ruter.
        int width = this.getWidth();
        int height = this.getHeight();
        if (model.getGameState() == GameState.START) {
            Rectangle2D gameOverBox = new Rectangle2D.Double(0, 0, width, height);
            g2.setColor(theme.getGameOverColor());
            g2.fill(gameOverBox);
            g2.setColor(theme.getTextColor());
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            Inf101Graphics.drawCenteredString(g2, "Tetris", 0, 0, width, height);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            Inf101Graphics.drawCenteredString(g2, "Press down to start", 0, 40, width, height);
        }
        if (model.getGameState() == GameState.PAUSED) {
            Rectangle2D gameOverBox = new Rectangle2D.Double(0, 0, width, height);
            g2.setColor(theme.getGameOverColor());
            g2.fill(gameOverBox);
            g2.setColor(theme.getTextColor());
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            Inf101Graphics.drawCenteredString(g2, "Paused", 0, 0, width, height);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            Inf101Graphics.drawCenteredString(g2, "Score: " + Integer.toString(model.getScore()), 0, 40, width, height);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            Inf101Graphics.drawCenteredString(g2, "Level: " + Integer.toString(model.getLevel()), 0, 70, width, height);
        }
        if (model.getGameState() == GameState.ACTIVE_GAME || model.getGameState() == GameState.GAME_OVER) {

            final double OUTMARGIN = 40;
            final double WIDTHMARGIN = 100;
            final Color MARGINCOLOR = theme.getFrameColor();
            Rectangle2D boardBox = new Rectangle2D.Double(WIDTHMARGIN, OUTMARGIN, width - WIDTHMARGIN * 2, height - OUTMARGIN * 2);
            g2.setColor(MARGINCOLOR);
            g2.fill(boardBox);
            CellPositionToPixelConverter converter = new CellPositionToPixelConverter(boardBox, model.getDimension(), 2);
            drawCells(g2, model.getCells(), converter, theme);
            drawCells(g2, model.fallingTetromino(), converter, theme);
            drawHeldPiece(g2, model.heldTetromino(), theme);

            if (model.getGameState() == GameState.GAME_OVER) {
                Rectangle2D gameOverBox = new Rectangle2D.Double(0, 0, width, height);
                g2.setColor(theme.getGameOverColor());
                g2.fill(gameOverBox);
                g2.setColor(theme.getTextColor());
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                Inf101Graphics.drawCenteredString(g2, "Game over", 0, 0, width, height);
                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                Inf101Graphics.drawCenteredString(g2, "Score: " + Integer.toString(model.getScore()), 0, 40, width, height);
                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                Inf101Graphics.drawCenteredString(g2, "Level: " + Integer.toString(model.getLevel()), 0, 70, width, height);
            }
        }
    }

    // The drawCells method is called by drawGame, and is responsible for drawing
    // a collection of cells.
    private static void drawCells(Graphics2D g2, Iterable<GridCell<Character>> cells,
            CellPositionToPixelConverter converter, ColorTheme theme) {
        // ansvar å tegne en samling av ruter.
        // For hver rute regner denne metode ut hvor ruten skal være ved å kalle på
        // hjelpemetoden
        for (GridCell<Character> cell : cells) {
            Rectangle2D rect = converter.getBoundsForCell(cell.pos());
            g2.setColor(theme.getCellColor(cell.value()));
            g2.fill(rect);
            if(cell.value() != '-') drawCellEdge(g2, rect, 3);
        }
    }

    private static void drawCellEdge(Graphics2D g2, Rectangle2D rect, int edgeWidth) {
        // ansvar å tegne en ramme rundt en rute.
        double x = rect.getX();
        double y = rect.getY();
        double width = rect.getWidth();
        double height = rect.getHeight();
        Rectangle2D topEdge = new Rectangle2D.Double(x, y, width, edgeWidth);
        Rectangle2D leftEdge = new Rectangle2D.Double(x, y, edgeWidth, height);
        Rectangle2D bottomEdge = new Rectangle2D.Double(x, y + height - edgeWidth, width, edgeWidth);
        Rectangle2D rightEdge = new Rectangle2D.Double(x + width - edgeWidth, y, edgeWidth, height);
        
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fill(topEdge);
        g2.fill(leftEdge);
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fill(bottomEdge);
        g2.fill(rightEdge);
    }

    private void drawHeldPiece(Graphics2D g2, Iterable<GridCell<Character>> cells, ColorTheme theme) {
        // ansvar å tegne den tetrominoen som spilleren har holdt.
        Rectangle2D holdBox = new Rectangle2D.Double(10, 40, 80, 80);
        TetrisBoard board = new TetrisBoard(4, 4);
        g2.setColor(theme.getFrameColor());
        g2.fill(holdBox);
        if (cells != null) {
            for (GridCell<Character> cell : cells) {
                if(cell.value() == 'I') {
                    holdBox = new Rectangle2D.Double(10, 50, 80, 80);
                }
                else if ("JLSTZ".indexOf(cell.value()) != -1) {
                    holdBox = new Rectangle2D.Double(20, 40, 80, 80);
                }
            }
            CellPositionToPixelConverter converter = new CellPositionToPixelConverter(holdBox, board, 2);
            drawCells(g2, cells, converter, theme);
        }
    }
}