package assignments.Ex3;

/**
 * A class that has all the parameters needed for the pacman algo
 */
public class Parameters {
    // when to switch pacman mode to run from the ghosts(based on remain time to eat)
    public static final double time_switch_to_run = 1.0;

    //min distance to eat(if less than pacman runs away from ghosts)
    public static final int min_distance = 3;

    //max distance to eat ghosts(above that distance pacman continues to eat pink points)
    public static final int max_eat_ghost_dis =5;

}
