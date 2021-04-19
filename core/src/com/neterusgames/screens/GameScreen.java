package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class GameScreen extends BaseScreen{

    private Texture img;

    GameScreen(Main main){
        super(main);
        img = new Texture("badlogic.jpg");
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(img, 0, 0);
        main.batch.end();
    }

    public void dispose() {
        main.batch.dispose();
        img.dispose();
    }
}
