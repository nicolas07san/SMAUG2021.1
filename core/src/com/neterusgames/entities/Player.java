package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neterusgames.game.Main;

import java.util.ArrayList;

public class Player extends BaseEntity{

    private float health;

    private float timer;
    private float shootTimer;

    private float frameTime = 0.25f;
    private float elapsedTime;

    private Animation animation;

    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> bulletsToRemove;

    public Player(float x, float y){
        super(x, y);

        if(getTexture() == null){
            setTexture(new Texture("entities/player.png"));
        }

        setWidth(32);
        setHeight(32);
        setSpeed(250);

        animation = createAnimation(frameTime);

        bullets = new ArrayList<>();
        bulletsToRemove = new ArrayList<>();

        health = 1;
        shootTimer = 0.3f;

    }

    private void shoot(float deltaTime){
        timer += deltaTime;
        if(timer >= shootTimer){
            timer = 0;
            int offset = 4;
            bullets.add(new Bullet(getX() - offset,getY() + getHeight()/2,
                    "entities/player-bullet.png"));
            bullets.add(new Bullet(getX() + getWidth() - offset ,getY() + getHeight()/2,
                    "entities/player-bullet.png"));
        }
    }

    public void update(float deltaTime){

        //check bullets that are off the screen
        for(Bullet bullet : bullets){
            bullet.update(deltaTime);
            if(bullet.isOutOfScreen()){
                bulletsToRemove.add(bullet);
            }
        }

        // check for inputs
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            shoot(deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            setY(getY() + (getSpeed()*deltaTime));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            setY(getY() - (getSpeed()*deltaTime));
            if(getY() < 0){
                setY(0);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            setX(getX()+getSpeed()*deltaTime);
            if(getX() > Gdx.graphics.getWidth() - getWidth()){
                setX(Gdx.graphics.getWidth() - getWidth());
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            setX(getX() - getSpeed() * deltaTime);

            if(getX() < 0){
                setX(0);
            }
        }

        bullets.removeAll(bulletsToRemove);

        elapsedTime += deltaTime;
    }

    public void render(SpriteBatch batch){

        for(Bullet bullet : bullets){
            bullet.render(batch);
        }
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),getX(),getY());
    }
}
