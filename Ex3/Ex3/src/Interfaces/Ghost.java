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
     * Sets the ghost's next move based on game logic
     */
    public void move(Game game);

    /**
     * @return remaining time the ghost is eatable
     */
    public double remainTimeAsEatable();
}