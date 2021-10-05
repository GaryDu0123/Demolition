package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    public App() {

    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }
    PImage player;
    public void setup() {
        frameRate(FPS);
        player = this.loadImage("src/main/resources/player/player.gif");
//        background(239,129,0);

        // Load images during setup
    }
    static int i = 0;
    public void draw() {
        background(239,129,0);
        image(player,0,0);
        if (keyPressed && (key == CODED))
        {
            switch(keyCode)
            {
                case LEFT:
                    System.out.println("LEFT");
                    break;
                case RIGHT:
                    System.out.println("RIGHT");
                    break;
                case UP:
                    System.out.println("UP");
                    break;
                case DOWN:
                    System.out.println("DOWN");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
