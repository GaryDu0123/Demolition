package demolition;

import demolition.core.Resource;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    public void enemyYellowTest(){
        Enemy enemy = new Enemy(1, 2, Resource.playerDownImg, Resource.playerUpImg,
                Resource.playerLeftImg, Resource.playerRightImg, "Y", AppTest.app);
        // Determine the status of the get and set method
        assertEquals(1, enemy.getX());
        assertEquals(2, enemy.getY());
        enemy.setX(3);
        enemy.setY(4);
        assertEquals(3, enemy.getX());
        assertEquals(4, enemy.getY());
        assertEquals("Y", enemy.getCharacter());
    }

    @Test
    public void enemyRedTest(){
        Enemy enemy = new Enemy(1, 2, Resource.playerDownImg, Resource.playerUpImg,
                Resource.playerLeftImg, Resource.playerRightImg, "R", AppTest.app);
        // Determine the status of the get and set method
        assertEquals(1, enemy.getX());
        assertEquals(2, enemy.getY());
        enemy.setX(3);
        enemy.setY(4);
        assertEquals(3, enemy.getX());
        assertEquals(4, enemy.getY());
        assertEquals("R", enemy.getCharacter());
    }

    @Test
    public void playerTest(){
        Player player = new Player(1, 2, Resource.playerDownImg, Resource.playerUpImg, Resource.playerLeftImg,
                Resource.playerRightImg, "P", AppTest.app);
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
        player.setX(3);
        player.setY(4);
        assertEquals(3, player.getX());
        assertEquals(4, player.getY());
        assertEquals("P", player.getCharacter());
    }

}
