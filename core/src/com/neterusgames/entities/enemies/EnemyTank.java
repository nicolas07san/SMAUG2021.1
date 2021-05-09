package com.neterusgames.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neterusgames.entities.BaseEntity;

public class EnemyTank extends BaseEntity {

    private boolean remove = false;

    private float stateTime;
    private final Animation<TextureRegion> anim;

    public EnemyTank(float x, float y){
        super(x, y);
        if(getTexture() == null){
            setTexture(new Texture("entities/enemy-tank.png"));
        }

        setWidth(32);
        setHeight(32);

        setRectangle(getX(),getY(),getWidth(),getHeight());

        anim = createAnimation(0.25f);

        setSpeed(150);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        setY(getY() - getSpeed() * deltaTime);
        if(getY() <= -getHeight()){
            remove = true;
        }

        moveRectangle(getCenterX(),getCenterY());

    }
    public void render(SpriteBatch batch){
        batch.draw(anim.getKeyFrame(stateTime,true),getX(),getY(),getWidth(),getHeight());
        drawHealthBar(batch, getX(), getY()+getHeight()+2, getWidth()*getHealth(),3);
    }

    public boolean isRemove(){
        return remove;
    }
}
