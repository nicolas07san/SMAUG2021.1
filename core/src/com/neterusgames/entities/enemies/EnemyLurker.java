package com.neterusgames.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.BaseEntity;

public class EnemyLurker extends BaseEntity {

    private boolean remove = false;

    public EnemyLurker(float x, float y){
        super(x, y);

        setTexture(new Texture("entities/enemy-lurker.png"));
        setRectangle(getX(),getY(),getWidth(),getHeight());
        setSpeed(250);

    }

    public void update(float deltaTime) {
        setX(getX() - getSpeed() * deltaTime);
        setY(getY() - (getSpeed()/2) * deltaTime);

        if(getY() <= -getHeight()){
            remove = true;
        }
        if(getX() <= -getWidth()){
            remove = true;
        }
        moveRectangle(getCenterX(),getCenterY());
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        drawHealthBar(batch,getX(),getY()+getHeight()+2, getWidth()*getHealth(),3);
    }

    public boolean isRemove(){
        return remove;
    }
}
