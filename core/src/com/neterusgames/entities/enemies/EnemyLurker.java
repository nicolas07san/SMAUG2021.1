package com.neterusgames.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neterusgames.entities.BaseEntity;

public class EnemyLurker extends BaseEntity {

    private boolean remove = false;

    private float stateTime;
    private final Animation<TextureRegion> anim;

    public EnemyLurker(float x, float y){
        super(x, y);

        if(getTexture() ==  null){
            setTexture(new Texture("entities/enemy-lurker.png"));
        }

        setWidth(32);
        setHeight(32);

        setRectangle(getX(),getY(),getWidth(),getHeight());

        anim = createAnimation(0.25f);

        setSpeed(250);

    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        setX(getX() - getSpeed() * deltaTime);

        if(getX() <= -getWidth()){
            remove = true;
        }

        moveRectangle(getCenterX(),getCenterY());
    }

    public void render(SpriteBatch batch){
        batch.draw(anim.getKeyFrame(stateTime,true), getX(), getY(), getWidth(), getHeight());
        drawHealthBar(batch,getX(),getY()+getHeight()+2, getWidth()*getHealth(),3);
    }

    public boolean isRemove(){
        return remove;
    }
}
