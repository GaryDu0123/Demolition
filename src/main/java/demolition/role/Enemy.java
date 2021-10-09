package demolition.role;


import demolition.core.DynamicRemovable;
import demolition.enums.Direction;
import demolition.tile.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Enemy extends Role implements Movable, DynamicRemovable {
    private static final ArrayList<Direction> clockwiseDirection = new ArrayList<>(Arrays.asList(Direction.DIRECTION_UP,
            Direction.DIRECTION_RIGHT, Direction.DIRECTION_DOWN, Direction.DIRECTION_LEFT));
    private int counter = 0;

    public Enemy(int X, int Y, PImage[] downImg, PImage[] upImg,
                 PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        if (counter != 60){ // 可以考虑改为获取app的帧率
            counter ++;
            return false;
        }
        counter = 0; // 帧率计数器重置, 一秒动一回, 此分支为执行

        Direction nextMoveDirection = null;
        if ("R".equals(getCharacter())){
            if (!moveValidityCheck(this, getPreDirection(), database)){
                ArrayList<Direction> validDirection = new ArrayList<>();
                for (Direction d : clockwiseDirection) {
                    if (moveValidityCheck(this, d, database)){
                        validDirection.add(d);
                    }
                }
                if (validDirection.size() == 0) return false;
                Random random = new Random();
                nextMoveDirection = validDirection.get(random.nextInt(validDirection.size()));
            } else {
                nextMoveDirection = getPreDirection();
            }
//            System.out.format("\33[0;31mRED Enemy Message:    %s\33[0m\n", nextMoveDirection); // todo debug message
        } else if ("Y".equals(getCharacter())){
            int index = clockwiseDirection.indexOf(getPreDirection());
            int count = 0;
            while (count < 4){
                if (index == 4) index = 0;
                if (moveValidityCheck(this, clockwiseDirection.get(index), database)){
                    nextMoveDirection = clockwiseDirection.get(index);
//                    System.out.format("\33[0;33mYELLOW Enemy Message: %s\33[0m\n", nextMoveDirection); // todo debug message
                    break;
                }
                index++;
                count++;
            }
        }

        if (nextMoveDirection == null) { // 四周都是墙
            return false;
        }
        if (nextMoveDirection != getPreDirection()){ // 将现有动画计数器归零
            setActionStatus(0);
            setCounter(0);
            setPreDirection(nextMoveDirection);
        }
        switch (nextMoveDirection) {
            case DIRECTION_UP:
                setY(getY() - 1);
                return true;
            case DIRECTION_DOWN:
                setY(getY() + 1);
                return true;
            case DIRECTION_LEFT:
                setX(getX() - 1);
                return true;
            case DIRECTION_RIGHT:
                setX(getX() + 1);
                return true;
        }
        return false;
    }

    @Override
    public boolean isNeedRemove() {
        return false;
    }
}
