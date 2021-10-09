package demolition;

import demolition.battle.Bomb;
import demolition.battle.Explosion;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.role.Enemy;
import demolition.tile.*;
import processing.core.PApplet;
import java.util.ArrayList;


public class App extends PApplet {
    private final Resource resource = new Resource();
    private int level = 0; // 初步思路是根据关卡load, 关卡改变去调用update方法
    private final ArrayList<Bomb> bombs = new ArrayList<>();
    private final ArrayList<Explosion> explosions = new ArrayList<>();
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
        resource.mapStaticRecourseInitUpdate(level, this); // 第一次初始化数据库为第一个地图,后面只有level更新的时候才生效
    }


    private void mapStaticGraphUpdate() {
        for (Tile[] tiles : Resource.mapDatabase) {
            for (Tile tile : tiles) {
                image(tile.getImage(), 32 * tile.getX(), 64 + 32 * tile.getY());
            }
        }
    }

    private void remove(){

    }

    @Override
    public void draw() {
        background(239, 129, 0); // 设置背景色为橙色, 每次都要更新
        mapStaticGraphUpdate(); // 显示当前地图状态
        ArrayList<Bomb> bombRemoveList = new ArrayList<>();
        // Bomb part
        for (Bomb bomb : bombs) {
            Bomb removeObj = bomb.draw();
            if (removeObj != null) bombRemoveList.add(bomb);
        }

        // todo remove移动
        for (Bomb bomb : bombRemoveList) {
            System.out.format("\33[0;32mBomb Message: %s removed\33[0m\n", Integer.toHexString(bomb.hashCode())); // todo debug message
            explosions.add(new Explosion(bomb.getX(), bomb.getY(), Resource.mapDatabase, Resource.player, Resource.enemies, this)); // 将爆炸交给爆炸模块处理
            bombs.remove(bomb);
        }

        // Explosion part
        ArrayList<Explosion> explosionsRemoveList = new ArrayList<>();
        for (Explosion explosion : this.explosions) {
            Explosion remove = explosion.draw();
            if (remove != null) explosionsRemoveList.add(remove);
        }

        //todo remove移动
        for (Explosion explosion : explosionsRemoveList) {
            explosions.remove(explosion);
        }

        // 人物绘画(玩家和敌人)
        Resource.player.draw();
        for (Enemy enemy : Resource.enemies) {
            enemy.move(Direction.AUTO, Resource.mapDatabase); // Direction here doesn't matter
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
                case LEFT: // Constant define in processing.core.PConstants
                    status = Resource.player.move(Direction.DIRECTION_LEFT, Resource.mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press LEFT " + status);
                    break;
                case RIGHT:
                    status = Resource.player.move(Direction.DIRECTION_RIGHT, Resource.mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press RIGHT " + status);
                    break;
                case UP:
                    status = Resource.player.move(Direction.DIRECTION_UP, Resource.mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press UP " + status);
                    break;
                case DOWN:
                    status = Resource.player.move(Direction.DIRECTION_DOWN, Resource.mapDatabase);
                    actionComplete = false;
                    System.err.println("Key Press DOWN " + status);
                    break;
                case 32: // space
                    Bomb bomb = new Bomb(Resource.player.getX(), Resource.player.getY(), Resource.bombImg, this, bombs);
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
