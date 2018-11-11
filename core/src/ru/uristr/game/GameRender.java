package ru.uristr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ru.uristr.loader.ResourseLoader;
import ru.uristr.objects.Bee;
import ru.uristr.objects.Grass;
import ru.uristr.objects.HoneyComb;
import ru.uristr.objects.MoveHandler;

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

    private Sprite background, grass, beeMid, honeycombUp, honeycombDown, ready, beeLogo,
    gameOver, highScore, scoreBoard, starOn, starOff, retry;
    private Animation beeAnimation;
    private Music music;

    public GameRender(GameWorld world, int gameHeight, int midPointX, int midPointY) {
        mWorld = world;
        this.midPointX = midPointX;
        this.midPointY = midPointY;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136,gameHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();

    }

    private void initAssets() {
        background = ResourseLoader.background;
        grass = ResourseLoader.grass;
        beeAnimation = ResourseLoader.beeAnimation;
        beeMid = ResourseLoader.bee2;
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

        drawFly(runTime);
        drawHoneyCombs();
        drawGrass();

        batch.end();

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
        batch.draw(honeycombDown, hc1.getX(), hc1.getY()+5 + hc1.getHeight() + 45, hc1.getWidth(), midPointY + 66 - (hc1.getHeight() + 45));

        batch.draw(honeycombUp, hc2.getX(), hc2.getY(), hc2.getWidth(), hc2.getHeight());
        batch.draw(honeycombDown, hc2.getX(), hc2.getY()+5 + hc2.getHeight() + 45, hc2.getWidth(), midPointY + 66 - (hc2.getHeight() + 45));

        batch.draw(honeycombUp, hc3.getX(), hc3.getY(), hc3.getWidth(), hc3.getHeight());
        batch.draw(honeycombDown, hc3.getX(), hc3.getY()+5 + hc3.getHeight() + 45, hc3.getWidth(), midPointY + 66 - (hc3.getHeight() + 45));


    }

}
