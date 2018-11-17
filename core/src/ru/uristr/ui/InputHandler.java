package ru.uristr.ui;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

import ru.uristr.game.GameWorld;
import ru.uristr.loader.ResourseLoader;
import ru.uristr.objects.Bee;

public class InputHandler implements InputProcessor {
    private Bee myBee;
    private List<PlayButton> menuButtons;
    private PlayButton playButton;
    private GameWorld myWorld;
    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        this.myWorld = myWorld;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        myBee = myWorld.getBee();
        int midPointX = myWorld.getMidPointX();
        int midPointY = myWorld.getMidPointY();

        menuButtons = new ArrayList<PlayButton>();
        playButton = new PlayButton(midPointX - 14.5f, midPointY + 10, 29, 29, ResourseLoader.buttonOn, ResourseLoader.buttonOff);
        menuButtons.add(playButton);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public List<PlayButton> getMenuButtons() {
        return menuButtons;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
        } else if (myWorld.isReady()) {
            myWorld.start();
            myBee.onClick();
        } else if (myWorld.isRunning()) {
            myBee.onClick();
        }


        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            myWorld.restart();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public GameWorld getMyWorld() {
        return myWorld;
    }
}
