package demolition.role;


import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Role implements Movable {


    public Player(int X, int Y, PImage[] downImg, PImage[] upImg,
                  PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
    }

    @Override
    protected void enemyCollision(){
        for (DynamicObject enemy : Resource.dynamicObjects) {
            if (enemy instanceof Enemy && Resource.player.getX() == enemy.getX() && Resource.player.getY() == enemy.getY()){
                Resource.player.setLivesLoss();
            }
        }
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        if (direction != getPreDirection()) {
            setActionStatus(0);
            setCounter(0);
        }
        if (moveValidityCheck(this, direction, database)) {
            setPreDirection(direction); // todo 检查有没有bug
            switch (direction) {
                case DIRECTION_UP:
                    setY(getY() - 1);
                    enemyCollision();
                    return true;
                case DIRECTION_DOWN:
                    setY(getY() + 1);
                    enemyCollision();
                    return true;
                case DIRECTION_LEFT:
                    setX(getX() - 1);
                    enemyCollision();
                    return true;
                case DIRECTION_RIGHT:
                    setX(getX() + 1);
                    enemyCollision();
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getLives() {
        return Resource.lives;
    }

    @Override
    public void setLivesLoss() {
        Resource.lives--;
        Resource.mapStaticRecourseInitUpdate(Resource.currentLevel, app);
    }
}
