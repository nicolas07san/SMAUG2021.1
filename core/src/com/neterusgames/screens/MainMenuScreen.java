package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class MainMenuScreen extends BaseScreen {

    private int playButtonX;
    private int playButtonY;

    private int scoreButtonX;
    private int scoreButtonY;

    private int h2PlayButtonX;
    private int h2PlayButtonY;

    private int creditsButtonX;
    private int creditsButtonY;

    private int exitButtonX;
    private int exitButtonY;

    private final Texture playButtonInactive;
    private final Texture playButtonActive;

    private final Texture scoreButtonInactive;
    private final Texture scoreButtonActive;

    private final Texture h2PlayButtonInactive;
    private final Texture h2PlayButtonActive;

    private final Texture creditsButtonInactive;
    private final Texture creditsButtonActive;

    private final Texture exitButtonInactive;
    private final Texture exitButtonActive;

    public MainMenuScreen(Main main){
        super(main);

        //playButton Images
        playButtonInactive = new Texture("buttons/play-inactive.png");
        playButtonActive = new Texture("buttons/play-active.png");
        playButtonX = Main.WIDTH/2 - (playButtonInactive.getWidth()*SCALE)/2;
        playButtonY = 200;

        //scoreButton Images
        scoreButtonInactive = new Texture("buttons/highscore-inactive.png");
        scoreButtonActive = new Texture("buttons/highscore-active.png");
        scoreButtonX = Main.WIDTH/2 - (scoreButtonInactive.getWidth()*SCALE)/2;
        scoreButtonY = 150;

        //h2PlayButton Images
        h2PlayButtonInactive = new Texture("buttons/h2p-inactive.png");
        h2PlayButtonActive = new Texture("buttons/h2p-active.png");
        h2PlayButtonX = Main.WIDTH/2 - (h2PlayButtonInactive.getWidth()*SCALE)/2;
        h2PlayButtonY = 100;

        //creditsButton Images
        creditsButtonInactive = new Texture("buttons/credits-inactive.png");
        creditsButtonActive = new Texture("buttons/credits-active.png");
        creditsButtonX = Main.WIDTH/2 - (creditsButtonInactive.getWidth()*SCALE)/2;
        creditsButtonY = 50;

        // exitButton Images
        exitButtonInactive = new Texture("buttons/exit-inactive.png");
        exitButtonActive = new Texture("buttons/exit-active.png");
        exitButtonX = Main.WIDTH - (exitButtonInactive.getWidth()*SCALE) - 20;
        exitButtonY = 50;

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        //playButton logic
        drawButton(playButtonInactive, playButtonActive, playButtonX, playButtonY,new GameScreen(main), main,
                false);

        //scoreButton logic
        drawButton(scoreButtonInactive, scoreButtonActive, scoreButtonX, scoreButtonY, new ScoreScreen(main), main,
                false);

        //h2pButton logic
        drawButton(h2PlayButtonInactive, h2PlayButtonActive, h2PlayButtonX, h2PlayButtonY, new HowToPlayScreen(main),
                main,false);

        //creditsButton logic
        drawButton(creditsButtonInactive, creditsButtonActive, creditsButtonX, creditsButtonY, new CreditsScreen(main),
                main,false);

        //exitButton logic
        drawButton(exitButtonInactive, exitButtonActive,exitButtonX,exitButtonY,new MainMenuScreen(main),main,true);

        main.batch.end();
    }

}
