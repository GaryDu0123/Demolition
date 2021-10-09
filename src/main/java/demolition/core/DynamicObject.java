package demolition.core;

public abstract class DynamicObject extends GameObject{
    public DynamicObject(int x, int y) {
        super(x, y);
    }

    public abstract int getDisplayX();

    public abstract int getDisplayY();
}
