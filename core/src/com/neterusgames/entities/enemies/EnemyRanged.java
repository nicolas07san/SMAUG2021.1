package com.neterusgames.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.BaseEntity;
import com.neterusgames.entities.Bullet;

import java.util.ArrayList;

public class EnemyRanged extends BaseEntity {

    private boolean remove = false;

    private float timer;
    private final float SHOOT_TIMER = 1f;

    private final ArrayList<Bullet> BULLETS;
    private final ArrayList<Bullet> BULLETS_TO_REMOVE;

    private final Sound BULLET_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/slime_shoot.ogg"));

    public EnemyRanged(float x, float y) {

        super(x, y);
        setTexture(new Texture("entities/enemy-ranged.png"));
        setRectangle(getX(), getY(), getWidth(), getHeight());
        setSpeed(200);

        BULLETS = new ArrayList<>();
        BULLETS_TO_REMOVE = new ArrayList<>();

    }

    public void update(float deltaTime) {

        setY(getY() - getSpeed() * deltaTime);
        if(getY() <= -getHeight()){
            remove = true;
        }

        for (Bullet bullet : BULLETS){
            bullet.update(deltaTime);
            if(bullet.isRemove()){
                BULLETS_TO_REMOVE.add(bullet);
            }
        }
        BULLETS.removeAll(BULLETS_TO_REMOVE);
        BULLETS_TO_REMOVE.clear();

        shoot(deltaTime);

        moveRectangle(getCenterX(),getCenterY());
    }

    public void render(SpriteBatch batch){
        for(Bullet bullet : BULLETS){
            bullet.render(batch);
        }
        super.render(batch);
        drawHealthBar(batch,getX(),getY()+getHeight()+2, getWidth()*getHealth(),3);
    }

    private void shoot(float deltaTime){
        timer += deltaTime;
        if(timer >= SHOOT_TIMER){
            BULLET_SOUND.play(0.2f,1.0f,0.0f);
            timer = 0;
            BULLETS.add(new Bullet(getCenterX(),getCenterY(),
                    "entities/enemy-bullet.png",-1f, 0.3f));

        }
    }

    public boolean isRemove(){
        return remove;
    }

    public ArrayList<Bullet> getBullets(){
        return BULLETS;
    }

    public void dispose(){
        for(Bullet bullet:BULLETS){
            bullet.dispose();
        }
        BULLET_SOUND.dispose();
    }
}
