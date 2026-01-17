package assignments.Ex3.new_game;

import assignments.Ex3.Index2D;
import assignments.Ex3.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.classes.MyGame;
import assignments.Ex3.classes.MyGhost;

import java.util.ArrayList;

public class EX3_NEW_MAIN {
    public static void play1() {
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
        int dt = 50;
        boolean cyclic_mode = true;
        for (int i = 0; i < 5; i++) {
            // We pass the starting position and the shared game seed
            ghosts.add(new MyGhost(ghostHouse, seed));
        }
        ex3.init(map, pacman, ghosts, seed, dt, cyclic_mode);



    }
}
