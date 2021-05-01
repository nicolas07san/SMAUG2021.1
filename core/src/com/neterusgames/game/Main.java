package com.neterusgames.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.screens.MainMenuScreen;
import com.neterusgames.tools.ScrollingBackground;

public class Main extends Game {
	public static final int WIDTH = 550;
	public static final int HEIGHT = 650;
	public static final int SCALE = 2;

	private SplashWorker splashWorker;

	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	
	@Override
	public void create () {
		splashWorker.closeSplashScreen();
		batch = new SpriteBatch();
		scrollingBackground = new ScrollingBackground();
		setScreen(new MainMenuScreen(this));

	}
	@Override
	public void render () {
		super.render();

	}
	@Override
	public void dispose () {

	}
	public SplashWorker getSplashWorker() {
		return splashWorker;
	}

	public void setSplashWorker(SplashWorker splashWorker) {
		this.splashWorker = splashWorker;
	}

	public void resize(int width, int height){
		this.scrollingBackground.resize(width);
		super.resize(width,height);
	}
}
