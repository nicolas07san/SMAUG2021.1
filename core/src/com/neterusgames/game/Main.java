package com.neterusgames.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.screens.MainMenuScreen;


public class Main extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int SCALE = 2;

	private SplashWorker splashWorker;

	public SpriteBatch batch;

	public void create () {
		splashWorker.closeSplashScreen();
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));

	}

	public void render () {
		super.render();
	}

	public void dispose () {
		batch.dispose();
		this.getScreen().dispose();
	}

	public void setSplashWorker(SplashWorker splashWorker) {
		this.splashWorker = splashWorker;
	}

	public void resize(int width, int height){
		super.resize(width,height);
	}
}
