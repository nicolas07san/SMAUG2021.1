package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.entities.Player;
import com.neterusgames.game.Main;

public class GameScreen extends BaseScreen{

    Player player;

    GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2 - 16, 15 );
    }

    public void render(float delta) {

        player.update(delta);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        player.render(main.batch);
        main.batch.end();
    }

    public void dispose() {
        main.batch.dispose();
    }
}
