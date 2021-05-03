package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

    private final int SPEED = 80;

    private static final Texture TEXTURE = new Texture("background.png");
    private float y1;
    private float y2;
    private float scale;


    public ScrollingBackground(){
        y1 = 0;
        y2 = TEXTURE.getHeight();
        scale = 0;
    }

    public void update(float deltaTime){

        y1 -= SPEED*deltaTime;
        y2 -= SPEED*deltaTime;

        if(y1 + TEXTURE.getHeight() * scale <= 0){
            y1 = y2+ TEXTURE.getHeight()*scale;
        }
        if(y2 + TEXTURE.getHeight() * scale <= 0){
            y2 = y1+ TEXTURE.getHeight()*scale;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(TEXTURE,0,y1, Gdx.graphics.getWidth(), TEXTURE.getHeight()*scale);
        batch.draw(TEXTURE,0,y2, Gdx.graphics.getWidth(), TEXTURE.getHeight()*scale);
    }

    public void resize(int width){
        scale = width / TEXTURE.getWidth();
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch){
        update(deltaTime);
        render(batch);
    }
}
