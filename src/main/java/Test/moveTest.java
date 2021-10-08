package Test;

import demolition.enums.Direction;
import demolition.tile.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class moveTest {
    private static ArrayList<Direction> clockwiseDirection = new ArrayList<>(Arrays.asList(
            Direction.DIRECTION_UP,
            Direction.DIRECTION_RIGHT,
            Direction.DIRECTION_DOWN,
            Direction.DIRECTION_LEFT));

    public boolean test(Direction direction){
        return direction == Direction.DIRECTION_RIGHT||direction == Direction.DIRECTION_LEFT;

    }

    public boolean move() {
        Direction nextMoveDirection = null;
            int index = clockwiseDirection.indexOf(
                    Direction.DIRECTION_UP

            );
            int count = 0;
            while (count < 4){
                if (index == 4) index = 0;
                if (test(clockwiseDirection.get(index))){
                    nextMoveDirection = clockwiseDirection.get(index);
                    System.err.println(nextMoveDirection);
                    return true;
                }
                index++;
                count++;
            }
        System.err.println(nextMoveDirection);
        return false;
    }

    public static void main(String[] args) {
        final moveTest moveTest = new moveTest();
        moveTest.move();
    }
}
