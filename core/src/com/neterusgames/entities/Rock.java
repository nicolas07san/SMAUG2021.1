package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rock extends BaseEntity {

    private boolean outOfScreen = false;

    public Rock(float x, float y){
        super(x, y);

        if(getTexture() == null){
            setTexture(new Texture("entities/rock.png"));
        }
        setSpeed(350);

    }

    public void update(float deltaTime) {
        setY(getY() - getSpeed()*deltaTime);
        if(getY() < -getHeight()){
            outOfScreen = true;
        }
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    public boolean isOutOfScreen() {
        return outOfScreen;
    }
}
