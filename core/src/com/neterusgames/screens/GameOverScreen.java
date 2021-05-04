package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class GameOverScreen extends BaseScreen{

    private int highscore;

    private final Texture BACK_BUTTON_INACTIVE =  new Texture("buttons/back-inactive.png");
    private final Texture BACK_BUTTON_ACTIVE = new Texture("buttons/back-active.png");
    private final int BACK_BUTTON_X = 20;
    private final int BACK_BUTTON_Y = 50;

    private final Texture PLAY_BUTTON_INACTIVE = new Texture("buttons/again-inactive.png");;
    private final Texture PLAY_BUTTON_ACTIVE = new Texture("buttons/again-active.png");;
    private final int PLAY_BUTTON_X = Main.WIDTH - (PLAY_BUTTON_INACTIVE.getWidth()*SCALE) - 20;
    private final int PLAY_BUTTON_Y = 50;

    private final Texture BANNER = new Texture("banners/gameover-banner.png");

    GameOverScreen(Main main, int score) {
        super(main);

        Preferences pref = Gdx.app.getPreferences("neterusgames.shmup");
        highscore = pref.getInteger("highscore",0);

        if(score > highscore){
            highscore = score;
            pref.putInteger("highscore",score);
            pref.flush();
        }

    }

    public void render(float delta){
        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        //playButton logic
        drawButton(PLAY_BUTTON_INACTIVE, PLAY_BUTTON_ACTIVE, PLAY_BUTTON_X, PLAY_BUTTON_Y, new GameScreen(main),
                main, false);

        //backButton logic
        drawButton(BACK_BUTTON_INACTIVE, BACK_BUTTON_ACTIVE, BACK_BUTTON_X, BACK_BUTTON_Y, new MainMenuScreen(main),
                main,false);

        main.batch.draw(BANNER, Gdx.graphics.getWidth()/2f - BANNER.getWidth()/2f,
                Gdx.graphics.getHeight() - BANNER.getHeight() - 20);

        main.batch.end();
    }

    public void dispose(){

        PLAY_BUTTON_ACTIVE.dispose();
        PLAY_BUTTON_INACTIVE.dispose();

        BACK_BUTTON_ACTIVE.dispose();
        BACK_BUTTON_INACTIVE.dispose();

        System.out.println("Game over Screen Disposed");
    }
}
