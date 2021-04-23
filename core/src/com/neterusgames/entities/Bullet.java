package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends BaseEntity {

    private boolean outOfScreen = false;

    public Bullet(float x, float y, String path){
        super(x, y);

        if(getTexture() ==  null){
            setTexture(new Texture(path));
        }

        setSpeed(500);
    }

    public void update(float deltaTime){
        setY(getY() + getSpeed()*deltaTime);
        if(getY() > Gdx.graphics.getHeight()){
            outOfScreen = true;
        }
    }

    public void render(SpriteBatch batch){
        super.render(batch);
    }

    public boolean isOutOfScreen() {
        return outOfScreen;
    }
}
