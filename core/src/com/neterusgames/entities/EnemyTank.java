package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyTank extends BaseEntity{

    private boolean remove = false;

    public EnemyTank(float x, float y){
        super(x, y);
        setTexture(new Texture("entities/enemy-tank.png"));
        setRectangle(getX(),getY(),getWidth(),getHeight());
        setSpeed(150);
    }

    public void update(float deltaTime) {
        setY(getY() - getSpeed() * deltaTime);
        if(getY() <= -getHeight()){
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
