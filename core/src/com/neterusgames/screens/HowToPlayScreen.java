package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class HowToPlayScreen extends BaseScreen{

    private final Texture BACK_BUTTON_INACTIVE = new Texture("buttons/back-inactive.png");
    private final Texture BACK_BUTTON_ACTIVE = new Texture("buttons/back-active.png");
    private final int BACK_BUTTON_X = 20;
    private final int BACK_BUTTON_Y = 50;

    private final Texture BANNER = new Texture("banners/h2p-banner.png");
    private final Texture TEXT =  new Texture("banners/h2p-text.png");

    HowToPlayScreen(Main main) {
        super(main);
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        // backButton logic
        drawButton(BACK_BUTTON_INACTIVE, BACK_BUTTON_ACTIVE, BACK_BUTTON_X, BACK_BUTTON_Y, new MainMenuScreen(main),
                main, false);

        main.batch.draw(BANNER, Gdx.graphics.getWidth()/2f - BANNER.getWidth()/2f,
                Gdx.graphics.getHeight() - BANNER.getHeight() - 20);

        main.batch.draw(TEXT,Gdx.graphics.getWidth()/2f - (TEXT.getWidth()*SCALE)/2f,
                Gdx.graphics.getHeight()/2f - (TEXT.getHeight()*SCALE)/2f,
                TEXT.getWidth()*SCALE,
                TEXT.getHeight()*SCALE);

        main.batch.end();

    }

    public void dispose() {
        super.dispose();
        BACK_BUTTON_INACTIVE.dispose();
        BACK_BUTTON_ACTIVE.dispose();
        BANNER.dispose();
        System.out.println("H2P Screen Disposed");

    }
}
