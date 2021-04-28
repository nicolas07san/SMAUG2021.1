package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.neterusgames.game.Main;

public abstract class BaseEntity {

    private float x;
    private float y;
    private float speed;
    private float health;

    private int width;
    private int height;
    private final int SCALE = Main.SCALE;

    private Texture texture;
    private Texture healthBar;

    private Rectangle rectangle;

    private boolean dead;

    public BaseEntity(float x, float y) {

        this.x = x;
        this.y = y;
        this.speed = 100;
        texture = null;
        healthBar = new Texture("entities/blank.png");
        health = 1f;
        dead = false;

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

    public Animation<TextureRegion> createAnimation(float frameTime){
        return new Animation<>(frameTime,
                TextureRegion.split(texture, width / SCALE, height / SCALE)[0]);
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

    public void setRectangle(float x, float y, float width, float height){
        if(rectangle ==  null){
            this.rectangle = new Rectangle(x, y, width, height);
        }
        else{
            rectangle.set(x, y, width, height);
        }
    }

    public Rectangle getRectangle(){
        return this.rectangle;
    }

    public void moveRectangle(float x, float y){
        rectangle.setCenter(x, y);
    }

    public void decreaseHealth(float damage){
        health -= damage;
    }

    public boolean isDead(){
        if(health <= 0){
            dead = true;
        }
        return dead;
    }

    public float getHealth(){
        return health;
    }

    public void drawHealthBar(SpriteBatch batch, float x, float y, float width, float height){

        if(health > 0.6f){
            batch.setColor(Color.GREEN);
        }
        else if(health > 0.2f){
            batch.setColor(Color.ORANGE);
        }
        else{
            batch.setColor(Color.RED);
        }

        batch.draw(healthBar, x, y, width, height);
        batch.setColor(Color.WHITE);

    }

}
