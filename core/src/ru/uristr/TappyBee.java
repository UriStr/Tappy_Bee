package ru.uristr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.uristr.loader.ResourseLoader;
import ru.uristr.screen.SplashScreen;

public class TappyBee extends Game {

	@Override
	public void create () {
		ResourseLoader.load();
		setScreen(new SplashScreen(this));
	}

	
	@Override
	public void dispose () {
		super.dispose();
		ResourseLoader.dispose();
	}
}
