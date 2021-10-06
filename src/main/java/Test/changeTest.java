package Test;

import demolition.enums.Direction;

public class changeTest {
    int actionStatus = 0;
    Direction preDirection = Direction.DIRECTION_DOWN;
    int counter = 0;

    public void draw(Direction direction){

        if (direction != preDirection){
            actionStatus = 0;
            counter = 0;
            preDirection = direction;
        }
        if (actionStatus == 4)
            actionStatus = 0;

        if (counter % 12 == 0){
            System.out.println(actionStatus);
            actionStatus ++;
            counter = 0;
        }
        counter ++;
    }

    public static void main(String[] args) {
        final changeTest changeTest = new changeTest();
        changeTest.draw(Direction.DIRECTION_LEFT);
        for (int i = 0; i < 24; i++) {
            changeTest.draw(Direction.DIRECTION_LEFT);
        }
        for (int i = 0; i < 47; i++) {
            changeTest.draw(Direction.DIRECTION_RIGHT);
        }
    }
}
