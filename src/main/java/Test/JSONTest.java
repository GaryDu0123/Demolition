package Test;

import processing.data.JSONArray;
import processing.data.JSONObject;
import java.io.*;

/**
 * 读取JSON文件
 */
public class JSONTest {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
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
        System.out.println(levels);
        System.out.println(((JSONObject) levels.get(0)).get("path"));
    }
}
