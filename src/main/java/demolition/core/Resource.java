package demolition.core;

import demolition.util.JSONReader;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

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

    public static int lives;
    public static ArrayList<String> levelPathList;

    private final JSONReader jsonReader = new JSONReader("config.json");// 初始化JSON库 影响各文件的路径 生命数,

    public Resource(){
    }
    
    public void setup(PApplet app){
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


        lives = jsonReader.getLives();
        levelPathList = jsonReader.getLevelPathList();
    }
    
    
    
}
