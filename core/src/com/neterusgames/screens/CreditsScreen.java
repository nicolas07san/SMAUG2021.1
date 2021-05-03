package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public class CreditsScreen extends BaseScreen{

    private Texture backButtonInactive;
    private Texture backButtonActive;
    private int backButtonX;
    private int backButtonY;

    private Texture banner;

    CreditsScreen(Main main) {
        super(main);

        banner = new Texture("banners/credits-banner.png");

        backButtonInactive = new Texture("buttons/back-inactive.png");
        backButtonActive = new Texture("buttons/back-active.png");
        backButtonX = 20;
        backButtonY = 50;

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        // backButton logic
        drawButton(backButtonInactive, backButtonActive, backButtonX, backButtonY, new MainMenuScreen(main),
                main, false);

        main.batch.draw(banner,Gdx.graphics.getWidth()/2f - banner.getWidth()/2f,
                Gdx.graphics.getHeight() - banner.getHeight() - 20);

        main.batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
