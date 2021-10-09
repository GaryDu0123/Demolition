package demolition.battle;

import demolition.core.DynamicObject;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Bomb extends DynamicObject {
    public PApplet app;
    private final PImage[] images;
    private int counter = 0;
    private int actionStatus = 0;
    private final ArrayList<Bomb> bombsList;

    public Bomb(int x, int y, PImage[] images, PApplet app, ArrayList<Bomb> bombsList) {
        super(x, y);
        this.images = images;
        this.app = app;
        this.bombsList = bombsList;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public PApplet getApp() {
        return app;
    }

    @Override
    public int getDisplayX() {
        return getX() * 32;
    }

    @Override
    public int getDisplayY() {
        return getY() * 32 + 64;
    }

    public Bomb draw() {
        if (actionStatus == 8) {
            return this;
        }
        getApp().image(images[actionStatus], getDisplayX(), getDisplayY());
        if (counter % 15 == 0){ // 2  * app.frameRate / 8 = 15
            actionStatus ++;
            counter = 0;
        }
        counter ++;
        return null;
    }
}
