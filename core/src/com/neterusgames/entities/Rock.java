package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Rock extends BaseEntity {

    private float frameTime = 0.25f;
    private float stateTime;
    private boolean outOfScreen = false;

    Animation animation;

    public Rock(float x, float y){
        super(x, y);

        if(getTexture() == null){
            setTexture(new Texture("entities/rock.png"));
        }

        setWidth(24);
        setHeight(24);

        animation = createAnimation(0.25f);

        setSpeed(350);

    }

    public void update(float deltaTime) {
        setY(getY() - getSpeed()*deltaTime);
        if(getY() < -getHeight()){
            outOfScreen = true;
        }
        stateTime += deltaTime;
    }

    public void render(SpriteBatch batch) {
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime,true), getX(), getY());
    }

    public boolean isOutOfScreen() {
        return outOfScreen;
    }
}
