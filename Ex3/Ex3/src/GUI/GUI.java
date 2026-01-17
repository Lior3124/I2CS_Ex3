package assignments.Ex3.GUI;

import assignments.Ex3.Index2D;
import assignments.Ex3.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.classes.MyGame;


import java.awt.Color;
import java.awt.event.KeyEvent;

public class GUI {

    private MyGame _game;
    private boolean _isStarted = false;

    public GUI(MyGame game) {
        this._game = game;
    }

    public void run() {
        Map map = _game.getMap();
        // Set canvas to match map proportions (multiplying by 20 for a good size)
        StdDraw.setCanvasSize(map.getWidth() * 20, map.getHeight() * 25);
        StdDraw.setXscale(-1, map.getWidth());
        StdDraw.setYscale(-1, map.getHeight());
        StdDraw.enableDoubleBuffering();

        while (_game.getStatus() == 0) {
            // Handle the "Wait for Start" logic
            if (!_isStarted) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                    _isStarted = true;
                }
            } else {
                _game.move(getInput());
            }

            drawBoard();
            StdDraw.show();
            StdDraw.pause(_game.get_dt());
        }

        drawEndScreen();
    }

    private void drawBoard() {
        StdDraw.clear(Color.BLACK);
        Map map = _game.getMap();

        // Draw Map Elements
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int pixel = map.getPixel(x, y);

                if (pixel == 1) { // Wall: Blue hollow squares
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.square(x, y, 0.4);
                } else if (pixel == 3) { // Pink small dots
                    StdDraw.setPenColor(new Color(255, 182, 193)); // Light pink
                    StdDraw.filledCircle(x, y, 0.1);
                } else if (pixel == 5) { // Green Power Pellets
                    StdDraw.setPenColor(Color.GREEN);
                    StdDraw.filledCircle(x, y, 0.35);
                }
            }
        }

        // Draw Pacman (Yellow semi-circle)
        Index2D p = _game.get_pacman();
        StdDraw.setPenColor(Color.YELLOW);
        // Drawing a circle with a small "mouth" cutout
        StdDraw.filledCircle(p.getX(), p.getY(), 0.45);

        // Draw Ghosts
        for (int i = 0; i < _game.getGhosts().size(); i++) {
            Ghost g = _game.getGhosts().get(i);
            if (g.getStatus() == 0) continue; // Don't draw if "in jail"

            if (g.getRemainTimeAsEatable() > 0) {
                StdDraw.setPenColor(Color.WHITE); // Or Cyan for "frightened" mode
            } else {
                // Assign classic colors
                Color[] colors = {Color.RED, Color.PINK, Color.CYAN, Color.ORANGE};
                StdDraw.setPenColor(colors[i % colors.length]);
            }
            // Simple square for the ghost body
            StdDraw.filledRectangle(g.getPos().getX(), g.getPos().getY(), 0.4, 0.4);
        }

    }

    private int getInput() {
        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) return _game.UP;
        if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) return _game.DOWN;
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) return _game.LEFT;
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) return _game.RIGHT;
        return 0;
    }

    private void drawEndScreen() {
        StdDraw.setPenColor(Color.WHITE);
        String txt = (_game.getStatus() == 1) ? "YOU WIN!" : "GAME OVER";
        StdDraw.text(_game.getMap().getWidth()/2.0, _game.getMap().getHeight()/2.0, txt);
        StdDraw.show();
    }
}