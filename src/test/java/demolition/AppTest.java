package demolition;

import demolition.battle.Bomb;
import demolition.battle.Explosion;
import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.enums.GameStatus;
import demolition.role.Enemy;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PConstants;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class AppTest {
    public static App app;

    /**
     * Start the test of the game, sometimes the static resource loading of the game is too slow,
     * which will cause the testcase to fail, and it will run normally after a few more attempts
     */
    @Test
    public void basicTest() {
        // Create an instance of your application
        app = new App();

        // Set the program to not loop automatically
        app.noLoop();

        // Set the path of the config file to use
        app.setConfigName("src/test/resources/config.json");

        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[]{"App"}, app);

        // Call App.setup() to load in sprites
        app.setup();

        // Set a 1 seconds delay ensuring all resources are loaded
        app.delay(1000);

        // Call draw to update the program.
        app.draw();
    }

    /**
     * Test the player's movement, call keyPressed to simulate the action of pressing the button in reality.
     * Because it becomes a whole process after pressing and releasing, so call keyPressed() first
     * then call keyReleased to not influence next test
     */
    @Test
    public void test1playerMoveTest() {
        /* Player should at (1, 1) at this time
            WWWWWWWWWWWWWWW
            WP   GBBB BBBBW
            W W W W W W W W
            W         B B W
            ....
        */
        app.keyCode = PConstants.RIGHT;
        app.keyPressed();
        assertEquals(2, Resource.player.getX());
        assertEquals(1, Resource.player.getY());
        app.keyReleased();
        // player because move right successful, so this time should face right
        assertEquals(Direction.DIRECTION_RIGHT, Resource.player.getPreDirection());
        /*  Player should at (2, 1) after move
            WWWWWWWWWWWWWWW
            W P  GBBB BBBBW
            W W W W W W W W
            W         B B W
            ....
        */
        app.keyCode = PConstants.LEFT;
        app.keyPressed();
        assertEquals(1, Resource.player.getX());
        assertEquals(1, Resource.player.getY());
        app.keyReleased();
        // player because move Left successful, so this time should face left
        assertEquals(Direction.DIRECTION_LEFT, Resource.player.getPreDirection());
        /* Player should at (1, 1) at this time
            WWWWWWWWWWWWWWW
            WP   GBBB BBBBW
            W W W W W W W W
            W         B B W
            ....
        */
        app.keyCode = PConstants.DOWN;
        app.keyPressed();
        assertEquals(1, Resource.player.getX());
        assertEquals(2, Resource.player.getY());
        app.keyReleased();
        app.draw();
        // player because move down successful, so this time should face down
        assertEquals(Direction.DIRECTION_DOWN, Resource.player.getPreDirection());
        /* Player should in (1, 2) at this time, and facing down
            WWWWWWWWWWWWWWW
            W    GBBB BBBBW
            WPW W W W W W W
            W         B B W
            ....
        */
        app.keyCode = PConstants.UP;
        app.keyPressed();
        assertEquals(1, Resource.player.getX());
        assertEquals(1, Resource.player.getY());
        app.keyReleased();
        // player because move down successful, so this time should face down
        assertEquals(Direction.DIRECTION_UP, Resource.player.getPreDirection());
        /*
            Player should in (1, 2) at this time
            WWWWWWWWWWWWWWW
            WP   GBBB BBBBW
            W W W W W W W W
            W         B B W
            ....
        */
    }

    /**
     * Test UI display data such as timer
     */
    @Test
    public void test2uiTest() {
        int temp = --Resource.timer;
        Resource.timer = 120;
        for (int i = 0; i < 60; i++) {
            app.draw();
        }
        assertEquals(119, Resource.timer);
        Resource.timer = temp;
    }

    /**
     * Test whether the bomb can operate normally and whether it can destroy the block
     */
    @Test
    public void test3BombTest() {
        Resource.player.setX(11); // Set the explosion location
        Resource.player.setY(3);
        app.keyCode = 32;  // place the bomb
        app.keyPressed();  // Simulate key press
        int count = 0;  //
        for (DynamicObject dynamicObject : Resource.dynamicObjects) {
            if (dynamicObject instanceof Bomb) count++;
        }
        assertEquals(1, count); // Check whether the placement is successful
        app.keyReleased();
        Resource.player.setX(1);
        Resource.player.setY(1);

        //
        boolean explosionTestDone = false;
        for (int i = 0; i < 150; i++) {
            if (!explosionTestDone){ // Check the place where the explosion took effect
                for (DynamicObject dynamicObject : Resource.dynamicObjects) {
                    if (dynamicObject instanceof Explosion){
                        Explosion explosion = (Explosion)dynamicObject;
                        assertEquals(11 , explosion.getX());
                        assertEquals(3 , explosion.getY());
                        assertEquals(11 * 32, explosion.getDisplayX());
                        assertEquals(3 * 32 + 64, explosion.getDisplayY());
                        explosionTestDone = true;
                    }
                }
            }
            app.draw();
        }
        assertTrue(explosionTestDone); // Check the explosion Testcase in loop is activated

        // Check whether all the blocks are cleared after the explosion
        assertEquals(" ", Resource.mapDatabase[4][11].getCharacter());
        assertEquals(" ", Resource.mapDatabase[3][10].getCharacter());
        assertEquals(" ", Resource.mapDatabase[3][12].getCharacter());
        assertEquals(" ", Resource.mapDatabase[2][11].getCharacter());
    }

    /**
     * Test the movement of the red enemy
     */
    @Test
    public void test4TestRedEnemyMove() {
        for (DynamicObject dynamicObject : Resource.dynamicObjects) {
            if (dynamicObject instanceof Enemy) {
                if (((Enemy) dynamicObject).getCharacter().equals("R")) {
                    Enemy enemy = (Enemy) dynamicObject;
                    enemy.setPreDirection(Direction.DIRECTION_RIGHT);
                    enemy.setX(3);
                    enemy.setY(5);
                    for (int i = 0; i < 65; i++) {
                        app.draw();
                    }
                    assertEquals(4, enemy.getX());
                    assertEquals(5, enemy.getY());
                    break;
                }
            }
        }
    }

    /**
     * Test the movement of the yellow enemy
     */
    @Test
    public void test5TestYellowEnemy() {
        for (DynamicObject dynamicObject : Resource.dynamicObjects) {
            if (dynamicObject instanceof Enemy) {
                if (((Enemy) dynamicObject).getCharacter().equals("Y")) {
                    Enemy enemy = (Enemy) dynamicObject;
                    enemy.setPreDirection(Direction.DIRECTION_UP); // Fixed direction to move
                    enemy.setX(1);
                    enemy.setY(10);
                    for (int i = 0; i < 65; i++) {
                        app.draw();
                    }
                    assertEquals(1, enemy.getX());
                    assertEquals(9, enemy.getY());
                    break;
                }
            }
        }
    }

    /**
     * Check the status of the enemy losing life game
     */
    @Test
    public void test6TestEnemyLiveLoss() {
        Enemy enemy = null;
        for (DynamicObject dynamicObject : Resource.dynamicObjects) {
            if (dynamicObject instanceof Enemy) {
                enemy = ((Enemy) dynamicObject);
                enemy.setLivesLoss();
                assertEquals(0, enemy.getLives()); // check lives
                break;
            }
        }

        for (int i = 0; i < 30; i++) {
            app.draw();
        }
        assertFalse(Resource.dynamicObjects.contains(enemy));  // Check if it is deleted
    }

    /**
     * Test whether the map and location are reset after the player is injured
     */
    @Test
    public void test7TestPlayerLivesLoss() {
        Resource.player.setY(11);
        Resource.player.setY(4);
        Resource.player.setLivesLoss();
        assertEquals(2, Resource.player.getLives()); // test lives
        assertEquals(1, Resource.player.getX());
        assertEquals(1, Resource.player.getY());
    }

    /**
     * Check if the game map changes when the player reaches the goal
     */
    @Test
    public void test8TestGameStatus() {
        Resource.player.setX(5);
        Resource.player.setY(1);
        app.draw();
        assertEquals(1, Resource.currentLevel);
    }

    /**
     * Test game over, victory
     */
    @Test
    public void test9TestGameFinish() {
        Resource.player.setX(13);
        Resource.player.setY(6);
        app.draw();
        assertEquals(GameStatus.WIN, Resource.gameStatus);
        assertNotEquals(GameStatus.LOSE, Resource.gameStatus);
    }
}