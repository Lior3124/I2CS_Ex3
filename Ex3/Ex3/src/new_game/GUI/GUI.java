package assignments.Ex3.new_game.GUI;

import assignments.Ex3.Index2D;
import assignments.Ex3.new_game.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.new_game.classes.MyGame;

import java.awt.Color;

public class GUI {

    public static void init(MyGame game) {
        int w = game.getMap().getWidth();
        int h = game.getMap().getHeight();
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.enableDoubleBuffering();
    }


    public static void draw(MyGame game, boolean isGameStarted) {
        StdDraw.clear(Color.WHITE);
        Map map = game.getMap();
        for (int x = 0; x <= map.getWidth(); x++) {
            for (int y = 0; y <= map.getHeight(); y++) {
                int pixel = map.getPixel(x, y);
                if (pixel == 1) { // Wall
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.filledSquare(x, y, 0.5);
                    StdDraw.picture(x, y, "wall1.png", 0.5 * 2, 0.5 * 2);
                } else if (pixel == 3 || pixel == 5) { // Dots/Powerups
                    if(pixel ==3) {
                        StdDraw.picture(x, y, "point2.png", 0.4 * 2, 0.4 * 2);
                    }else{
                        StdDraw.picture(x, y, "point6.jpg", 0.4 * 2, 0.4 * 2);
                    }
                }
            }
        }


        // 2. Draw Pacman
        Index2D pac = game.get_pacman();
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.filledCircle(pac.getX(), pac.getY(), 0.4);
        StdDraw.picture(pac.getX(), pac.getY(), "pacman3.jpg", 0.4 * 2, 0.4 * 2);

        // 3. Draw Ghosts
        for (Ghost g : game.getGhosts()) {
            if (g.getStatus() == 1) {
                if (g.getRemainTimeAsEatable() > 0){
                    StdDraw.setPenColor(Color.CYAN);
                    StdDraw.picture(g.getPos().getX(), g.getPos().getY(), "ghost2.jpg", 0.4 * 2, 0.4 * 2);

                }
                else {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.picture(g.getPos().getX(), g.getPos().getY(), "ghost1.png", 0.4 * 2, 0.4 * 2);
                }
            }
        }

        // 4. Start Button
        if (!isGameStarted) {
            drawStartScreen(map.getWidth(), map.getHeight());
            playBackgroundMusic("pacman_beginning.wav",0.1);
        }

        StdDraw.show();
    }

    public static void playBackgroundMusic(String filename, double volume) {
        new Thread(() -> {
            try {
                // Read the sound file into an array of samples
                double[] samples = StdAudio.read(filename);

                // Multiply every sample by the volume factor (e.g., 0.2 for 20% volume)
                for (int i = 0; i < samples.length; i++) {
                    samples[i] *= volume;
                }

                // Loop the modified samples
                while (true) {
                    StdAudio.play(samples);
                }
            } catch (Exception e) {
                System.out.println("Error playing music: " + e.getMessage());
            }
        }).start();
    }

    private static void drawStartScreen(int w, int h) {
        StdDraw.setPenColor(new Color(0, 0, 0, 150)); // Dark overlay
        StdDraw.filledRectangle(w / 2.0, h / 2.0, w, h);
        StdDraw.setPenColor(Color.PINK);
        StdDraw.text(w / 2.0, h / 2.0, "Press 'SPACE' to Start Game");
    }

}
