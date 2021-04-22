package com.neterusgames.entities;

import com.badlogic.gdx.graphics.Texture;

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

    private Texture texture;

    public Player(float x, float y){
        this.x = x;
        this.y = y;

        texture = new Texture("ship.png");

        bullets = new ArrayList<>();

        width = 17;
        height = 32;

        health = 1;
        speed = 250;
        shootTimer = 0.3f;

    }

    public void shoot(float deltaTime){
        timer += deltaTime;
        if(timer >= shootTimer){
            timer = 0;
            int offset = 4;
            bullets.add(new Bullet(x + offset,y / height/2));
            bullets.add(new Bullet(x + offset + width,y + height/2));
        }
    }

    public void render(float deltaTime){

    }
}
