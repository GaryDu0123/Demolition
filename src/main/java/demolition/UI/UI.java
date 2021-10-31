package demolition.UI;

import demolition.core.Resource;
import processing.core.PApplet;

/**
 * Used to display the game UI part, life, time.
 */
public class UI {
    /**
     * Counter, counting one minute
     */
    private int counter = 0;

    /**
     * PApplet object
     *
     * @see PApplet
     */
    private final PApplet app;

    /**
     * Display the game UI, time, statement, etc.
     *
     * @param app PApplet Object
     */
    public UI(PApplet app) {
        this.app = app;
    }

    /**
     * Display the game UI, and modify the game timer internally.
     */
    public void show() {
        if (counter == 60) {
            counter = 0;
            Resource.timer--;
        }
        app.image(Resource.playerIcon, 32 * 4, 16);
        app.text(Resource.player.getLives(), 170, 44);
        app.image(Resource.clockIcon, 32 * 8, 16);
        app.text(Resource.timer, 290, 44);
        counter++;
    }

}
