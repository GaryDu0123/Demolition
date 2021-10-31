package demolition.core;

public abstract class DynamicObject extends GameObject implements Comparable<DynamicObject> {

    /**
     * Create a dynamic object whose position on the map is not fixed.
     *
     * @param x Current coordinate X.
     * @param y Current coordinate Y.
     */
    public DynamicObject(int x, int y) {
        super(x, y);
    }

    /**
     * Returns the displayed coordinates X.
     *
     * @return Displayed coordinates X.
     */
    public abstract int getDisplayX();

    /**
     * Returns the displayed coordinates Y.
     *
     * @return Displayed coordinates Y.
     */
    public abstract int getDisplayY();

    /**
     * Let the image render in the window.
     *
     * @return If the object needs to be removed by the mechanism in the app, then return to itself.
     */
    public abstract DynamicObject draw();

    /**
     * {@inheritDoc}
     * <p>
     * Overriding the comparable interface is that the subclasses that inherit
     * the object can be sorted in the order of the y-axis, thereby controlling the rendering
     * order of the entire map dynamic object.
     *
     * @param o Object to be compared
     * @return Subtracted amount
     */
    @Override
    public int compareTo(DynamicObject o) {
        return this.getY() - o.getY();
    }

}
