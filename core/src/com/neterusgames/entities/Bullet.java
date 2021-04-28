package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends BaseEntity {

    private boolean remove = false;
    private float damage;

    public Bullet(float x, float y, String path, float multiplier, float damage){
        super(x, y);

        if(getTexture() ==  null){
            setTexture(new Texture(path));
        }

        setRectangle(getX(),getY(),getWidth(),getHeight());

        setSpeed(500*multiplier);
        this.damage =  damage;
    }

    public void update(float deltaTime){
        setY(getY() + getSpeed()*deltaTime);
        if(getY() > Gdx.graphics.getHeight() || getY() < getHeight()){
            remove = true;
        }
        moveRectangle(getCenterX(),getCenterY());
    }

    public void render(SpriteBatch batch){
        super.render(batch);
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public float getDamage(){
        return damage;
    }
}
