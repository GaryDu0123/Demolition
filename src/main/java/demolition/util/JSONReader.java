package demolition.util;


import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Read game json config file.
 */
public class JSONReader {

    /**
     * Player HP
     */
    private final int lives;

    /**
     * Path to the game map
     */
    private final ArrayList<String> levelPathList = new ArrayList<>();

    /**
     * List of game times for each level
     */
    private final ArrayList<Integer> timeList = new ArrayList<>();

    /**
     * Read the config file according to the provided file.
     *
     * @param name name of config file
     */
    public JSONReader(String name) {
        // Prevent constant pool overflow caused by string addition, also not multithreading so use builder
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(name))) {
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not Found");
        }
        // read data from json text
        lives = (int) JSONObject.parse(stringBuilder.toString()).get("lives");
        JSONArray levels = (JSONArray) JSONObject.parse(stringBuilder.toString()).get("levels");
        for (int i = 0; i < levels.size(); i++) {
            levelPathList.add((String) ((JSONObject) levels.get(i)).get("path"));
            timeList.add((Integer) ((JSONObject) levels.get(i)).get("time"));
        }
    }

    /**
     * Get the player's lives.
     *
     * @return Player's lives for whole game.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Get the list of games file path for each level.
     *
     * @return List of games file path
     */
    public ArrayList<String> getLevelPathList() {
        return levelPathList;
    }

    /**
     * Get the List of game times for each level.
     *
     * @return List of game times.
     */
    public ArrayList<Integer> getTimeList() {
        return timeList;
    }

}
