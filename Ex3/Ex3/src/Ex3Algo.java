package assignments.Ex3;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

import java.awt.*;
import java.util.Arrays;

import static assignments.Ex3.GameInfo.CYCLIC_MODE;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo{
    private int _count;
    //new variable path, declared here because don't want it to be declared multiple times

    public Ex3Algo() {_count=0;}
    @Override
    /**
     *  Add a short description for the algorithm as a String.
     */
    public String getInfo() {
        return null;
    }
    @Override
    /**
     * This ia the main method - that you should design, implement and test.
     */
    public int move(PacmanGame game) {
        if(_count==0 || _count==300) {
            int code = 0;
            int[][] board = game.getGame(0);
            printBoard(board);
            int blue = Game.getIntColor(Color.BLUE, code);
            int pink = Game.getIntColor(Color.PINK, code);
            int black = Game.getIntColor(Color.BLACK, code);
            int green = Game.getIntColor(Color.GREEN, code);
            System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
            String pos = game.getPos(code).toString();
            System.out.println("Pacman coordinate: "+pos);
            GhostCL[] ghosts = game.getGhosts(code);
            printGhosts(ghosts);
            int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
        }
        _count++;
        //get the board,pacman position and ghosts
        String[] parts = game.getPos(0).toString().split(",");
        Index2D pacman = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        int[][] board = game.getGame(0);
        GhostCL[] ghosts = game.getGhosts(0);

        int dir = calculate_path(ghosts,pacman,board);
        return dir;
    }

    private static void printBoard(int[][] b) {
        for(int y =0;y<b[0].length;y++){
            for(int x =0;x<b.length;x++){
                int v = b[x][y];
                System.out.print(v+"\t");
            }
            System.out.println();
        }
    }

    private static void printGhosts(GhostCL[] gs) {
        for(int i=0;i<gs.length;i++){
            GhostCL g = gs[i];
            System.out.println(i+") status: "+g.getStatus()+",  type: "+g.getType()+",  pos: "+g.getPos(0)+",  time: "+g.remainTimeAsEatable(0));
        }
    }

    private static int randomDir() {
        int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
        int ind = (int)(Math.random()*dirs.length);
        return dirs[ind];
    }

    ////////////////////////////////////////

    /**
     * moves pacman almost randomly, right, left, up and down, based on if there is a pink point.
     */
    private static int moveP_next(Index2D pacman,int [][] board){
        int ans =0;
        boolean danger = false;
        Map game = new Map(board);

        while(!danger){

            //get left right up down based on if cyclic
            Index2D right = new Index2D(pacman.getX() + 1, pacman.getY());
            Index2D left = new Index2D(pacman.getX() - 1, pacman.getY());
            Index2D up = new Index2D(pacman.getX(), pacman.getY() + 1);
            Index2D down = new Index2D(pacman.getX(), pacman.getY() - 1);
            if(CYCLIC_MODE){
                right = new Index2D((pacman.getX() + 1) %game.getWidth(), pacman.getY());
                left = new Index2D((pacman.getX() - 1 + game.getWidth()) %game.getWidth(), pacman.getY());
                up = new Index2D(pacman.getX(), (pacman.getY() + 1) % game.getHeight());
                down = new Index2D(pacman.getX(), (pacman.getY() - 1 + game.getHeight()) % game.getHeight());
            }
            if(game.getPixel(right)==3){
                return Game.RIGHT;
            }
            if(game.getPixel(left)==3){
                return Game.LEFT;
            }
            if(game.getPixel(up)==3){
                return Game.UP;
            }
            if(game.getPixel(down)==3){
                return Game.DOWN;
            }else{
                Pixel2D [] path = closestPoint(pacman, board);
                System.out.println(Arrays.toString(path));
                return followPath(pacman,path,board);
            }
        }
        return ans;
    }


    /**
     * return the index2D of the closest ghost to pacman
     * @param pacman - the Index2D of pacman
     * @param board - represents the game's matrix
     * @param ghosts - an array with all the ghosts
     * @return Index2D of the closest ghost to pacman
     */
    private static Index2D closestGhost(Index2D pacman,int[][] board,GhostCL[] ghosts){
        int closest = 0;
        Index2D ghost_cord = null;
        double min_dis = Double.POSITIVE_INFINITY;

        for(int i =0 ;i<ghosts.length;i++) {
            String[] parts = ghosts[i].getPos(0).split(",");
            ghost_cord = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            if((ghost_cord.distance2D(pacman) <  min_dis) && (ghosts[i].getStatus()==1) && !(isGhost_in_start(ghosts[i]))) {
                min_dis = ghost_cord.distance2D(pacman);
                closest = i;
            }
        }
        String[] parts_closest = ghosts[closest].getPos(0).split(",");
        Index2D ghost_cord_closest = new Index2D(Integer.parseInt(parts_closest[0]), Integer.parseInt(parts_closest[1]));

        return  ghost_cord_closest;
    }


    /**
     * return the path to the closest pink or green point(pink or green based on what is closer)
     * @param pacman - the Index2D of pacman
     * @param board - represents the game's matrix
     * @return - and array of Pixel2D representing the path to the closest point(pink or green)
     */
    private static Pixel2D[] closestPoint(Index2D pacman,int [][] board){
        Pixel2D[] ans = null;
        int dis =1;
        Map game = new Map(board);
        Map2D copy = new Map(board);
        copy = copy.allDistance(pacman,1);
        while(ans == null){
            for(int i =0;i<game.getHeight();i++){
                for(int j =0;j<game.getWidth();j++){
                    if((game.getPixel(j,i) == 3 || game.getPixel(j,i) == 5) && copy.getPixel(j,i) == dis){
                        Index2D des = new Index2D(j,i);
                        ans = game.shortestPath(pacman,des,1);
                    }
                    if (ans != null) {
                        return ans; // Found a valid path,exit
                    }
                }
            }
            dis++;
        }

        return  ans;
    }


    /**
     * return if a ghost is in the start
     * @param ghost - GhostCl representing a ghost
     * @return true if the ghost is in the starting rectangle false if isn't
     */
    private static boolean isGhost_in_start(GhostCL ghost){
        boolean ans = false;
        String[] parts_closest = ghost.getPos(0).split(",");
        Index2D ghost_cord_ = new Index2D(Integer.parseInt(parts_closest[0]), Integer.parseInt(parts_closest[1]));

        Index2D[] arr = new Index2D[12];
        arr[0] = new Index2D(11,11);
        arr[1] = new Index2D(11,12);
        arr[2] = new Index2D(11,13);
        arr[3] = new Index2D(11,14);
        arr[4] = new Index2D(12,11);
        arr[5] = new Index2D(13,11);
        arr[6]  = new Index2D(13,12);
        arr[7]  = new Index2D(12,12);
        arr[8]  = new Index2D(10,12);
        arr[9]  = new Index2D(10,11);
        arr[10]  = new Index2D(9,11);
        arr[11]  = new Index2D(9,12);

        for(int i=0;i<arr.length;i++){
            if(ghost_cord_.equals(arr[i])){
                return true;
            }
        }
        return false;
    }


    /**
     * return the path to the closest ghost
     * @param ghosts - an array of GhostCl containing all the ghosts
     * @param pacman - the Index2D of pacman
     * @param board - represents the game's matrix
     * @return an array of Pixel2D representing the path to the closest ghost
     */
    private static Pixel2D[] closestGhost_path(GhostCL[] ghosts,Index2D pacman,int [][] board){
        Pixel2D[] ans = null;
        Map  game = new Map(board);

        Index2D ghost_cord_closest = closestGhost(pacman,board,ghosts);

        ans = game.shortestPath(pacman,ghost_cord_closest,1);
        return ans;
    }


    /**
     * moves pacman in the direction of a given path
     * @param pacman - the Index2D of pacman
     * @param path - an array of Pixel2D representing the path which pacman should move by
     * @param board - represents the game's matrix
     * @return returns int(right -4, left -2, up-1, down-3)
     */
    private static int followPath(Index2D pacman,Pixel2D[] path,int [][] board){
        Map game  = new Map(board);

        //get left right up down based on if cyclic
        Index2D right = new Index2D(pacman.getX() + 1, pacman.getY());
        Index2D left = new Index2D(pacman.getX() - 1, pacman.getY());
        Index2D up = new Index2D(pacman.getX(), pacman.getY() + 1);
        Index2D down = new Index2D(pacman.getX(), pacman.getY() - 1);
        if(CYCLIC_MODE){
            right = new Index2D((pacman.getX() + 1) %game.getWidth(), pacman.getY());
            left = new Index2D((pacman.getX() - 1 + game.getWidth()) %game.getWidth(), pacman.getY());
            up = new Index2D(pacman.getX(), (pacman.getY() + 1) % game.getHeight());
            down = new Index2D(pacman.getX(), (pacman.getY() - 1 + game.getHeight()) % game.getHeight());
        }

        if(right.equals(path[1])){
            return Game.RIGHT;
        }
        if(left.equals(path[1])){
            return Game.LEFT;
        }
        if(up.equals(path[1])){
            return Game.UP;
        }
        if(down.equals(path[1])){
            return Game.DOWN;
        }
        return Game.DOWN;
    }


    /**
     * returns if pacman is in eat ghosts state (remain time to eat is bigger than time_switch_to_run)
     * @param ghosts an array of ghosts
     * @return true if pacman should be in eat state, false if isn't
     */
    private static boolean eat_state(GhostCL[] ghosts,Index2D pacman,int[][] board){
        boolean ans = false;
        Map game = new Map(board);
        for (GhostCL ghost : ghosts) {
            String[] parts = ghost.getPos(0).split(",");
            Index2D ghost_cord = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            Pixel2D[] path = game.shortestPath(pacman,ghost_cord,1);
            if (ghost.remainTimeAsEatable(0) > Parameters.time_switch_to_run && ghost.getStatus() == 1 && !(isGhost_in_start(ghost)) && (path.length) <= Parameters.max_eat_ghost_dis) {
                return true;
            }
        }
        return ans;
    }


    /**
     * return if pacman is within min_distance of a ghost(if pacman should run)
     * @param ghosts an array of the ghosts
     * @param pacman the coordinates of pacman(pacman himself)
     * @param board the board of the game
     * @return true if pacman should run false if he shouldn't
     */
    private static boolean run_state(GhostCL[] ghosts,Index2D pacman,int [][] board){
        boolean ans = false;
        Map game = new Map(board);
        Index2D ghost_cord = null;

        for(GhostCL ghost : ghosts){
            String[] parts = ghost.getPos(0).split(",");
            ghost_cord = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            if(!(ghost.remainTimeAsEatable(0) > Parameters.time_switch_to_run) && ghost.getStatus() == 1 && !(isGhost_in_start(ghost))){
                Pixel2D[] path = game.shortestPath(pacman,ghost_cord,1);
                if (pacman.distance2D(ghost_cord)< Parameters.min_distance){
                    return true;
                }
            }
        }

        return ans;
    }


    /**
     * return if the pos it got is a dead end
     * @param pos - Index2D representing pacman potential next move
     * @param game - the map of the game
     * @return true if pos is a dead end false if it isn't
     */
    private static boolean isDeadEnd(Index2D pos,Map game) {
        int paths = 0;

        // Get neighbors based on your cyclic logic
        Index2D right = new Index2D(pos.getX() + 1, pos.getY());
        Index2D left = new Index2D(pos.getX() - 1, pos.getY());
        Index2D up = new Index2D(pos.getX(), pos.getY() + 1);
        Index2D down = new Index2D(pos.getX(), pos.getY() - 1);

        if (CYCLIC_MODE) {
            right = new Index2D((pos.getX() + 1) % game.getWidth(), pos.getY());
            left = new Index2D((pos.getX() - 1 + game.getWidth()) % game.getWidth(), pos.getY());
            up = new Index2D(pos.getX(), (pos.getY() + 1) % game.getHeight());
            down = new Index2D(pos.getX(), (pos.getY() - 1 + game.getHeight()) % game.getHeight());
        }

        // Check each neighbor - if it's NOT a wall (1), it's a valid path
        if (game.getPixel(right) != 1) paths++;
        if (game.getPixel(left) != 1)  paths++;
        if (game.getPixel(up) != 1)    paths++;
        if (game.getPixel(down) != 1)  paths++;

        // A dead end is a tile where you only have 1 neighbor to move to
        // (The one you just came from)
        return paths <= 1;
    }


    /**
     * returns the distance of pacman from a ghost
     * @param direction Index2D representing pacman or pacman's next move
     * @param ghost - Index2D of a ghost
     * @param board - represents the game's matrix
     * @return a double that is the distance of direction from a ghost
     */
    private static double dis_from_ghost(Pixel2D direction, Index2D ghost, int[][] board) {
        Map game = new Map(board);
        Pixel2D[] path = game.shortestPath(direction, ghost, 1);
        return path.length;
    }


    /**
     * calculates the danger in a specific point(Pacman's potential next move), closer ghosts have more impact on danger
     * @param direction - Index2D representing pacman's potential next move
     * @param ghosts - an array of GhostCl representing all the ghosts in the game
     * @param board - represents the game's matrix
     * @return a double that represents the danger a certain coordinates in the game
     */
    private static double calculateDanger(Pixel2D direction, GhostCL[] ghosts, int[][] board) {
        double danger = 0;
        Index2D ghost_cord = null;
        for (GhostCL g : ghosts) {
            if (g.getStatus() == 1 && !isGhost_in_start(g)) {
                String[] parts = g.getPos(0).split(",");
                ghost_cord = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                double d = dis_from_ghost(direction, ghost_cord, board);
                d++;
                // Closer ghosts are more dangerous than far ones, +1 to not divide by zero
                danger += 1000.0 / ((d * d) + 1);
                if(d<=1){
                    danger = danger+1000;
                }
            }
        }
        return danger;
    }


    /**
     * return where to move pacman in order to run away from teh ghosts in the most efficient way
     * @param ghosts an array of GhostCl representing all the ghosts in the game
     * @param pacman - Index2D of pacman
     * @param board -represents the game's matrix
     * @return an int based on where pacman should go next in order to run away from the ghosts(right -4, left -2, up-1, down-3)
     */
    private static int run(GhostCL[] ghosts, Index2D pacman, int [][] board){
        Map game = new Map(board);
        double max_score = -Double.MAX_VALUE;
        int move = 0;
        double totalDanger;

        Index2D closest_ghost = closestGhost(pacman, board, ghosts);

        //get left right up down based on if cyclic
        Index2D right = new Index2D(pacman.getX() + 1, pacman.getY());
        Index2D left = new Index2D(pacman.getX() - 1, pacman.getY());
        Index2D up = new Index2D(pacman.getX(), pacman.getY() + 1);
        Index2D down = new Index2D(pacman.getX(), pacman.getY() - 1);
        if(CYCLIC_MODE){
            right = new Index2D((pacman.getX() + 1) %game.getWidth(), pacman.getY());
            left = new Index2D((pacman.getX() - 1 + game.getWidth()) %game.getWidth(), pacman.getY());
            up = new Index2D(pacman.getX(), (pacman.getY() + 1) % game.getHeight());
            down = new Index2D(pacman.getX(), (pacman.getY() - 1 + game.getHeight()) % game.getHeight());
        }
        Index2D[] moves = {right,left,up,down};

        for (Index2D direction : moves) {

            if (game.getPixel(direction) != 1) {
                double ghostDanger = calculateDanger(direction,ghosts,board);
                double greenDist = distToNearestGreen(direction, board);

                //distance from ghost more important than green
                totalDanger = -(ghostDanger * 5) - (greenDist*2);

                if (isDeadEnd(direction,game)) {
                    totalDanger -= 500.0; //penalty for traps
                }
                if (Double.isInfinite(totalDanger)) {
                    totalDanger = -1000000.0*Parameters.min_distance;
                }

                System.out.println(totalDanger);
                if (totalDanger > max_score) {
                    max_score = totalDanger;
                    if(direction.equals(right)){
                        if(game.getPixel(right)==5){
                            return Game.RIGHT;
                        }
                        move = Game.RIGHT;
                    } else if (direction.equals(left)) {
                        if(game.getPixel(left)==5){
                            return Game.LEFT;
                        }
                        move = Game.LEFT;
                    } else if (direction.equals(up)) {
                        if(game.getPixel(up)==5){
                            return Game.UP;
                        }
                        move = Game.UP;
                    } else if (direction.equals(down)) {
                        if(game.getPixel(down)==5){
                            return Game.DOWN;
                        }
                        move = Game.DOWN;
                    }
                }
            }
        }
        if(move ==0 ){
            System.out.println("rand");
            return randomDir();
        }else{
            return  move;
        }
    }


    /**
     * if pacman no in run state or eat state then he should collect pinks
     * @param ghosts an array of the ghosts
     * @param pacman the coordinates of pacman(pacman himself)
     * @param board the board of the game
     * @return true if pacman is in eat pink state, false if isn't
     */
    private static boolean eat_pink_state(GhostCL[] ghosts,Index2D pacman,int [][] board){
        if(eat_state(ghosts,pacman,board)){
            return false;
        }else if(run_state(ghosts,pacman,board)){
            return false;
        }
        return true;
    }


    /**
     * return the distance to the nearest green point
     * @param pos - pacman's next potential move
     * @param board - represents the game's matrix
     * @return return a double the is equals to the distance of pos from the next green point
     */
    private static double distToNearestGreen(Pixel2D pos, int[][] board) {
        Map game = new Map(board);
        double min_dist = Double.POSITIVE_INFINITY;

        for (int y = 0; y < game.getHeight(); y++) {
            for (int x = 0; x < game.getWidth(); x++) {
                if (game.getPixel(x, y) == 5) {
                    Index2D point = new Index2D(x, y);
                    double d = (game.shortestPath(pos,point,1)).length;
                    if (d < min_dist) min_dist = d;
                }
            }
        }
        return min_dist;
    }


    /**
     * decides the final move of pacman, based on danger, eating pink points and eating ghosts
     * @param ghosts - an array of GhostCl representing all the ghosts in the game
     * @param pacman - Index2D representing pacman
     * @param board - represents the game's matrix
     * @return return and int based on where pacman should move
     */
    private static int calculate_path(GhostCL[] ghosts,Index2D pacman,int [][] board){
        int direction = Game.RIGHT;
        Pixel2D[] path = null;
        boolean run = run_state(ghosts,pacman,board);
        boolean eat = eat_state(ghosts,pacman,board);
        boolean collect =  eat_pink_state(ghosts,pacman,board);

        if (collect) {
            path = closestPoint(pacman, board);
            if(path == null) System.out.println("Debug: Failed to find path to PINK DOT");
        } else if (eat) {
            path = closestGhost_path(ghosts, pacman, board);
            if(path == null) System.out.println("Debug: Failed to find path to EATABLE GHOST");
        } else if (run) {
            System.out.println("run");
            return run(ghosts, pacman, board);
        }

        if(path == null){
            System.out.println("path is null");
            return randomDir();
        }

        direction = followPath(pacman,path,board);
        return direction;
    }

}