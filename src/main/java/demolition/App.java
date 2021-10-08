package demolition;

import demolition.battle.Bomb;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.*;
import processing.core.PApplet;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class App extends PApplet {
    private final Resource resource = new Resource();
    private final Tile[][] mapDatabase = new Tile[13][15];
    private int level = 0; // 初步思路是根据关卡load, 关卡改变去调用update方法
    private Player player;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Bomb> bombs = new ArrayList<>();

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
        resource.setup(this);
        mapStaticRecourseInitUpdate(level); // 第一次初始化数据库为第一个地图,后面只有level更新的时候才生效
    }


    private void mapStaticRecourseInitUpdate(int level) {
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
                    } else if (c == 'R') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, Resource.redEnemyDownImg, Resource.redEnemyUpImg,
                                Resource.redEnemyLeftImg, Resource.redEnemyRightImg, "R", this));
                    } else if (c == 'Y') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        enemies.add(new Enemy(columnIndex, rowIndex, Resource.yellowEnemyDownImg, Resource.yellowEnemyUpImg,
                                Resource.yellowEnemyLeftImg, Resource.yellowEnemyRightImg, "Y", this));
                    } else if (c == 'P') {
                        tile = new EmptyTile(columnIndex, rowIndex, Resource.empty);
                        player = new Player(columnIndex, rowIndex, Resource.playerDownImg, Resource.playerUpImg,
                                Resource.playerLeftImg, Resource.playerRightImg, "P", this, Resource.lives); // todo 下一步初始化玩家
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

    @Override
    public void draw() {
        background(239, 129, 0); // 设置背景色为橙色, 每次都要更新
        mapStaticGraphUpdate(mapDatabase); // 显示当前地图状态
        player.draw();
        for (Enemy enemy : enemies) {
            enemy.move(Direction.AUTO, mapDatabase); // Direction here doesn't matter
            enemy.draw();
        }

        ArrayList<Bomb> bombRemoveList = new ArrayList<>();

        for (Bomb bomb : bombs) {
            Bomb removeObj = bomb.draw();
            if (removeObj != null) bombRemoveList.add(bomb);
        }
        for (Bomb bomb : bombRemoveList) {
            System.out.format("\33[0;32mBomb Message: %s removed\33[0m\n", Integer.toHexString(bomb.hashCode())); // todo debug message
            bombs.remove(bomb);
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
                case LEFT: // Constant define in processing.core.PConstants
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
                case 32: // space
                    Bomb bomb = new Bomb(player.getX(), player.getY(), Resource.bombImg, this, bombs);
                    bombs.add(bomb);
                    System.err.println("Key Press SPACE");
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
