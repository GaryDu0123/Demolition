package demolition.battle;

import demolition.core.DynamicObject;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * Control the display of the bomb and create the instantiation of the explosion
 */
public class Bomb extends DynamicObject {
    /**
     * PApplet Object
     */
    public PApplet app;

    /**
     * PImage of Bomb
     */
    private final PImage[] images;

    /**
     * time counter
     */
    private int counter = 0;

    /**
     * Exploded image display index.
     */
    private int actionStatus = 0;

    /**
     * Create a bomb object, after the bomb explodes, an explosive object will be produced,
     * and the explosive object is responsible for handling the damage.
     *
     * @param x      Coordinate X
     * @param y      Coordinate Y
     * @param images PImage of Bomb
     * @param app    PApplet Object
     */
    public Bomb(int x, int y, PImage[] images, PApplet app) {
        super(x, y);
        this.images = images;
        this.app = app;
    }

    /**
     * Return the PApplet object.
     *
     * @return PApplet object.
     */
    public PApplet getApp() {
        return app;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDisplayX() {
        return getX() * 32;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDisplayY() {
        return getY() * 32 + 64;
    }

    /**
     * {@inheritDoc}
     */
    public Bomb draw() {
        // Calculate the time the bomb was placed
        if (actionStatus == 8) {
            return this;
        }
        // draw the image on the bomb
        getApp().image(images[actionStatus], getDisplayX(), getDisplayY());
        if (counter % 15 == 0) { // 2  * app.frameRate / 8 = 15
            actionStatus++;
            counter = 0;
        }
        // Change the image of the bomb
        counter++;
        return null;
    }
}
