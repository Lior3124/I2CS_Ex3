package assignments.Ex3.new_game.classes;

import assignments.Ex3.Index2D;
import assignments.Ex3.new_game.Interfaces.Game;
import assignments.Ex3.new_game.Interfaces.Ghost;
import assignments.Ex3.Map;
import assignments.Ex3.Pixel2D;

import java.util.Random;

import static assignments.Ex3.GameInfo.CYCLIC_MODE;


public class MyGhost implements Ghost {

    private Index2D _pos;
    private int _status =1; // 1- alive 0- dead
    private double remainTimeAsEatable =0;
    private double timeDead=0;
    private int _seed;
    private Random _rand;

    public MyGhost(Index2D startPos, int seed) {
        this._pos = startPos;
        this._seed=seed;
        this._rand = new Random((_seed));

    }

    @Override
    public Index2D getPos() {
        return _pos;
    }

    @Override
    public int getStatus() {
        return _status;
    }

    @Override
    public void move(Game game,int index) {
        Index2D start1 = new Index2D(11,11);
        Index2D start2 = new Index2D(11,12);
        Index2D start3 = new Index2D(11,13);
        Index2D start4 = new Index2D(11,14);
        int direction=0;
        if(this._status==0 ){
            return;
        }
        if(game instanceof MyGame) {
            Index2D[] path = null;
            if (game.getTime() > (index+1) * 2.0) {
                if (index != 0) {
                    int random_num = _rand.nextInt(((MyGame) game).getDifficulty());
                    if(random_num==0){
                        path = (Index2D[]) game.getMap().shortestPath(_pos, ((MyGame) game).get_pacman(), 1);
                        if (path != null && path.length > 1) {
                            direction = followPath(_pos, path, game.getMap(), (MyGame) game);
                        }
                    }else {
                        direction = _rand.nextInt(4) + 1;
                    }
                }else{
                    path = (Index2D[]) game.getMap().shortestPath(_pos, ((MyGame) game).get_pacman(), 1);
                    if (path != null && path.length > 1) {
                        direction = followPath(_pos, path, game.getMap(), (MyGame) game);
                    }
                }

            }
        }
        Index2D next = null;
        if (direction == ((MyGame) game).UP) next = up(_pos, ((MyGame) game));
        else if (direction == ((MyGame) game).DOWN) next = down(_pos, ((MyGame) game));
        else if (direction == ((MyGame) game).LEFT) next = left(_pos, ((MyGame) game));
        else if (direction == ((MyGame) game).RIGHT) next = right(_pos, ((MyGame) game));

        if (next != null && ((MyGame) game).getMap().getPixel(next) != 1) {
            this._pos = next;
        }
    }

    @Override
    public double getRemainTimeAsEatable() {
        return remainTimeAsEatable;
    }

    void setRemainTimeAsEatable(double time) {
        this.remainTimeAsEatable = time;
    }

    void setStatus(int status) {
        this._status = status;
    }
    void setPos(Index2D pos) {
        this._pos = pos;
    }

    void setTimeDead(double timeDead) {
        this.timeDead = timeDead;
    }

    public double getTimeDead() {
        return timeDead;
    }

    /**
     * moves ghost in the direction of a given path
     * @param ghost - the Index2D of ghost
     * @param path - an array of Pixel2D representing the path which ghost should move by
     * @param board - represents the game's matrix
     * @return returns int(right -4, left -2, up-1, down-3)
     */
    private static int followPath(Index2D ghost, Pixel2D[] path, Map board,MyGame game){
        //get left right up down based on if cyclic
        Index2D right = new Index2D(ghost.getX() + 1, ghost.getY());
        Index2D left = new Index2D(ghost.getX() - 1, ghost.getY());
        Index2D up = new Index2D(ghost.getX(), ghost.getY() + 1);
        Index2D down = new Index2D(ghost.getX(), ghost.getY() - 1);
        if(CYCLIC_MODE){
            right = new Index2D((ghost.getX() + 1) %game.getMap().getWidth(), ghost.getY());
            left = new Index2D((ghost.getX() - 1 + game.getMap().getWidth()) %game.getMap().getWidth(), ghost.getY());
            up = new Index2D(ghost.getX(), (ghost.getY() + 1) % game.getMap().getHeight());
            down = new Index2D(ghost.getX(), (ghost.getY() - 1 + game.getMap().getHeight()) % game.getMap().getHeight());
        }
        if(up.equals(path[1])){
            return game.UP;
        }
        if(right.equals(path[1])){
            return game.RIGHT;
        }
        if(left.equals(path[1])){
            return game.LEFT;
        }
        if(down.equals(path[1])){
            return game.DOWN;
        }
        return game.DOWN;
    }

    private Index2D right(Index2D p, MyGame g) {
        Index2D right = new Index2D(p.getX() + 1, p.getY());
        if(g.get_cyclic()){
            right = new Index2D((p.getX() + 1) %g.get_map().getWidth(), p.getY());
        }
        return right;
    }

    private Index2D up(Index2D p, MyGame g) {
        Index2D up = new Index2D(p.getX(), p.getY() + 1);
        if(g.get_cyclic()){
            up = new Index2D(p.getX(), (p.getY() + 1) % g.get_map().getHeight());
        }
        return up;
    }

    private Index2D left(Index2D p, MyGame g) {
        Index2D left = new Index2D(p.getX() - 1, p.getY());
        if(g.get_cyclic()){
            left = new Index2D((p.getX() - 1 + g.get_map().getWidth()) %g.get_map().getWidth(), p.getY());
        }
        return left;
    }

    private Index2D down(Index2D p, MyGame g) {
        Index2D down = new Index2D(p.getX(), p.getY() - 1);
        if(g.get_cyclic()){
            down = new Index2D(p.getX(), (p.getY() - 1 + g.get_map().getHeight()) % g.get_map().getHeight());
        }
        return down;
    }
}
