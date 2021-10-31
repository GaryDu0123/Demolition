package demolition;

import demolition.core.Resource;
import demolition.tile.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TailTest {

    @Test
    public void goalTileTest() {
        GoalTile goalTile = new GoalTile(1, 2 , Resource.goal);
        assertEquals(1, goalTile.getX());
        assertEquals(2, goalTile.getY());
        goalTile.setX(3);
        goalTile.setY(4);
        assertEquals(3, goalTile.getX());
        assertEquals(4, goalTile.getY());
        assertEquals(Resource.goal, goalTile.getImage());
        assertEquals("G", goalTile.getCharacter());
        assertEquals("G", goalTile.toString());
    }

    @Test
    public void solidTileTest() {
        SolidWall solidWall = new SolidWall(1, 2, Resource.goal);
        assertEquals(1, solidWall.getX());
        assertEquals(2, solidWall.getY());
        solidWall.setX(3);
        solidWall.setY(4);
        assertEquals(3, solidWall.getX());
        assertEquals(4, solidWall.getY());
        assertEquals(Resource.goal, solidWall.getImage());
        assertEquals("W", solidWall.getCharacter());
        assertEquals("W", solidWall.toString());
    }

    @Test
    public void emptyTileTest() {
        EmptyTile emptyTile = new EmptyTile(1, 2, Resource.empty);
        assertEquals(1,emptyTile.getX());
        assertEquals(2, emptyTile.getY());
        emptyTile.setX(3);
        emptyTile.setY(4);
        assertEquals(3, emptyTile.getX());
        assertEquals(4, emptyTile.getY());
        assertEquals(Resource.empty, emptyTile.getImage());
        assertEquals(" ", emptyTile.getCharacter());
        assertEquals(" ", emptyTile.toString());
    }


    @Test
    public void brokenWallTest() {
        BrokenWall brokenWall = new BrokenWall(1, 2, Resource.empty);
        assertEquals(1, brokenWall.getX());
        assertEquals(2, brokenWall.getY());
        brokenWall.setX(3);
        brokenWall.setY(4);
        assertEquals(3, brokenWall.getX());
        assertEquals(4, brokenWall.getY());
        assertEquals(Resource.empty, brokenWall.getImage());
        assertEquals("B", brokenWall.getCharacter());
        assertEquals("B", brokenWall.toString());

    }
}
