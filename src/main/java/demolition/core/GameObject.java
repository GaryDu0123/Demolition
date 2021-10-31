package demolition.core;

/**
 * The parent class of all objects on the map, store Coordinate .etc
 */
public abstract class GameObject {
    /**
     * Object Coordinate X
     */
    private int X;

    /**
     * Object Coordinate Y
     */
    private int Y;

    /**
     * Creat a new GameObject.
     *
     * @param x Coordinate X
     * @param y Coordinate Y
     */
    public GameObject(int x, int y) {
        X = x;
        Y = y;
    }

    /**
     * Get Coordinate X
     *
     * @return Coordinate X
     */
    public int getX() {
        return X;
    }

    /**
     * Get Coordinate Y
     *
     * @return Coordinate Y
     */
    public int getY() {
        return Y;
    }

    /**
     * Set Coordinate X
     *
     * @param x Coordinate X
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * Set Coordinate Y
     *
     * @param y Coordinate Y
     */
    public void setY(int y) {
        Y = y;
    }

}
