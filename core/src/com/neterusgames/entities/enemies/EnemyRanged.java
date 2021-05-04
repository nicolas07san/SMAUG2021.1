package com.neterusgames.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.BaseEntity;
import com.neterusgames.entities.Bullet;

import java.util.ArrayList;

public class EnemyRanged extends BaseEntity {

    private boolean remove = false;

    private float timer;
    private float shootTimer = 1f;

    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> bulletsToRemove;

    public EnemyRanged(float x, float y) {
        super(x, y);
        setTexture(new Texture("entities/enemy-ranged.png"));
        setRectangle(getX(), getY(), getWidth(), getHeight());
        setSpeed(200);

        bullets = new ArrayList<>();
        bulletsToRemove = new ArrayList<>();

    }

    public void update(float deltaTime) {

        setY(getY() - getSpeed() * deltaTime);
        if(getY() <= -getHeight()){
            remove = true;
        }

        for (Bullet bullet : bullets){
            bullet.update(deltaTime);
            if(bullet.isRemove()){
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);
        bulletsToRemove.clear();

        shoot(deltaTime);

        moveRectangle(getCenterX(),getCenterY());
    }

    public void render(SpriteBatch batch){
        for(Bullet bullet : bullets){
            bullet.render(batch);
        }
        super.render(batch);
        drawHealthBar(batch,getX(),getY()+getHeight()+2, getWidth()*getHealth(),3);
    }

    private void shoot(float deltaTime){
        timer += deltaTime;
        if(timer >= shootTimer){
            timer = 0;
            bullets.add(new Bullet(getCenterX(),getCenterY(),
                    "entities/enemy-bullet.png",-1f, 0.3f));

        }
    }

    public boolean isRemove(){
        return remove;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public void dispose(){
        for(Bullet bullet:bullets){
            bullet.dispose();
        }
    }
}
