package assignments.Ex3.new_game;

import assignments.Ex3.Index2D;
import assignments.Ex3.new_game.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.new_game.classes.MyGame;
import assignments.Ex3.new_game.classes.MyGhost;

import java.util.ArrayList;

public class EX3_NEW_MAIN {

    public static void main(String[] args) {
        game_settings gs = new game_settings();

        play1(gs.getBoard(),gs.getPacman(),gs.getGhostHouse(),gs.getSeed(), gs.getDt(),gs.isCyclic_mode(), gs.getDifficulty());
    }


    public static void play1(int[][] board,Index2D pacman,Index2D ghostHouse,int seed,int dt,boolean cyclic_mode,int difficulty) {
        new_algo algo = new new_algo();
        Map map = new Map(board);
        MyGame ex3 = new MyGame();
        ArrayList<Ghost> ghosts = new ArrayList<>();

        ex3.setDifficulty(difficulty);

        for (int i = 0; i < 5; i++) {
            ghosts.add(new MyGhost(ghostHouse, seed));
        }
        ex3.init(map, pacman, ghosts, seed, dt, cyclic_mode);

        ex3.play();


    }
}
