package demolition;

import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class App extends PApplet {

    private PImage playerImg;
    private PImage goal;
    private PImage solidWall;
    private PImage brokenWell;
    private PImage empty;
    private PImage redEnemyImg;
    private PImage YellowEnemyImg;
    private int level = 0; // 初步思路是根据关卡load, 关卡改变去调用update方法
    private int lives;
    private final ArrayList<String> levelPathList = new ArrayList<>();
    private final Tile[][] mapDatabase = new Tile[13][15];
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    public App() {
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }


    public void setup() {
        frameRate(FPS); // 游戏刷新帧率
        background(239, 129, 0); // 设置背景色为橙色

        // load 图片区
        playerImg = this.loadImage("src/main/resources/player/player.gif"); // 暂时使用

        goal = this.loadImage("src/main/resources/goal/goal.png");
        solidWall = this.loadImage("src/main/resources/wall/solid.png");
        brokenWell = this.loadImage("src/main/resources/broken/broken.png");
        empty = this.loadImage("src/main/resources/empty/empty.png");
        JSONReader(); // 初始化JSON库 影响各文件的路径 生命数,
        mapStaticRecourseUpdate(level); // 第一次初始化数据库为第一个地图,后面只有level更新的时候才生效
        mapStaticGraphUpdate(mapDatabase);
        System.out.println(Arrays.deepToString(mapDatabase));
        // Load images during setup
    }

    private void JSONReader() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("config.json"))) {
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
        }
    }

    private void mapStaticRecourseUpdate(int level) {
        int rowIndex = 0;
        try (Scanner fileReader = new Scanner(new File(levelPathList.get(level)))) {
            while (fileReader.hasNextLine()) {
                String config = fileReader.nextLine();
                if (config.length() != 15) {
                    System.out.println("Config File Bad Format!");
                    System.exit(-1);
                }
                int columnIndex = 0;
                for (char c : config.toCharArray()) {
                    Tile tile = null;
                    if (c == 'W') {
                        tile = new SolidWall(columnIndex, rowIndex, solidWall);
                    } else if (c == 'B') {
                        tile = new BrokenWall(columnIndex, rowIndex,brokenWell);
                    } else if (c == ' ') {
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                    } else if (c == 'G') {
                        tile = new GoalTile(columnIndex, rowIndex, goal);
                    } else if (c == 'R'){
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, "R"));
                    } else if (c == 'Y'){
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, "Y"));
                    } else if (c == 'P'){
                        tile = new EmptyTile(columnIndex, rowIndex,empty);
                        player = new Player(columnIndex,rowIndex, playerImg); // todo 下一步初始化玩家
                    } else {
                        System.out.println(c);
                        System.out.println("Unknown type in config file");
                        System.exit(-1);
                    }
                    mapDatabase[rowIndex][columnIndex] = tile;
                    columnIndex++;
                }
                rowIndex++;
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println("file not found");
            System.exit(-1);
        }
    }

    private void mapStaticGraphUpdate(Tile[][] mapDatabase){
        for (Tile[] tiles : mapDatabase) {
            for (Tile tile : tiles) {
                image(tile.getImage(), 32 * tile.getX(), 64 + 32 * tile.getY());
            }
        }
    }

    public void draw() {

        /*
        image(playerImg, 0, 0);
        image(goal, 32, 0);
        image(solidWall, 64, 0);
        image(brokenWell, 32, 32);
        image(empty, 64, 32);
        */
//        System.out.println(str(mouseX) + " "+ mouseY);
        if (keyPressed && (key == CODED)) {
            switch (keyCode) {
                case LEFT:
                    System.out.println("LEFT");
                    break;
                case RIGHT:
                    System.out.println("RIGHT");
                    break;
                case UP:
                    System.out.println("UP");
                    break;
                case DOWN:
                    System.out.println("DOWN");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
