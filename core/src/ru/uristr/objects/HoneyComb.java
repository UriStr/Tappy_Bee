package ru.uristr.objects;

import java.util.Random;

public class HoneyComb extends Moving {
    private Random r;
    private float groundY;

    public HoneyComb(float x, float y, int width, int height, float movspeed, float groundY) {
        super(x, y, width, height, movspeed);
        r = new Random();
        this.groundY = groundY;
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;
    }
}
