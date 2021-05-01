package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

    private final int SPEED = 80;

    private Texture texture;
    private float y1;
    private float y2;
    private float scale;


    public ScrollingBackground(){
        texture = new Texture("background.png");
        y1 = 0;
        y2 = texture.getHeight();
        scale = 0;
    }

    public void update(float deltaTime){

        y1 -= SPEED*deltaTime;
        y2 -= SPEED*deltaTime;

        if(y1 + texture.getHeight() * scale <= 0){
            y1 = y2+texture.getHeight()*scale;
        }
        if(y2 + texture.getHeight() * scale <= 0){
            y2 = y1+texture.getHeight()*scale;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,0,y1, Gdx.graphics.getWidth(), texture.getHeight()*scale);
        batch.draw(texture,0,y2, Gdx.graphics.getWidth(), texture.getHeight()*scale);
    }

    public void resize(int width){
        scale = width / texture.getWidth();
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch){
        update(deltaTime);
        render(batch);
    }
}
