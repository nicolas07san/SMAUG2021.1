package com.neterusgames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player {

    private float x;
    private float y;

    private int width;
    private int height;

    private float health;
    private float speed;

    private float timer;
    private float shootTimer;

    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> bulletsToRemove;

    private Texture texture;

    public Player(float x, float y){
        this.x = x;
        this.y = y;

        texture = new Texture("entities/player.png");

        bullets = new ArrayList<>();
        bulletsToRemove = new ArrayList<>();

        width = texture.getWidth();
        height = texture.getHeight();

        health = 1;
        speed = 250;
        shootTimer = 0.3f;

    }

    private void shoot(float deltaTime){
        timer += deltaTime;
        if(timer >= shootTimer){
            timer = 0;
            int offset = 4;
            bullets.add(new Bullet(x - offset,y + height/2,"entities/player-bullet.png"));
            bullets.add(new Bullet(x + width - offset ,y + height/2,"entities/player-bullet.png"));
        }
    }

    public void update(float deltaTime){

        //check bullets that are off the screen
        for(Bullet bullet : bullets){
            bullet.update(deltaTime);
            if(bullet.isRemove()){
                bulletsToRemove.add(bullet);
            }
        }

        // check for inputs
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            shoot(deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += speed*deltaTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= speed*deltaTime;
            if(y < 0){
                y = 0;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x += speed*deltaTime;
            if(x > Gdx.graphics.getWidth() - width){
                x = Gdx.graphics.getWidth() - width;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= speed*deltaTime;

            if(x < 0){
                x = 0;
            }
        }

        bullets.removeAll(bulletsToRemove);
    }

    public void render(SpriteBatch batch){

        for(Bullet bullet : bullets){
            bullet.render(batch);
        }
        batch.draw(texture, x, y, width, height);
    }
}
