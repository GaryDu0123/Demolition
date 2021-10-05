package demolition.role;

public class Enemy {
    private String character;
    private String color;
    private int X;
    private int Y;

    public Enemy(int X, int Y, String character) {
        this.X = X;
        this.Y = Y;
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public String getColor() {
        return color;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
