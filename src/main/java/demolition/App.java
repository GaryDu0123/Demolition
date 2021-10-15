package demolition;


import demolition.battle.Bomb;
import demolition.battle.Explosion;
import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.enums.GameStatus;
import demolition.role.Enemy;
import demolition.tile.*;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class App extends PApplet {
    private int level = 0; // 初步思路是根据关卡load, 关卡改变去调用update方法
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    public App() {
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        frameRate(FPS); // 游戏刷新帧率
        Resource.setup(this);
        this.textFont(Resource.pFont);
        this.fill(0);
        Resource.mapStaticRecourseInitUpdate(level, this); // 第一次初始化数据库为第一个地图,后面只有level更新的时候才生效
    }


    private void mapStaticGraphUpdate() {
        for (Tile[] tiles : Resource.mapDatabase) {
            for (Tile tile : tiles) {
                image(tile.getImage(), 32 * tile.getX(), 64 + 32 * tile.getY());
            }
        }
    }


    private void gameStatusChanger() {
        if (Resource.goalTile.getX() == Resource.player.getX() && Resource.goalTile.getY() == Resource.player.getY()) {
            level++;
            if (level < Resource.levelPathList.size()) {
                Resource.mapStaticRecourseInitUpdate(level, this);


            } else {
                background(239, 129, 0); // 设置背景色为橙色, 每次都要更新
                Resource.gameStatus = GameStatus.WIN;
                this.text("YOU WIN", 160, 240);
            }
        } else if (Resource.timer <= 0 || Resource.player.getLives() <= 0) {
            background(239, 129, 0); // 设置背景色为橙色, 每次都要更新
            Resource.gameStatus = GameStatus.LOSE;
            this.text("YOU LOSE", 160, 240);
        }
    }

    @Override
    public void draw() {

        background(239, 129, 0); // 设置背景色为橙色, 每次都要更新

        gameStatusChanger();

        if (Resource.gameStatus != null) {
            return;
        }

        Resource.UI.show();

        mapStaticGraphUpdate(); // 显示当前地图状态

        ArrayList<DynamicObject> processList = new ArrayList<>();
        Collections.sort(Resource.dynamicObjects);
        Iterator<DynamicObject> iterator = Resource.dynamicObjects.iterator();

        while (iterator.hasNext()) {
            DynamicObject object = iterator.next(); // todo

            if (object instanceof Enemy)
                ((Enemy) object).move(Direction.AUTO, Resource.mapDatabase);

            DynamicObject rmObject = object.draw();
            if (rmObject != null) {
                if (rmObject instanceof Bomb) {
                    processList.add(new Explosion(rmObject.getX(), rmObject.getY(),
                            Resource.mapDatabase, Resource.player, Resource.dynamicObjects, this)); // 将爆炸交给爆炸模块处理
                }
                iterator.remove();
            }
        }
        Resource.dynamicObjects.addAll(processList);
    }

    boolean actionComplete = true; // 为了只执行一次动作

    @Override
    public void keyPressed() {
        // 玩家移动处理部分
        if (actionComplete && Resource.gameStatus == null) {
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
                    Bomb bomb = new Bomb(Resource.player.getX(), Resource.player.getY(), Resource.bombImg, this);
                    Resource.dynamicObjects.add(bomb);
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
