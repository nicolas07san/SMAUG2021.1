package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class GameOverScreen extends BaseScreen{

    private int score;
    private int highscore;

    private Texture backButtonInactive;
    private Texture backButtonActive;
    private int backButtonX;
    private int backButtonY;

    private Texture playButtonInactive;
    private Texture playButtonActive;
    private int playButtonX;
    private int playButtonY;

    private Texture banner;

    GameOverScreen(Main main, int score) {
        super(main);
        this.score = score;

        Preferences pref = Gdx.app.getPreferences("neterusgames.shmup");
        highscore = pref.getInteger("highscore",0);

        if(score > highscore){
            highscore = score;
            pref.putInteger("highscore",score);
            pref.flush();
        }

        backButtonInactive =  new Texture("buttons/back-inactive.png");
        backButtonActive = new Texture("buttons/back-active.png");
        backButtonX = 20;
        backButtonY = 50;

        playButtonInactive = new Texture("buttons/again-inactive.png");
        playButtonActive = new Texture("buttons/again-active.png");
        playButtonX = Main.WIDTH - (playButtonInactive.getWidth()*SCALE) - 20;
        playButtonY = 50;

        banner = new Texture("banners/gameover-banner.png");

    }

    public void render(float delta){
        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        //playButton logic
        drawButton(playButtonInactive, playButtonActive, playButtonX, playButtonY, new GameScreen(main),
                main, false);

        //backButton logic
        drawButton(backButtonInactive,backButtonActive,backButtonX,backButtonY, new MainMenuScreen(main),
                main,false);

        main.batch.draw(banner, Gdx.graphics.getWidth()/2f - banner.getWidth()/2f,
                Gdx.graphics.getHeight() - banner.getHeight() - 20);

        System.out.println("Game Over Screen Running");

        main.batch.end();
    }

    public void dispose(){
        super.dispose();
    }
}
