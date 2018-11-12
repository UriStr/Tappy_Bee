package ru.uristr.objects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class HoneyComb extends Moving {
    private Random r;
    private float groundY;
    private Rectangle honeyCombUp, honeyCombDown;
    public static final int GAP = 45;
    private boolean isScored = false;

    public HoneyComb(float x, float y, int width, int height, float movspeed, float groundY) {
        super(x, y, width, height, movspeed);
        r = new Random();
        this.groundY = groundY;
        honeyCombUp = new Rectangle();
        honeyCombDown = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        honeyCombUp.set(position.x, position.y, width, height);
        honeyCombDown.set(position.x, position.y + height + GAP, width, groundY - (position.y + height + GAP));
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;
        isScored = false;
    }

    public boolean collides(Bee bee) {
        if (position.x < bee.getX() + bee.getWidth()) {
            return (Intersector.overlaps(bee.getCircle(), honeyCombUp)
                    || Intersector.overlaps(bee.getCircle(), honeyCombDown)
            );
        }
        return false;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean scored) {
        isScored = scored;
    }

    public void onRestart(float x, float movSpeed) {
        velocity.x = movSpeed;
        reset(x);
    }
}
