package demolition.role;

import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.enums.Direction;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Role extends DynamicObject{
    public PApplet app;
    private final PImage[] downImg;
    private final PImage[] upImg;
    private final PImage[] leftImg;
    private final PImage[] rightImg;
    private final String character;
    private int counter = 0;
    private int actionStatus = 0;
    private Direction preDirection = Direction.DIRECTION_DOWN;


    public Role(int X, int Y, PImage[] downImg, PImage[] upImg,
                PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X,Y);
        this.downImg = downImg;
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.character = character;
        this.app = app;
    }

    public PApplet getApp() {
        return app;
    }

    public String getCharacter() {
        return character;
    }

    public Direction getPreDirection() {
        return preDirection;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setActionStatus(int actionStatus) {
        this.actionStatus = actionStatus;
    }

    public void setPreDirection(Direction preDirection) {
        this.preDirection = preDirection;
    }

    @Override
    public int getDisplayX() {
        return getX() * 32;
    }

    @Override
    public int getDisplayY() {
        return getY() * 32 - 16 + 64;
    }

    public void draw(){
        if (actionStatus == 4)
            actionStatus = 0;
        PImage[] image;
        switch (preDirection){
            case DIRECTION_DOWN:
                image = downImg;
                break;
            case DIRECTION_UP:
                image = upImg;
                break;
            case DIRECTION_LEFT:
                image = leftImg;
                break;
            case DIRECTION_RIGHT:
                image = rightImg;
                break;
            default:
                return;
        }
        getApp().image(image[actionStatus], getDisplayX(), getDisplayY());

        if (counter % 12 == 0){ // 0.2 * app.frameRate
            actionStatus ++;
            counter = 0;
        }
        counter ++;
    }

    protected abstract void enemyCollision();

    public abstract int getLives();

    public abstract void setLivesLoss();
}
