package assignments.Ex3.tests;
import assignments.Ex3.Index2D;
import exe.ex3.game.GhostCL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

public class testEx3Algo {

    ////////////important///////////////////
    /**
     * because I made the tests with specific parameters you need to also have them in order to test
     * double time_switch_to_run = 1;
     * min_distance = 5;
     * max_eat_ghost_dis =8;
     *
     */

    @Test
    void testClosesGhost(){
        int[][] board = {
                {1, 1},
                {1, 1},
                {1, 1},
                {1, 1}
        };

        Index2D pacman = new Index2D(0,0);

    }






}
