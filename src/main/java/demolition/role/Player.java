package demolition.role;


import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Role implements Movable {
    private int lives;

    public Player(int X, int Y, PImage[] downImg, PImage[] upImg,
                  PImage[] leftImg, PImage[] rightImg, String character, PApplet app, int lives) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
        this.lives = lives;
    }

    @Override
    protected void enemyCollision(){
        for (Enemy enemy : Resource.enemies) {
            if (Resource.player.getX() == enemy.getX() && Resource.player.getY() == enemy.getY()){
                Resource.player.setLivesLoss();
//                System.out.println("processing" + Resource.player.getLives());
            }
        }
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        if (direction != getPreDirection()) {
            setActionStatus(0);
            setCounter(0);
            setPreDirection(direction);
        }
        if (moveValidityCheck(this, direction, database)) {
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
        return lives;
    }

    @Override
    public void setLivesLoss() {
        lives--;
    }
}
