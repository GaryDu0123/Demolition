package demolition.core;

public abstract class DynamicObject extends GameObject implements Comparable<DynamicObject>{
    public DynamicObject(int x, int y) {
        super(x, y);
    }

    public abstract int getDisplayX();

    public abstract int getDisplayY();

    public abstract DynamicObject draw();

    @Override
    public int compareTo(DynamicObject o) {
        return this.getY() - o.getY();
    }

}
