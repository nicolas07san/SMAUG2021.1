package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.neterusgames.game.Main;

public abstract class BaseScreen implements Screen {

    final static int SCALE = Main.SCALE;

    Main main;
    BaseScreen(Main main){
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

    public void drawButton(Texture textureInactive, Texture textureActive , int textureX, int textureY,
                           BaseScreen screen, Main main, boolean exit){
        this.main = main;



        if(Gdx.input.getX() < textureX + textureInactive.getWidth() * SCALE && Gdx.input.getX() > textureX
                && Main.HEIGHT - Gdx.input.getY() < textureY + textureInactive.getHeight() * SCALE
                && Main.HEIGHT - Gdx.input.getY() > textureY){

            main.batch.draw(textureActive, textureX, textureY, textureActive.getWidth()*SCALE,
                    textureActive.getHeight()*SCALE);

            if(Gdx.input.justTouched()){
                if(exit){
                    Gdx.app.exit();
                }
                else{
                    main.setScreen(screen);
                    this.dispose();
                }
            }

        }else{
            main.batch.draw(textureInactive, textureX, textureY, textureInactive.getWidth()*SCALE,
                    textureInactive.getHeight()*SCALE);
        }

    }
}
