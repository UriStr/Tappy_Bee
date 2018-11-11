package ru.uristr.objects;

import ru.uristr.game.GameWorld;

public class MoveHandler {
    private Grass frontGrass, backGrass;
    private  HoneyComb hc1, hc2, hc3;
    public static final int MOV_SPEED = -59;
    public static final int HC_GAP = 60;

    private GameWorld gameWorld;

    public MoveHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontGrass = new Grass(0, yPos, 143, 11, MOV_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, MOV_SPEED);

        hc1 = new HoneyComb(210, 0, 22, 60, MOV_SPEED, yPos);
        hc2 = new HoneyComb(hc1.getTailX() + HC_GAP, 0, 22, 70, MOV_SPEED, yPos);
        hc3 = new HoneyComb(hc2.getTailX() + HC_GAP, 0, 22, 60, MOV_SPEED, yPos);
    }

    public void update(float delta) {
        frontGrass.update(delta);
        backGrass.update(delta);
        hc1.update(delta);
        hc2.update(delta);
        hc3.update(delta);

        if (hc1.isScrolledLeft()) {
            hc1.reset(hc3.getTailX() + HC_GAP);
        } else if (hc2.isScrolledLeft()) {
            hc2.reset(hc1.getTailX() + HC_GAP);
        } else if (hc3.isScrolledLeft()) {
            hc2.reset(hc2.getTailX() + HC_GAP);
        }

        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }

    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public HoneyComb getHc1() {
        return hc1;
    }

    public HoneyComb getHc2() {
        return hc2;
    }

    public HoneyComb getHc3() {
        return hc3;
    }
}
