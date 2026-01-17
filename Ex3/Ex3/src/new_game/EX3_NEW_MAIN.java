package assignments.Ex3.new_game;

import assignments.Ex3.GUI.GUI;
import assignments.Ex3.GUI.StdDraw;
import assignments.Ex3.Index2D;
import assignments.Ex3.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.classes.MyGame;
import assignments.Ex3.classes.MyGhost;

import java.util.ArrayList;

public class EX3_NEW_MAIN {

    public static void main(String[] args) {
        play1();
    }


    public static void play1() {
        new_algo algo = new new_algo();
        int[][] board = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 3, 1},
                {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 3, 1},
                {1, 3, 1, 3, 3, 5, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 5, 3, 3, 1},
                {1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 1, 1, 3, 1},
                {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
                {1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1},
                {1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1},
                {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1},
                {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 0, 0, 1, 3, 1, 3, 1, 3, 1, 3, 1},
                {1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 0, 0, 1, 3, 3, 3, 1, 3, 3, 3, 1},
                {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 0, 0, 0, 0, 1, 1, 1, 3, 1, 1, 1},
                {1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 0, 0, 1, 3, 3, 3, 1, 3, 3, 3, 1},
                {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 0, 0, 1, 3, 1, 3, 1, 3, 1, 3, 1},
                {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1},
                {1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1},
                {1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1},
                {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
                {1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 1, 1, 3, 1},
                {1, 3, 1, 3, 3, 5, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 5, 3, 3, 1},
                {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 3, 1},
                {1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 3, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        Map map = new Map(board);
        MyGame ex3 = new MyGame();
        Index2D pacman = new Index2D(11, 14);
        ArrayList<Ghost> ghosts = new ArrayList<>();
        Index2D ghostHouse = new Index2D(11, 11);
        int seed = 5;
        int dt = 100;
        boolean cyclic_mode = true;
        for (int i = 0; i < 5; i++) {
            ghosts.add(new MyGhost(ghostHouse, seed));
        }
        ex3.init(map, pacman, ghosts, seed, dt, cyclic_mode);

        GUI.init(ex3);
        boolean isGameStarted = false;

        while (ex3.getStatus() == 0) {

            if (!isGameStarted) {

                if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_SPACE)) {
                    isGameStarted = true;
                }
                GUI.draw(ex3);
                continue;
            }


            int nextDir = algo.move(ex3.getGhosts(), ex3.get_pacman(), ex3.getMap().getMap());

            ex3.move(nextDir);

            GUI.draw(ex3);

            StdDraw.pause(ex3.get_dt());
        }


    }
}
