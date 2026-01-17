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

    @Override
    public void init(Map map, Index2D pacman, ArrayList<Ghost> ghosts, int seed, int dt, boolean cyclic_mode) {
        this._map = map;
        this._pacman = pacman;
        this._ghosts = ghosts;
        this._status = 0;
        this._time = 0;
        this._cyclic = cyclic_mode;
    }

    @Override
    public void move(int direction) {
    }

    @Override
    public int getStatus() {
        return 0;
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
}
