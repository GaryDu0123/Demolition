package demolition;

import demolition.enums.Direction;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class App extends PApplet {

    private final PImage[] playerDownImg = new PImage[4];
    private final PImage[] playerLeftImg = new PImage[4];
    private final PImage[] playerRightImg = new PImage[4];
    private final PImage[] playerUpImg = new PImage[4];

    private final PImage[] redEnemyDownImg = new PImage[4];
    private final PImage[] redEnemyUpImg = new PImage[4];
    private final PImage[] redEnemyLeftImg = new PImage[4];
    private final PImage[] redEnemyRightImg = new PImage[4];

    private final PImage[] yellowEnemyDownImg = new PImage[4];
    private final PImage[] yellowEnemyUpImg = new PImage[4];
    private final PImage[] yellowEnemyLeftImg = new PImage[4];
    private final PImage[] yellowEnemyRightImg = new PImage[4];

    private final PImage[] bombImg = new PImage[8];

    private PImage explosionCenterImg;
    private PImage explosionEndBottomImg;
    private PImage explosionEndTopImg;
    private PImage explosionEndLeftImg;
    private PImage explosionEndRightImg;
    private PImage explosionHorizontalImg;
    private PImage explosionVerticalImg;

    private PImage playerIcon;
    private PImage clockIcon;
    private PImage goal;
    private PImage solidWall;
    private PImage brokenWell;
    private PImage empty;


    private int level = 0; // 初步思路是根据关卡load, 关卡改变去调用update方法
    private int lives;
    private final ArrayList<String> levelPathList = new ArrayList<>();
    private final Tile[][] mapDatabase = new Tile[13][15];
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private static long frame = 0L;
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
        // load 图片区

        // 玩家
        for (int i = 0; i < playerDownImg.length; i++) {
            playerDownImg[i] = this.loadImage(String.format("src/main/resources/player/player%d.png", i + 1));
        }

        for (int i = 0; i < playerUpImg.length; i++) {
            playerUpImg[i] = this.loadImage(String.format("src/main/resources/player/player_up%d.png", i + 1));
        }

        for (int i = 0; i < playerLeftImg.length; i++) {
            playerLeftImg[i] = this.loadImage(String.format("src/main/resources/player/player_left%d.png", i + 1));
        }

        for (int i = 0; i < playerRightImg.length; i++) {
            playerRightImg[i] = this.loadImage(String.format("src/main/resources/player/player_right%d.png", i + 1));
        }

        // 红色敌人
        for (int i = 0; i < redEnemyDownImg.length; i++) {
            redEnemyDownImg[i] = this.loadImage(String.format("src/main/resources/red_enemy/red_down%d.png", i + 1));
        }

        for (int i = 0; i < redEnemyUpImg.length; i++) {
            redEnemyUpImg[i] = this.loadImage(String.format("src/main/resources/red_enemy/red_up%d.png", i + 1));
        }

        for (int i = 0; i < redEnemyLeftImg.length; i++) {
            redEnemyLeftImg[i] = this.loadImage(String.format("src/main/resources/red_enemy/red_left%d.png", i + 1));
        }

        for (int i = 0; i < redEnemyRightImg.length; i++) {
            redEnemyRightImg[i] = this.loadImage(String.format("src/main/resources/red_enemy/red_right%d.png", i + 1));
        }

        // 黄色敌人
        for (int i = 0; i < yellowEnemyDownImg.length; i++) {
            yellowEnemyDownImg[i] = this.loadImage(String.format("src/main/resources/yellow_enemy/yellow_down%d.png", i + 1));
        }

        for (int i = 0; i < yellowEnemyUpImg.length; i++) {
            yellowEnemyUpImg[i] = this.loadImage(String.format("src/main/resources/yellow_enemy/yellow_up%d.png", i + 1));
        }

        for (int i = 0; i < yellowEnemyLeftImg.length; i++) {
            yellowEnemyLeftImg[i] = this.loadImage(String.format("src/main/resources/yellow_enemy/yellow_left%d.png", i + 1));
        }

        for (int i = 0; i < yellowEnemyRightImg.length; i++) {
            yellowEnemyRightImg[i] = this.loadImage(String.format("src/main/resources/yellow_enemy/yellow_right%d.png", i + 1));
        }

        for (int i = 0; i < bombImg.length; i++) {
            bombImg[i] = this.loadImage(String.format("src/main/resources/bomb/bomb%d.png", i + 1));
        }

        // 爆炸画面
        explosionCenterImg = this.loadImage("src/main/resources/explosion/centre.png");
        explosionEndLeftImg = this.loadImage("src/main/resources/explosion/end_left.png");
        explosionHorizontalImg = this.loadImage("src/main/resources/explosion/horizontal.png");
        explosionEndBottomImg = this.loadImage("src/main/resources/explosion/end_bottom.png");
        explosionEndTopImg = this.loadImage("src/main/resources/explosion/end_top.png");
        explosionEndRightImg = this.loadImage("src/main/resources/explosion/end_right.png");
        explosionVerticalImg = this.loadImage("src/main/resources/explosion/vertical.png");

        clockIcon = this.loadImage("src/main/resources/icons/clock.png");
        playerIcon = this.loadImage("src/main/resources/icons/player.png");
        goal = this.loadImage("src/main/resources/goal/goal.png");
        solidWall = this.loadImage("src/main/resources/wall/solid.png");
        brokenWell = this.loadImage("src/main/resources/broken/broken.png");
        empty = this.loadImage("src/main/resources/empty/empty.png");

        JSONReader(); // 初始化JSON库 影响各文件的路径 生命数,
        mapStaticRecourseInitUpdate(level); // 第一次初始化数据库为第一个地图,后面只有level更新的时候才生效

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

    private void mapStaticRecourseInitUpdate(int level) {
        int rowIndex = 0;
        try (Scanner fileReader = new Scanner(new File(levelPathList.get(level)))) {
            while (fileReader.hasNextLine()) {
                String config = fileReader.nextLine();
                if (config.length() != 15) {
                    System.err.println("Config File Bad Format!");
                    System.exit(-1);
                }
                int columnIndex = 0;
                for (char c : config.toCharArray()) {
                    Tile tile = null;
                    if (c == 'W') {
                        tile = new SolidWall(columnIndex, rowIndex, solidWall);
                    } else if (c == 'B') {
                        tile = new BrokenWall(columnIndex, rowIndex, brokenWell);
                    } else if (c == ' ') {
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                    } else if (c == 'G') {
                        tile = new GoalTile(columnIndex, rowIndex, goal);
                    } else if (c == 'R') {
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, redEnemyDownImg, redEnemyUpImg,
                                redEnemyLeftImg, redEnemyRightImg, "R", this));
                    } else if (c == 'Y') {
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, yellowEnemyDownImg, yellowEnemyUpImg,
                                yellowEnemyLeftImg, yellowEnemyRightImg, "Y", this));
                    } else if (c == 'P') {
                        tile = new EmptyTile(columnIndex, rowIndex, empty);
                        player = new Player(columnIndex, rowIndex, playerDownImg, playerUpImg,
                                playerLeftImg, playerRightImg, "P", this); // todo 下一步初始化玩家
                    } else {
                        System.err.printf("Unknown type %c in config file\n", c);
                        System.exit(-1);
                    }
                    mapDatabase[rowIndex][columnIndex] = tile;
                    columnIndex++;
                }
                rowIndex++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found");
            System.exit(-1);
        }
    }

    private void mapStaticGraphUpdate(Tile[][] mapDatabase) {
        for (Tile[] tiles : mapDatabase) {
            for (Tile tile : tiles) {
                image(tile.getImage(), 32 * tile.getX(), 64 + 32 * tile.getY());
            }
        }
    }


    public void draw() {
        background(239, 129, 0); // 设置背景色为橙色, 每次都要更新
        mapStaticGraphUpdate(mapDatabase); // 显示当前地图状态
        player.draw();
        for (Enemy enemy : enemies) {
            enemy.move(Direction.AUTO, mapDatabase);
            enemy.draw();
        }
        frame++;
    }

    boolean actionComplete = true; // 为了只执行一次动作

    @Override
    public void keyPressed() {
        // 玩家移动处理部分
        if (actionComplete) {
            boolean status;
            switch (keyCode) {
                case LEFT:
                    status = player.move(Direction.DIRECTION_LEFT, mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press LEFT " + status);
                    break;
                case RIGHT:
                    status = player.move(Direction.DIRECTION_RIGHT, mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press RIGHT " + status);
                    break;
                case UP:
                    status = player.move(Direction.DIRECTION_UP, mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press UP " + status);
                    break;
                case DOWN:
                    status = player.move(Direction.DIRECTION_DOWN, mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press DOWN " + status);
                    break;
            }
        }
    }

    @Override
    public void keyReleased() {
        actionComplete = true;
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
