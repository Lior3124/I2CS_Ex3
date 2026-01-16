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
    private Pixel2D [] path = null;

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

        //get the path to the closest point and then move with the path
        path = closestPoint(pacman, board);
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

    private static Pixel2D[] closestGhost_path(GhostCL[] ghosts,Index2D pacman,int [][] board){
        Pixel2D[] ans = null;
        Map  game = new Map(board);
        int closest = 0;
        Index2D ghost_cord = null;
        double min_dis = Double.POSITIVE_INFINITY;

        for(int i =0 ;i<ghosts.length;i++) {
            String[] parts = ghosts[i].getPos(0).split(",");
            ghost_cord = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            System.out.println(!(isGhost_in_start(ghosts[i])));
            if((ghost_cord.distance2D(pacman) <  min_dis) && (ghosts[i].getStatus()==1) && !(isGhost_in_start(ghosts[i]))) {
                min_dis = ghost_cord.distance2D(pacman);
                closest = i;
            }
        }
        String[] parts_closest = ghosts[closest].getPos(0).split(",");
        Index2D ghost_cord_closest = new Index2D(Integer.parseInt(parts_closest[0]), Integer.parseInt(parts_closest[1]));
        ans = game.shortestPath(pacman,ghost_cord_closest,1);
        return ans;
    }

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
    private static boolean eat_state(GhostCL[] ghosts){
        boolean ans = false;
        for (GhostCL ghost : ghosts) {
            if (ghost.remainTimeAsEatable(0) > Parameters.time_switch_to_run && ghost.getStatus() == 1 && !(isGhost_in_start(ghost))) {
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
            if(!(ghost.remainTimeAsEatable(0) > Parameters.time_switch_to_run)){
                if (ghost_cord.distance2D(pacman) < Parameters.min_distance){
                    return true;
                }
            }
        }

        return ans;
    }

    /**
     * if pacman no in run state or eat state then he should collect pinks
     * @param ghosts an array of the ghosts
     * @param pacman the coordinates of pacman(pacman himself)
     * @param board the board of the game
     * @return true if pacman is in eat pink state, false if isn't
     */
    private static boolean eat_pink_state(GhostCL[] ghosts,Index2D pacman,int [][] board){
        if(eat_state(ghosts)){
            return false;
        }else if(run_state(ghosts,pacman,board)){
            return false;
        }
        return true;
    }


    private static int calculate_path(GhostCL[] ghosts,Index2D pacman,int [][] board){
        int direction = Game.RIGHT;
        Pixel2D[] path = null;
        boolean run = run_state(ghosts,pacman,board);
        boolean eat = eat_state(ghosts);
        boolean collect =  eat_pink_state(ghosts,pacman,board);

        if(collect){
            path = closestPoint(pacman, board);
        } else if (eat) {
            path = closestGhost_path(ghosts, pacman, board);
        } else if (run) {
            path  = closestPoint(pacman, board);
        }

        direction = followPath(pacman,path,board);
        return direction;
    }

}