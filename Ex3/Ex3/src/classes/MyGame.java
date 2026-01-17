package assignments.Ex3.classes;

import assignments.Ex3.Index2D;
import assignments.Ex3.Interfaces.Game;
import assignments.Ex3.Interfaces.Ghost;
import assignments.Ex3.Map;

import java.util.ArrayList;

public class MyGame implements Game {

    private Map _map;
    private Index2D _pacman;
    private ArrayList<Ghost> _ghosts;
    private int _status =0 ; // 0- running , 1- won , (-1)-lost
    private double _time = 0;
    private boolean _cyclic;
    private int dt = 20;

    public final int RIGHT = 4;
    public final int LEFT = 2;
    public final int UP = 1;
    public final int DOWN = 3;

    @Override
    public void init(Map map, Index2D pacman, ArrayList<Ghost> ghosts, int seed, int dt, boolean cyclic_mode) {
        this._map = map;
        this._pacman = pacman;
        this._ghosts = ghosts;
        this._status = 0;
        this._time = 0;
        this._cyclic = cyclic_mode;
        this.dt = dt;
    }


    @Override
    public void move(int direction) {
        if(_status!=0){         //if game is not running return
            return;
        }
        //makes the time real
        double secondsPassed = this.dt / 1000.0;
        this._time += secondsPassed;

        //get wanted movement of pacman
        Index2D move_pacman = null;
        if(direction == 4){
            move_pacman = right(_pacman);
        } else if (direction == 2) {
            move_pacman = left(_pacman);
        } else if (direction == 1) {
            move_pacman = up(_pacman);
        }else if(direction ==3){
            move_pacman = down(_pacman);
        }

        //get movement of pacman based on game
        if (_map.getPixel(move_pacman) == 3) {
            _pacman = move_pacman;
            _map.setPixel(move_pacman, 0);
        }else if(_map.getPixel(move_pacman) == 5){
            _pacman = move_pacman;
            _map.setPixel(move_pacman, 0);
            triggerEatable();
        }else if(_map.getPixel(move_pacman) == 0){
            _pacman = move_pacman;
            _map.setPixel(move_pacman, 0);
        }

        //update ghosts time as eatable and move them
        for(int i=0;i<_ghosts.size();i++){
            if(_ghosts.get(i) instanceof MyGhost){
                double current_time_eat = _ghosts.get(i).getRemainTimeAsEatable();
                if(current_time_eat > 0){
                    ((MyGhost) _ghosts.get(i)).setRemainTimeAsEatable(Math.max(0, current_time_eat - secondsPassed));
                }
                _ghosts.get(i).move(this,i);
            }
        }

        checkEaten();
        if(checkIfWon()){
            _status = 1;
        }
    }

    @Override
    public int getStatus() {
        return _status;
    }

    @Override
    public Map getMap() {
        return _map;
    }

    @Override
    public ArrayList<Ghost> getGhosts() {
        return _ghosts;
    }

    @Override
    public double getTime() {
        return _time;
    }

    private void setStatus(int state){
        this._status = state;
    }

    // changes all of the ghosts remain time as eatable to 8
    private void triggerEatable(){
            for (Ghost ghost : _ghosts) {
                if(ghost instanceof MyGhost && ghost.getStatus() ==1){
                    ((MyGhost) ghost).setRemainTimeAsEatable(8);
                }
            }
    }

    private Index2D right(Index2D p){
        Index2D right = new Index2D(p.getX() + 1,p.getY());
        if(_cyclic){
            right = new Index2D((p.getX() + 1) %_map.getWidth(), p.getY());
        }
        return right;
    }

    private Index2D left(Index2D p){
        Index2D left = new Index2D(p.getX() - 1, p.getY());
        if(_cyclic){
            left = new Index2D((p.getX() - 1 + _map.getWidth()) %_map.getWidth(), p.getY());
        }
        return left;
    }

    private Index2D up(Index2D p){
        Index2D up = new Index2D(p.getX(), p.getY() + 1);
        if(_cyclic){
            up = new Index2D(p.getX(), (p.getY() + 1) % _map.getHeight());
        }
        return up;
    }

    private Index2D down(Index2D p){
        Index2D down = new Index2D(p.getX(), p.getY() - 1);
        if(_cyclic){
            down = new Index2D(p.getX(), (p.getY() - 1 + _map.getHeight()) % _map.getHeight());
        }
        return down;
    }

    //check if pacman has been eaten or a ghost has been eaten, if pacman eaten changes game status to lost(-1) , if ghost has been eaten changes it state to dead(0)
    private void checkEaten(){
        for(Ghost  ghost : _ghosts){
            if(ghost.getStatus() == 1 && ghost.getPos().equals(_pacman)){ //if ghost is alive and in the coordinate as pacman
                if(ghost.getRemainTimeAsEatable()>0){ //the ghost can be eaten
                    if(ghost instanceof MyGhost){
                        ((MyGhost) ghost).setStatus(0);
                    }
                } else{
                    _status = -1;
                }
            }
        }
    }

    //check if there are pink points left , if no left return true, if there are pink points return false
    private boolean checkIfWon(){
        for (int i =0; i<_map.getWidth();i++){
            for (int j =0; j<_map.getHeight();j++){
                if(_map.getPixel(i,j) == 3){
                    return false;
                }
            }
        }
        return true;
    }


}
