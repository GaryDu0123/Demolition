package Test;

import processing.data.JSONArray;
import processing.data.JSONObject;
import java.io.*;
import java.util.ArrayList;

/**
 * 读取JSON文件
 */
public class JSONTest {
    static private ArrayList<String> levelPath = new ArrayList<>();
    static private int lives;

    public static void main(String[] args) {
        /*StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.json"));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray levels = (JSONArray) JSONObject.parse(stringBuilder.toString()).get("levels");
        lives = (int) JSONObject.parse(stringBuilder.toString()).get("lives");
        for (int i = 0; i < levels.size(); i++) {
            levelPath.add((String) ((JSONObject) levels.get(i)).get("path"));
        }
        System.out.println(levelPath);
        System.out.println(lives);*/

    }
}
