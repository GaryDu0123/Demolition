package demolition.util;

import processing.data.JSONArray;
import processing.data.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSONReader {
    private final int lives;
    private final ArrayList<String> levelPathList = new ArrayList<>();
    private final ArrayList<Integer> timeList = new ArrayList<>();


    public JSONReader(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(name))) {
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lives = (int) JSONObject.parse(stringBuilder.toString()).get("lives");
        JSONArray levels = (JSONArray) JSONObject.parse(stringBuilder.toString()).get("levels");
        for (int i = 0; i < levels.size(); i++) {
            levelPathList.add((String) ((JSONObject) levels.get(i)).get("path"));
            timeList.add((Integer) ((JSONObject) levels.get(i)).get("time"));
        }
    }

    public int getLives() {
        return lives;
    }

    public ArrayList<String> getLevelPathList() {
        return levelPathList;
    }

    public ArrayList<Integer> getTimeList() {
        return timeList;
    }

    public static void main(String[] args) {
        JSONReader jsonReader = new JSONReader("config.json");

    }
}
