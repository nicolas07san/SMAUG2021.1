package com.neterusgames.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class MainMenuScreen implements Screen {

    private static final int PLAYB_X = 100;
    private static final int PLAYB_Y = 300;

    private static final int SCOREB_X = 100;
    private static final int SCOREB_Y = 200;

    private static final int CREDITSB_X = 100;
    private static final int CREDITSB_Y = 100;

    private static final int EXITB_X = 500;
    private static final int EXIT_Y = 100;

    Main main;

    Texture playButtonInactive;
    Texture playButtonActive;

    Texture scoreButtonInactive;
    Texture scoreButtonActive;

    Texture creditsButtonInactive;
    Texture creditsButtonActive;

    Texture exitButtonInactive;
    Texture exitButtonActive;

    public MainMenuScreen(Main main){
        this.main = main;

    }


    public void show() {

    }

    public void render(float delta) {

    }

    public void resize(int width, int height) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

    public void dispose() {

    }
}
