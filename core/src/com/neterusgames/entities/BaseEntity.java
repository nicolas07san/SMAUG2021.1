package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.neterusgames.game.Main;

public abstract class BaseEntity {

    private float x;
    private float y;
    private float speed;

    private int width;
    private int height;
    private final int SCALE = Main.SCALE;

    private Texture texture;

    private Circle circle;

    public BaseEntity(float x, float y) {

        this.x = x;
        this.y = y;
        this.speed = 100;
        texture = null;

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
        this.width = width * SCALE;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height){
        this.height = height * SCALE;
    }

    public int getHeight(){
        return height;
    }

    public Texture getTexture(){
        return texture;
    }

    public float getCenterX(){
        return x + width / 2f;
    }

    public float getCenterY() {
        return y + height / 2f;
    }

    public void setCircle(float x, float y, float radius){
        this.circle = new Circle(x, y, radius);
    }

    public Circle getCircle(){
        return this.circle;
    }

    public void moveCircle(float x, float y){
        circle.setPosition(x, y);
    }

}
