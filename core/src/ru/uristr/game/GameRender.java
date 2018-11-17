package ru.uristr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ru.uristr.loader.ResourseLoader;
import ru.uristr.objects.Bee;
import ru.uristr.objects.Grass;
import ru.uristr.objects.HoneyComb;
import ru.uristr.objects.MoveHandler;
import ru.uristr.tools.Value;
import ru.uristr.tools.ValueAccessor;
import ru.uristr.ui.InputHandler;
import ru.uristr.ui.PlayButton;

public class GameRender {

    private GameWorld mWorld;
    private int midPointX;
    private int midPointY;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Bee myBee;
    private OrthographicCamera camera;
    private MoveHandler moveHandler;
    private Grass frontGrass, backGrass;
    private HoneyComb hc1, hc2, hc3;
    private List<PlayButton> menuButtons;
    private TweenManager manager;
    private Value alpha = new Value();
    private Color transitionColor;
    private Sprite background, grass, beeMid, honeycombUp, honeycombDown, ready, beeLogo,
            gameOver, highScore, scoreBoard, starOn, starOff, retry;
    private Animation beeAnimation;
    private Music music;
    private Sound test;

    public GameRender(GameWorld world, int gameHeight, int midPointX, int midPointY) {
        mWorld = world;
        this.midPointX = midPointX;
        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, gameHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, 0.5f);

    }

    private void initAssets() {
        background = ResourseLoader.background;
        grass = ResourseLoader.grass;
        beeAnimation = ResourseLoader.beeAnimation;
        beeMid = ResourseLoader.bee2;
        beeLogo = ResourseLoader.tappyBee;
        honeycombUp = ResourseLoader.honeycombUp;
        honeycombDown = ResourseLoader.honeycombDown;
        ready = ResourseLoader.tapToFly;
        gameOver = ResourseLoader.gameOver;
        highScore = ResourseLoader.highScore;
        scoreBoard = ResourseLoader.honeycombResult;
        retry = ResourseLoader.retry;
        starOn = ResourseLoader.starOn;
        starOff = ResourseLoader.starOff;
        music = ResourseLoader.fly;
        test = Gdx.audio.newSound(Gdx.files.internal("sounds/fly.mp3"));
    }

    private void initGameObjects() {
        myBee = mWorld.getBee();
        moveHandler = mWorld.getMoveHandler();
        frontGrass = moveHandler.getFrontGrass();
        backGrass = moveHandler.getBackGrass();
        hc1 = moveHandler.getHc1();
        hc2 = moveHandler.getHc2();
        hc3 = moveHandler.getHc3();

    }

    public void render(float delta, float runTime) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(213 / 255.0f, 240 / 255.0f, 237 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        shapeRenderer.setColor(181 / 255.0f, 242 / 255.0f, 168 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        shapeRenderer.setColor(75 / 255.0f, 136 / 255.0f, 178 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 53);

        shapeRenderer.end();

        batch.begin();
        batch.disableBlending();

        batch.draw(ResourseLoader.background, 0, midPointY + 23, 136, 43);
        batch.enableBlending();


        drawHoneyCombs();
        drawGrass();

        if (mWorld.isRunning()) {
            drawFly(runTime);
            drawScore();
        } else if (mWorld.isReady()) {
            drawFly(runTime);
            drawReady();
        } else if (mWorld.isMenu()) {
            drawBeeCentered(runTime);
            drawMenuIU();
        } else if (mWorld.isGameOver()) {
            drawFly(runTime);
            drawScoreBoard();
            drawGameOver();
            drawRetry();
        } else if (mWorld.isHighScore()) {
            drawFly(runTime);
            drawScoreBoard();
            drawHighScore();
            drawRetry();
        }

        batch.end();
        drawTransition(delta);

        if (myBee.isAlive()) {
            music.play();
            music.setVolume(0.05f);
        } else {
            music.stop();
        }

    }

    private void drawFly(float runTime) {

        if (myBee.notFlap()) {
            batch.draw(beeMid, myBee.getX(), myBee.getY(), myBee.getWidth() / 2.0f, myBee.getHeight() / 2.0f,
                    myBee.getWidth(), myBee.getHeight(), 1, 1, myBee.getRotation());
        } else {
            batch.draw((Sprite) beeAnimation.getKeyFrame(runTime), myBee.getX(), myBee.getY(), myBee.getWidth() / 2.0f, myBee.getHeight() / 2.0f,
                    myBee.getWidth(), myBee.getHeight(), 1, 1, myBee.getRotation());
        }
    }

    private void drawGrass() {
        batch.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
        batch.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawHoneyCombs() {
        batch.draw(honeycombUp, hc1.getX(), hc1.getY(), hc1.getWidth(), hc1.getHeight());
        batch.draw(honeycombDown, hc1.getX(), hc1.getY() + 5 + hc1.getHeight() + 45, hc1.getWidth(), midPointY + 66 - (hc1.getHeight() + 45));

        batch.draw(honeycombUp, hc2.getX(), hc2.getY(), hc2.getWidth(), hc2.getHeight());
        batch.draw(honeycombDown, hc2.getX(), hc2.getY() + 5 + hc2.getHeight() + 45, hc2.getWidth(), midPointY + 66 - (hc2.getHeight() + 45));

        batch.draw(honeycombUp, hc3.getX(), hc3.getY(), hc3.getWidth(), hc3.getHeight());
        batch.draw(honeycombDown, hc3.getX(), hc3.getY() + 5 + hc3.getHeight() + 45, hc3.getWidth(), midPointY + 66 - (hc3.getHeight() + 45));


    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setVal(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }

    public void drawTransition(float delta) {
        if (alpha.getVal() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.getVal());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    private void drawMenuIU() {
        batch.draw(beeLogo, midPointX - 48, midPointY - 50, 96, 14);

        for (PlayButton button : menuButtons
                ) {
            button.draw(batch);
        }
    }

    private void drawScoreBoard() {
        batch.draw(scoreBoard, 24, midPointY - 32, 90, 40);
        batch.draw(starOff, 27, midPointY - 10, 7, 7);
        batch.draw(starOff, 35, midPointY - 10, 7, 7);
        batch.draw(starOff, 43, midPointY - 10, 7, 7);
        batch.draw(starOff, 51, midPointY - 10, 7, 7);
        batch.draw(starOff, 59, midPointY - 10, 7, 7);

        if (mWorld.getScore() > 2) {
            batch.draw(starOn, 27, midPointY - 10, 7, 7);
        }

        if (mWorld.getScore() > 17) {
            batch.draw(starOn, 35, midPointY - 10, 7, 7);
        }

        if (mWorld.getScore() > 50) {
            batch.draw(starOn, 43, midPointY - 10, 7, 7);
        }

        if (mWorld.getScore() > 80) {
            batch.draw(starOn, 51, midPointY - 10, 7, 7);
        }

        if (mWorld.getScore() > 120) {
            batch.draw(starOn, 59, midPointY - 10, 7, 7);
        }

        int length = ("" + mWorld.getScore()).length();
        ResourseLoader.whitefont.draw(batch, "" + mWorld.getScore(), 104 - (2 * length), midPointY - 20);

        int length2 = ("" + ResourseLoader.getHighScore()).length();
        ResourseLoader.whitefont.draw(batch, "" + ResourseLoader.getHighScore(), 105 - (2.5f * length2), midPointY - 9);

    }

    private void drawRetry() {
        batch.draw(retry, 36, midPointY + 10, 66, 14);
    }

    private void drawReady() {
        batch.draw(ready, 36, midPointY - 50, 68, 14);
    }

    private void drawGameOver() {
        batch.draw(gameOver, 24, midPointY - 50, 92, 14);
    }

    private void drawHighScore() {
        batch.draw(highScore, 22, midPointY - 50, 96, 14);
    }

    public void drawScore() {
        int length = ("" + mWorld.getScore()).length();
        ResourseLoader.shadow.draw(batch, "" + mWorld.getScore(), 68 - (3 * length), midPointY - 82);
        ResourseLoader.font.draw(batch, "" + mWorld.getScore(), 68 - (3 * length), midPointY - 83);
    }

    private void drawBeeCentered(float runTime) {
        batch.draw((TextureRegion) beeAnimation.getKeyFrame(runTime), 59f, myBee.getY() - 15f, myBee.getWidth() / 2.0f, myBee.getHeight() / 2.0f, myBee.getWidth(), myBee.getHeight(), 1f, 1f, myBee.getRotation());


    }
}
