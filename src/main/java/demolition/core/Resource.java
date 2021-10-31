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
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Game resource class, similar to the database of the game, storing all static
 * resources such as png images and the status of the player's enemies.
 */
public class Resource {
    /**
     * Player face down image list.
     */
    public static final PImage[] playerDownImg = new PImage[4];

    /**
     * Player face left image list.
     */
    public static final PImage[] playerLeftImg = new PImage[4];

    /**
     * Player faces right image list.
     */
    public static final PImage[] playerRightImg = new PImage[4];

    /**
     * Player faces up image list.
     */
    public static final PImage[] playerUpImg = new PImage[4];

    /**
     * Red enemy faces down image list.
     */
    public static final PImage[] redEnemyDownImg = new PImage[4];

    /**
     * Red enemy faces up image list.
     */
    public static final PImage[] redEnemyUpImg = new PImage[4];

    /**
     * Red enemy faces left image list.
     */
    public static final PImage[] redEnemyLeftImg = new PImage[4];

    /**
     * Red enemy faces right image list.
     */
    public static final PImage[] redEnemyRightImg = new PImage[4];

    /**
     * Yellow enemy faces down image list.
     */
    public static final PImage[] yellowEnemyDownImg = new PImage[4];

    /**
     * Yellow  enemy faces up image list.
     */
    public static final PImage[] yellowEnemyUpImg = new PImage[4];

    /**
     * Yellow  enemy faces left image list.
     */
    public static final PImage[] yellowEnemyLeftImg = new PImage[4];

    /**
     * Yellow enemy faces right image list.
     */
    public static final PImage[] yellowEnemyRightImg = new PImage[4];

    /**
     * Bomb image list.
     */
    public static final PImage[] bombImg = new PImage[8];

    /**
     * Image of the center explosion.
     */
    public static PImage explosionCenterImg;

    /**
     * Image of the End Bottom explosion.
     */
    public static PImage explosionEndBottomImg;

    /**
     * Image of the End Top explosion.
     */
    public static PImage explosionEndTopImg;

    /**
     * Image of the End Left explosion.
     */
    public static PImage explosionEndLeftImg;

    /**
     * Image of the End Right explosion.
     */
    public static PImage explosionEndRightImg;

    /**
     * Image of the Horizontal explosion.
     */
    public static PImage explosionHorizontalImg;

    /**
     * Image of the Vertical explosion.
     */
    public static PImage explosionVerticalImg;

    /**
     * Image of player lives icon.
     */
    public static PImage playerIcon;

    /**
     * Image of clock icon.
     */
    public static PImage clockIcon;

    /**
     * Image of goal tail.
     */
    public static PImage goal;

    /**
     * Image of solid wall.
     */
    public static PImage solidWall;

    /**
     * Image of broken wall.
     */
    public static PImage brokenWall;

    /**
     * Image of empty glass.
     */
    public static PImage empty;

    /**
     * Text Font.
     */
    public static PFont pFont;

    /**
     * ArrayList of goal tile object in the map.
     */
    public static ArrayList<GoalTile> goalTile = new ArrayList<>();

    /**
     * player current lives.
     */
    public static int lives;

    /**
     * list of every level path
     */
    public static ArrayList<String> levelPathList;

    /**
     * list of each level's time limit.
     */
    public static ArrayList<Integer> timeList;

    /**
     * Tail Array, store all object in the map.
     */
    public static final Tile[][] mapDatabase = new Tile[13][15];

    /**
     * Player object. Should call after setup.
     */
    public static Player player;

    /**
     * Game time counter.
     */
    public static int timer;

    /**
     * UI object.
     */
    public static UI UI;

    /**
     * Store the game state, any non-null assignment will cause the game to end.
     */
    public static GameStatus gameStatus = null;

    /**
     * The current map stage of the game.
     */
    public static int currentLevel;

    /**
     * Execution queue.
     */
    public static LinkedList<DynamicObject> dynamicObjects = new LinkedList<>();

    /**
     * Setup All static resources,
     * all static resource calls must go through setup before they can run,
     * otherwise will receive null.
     *
     * @param app    PApplet object.
     * @param config config file path.
     */
    public static void setup(PApplet app, String config) {
        // set up json reader
        JSONReader jsonReader = new JSONReader(config);
        // player image
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

        // Red enemy
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

        // Yellow Enemy
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

        // Explosion images
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
        brokenWall = app.loadImage("src/main/resources/broken/broken.png");
        empty = app.loadImage("src/main/resources/empty/empty.png");

        pFont = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 20);

        currentLevel = 1;
        lives = jsonReader.getLives();
        levelPathList = jsonReader.getLevelPathList();
        timeList = jsonReader.getTimeList();
    }

    /**
     * Update all static resource objects on the map.
     * <p>
     * Re-assign all existing variables to reset the game state, but retain the player's lives.
     * Update the map database to the incoming level.
     *
     * @param level To update the level of the map
     * @param app   PApplet object.
     */
    public static void mapStaticRecourseInitUpdate(int level, PApplet app) {
        // set current level as level
        currentLevel = level;
        timer = timeList.get(level); // get time from time list
        UI = new UI(app);  // reset UI
        dynamicObjects = new LinkedList<>();

        // Variable map, load the objects in the map into the list, complete the update
        int rowIndex = 0;
        try (Scanner fileReader = new Scanner(new File(Resource.levelPathList.get(level)))) { // read from list
            while (fileReader.hasNextLine()) {
                String config = fileReader.nextLine();
                if (config.length() != 15) {
                    throw new RuntimeException("Config File Bad Format!");
                }
                int columnIndex = 0;
                for (char c : config.toCharArray()) {
                    Tile tile = null;
                    if (c == 'W') {
                        tile = new SolidWall(columnIndex, rowIndex, Resource.solidWall);
                    } else if (c == 'B') {
                        tile = new BrokenWall(columnIndex, rowIndex, Resource.brokenWall);
                    } else if (c == ' ') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                    } else if (c == 'G') {
                        tile = new GoalTile(columnIndex, rowIndex, Resource.goal);
                        goalTile.add((GoalTile) tile);
                    } else if (c == 'R') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        dynamicObjects.add(new Enemy(columnIndex, rowIndex, Resource.redEnemyDownImg, Resource.redEnemyUpImg,
                                Resource.redEnemyLeftImg, Resource.redEnemyRightImg, "R", app));
                    } else if (c == 'Y') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        dynamicObjects.add(new Enemy(columnIndex, rowIndex, Resource.yellowEnemyDownImg, Resource.yellowEnemyUpImg,
                                Resource.yellowEnemyLeftImg, Resource.yellowEnemyRightImg, "Y", app));
                    } else if (c == 'P') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        player = new Player(columnIndex, rowIndex, Resource.playerDownImg, Resource.playerUpImg,
                                Resource.playerLeftImg, Resource.playerRightImg, "P", app); // todo 下一步初始化玩家
                    } else {
                        throw new RuntimeException("Unknown type %c in config file\n");
                    }
                    mapDatabase[rowIndex][columnIndex] = tile;
                    columnIndex++;
                }
                rowIndex++;
            }
            dynamicObjects.add(player);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Config file not found");
        }
    }
}
