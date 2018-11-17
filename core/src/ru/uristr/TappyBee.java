package ru.uristr;

import com.badlogic.gdx.Game;

import ru.uristr.loader.ResourseLoader;
import ru.uristr.screen.SplashScreen;

public class TappyBee extends Game {

    @Override
    public void create() {
        ResourseLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourseLoader.dispose();
    }
}
