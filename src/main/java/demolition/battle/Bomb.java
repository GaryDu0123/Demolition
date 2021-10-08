package demolition.battle;

import demolition.core.GameObject;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.tile.Tile;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Bomb extends GameObject {
    public PApplet app;
    private final PImage[] images;
    private int counter = 0;
    private int actionStatus = 0;
    private final ArrayList<Bomb> bombsList;

    public Bomb(int x, int y, PImage[] images, PApplet app, ArrayList<Bomb> bombsList) {
        super(x, y);
        this.images = images;
        this.app = app;
        this.bombsList = bombsList;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public PApplet getApp() {
        return app;
    }

    public PImage[] getImages() {
        return images;
    }

    public int getDisplayX() {
        return getX() * 32;
    }

    public int getDisplayY() {
        return getY() * 32 + 64;
    }

    public Bomb draw() {
        if (actionStatus == 8) {
            return this;
        }
        getApp().image(images[actionStatus], getDisplayX(), getDisplayY());
        if (counter % 15 == 0){ // 2  * app.frameRate / 8 = 15
            actionStatus ++;
            counter = 0;
        }
        counter ++;
        return null;
    }

    private static void damageDetection(){

    }

    public static void explosion(int x, int y, Tile[] database, Player player, Enemy[] enemies, PApplet app){
        for (int i = 1; i <= 2; i++) { // 1 2
            System.out.println(i);
        }
        for (int i = -1; i > -3; i--) { // -1 -2
            System.out.println(i);
        }
        for (int i = 1; i <= 2; i++) { // 1 2
            System.out.println(i);
        }
        for (int i = -1; i > -3; i--) { // -1 -2
            System.out.println(i);
        }
        /*
        todo
        1. 先查找爆炸范围, 变量一遍直到2格或者遇到阻碍, 实体和可破坏的分开考虑, 记录坐标点, 并修改数据库
        2.  通过坐标点遍历人, 看有没有人受伤
        3. 将受伤的人和要被删除的敌人返回{或者人直接减血吗,只返回要删除的敌人, 在循环开始处检查玩家状况}
         */
    }
}
