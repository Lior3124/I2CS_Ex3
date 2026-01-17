package assignments.Ex3.GUI;

import assignments.Ex3.Index2D;
import assignments.Ex3.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.classes.MyGame;

import java.awt.Color;
import java.util.ArrayList;

public class GUI {

    //Initialize the window based on your map size
    public static void init(MyGame game) {
        int w = game.getMap().getWidth();
        int h = game.getMap().getHeight();
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(-1, w);
        StdDraw.setYscale(-1, h);
        StdDraw.enableDoubleBuffering();
    }

    public static void draw(MyGame game) {
        StdDraw.clear(Color.BLACK);

        //Draw the map (Walls and Dots)
        Map map = game.getMap();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int pixel = map.getPixel(x, y);
                if (pixel == 1) { // Wall
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.filledSquare(x, y, 0.5);
                } else if (pixel == 3 || pixel == 5) { // Dots/Powerups
                    StdDraw.setPenColor(pixel == 3 ? Color.PINK : Color.GREEN);
                    StdDraw.filledCircle(x, y, 0.2);
                }
            }
        }

        //Draw Pacman using your Index2D
        Index2D pac = game.get_pacman();
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.filledCircle(pac.getX(), pac.getY(), 0.5);

        // 3. Draw Ghosts using your ArrayList<Ghost>
        ArrayList<Ghost> ghosts = game.getGhosts();
        for (Ghost g : ghosts) {
            if (g.getStatus() == 1) { // Only draw if alive
                // Cyan if eatable, Red if dangerous
                if (g.getRemainTimeAsEatable() > 0) StdDraw.setPenColor(Color.CYAN);
                else StdDraw.setPenColor(Color.RED);

                StdDraw.filledCircle(g.getPos().getX(), g.getPos().getY(), 0.4);
            }
        }

        StdDraw.show();
    }
}