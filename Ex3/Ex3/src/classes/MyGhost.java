package assignments.Ex3.classes;

import assignments.Ex3.Index2D;
import assignments.Ex3.Interfaces.Game;
import assignments.Ex3.Interfaces.Ghost;

public class MyGhost implements Ghost {

    private Index2D _pos;
    private int _status =1; // 1- alive 0- dead
    private double remainTimeAsEatable =0;

    public MyGhost(Index2D startPos) {
        this._pos = startPos;
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

}
