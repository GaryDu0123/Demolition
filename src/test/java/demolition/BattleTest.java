package demolition;

import demolition.battle.Bomb;
import demolition.battle.Explosion;
import demolition.core.Resource;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTest{

    @Test
    public void BombTest(){
        Bomb bomb = new Bomb(1, 1, Resource.bombImg, AppTest.app);
        assertEquals(1, bomb.getX());
        assertEquals(1, bomb.getY());
        bomb.setX(2);
        bomb.setY(3);
        assertEquals(2, bomb.getX());
        assertEquals(3, bomb.getY());
        assertEquals(2 * 32, bomb.getDisplayX());
        assertEquals(3 * 32 + 64, bomb.getDisplayY());
    }
}