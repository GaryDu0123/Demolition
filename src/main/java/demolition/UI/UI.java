package demolition.UI;

import demolition.core.Resource;
import demolition.enums.GameStatus;
import processing.core.PApplet;

public class UI {
    private int counter = 0;
    private final PApplet app;
    GameStatus gameStatus;

    public UI(PApplet app) {
        this.app = app;
    }

    public UI(PApplet app, GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.app = app;
    }

    public void show(){
        if (gameStatus == null){
            if (counter == 60){
                counter = 0;
                Resource.timer--;
            }
            app.image(Resource.playerIcon, 32 * 4, 16);
            app.text(Resource.player.getLives(), 170, 44);
            app.image(Resource.clockIcon, 32 * 8, 16);
            app.text(Resource.timer, 290, 44);
            counter++;
        } else if (gameStatus == GameStatus.WIN){
            app.text("YOU WIN", 170, 44);

        } else if (gameStatus == GameStatus.LOSE){
            app.text("YOU LOSE", 170, 44);

        }
    }

}
