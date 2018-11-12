package ru.uristr.objects;

import java.util.Random;

public class Grass extends Moving {


    public Grass(float x, float y, int width, int height, float movspeed) {
        super(x, y, width, height, movspeed);

    }

    public void onRestart(float x, float movSpeed) {
        position.x = x;
        velocity.x = movSpeed;
    }
}
