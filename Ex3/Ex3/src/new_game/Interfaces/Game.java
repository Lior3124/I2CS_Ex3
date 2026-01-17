package assignments.Ex3.new_game.Interfaces;


import assignments.Ex3.Index2D;
import assignments.Ex3.Map;

import java.util.ArrayList;

public interface Game {

    /**
     *
     * Constructs a game
     * @param map - the board of the game
     * @param pacman - the coordinates(Index2D) of pacman
     * @param ghosts - and arraylist of the ghosts
     * @param seed - random seed
     * @param dt - higher number mean pacman moves slower
     * @param cyclic_mode - is cyclic
     */
    public void init(Map map, Index2D pacman, ArrayList<Ghost> ghosts, int seed, int dt,boolean cyclic_mode);

    /**
     * moves pacman and the ghosts(moves the game)
     */
    public void move(int direction);

    /**
     * @return an integer representing the status of the game
     */
    public int getStatus();

    /**
     * @return the map of the game
     */
    public Map getMap();

    /**
     * @return an arraylist of the ghosts
     */
    public ArrayList<Ghost> getGhosts();

    /**
     * @return the time that the game is running
     */
    public double getTime();

}
