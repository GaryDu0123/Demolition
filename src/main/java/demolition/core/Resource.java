package demolition.core;

import demolition.UI.UI;
import demolition.enums.GameStatus;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.*;
import demolition.util.JSONReader;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Resource {
    public static final PImage[] playerDownImg = new PImage[4];
    public static final PImage[] playerLeftImg = new PImage[4];
    public static final PImage[] playerRightImg = new PImage[4];
    public static final PImage[] playerUpImg = new PImage[4];

    public static final PImage[] redEnemyDownImg = new PImage[4];
    public static final PImage[] redEnemyUpImg = new PImage[4];
    public static final PImage[] redEnemyLeftImg = new PImage[4];
    public static final PImage[] redEnemyRightImg = new PImage[4];

    public static final PImage[] yellowEnemyDownImg = new PImage[4];
    public static final PImage[] yellowEnemyUpImg = new PImage[4];
    public static final PImage[] yellowEnemyLeftImg = new PImage[4];
    public static final PImage[] yellowEnemyRightImg = new PImage[4];

    public static final PImage[] bombImg = new PImage[8];

    public static PImage explosionCenterImg;
    public static PImage explosionEndBottomImg;
    public static PImage explosionEndTopImg;
    public static PImage explosionEndLeftImg;
    public static PImage explosionEndRightImg;
    public static PImage explosionHorizontalImg;
    public static PImage explosionVerticalImg;

    public static PImage playerIcon;
    public static PImage clockIcon;
    public static PImage goal;
    public static PImage solidWall;
    public static PImage brokenWell;
    public static PImage empty;

    public static PFont pFont;

    public static GoalTile goalTile;
    public static int lives;
    public static ArrayList<String> levelPathList;
    public static ArrayList<Integer> timeList;

    private static final JSONReader jsonReader = new JSONReader("config.json");// 初始化JSON库 影响各文件的路径 生命数,

    public static final Tile[][] mapDatabase = new Tile[13][15];
    public static Player player;
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static int timer;
    public static UI UI;
    public static GameStatus gameStatus = null;


    public Resource(){
    }
    
    public static void setup(PApplet app){
        // load 图片区

        // 玩家
        for (int i = 0; i < playerDownImg.length; i++) {
            playerDownImg[i] = app.loadImage(String.format("src/main/resources/player/player%d.png", i + 1));
        }

        for (int i = 0; i < playerUpImg.length; i++) {
            playerUpImg[i] = app.loadImage(String.format("src/main/resources/player/player_up%d.png", i + 1));
        }

        for (int i = 0; i < playerLeftImg.length; i++) {
            playerLeftImg[i] = app.loadImage(String.format("src/main/resources/player/player_left%d.png", i + 1));
        }

        for (int i = 0; i < playerRightImg.length; i++) {
            playerRightImg[i] = app.loadImage(String.format("src/main/resources/player/player_right%d.png", i + 1));
        }

        // 红色敌人
        for (int i = 0; i < redEnemyDownImg.length; i++) {
            redEnemyDownImg[i] = app.loadImage(String.format("src/main/resources/red_enemy/red_down%d.png", i + 1));
        }

        for (int i = 0; i < redEnemyUpImg.length; i++) {
            redEnemyUpImg[i] = app.loadImage(String.format("src/main/resources/red_enemy/red_up%d.png", i + 1));
        }

        for (int i = 0; i < redEnemyLeftImg.length; i++) {
            redEnemyLeftImg[i] = app.loadImage(String.format("src/main/resources/red_enemy/red_left%d.png", i + 1));
        }

        for (int i = 0; i < redEnemyRightImg.length; i++) {
            redEnemyRightImg[i] = app.loadImage(String.format("src/main/resources/red_enemy/red_right%d.png", i + 1));
        }

        // 黄色敌人
        for (int i = 0; i < yellowEnemyDownImg.length; i++) {
            yellowEnemyDownImg[i] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_down%d.png", i + 1));
        }

        for (int i = 0; i < yellowEnemyUpImg.length; i++) {
            yellowEnemyUpImg[i] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_up%d.png", i + 1));
        }

        for (int i = 0; i < yellowEnemyLeftImg.length; i++) {
            yellowEnemyLeftImg[i] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_left%d.png", i + 1));
        }

        for (int i = 0; i < yellowEnemyRightImg.length; i++) {
            yellowEnemyRightImg[i] = app.loadImage(String.format("src/main/resources/yellow_enemy/yellow_right%d.png", i + 1));
        }

        for (int i = 0; i < bombImg.length; i++) {
            bombImg[i] = app.loadImage(String.format("src/main/resources/bomb/bomb%d.png", i + 1));
        }

        // 爆炸画面
        explosionCenterImg = app.loadImage("src/main/resources/explosion/centre.png");
        explosionEndLeftImg = app.loadImage("src/main/resources/explosion/end_left.png");
        explosionHorizontalImg = app.loadImage("src/main/resources/explosion/horizontal.png");
        explosionEndBottomImg = app.loadImage("src/main/resources/explosion/end_bottom.png");
        explosionEndTopImg = app.loadImage("src/main/resources/explosion/end_top.png");
        explosionEndRightImg = app.loadImage("src/main/resources/explosion/end_right.png");
        explosionVerticalImg = app.loadImage("src/main/resources/explosion/vertical.png");

        clockIcon = app.loadImage("src/main/resources/icons/clock.png");
        playerIcon = app.loadImage("src/main/resources/icons/player.png");
        goal = app.loadImage("src/main/resources/goal/goal.png");
        solidWall = app.loadImage("src/main/resources/wall/solid.png");
        brokenWell = app.loadImage("src/main/resources/broken/broken.png");
        empty = app.loadImage("src/main/resources/empty/empty.png");

        pFont = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 20);

        lives = jsonReader.getLives();
        levelPathList = jsonReader.getLevelPathList();
        timeList = jsonReader.getTimeList();
    }

    public static void mapStaticRecourseInitUpdate(int level, PApplet app) {
        enemies = new ArrayList<>();
        timer = timeList.get(level);
        UI = new UI(app);

        int rowIndex = 0;
        try (Scanner fileReader = new Scanner(new File(Resource.levelPathList.get(level)))) {
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
                        tile = new SolidWall(columnIndex, rowIndex, Resource.solidWall);
                    } else if (c == 'B') {
                        tile = new BrokenWall(columnIndex, rowIndex, Resource.brokenWell);
                    } else if (c == ' ') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                    } else if (c == 'G') {
                        tile = new GoalTile(columnIndex, rowIndex, Resource.goal);
                        goalTile = (GoalTile) tile;
                    } else if (c == 'R') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, Resource.redEnemyDownImg, Resource.redEnemyUpImg,
                                Resource.redEnemyLeftImg, Resource.redEnemyRightImg, "R", app));
                    } else if (c == 'Y') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, Resource.yellowEnemyDownImg, Resource.yellowEnemyUpImg,
                                Resource.yellowEnemyLeftImg, Resource.yellowEnemyRightImg, "Y", app));
                    } else if (c == 'P') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        player = new Player(columnIndex, rowIndex, Resource.playerDownImg, Resource.playerUpImg,
                                Resource.playerLeftImg, Resource.playerRightImg, "P", app, Resource.lives); // todo 下一步初始化玩家
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

//    public static void mapStaticRecourseInitUpdate(GameStatus status) {
//        gameStatus = status;
//        switch (gameStatus){
//            case WIN:
//
//
//                break;
//            case LOSE:
//
//
//                break;
//        }
//
//    }
}
