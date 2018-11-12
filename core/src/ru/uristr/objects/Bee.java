package ru.uristr.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import ru.uristr.loader.ResourseLoader;

public class Bee {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private Circle circle;

    private float rotation;
    private float width;
    private float height;
    private boolean isAlive;
    private float originalY;


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getRotation() {
        return rotation;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Circle getCircle() {
        return circle;
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        if (position.y < -5) {
            position.y = -5;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));

        circle.set(position.x + 9, position.y + 6, 6.5f);

        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        if (isFalling()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }
        }
    }

    public void  onClick() {
        if (isAlive) {
            velocity.y = -140;
            ResourseLoader.flap.play();
        }
    }


    public Bee(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.originalY = y;

        circle = new Circle();
        isAlive = true;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean notFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void cling() {
        acceleration.y = 0;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;

    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }
}
