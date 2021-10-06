package demolition.role;

import demolition.enums.Direction;
import demolition.interfaces.Movable;
import demolition.tile.Tile;
import processing.core.PApplet;
import processing.core.PImage;

public class Enemy extends Role implements Movable {

    private int counter = 0;

    public Enemy(int X, int Y, PImage[] downImg, PImage[] upImg,
                 PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        if (counter != 60){ // 可以考虑改为获取app的帧率
            counter ++;
            return false;
        }
        counter = 0; // 帧率计数器重置, 一秒动一回, 此分支为执行

        if ("R".equals(getCharacter())){
            if (moveValidityCheck(this, direction, database)){

            }
        } else if ("Y".equals(getCharacter())){

        }

        return false;
    }
}
