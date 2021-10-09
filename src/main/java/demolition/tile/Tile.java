package demolition.tile;

import demolition.core.GameObject;
import demolition.core.Resource;
import processing.core.PImage;

public abstract class Tile extends GameObject {
    private final PImage image;

    public Tile(int X, int Y, PImage image) {
        super(X, Y);
        this.image = image;
    }

    public PImage getImage() {
        return image;
    }

    public abstract String getCharacter();

    public void die(){
        Resource.mapDatabase[getY()][getX()] = new EmptyTile(getX(),getY(),Resource.empty);
    }

}
