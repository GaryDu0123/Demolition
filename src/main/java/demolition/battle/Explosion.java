package demolition.battle;

import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.role.Role;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class Explosion extends DynamicObject{
    public PApplet app;
    private int counter = 0;
    private final Tile[][] database;
    private final ArrayList<Role> roleList = new ArrayList<>();
    private final ArrayList<AnimationModule> animation = new ArrayList<>();
    private final ArrayList<int[]> deathCheck = new ArrayList<>(); // todo 查找玩家和敌人的死亡点
    private static int explosionCounter = 0;
    private final int damagedCount;


    public Explosion(int x, int y, Tile[][] database, Player player, LinkedList<DynamicObject> enemies, PApplet app) {
        super(x, y);
        this.app = app;
        this.database = database;
        roleList.add(player);
        for (DynamicObject enemy : enemies) {
            if (enemy instanceof Enemy)
                roleList.add((Enemy)enemy);
        }
        rangeDetection();
        damagedCount = explosionCounter;
    }


    static class AnimationModule {
        public int x;
        public int y;
        PImage image;

        public AnimationModule(int x, int y, PImage image) {
            this.x = x;
            this.y = y;
            this.image = image;
        }
        public int getDisplayX() {
            return x * 32;
        }


        public int getDisplayY() {
            return y * 32 + 64;
        }
    }

    @Override
    public int getDisplayX() {
        return getX() * 32;
    }

    @Override
    public int getDisplayY() {
        return getY() * 32 + 64;
    }

    public Explosion draw(){
        if (counter == 30){
            return this;
        }

        for (Role role : roleList) {
            for (int[] location : deathCheck) {
                if (role.getX() == location[0] && role.getY() == location[1] && !role.getExplosionCounterSet().contains(damagedCount)){
                    role.getExplosionCounterSet().add(damagedCount);
                    role.setLivesLoss(); // todo 敌人只有一滴血的时候不算bug, 玩家也不算, 因为地图会重置
                    break;
                }
            }
        }
        for (AnimationModule animationModule : animation) {
            app.image(animationModule.image, animationModule.getDisplayX(), animationModule.getDisplayY());
        }
        counter++;
        return null;
    }

    public void rangeDetection() {
        ArrayList<Tile> tails = new ArrayList<>(2);

        for (int step = 1; step <= 2; step++) { // 1 2  向下移动
            if (getY() + step < database.length)
                tails.add(database[getY() + step][getX()]);
        }
        checkProcess(tails, deathCheck, Resource.explosionVerticalImg, Resource.explosionEndBottomImg);

        tails = new ArrayList<>(2);
        for (int step = -1; step > -3; step--) { // -1 -2
            if (getY() + step >= 0)
                tails.add(database[getY() + step][getX()]);
        }
        checkProcess(tails, deathCheck, Resource.explosionVerticalImg, Resource.explosionEndTopImg);

        tails = new ArrayList<>(2);
        for (int step = 1; step <= 2; step++) { // 1 2
            if (getX() + step < database[0].length)
                tails.add(database[getY()][getX() + step]);
        }
        checkProcess(tails, deathCheck, Resource.explosionHorizontalImg, Resource.explosionEndRightImg);

        tails = new ArrayList<>(2);
        for (int step = -1; step > -3; step--) { // 1 2
            if (getX() + step >= 0)
                tails.add(database[getY()][getX() + step]);
        }
        checkProcess(tails, deathCheck, Resource.explosionHorizontalImg, Resource.explosionEndLeftImg);

        deathCheck.add(new int[]{getX(), getY(), damagedCount});
        animation.add(new AnimationModule(getX(), getY(), Resource.explosionCenterImg)); // 居中图片
        explosionCounter++;
        /*
        todo
        1. 先查找爆炸范围, 变量一遍直到2格或者遇到阻碍, 实体和可破坏的分开考虑, 记录坐标点, 并修改数据库
        2. 通过坐标点遍历人, 看有没有人受伤
        3. 将受伤的人和要被删除的敌人返回{或者人直接减血吗,只返回要删除的敌人, 在循环开始处检查玩家状况
        */
    }

    private void checkProcess(ArrayList<Tile> tile, ArrayList<int[]> deathCheck, PImage middle, PImage edge){
        if (tile.size() == 0) return;
        if (tile.get(0).getCharacter().equals(EmptyTile.character) || tile.get(0).getCharacter().equals(GoalTile.character)) {
            switch (tile.get(1).getCharacter()) {
                case EmptyTile.character:
                case GoalTile.character: {
                    animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), middle));
                    animation.add(new AnimationModule(tile.get(1).getX(), tile.get(1).getY(), edge));
                    // 位置点查找两格
                    deathCheck.add(new int[]{tile.get(0).getX(), tile.get(0).getY(), damagedCount});
                    deathCheck.add(new int[]{tile.get(1).getX(), tile.get(1).getY(), damagedCount});
                    // todo 死亡调用 搜索两格
                    break;
                }
                case BrokenWall.character: {
                    animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), middle));
                    animation.add(new AnimationModule(tile.get(1).getX(), tile.get(1).getY(), edge));
                    database[tile.get(1).getY()][tile.get(1).getX()].die(); // 数据库中把该对象删除
                    //查找一格
                    deathCheck.add(new int[]{tile.get(0).getX(), tile.get(0).getY(), damagedCount});
                    // todo 死亡调用 搜索一格
                    break;
                }
                case SolidWall.character: {
                    animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), edge));

                    // 查找一格
                    deathCheck.add(new int[]{tile.get(0).getX(), tile.get(0).getY(), damagedCount});
                    //todo 死亡调用, 搜索一格
                    break;
                }
            }
        } else if (tile.get(0).getCharacter().equals(BrokenWall.character)) {
            animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), edge));
            database[tile.get(0).getY()][tile.get(0).getX()].die();
        }
        // 减血 -1

    }
}
