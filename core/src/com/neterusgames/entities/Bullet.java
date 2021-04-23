package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

    private float speed;

    private int width;
    private int height;

    private float x;
    private float y;

    private int scale = 3;

    private static Texture texture;

    private boolean remove = false;

    public Bullet(float x, float y, String path){
        this.x = x;
        this.y = y;

        if(texture ==  null){
            texture = new Texture(path);
        }

        this.width = texture.getWidth()*scale;
        this.height = texture.getHeight()*scale;

        speed = 500;
    }

    public void update(float deltaTime){
        y += speed*deltaTime;
        if(y > Gdx.graphics.getHeight()){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y, width, height);
    }

    public boolean isRemove() {
        return remove;
    }
}
