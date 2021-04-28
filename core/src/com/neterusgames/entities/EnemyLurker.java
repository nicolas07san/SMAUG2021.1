package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyLurker extends BaseEntity{

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
    }

    public boolean isRemove(){
        return remove;
    }
}
