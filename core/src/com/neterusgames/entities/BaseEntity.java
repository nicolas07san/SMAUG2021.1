package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neterusgames.game.Main;

public abstract class BaseEntity {

    private float x;
    private float y;
    private float speed;

    private int width;
    private int height;
    private final int SCALE;

    private Texture texture;

    public BaseEntity(float x, float y) {

        this.x = x;
        this.y = y;
        this.speed = 100;
        texture = null;

        SCALE = Main.SCALE;
    }

    public abstract void update(float deltaTime);

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        width = texture.getWidth() * SCALE;
        height = texture.getHeight() * SCALE;
    }

    public Animation createAnimation(float frameTime){
        return new Animation(frameTime,
                TextureRegion.split(getTexture(),width/SCALE, height/SCALE)[0]);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setWidth(int width){
        this.width = width* SCALE;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height){
        this.height = height* SCALE;
    }

    public int getHeight(){
        return height;
    }

    public Texture getTexture(){
        return texture;
    }


}
