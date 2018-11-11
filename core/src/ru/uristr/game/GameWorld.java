package ru.uristr.game;

import com.badlogic.gdx.Gdx;

import ru.uristr.objects.Bee;
import ru.uristr.objects.MoveHandler;

public class GameWorld {

    private int midPointX;
    private int midPointY;
    private Bee bee;
    private MoveHandler moveHandler;



    public GameWorld(int midPointX, int midPointY) {
        this.midPointX = midPointX;
        this.midPointY = midPointY;

        bee = new Bee(33, midPointY - 5, 17, 12);

        moveHandler = new MoveHandler(this, midPointY + 66);

    }

    public void update(float delta) {
        bee.update(delta);
        moveHandler.update(delta);
    }

    public MoveHandler getMoveHandler() {
        return moveHandler;
    }

    public Bee getBee() {
        return bee;
    }
}
