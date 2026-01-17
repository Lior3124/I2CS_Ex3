package assignments.Ex3.Interfaces;

import assignments.Ex3.Index2D;

public interface Ghost {

    /**
     * @return current position of the ghost
     */
    public Index2D getPos();

    /**
     * @return 1 if active, 0 if eaten
     */
    public int getStatus();

    /**
     * Sets the ghost's next move based on game logic, the index is the number of the ghosts
     */
    public void move(Game game,int index);

    /**
     * @return remaining time the ghost is eatable
     */
    public double getRemainTimeAsEatable();
}