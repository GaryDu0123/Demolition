package demolition.role;

import demolition.enums.Direction;
import processing.core.PApplet;
import processing.core.PImage;

public class Role {
    public PApplet app;
    private final PImage[] downImg;
    private PImage[] upImg;
    private PImage[] leftImg;
    private PImage[] rightImg;
    private int X;
    private int Y;
    private final String character;


    private int counter = 0;
    private int actionStatus = 0;
    private Direction preDirection = Direction.DIRECTION_DOWN;


    public Role(int X, int Y, PImage[] downImg, PImage[] upImg,
                PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        this.X = X;
        this.Y = Y;
        this.downImg = downImg;
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.character = character;
        this.app = app;
    }


    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
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
    public int getDisplayX() {
        return X * 32;
    }

    public int getDisplayY() {
        return Y * 32 - 16 + 64;
    }

    public void draw(){
        if (actionStatus == 4)
            actionStatus = 0;
        PImage[] image = null;
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
        }
        app.image(image[actionStatus], getDisplayX(), getDisplayY());
//        System.err.println(actionStatus);
        if (counter % 12 == 0){
            actionStatus ++;
            counter = 0;
        }
        counter ++;
    }
}
