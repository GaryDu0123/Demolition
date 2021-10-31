package demolition;

import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.util.JSONReader;
import org.junit.jupiter.api.function.Executable;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PConstants;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class JSONReaderTest {
    /**
     *  Test the return value of the path list
     */
    @Test
    public void testLevelPathList(){
        JSONReader jsonReader = new JSONReader("src/test/resources/config.json");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("src/test/resources/level1.txt");
        strings.add("src/test/resources/level2.txt");
        assertIterableEquals(strings, jsonReader.getLevelPathList());
    }

    /**
     * Test the return value of lives
     */
    @Test
    public void testLives(){
        JSONReader jsonReader = new JSONReader("src/test/resources/config.json");
        assertEquals(3, jsonReader.getLives());
    }

    /**
     * Test the return value of TimeLis
     */
    @Test
    public void testTimeList(){
        JSONReader jsonReader = new JSONReader("src/test/resources/config.json");
        System.out.println(jsonReader.getTimeList());
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(120);
        integers.add(180);
        assertIterableEquals(integers, jsonReader.getTimeList());
    }

    /**
     * Test if it threw true error
     */
    @Test
    public void testFileNotFound(){
        boolean errorAppear = false;
        try {
            JSONReader jsonReader = new JSONReader("src/test/resources");
        } catch (RuntimeException e){
            errorAppear = true;
        }
        assertTrue(errorAppear);
    }

}
