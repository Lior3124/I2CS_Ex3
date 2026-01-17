package assignments.Ex3.new_game;

import assignments.Ex3.*;
import assignments.Ex3.new_game.Interfaces.Ghost;
import exe.ex3.game.Game;

import java.util.ArrayList;
import static assignments.Ex3.GameInfo.CYCLIC_MODE;

public class new_algo {
    private int _count;
    private int _prevDir = -1;


    // Removed the old 'move(PacmanGame game)' because PacmanGame doesn't use ArrayList<Ghost>
    // This is the new entry point for MyGame class
    public int move(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board) {
        _count++;
        int dir = calculate_path(ghosts, pacman, board, _prevDir);
        _prevDir = dir;
        return dir;
    }

    private static int randomDir() {
        int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
        int ind = (int) (Math.random() * dirs.length);
        return dirs[ind];
    }

    private static Index2D closestGhost(Index2D pacman, int[][] board, ArrayList<Ghost> ghosts) {
        int closest = 0;
        double min_dis = Double.POSITIVE_INFINITY;

        for (int i = 0; i < ghosts.size(); i++) {
            Index2D ghost_cord = ghosts.get(i).getPos(); // No more String splitting
            if ((ghost_cord.distance2D(pacman) < min_dis) && (ghosts.get(i).getStatus() == 1) && !(isGhost_in_start(ghosts.get(i)))) {
                min_dis = ghost_cord.distance2D(pacman);
                closest = i;
            }
        }
        return ghosts.get(closest).getPos();
    }

    private static Pixel2D[] closestPoint(Index2D pacman, int[][] board) {
        Pixel2D[] ans = null;
        int dis = 1;
        Map game = new Map(board);
        Map2D copy = new Map(board);
        copy = copy.allDistance(pacman, 1);
        while (ans == null) {
            for (int i = 0; i < game.getHeight(); i++) {
                for (int j = 0; j < game.getWidth(); j++) {
                    if ((game.getPixel(j, i) == 3 || game.getPixel(j, i) == 5) && copy.getPixel(j, i) == dis) {
                        Index2D des = new Index2D(j, i);
                        ans = game.shortestPath(pacman, des, 1);
                    }
                    if (ans != null) return ans;
                }
            }
            dis++;
            if(dis > 1000) break; // Safety break
        }
        return ans;
    }

    private static boolean isGhost_in_start(Ghost ghost) {
        Index2D ghost_cord_ = ghost.getPos();
        Index2D[] arr = {
                new Index2D(11,11), new Index2D(11,12), new Index2D(11,13), new Index2D(11,14),
                new Index2D(12,11), new Index2D(13,11), new Index2D(13,12), new Index2D(12,12),
                new Index2D(10,12), new Index2D(10,11), new Index2D(9,11), new Index2D(9,12)
        };

        for (Index2D index2D : arr) {
            if (ghost_cord_.equals(index2D)) return true;
        }
        return false;
    }

    private static Pixel2D[] closestGhost_path(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board) {
        Map game = new Map(board);
        Index2D ghost_cord_closest = closestGhost(pacman, board, ghosts);
        return game.shortestPath(pacman, ghost_cord_closest, 1);
    }

    public static int followPath(Index2D pacman, Pixel2D[] path, int[][] board) {
        Map game = new Map(board);
        Index2D right = new Index2D(pacman.getX() + 1, pacman.getY());
        Index2D left = new Index2D(pacman.getX() - 1, pacman.getY());
        Index2D up = new Index2D(pacman.getX(), pacman.getY() + 1);
        Index2D down = new Index2D(pacman.getX(), pacman.getY() - 1);

        if (CYCLIC_MODE) {
            right = new Index2D((pacman.getX() + 1) % game.getWidth(), pacman.getY());
            left = new Index2D((pacman.getX() - 1 + game.getWidth()) % game.getWidth(), pacman.getY());
            up = new Index2D(pacman.getX(), (pacman.getY() + 1) % game.getHeight());
            down = new Index2D(pacman.getX(), (pacman.getY() - 1 + game.getHeight()) % game.getHeight());
        }

        if (up.equals(path[1])) return Game.UP;
        if (right.equals(path[1])) return Game.RIGHT;
        if (left.equals(path[1])) return Game.LEFT;
        if (down.equals(path[1])) return Game.DOWN;
        return Game.DOWN;
    }

    private static boolean eat_state(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board) {
        Map game = new Map(board);
        for (Ghost ghost : ghosts) {
            Index2D ghost_cord = ghost.getPos();
            Pixel2D[] path = game.shortestPath(pacman, ghost_cord, 1);
            if (ghost.getRemainTimeAsEatable() > Parameters.time_switch_to_run && ghost.getStatus() == 1 && !(isGhost_in_start(ghost)) && (path.length) <= Parameters.max_eat_ghost_dis) {
                return true;
            }
        }
        return false;
    }

    private static boolean run_state(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board) {
        Map game = new Map(board);
        for (Ghost ghost : ghosts) {
            Index2D ghost_cord = ghost.getPos();
            if (!(ghost.getRemainTimeAsEatable() > Parameters.time_switch_to_run) && ghost.getStatus() == 1 && !(isGhost_in_start(ghost))) {
                if (pacman.distance2D(ghost_cord) < Parameters.min_distance) return true;
            }
        }
        return false;
    }

    private static boolean isDeadEnd(Index2D pos, Map game) {
        int paths = 0;
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

        if (game.getPixel(right) != 1) paths++;
        if (game.getPixel(left) != 1) paths++;
        if (game.getPixel(up) != 1) paths++;
        if (game.getPixel(down) != 1) paths++;
        return paths <= 1;
    }

    private static double dis_from_ghost(Pixel2D direction, Index2D ghost, int[][] board) {
        Map game = new Map(board);
        Pixel2D[] path = game.shortestPath(direction, ghost, 1);
        return path.length;
    }

    private static double calculateDanger(Pixel2D direction, ArrayList<Ghost> ghosts, int[][] board) {
        double danger = 0;
        for (Ghost g : ghosts) {
            if (g.getStatus() == 1 && !isGhost_in_start(g)) {
                Index2D ghost_cord = g.getPos();
                double d = dis_from_ghost(direction, ghost_cord, board);
                d++;
                danger += 1000.0 / ((d * d) + 1);
                if (d <= 1) danger += 1000;
            }
        }
        return danger;
    }

    private static int run(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board, int prevDir) {
        Map game = new Map(board);
        double max_score = -Double.MAX_VALUE;
        int move = 0;

        Index2D right = new Index2D(pacman.getX() + 1, pacman.getY());
        Index2D left = new Index2D(pacman.getX() - 1, pacman.getY());
        Index2D up = new Index2D(pacman.getX(), pacman.getY() + 1);
        Index2D down = new Index2D(pacman.getX(), pacman.getY() - 1);
        if (CYCLIC_MODE) {
            right = new Index2D((pacman.getX() + 1) % game.getWidth(), pacman.getY());
            left = new Index2D((pacman.getX() - 1 + game.getWidth()) % game.getWidth(), pacman.getY());
            up = new Index2D(pacman.getX(), (pacman.getY() + 1) % game.getHeight());
            down = new Index2D(pacman.getX(), (pacman.getY() - 1 + game.getHeight()) % game.getHeight());
        }
        Index2D[] moves = {right, left, up, down};

        for (Index2D direction : moves) {
            if (game.getPixel(direction) != 1) {
                double ghostDanger = calculateDanger(direction, ghosts, board);
                double greenDist = distToNearestGreen(direction, board);
                double totalDanger = -(ghostDanger * 5) - (greenDist * 2);

                if (isDeadEnd(direction, game)) totalDanger -= 5000.0;
                if (Double.isInfinite(totalDanger)) totalDanger = -1000000.0 * Parameters.min_distance;

                if (prevDir == 4 && direction.equals(right)) totalDanger += 5;
                if (prevDir == 2 && direction.equals(left)) totalDanger += 5;
                if (prevDir == 1 && direction.equals(up)) totalDanger += 5;
                if (prevDir == 3 && direction.equals(down)) totalDanger += 5;

                if (totalDanger > max_score) {
                    max_score = totalDanger;
                    if (direction.equals(right)) move = Game.RIGHT;
                    else if (direction.equals(left)) move = Game.LEFT;
                    else if (direction.equals(up)) move = Game.UP;
                    else if (direction.equals(down)) move = Game.DOWN;
                }
            }
        }
        return (move == 0) ? randomDir() : move;
    }

    private static boolean eat_pink_state(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board) {
        return !eat_state(ghosts, pacman, board) && !run_state(ghosts, pacman, board);
    }

    private static double distToNearestGreen(Pixel2D pos, int[][] board) {
        Map game = new Map(board);
        double min_dist = Double.POSITIVE_INFINITY;
        for (int y = 0; y < game.getHeight(); y++) {
            for (int x = 0; x < game.getWidth(); x++) {
                if (game.getPixel(x, y) == 5) {
                    Index2D point = new Index2D(x, y);
                    double d = (game.shortestPath(pos, point, 1)).length;
                    if (d < min_dist) min_dist = d;
                }
            }
        }
        return min_dist;
    }

    private static int calculate_path(ArrayList<Ghost> ghosts, Index2D pacman, int[][] board, int prevDir) {
        Pixel2D[] path = null;
        if (eat_pink_state(ghosts, pacman, board)) {
            path = closestPoint(pacman, board);
        } else if (eat_state(ghosts, pacman, board)) {
            path = closestGhost_path(ghosts, pacman, board);
        } else if (run_state(ghosts, pacman, board)) {
            return run(ghosts, pacman, board, prevDir);
        }
        if (path == null) return randomDir();
        return followPath(pacman, path, board);
    }
}