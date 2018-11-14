package ru.uristr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import ru.uristr.loader.ResourseLoader;
import ru.uristr.objects.Bee;
import ru.uristr.objects.MoveHandler;

public class GameWorld {

    public int getMidPointX() {
        return midPointX;
    }

    public int getMidPointY() {
        return midPointY;
    }

    private int midPointX;
    private int midPointY;
    private int score = 0;
    private float runTime = 0;



    private Bee bee;
    private MoveHandler moveHandler;
    private Rectangle ground;
    private GameRender renderer;
    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public GameWorld(int midPointX, int midPointY) {
        currentState = GameState.MENU;
        this.midPointX = midPointX;
        this.midPointY = midPointY;

        bee = new Bee(33, midPointY - 5, 17, 12);

        moveHandler = new MoveHandler(this, midPointY + 66);

        ground = new Rectangle(0, midPointY + 66, 137, 11);

    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    public void updateReady(float delta) {
        bee.updateReady(runTime);
        moveHandler.updateReady(delta);
    }

    public void updateRunning(float delta) {
        if (delta > 0.15f) {
            delta = 0.15f;
        }

        bee.update(delta);
        moveHandler.update(delta);

        if (moveHandler.collides(bee) && bee.isAlive()) {
            moveHandler.stop();
            bee.die();


            ResourseLoader.fall.play();
            renderer.prepareTransition(255,255,255,0.3f);
            currentState = GameState.GAMEOVER;
            highScore();
        }

        if (Intersector.overlaps(bee.getCircle(), ground)) {
            if (bee.isAlive()) {
                ResourseLoader.dead.play();
                bee.die();
                renderer.prepareTransition(255, 255, 255, 0.3f);
            }
            moveHandler.stop();
            bee.cling();
            currentState = GameState.GAMEOVER;

            highScore();
        }

    }

    private void highScore() {
        if (score > ResourseLoader.getHighScore()) {
            ResourseLoader.setHighScore(score);
            currentState = GameState.HIGHSCORE;
        }
    }

    public MoveHandler getMoveHandler() {
        return moveHandler;
    }

    public Bee getBee() {
        return bee;
    }

    public void setRenderer(GameRender renderer) {
        this.renderer = renderer;
    }

    public void restart() {

        score = 0;
        bee.onRestart(midPointY - 5);
        moveHandler.onRestart();
        ready();
    }

    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }






}
