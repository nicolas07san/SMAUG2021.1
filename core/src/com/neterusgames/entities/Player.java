package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Player extends BaseEntity{

    private float timer;
    private float shootTimer;
    private float frameTime = 0.25f;
    private float elapsedTime;

    private final Animation <TextureRegion> ANIMATION;

    private final ArrayList<Bullet> BULLETS;
    private final ArrayList<Bullet> BULLETS_TO_REMOVE;

    private final Sound BULLET_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/bowshoot.ogg"));;

    public Player(float x, float y){
        super(x, y);

        if(getTexture() == null){
            setTexture(new Texture("entities/player.png"));
        }

        setWidth(32);
        setHeight(32);

        setRectangle(getX(),getY(),getWidth(),getHeight());

        setSpeed(250);

        ANIMATION = createAnimation(frameTime);

        BULLETS = new ArrayList<>();
        BULLETS_TO_REMOVE = new ArrayList<>();

        shootTimer = 0.4f;

    }

    private void shoot(float deltaTime){
        timer += deltaTime;
        if(timer >= shootTimer){
            timer = 0;
            BULLET_SOUND.play(0.8f,1f,0.0f);
            int offset = 4;
            BULLETS.add(new Bullet(getX() + getWidth() - offset ,getY() + getHeight()/2f,
                    "entities/player-bullet.png",1f,0.85f));
        }
    }

    public void update(float deltaTime){

        //check bullets that are off the screen
        for(Bullet bullet : BULLETS){
            bullet.update(deltaTime);
            if(bullet.isRemove()){
                BULLETS_TO_REMOVE.add(bullet);
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

        moveRectangle(getCenterX(), getCenterY());

        BULLETS.removeAll(BULLETS_TO_REMOVE);
        BULLETS_TO_REMOVE.clear();

        elapsedTime += deltaTime;
    }

    public void render(SpriteBatch batch) {
        for (Bullet bullet : BULLETS) {
            bullet.render(batch);
        }
        batch.draw(ANIMATION.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());

        drawHealthBar(batch,0,0,Gdx.graphics.getWidth()*getHealth(),5);
    }

    public ArrayList<Bullet> getBullets(){
        return BULLETS;
    }

    public void dispose(){
        BULLET_SOUND.dispose();
        for(Bullet bullet : BULLETS){
            bullet.dispose();
        }
    }

    public void upgrade(){
        if(shootTimer > 0.2f){
            shootTimer -= 0.1f;
            if(shootTimer <= 0.2f){
                shootTimer = 0.2f;
            }
        }
    }

}
