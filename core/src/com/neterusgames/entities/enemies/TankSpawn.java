package com.neterusgames.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;

import java.util.ArrayList;
import java.util.Random;

public class TankSpawn {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;
    private Player player;
    private ArrayList<EnemyTank> tanks = new ArrayList<>();
    private ArrayList<EnemyTank> tanksToRemove = new ArrayList<>();

    public TankSpawn(float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.player = player;

        timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
    }

    public void update(float deltaTime){
        timer -= deltaTime;
        if(timer <= 0){
            timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
            tanks.add(new EnemyTank(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        for(EnemyTank tank : tanks){
            tank.update(deltaTime);
            if(tank.isRemove()){
                tanksToRemove.add(tank);
            }
            if(tank.getRectangle().overlaps(player.getRectangle())){
                player.decreaseHealth(0.3f);
                tanksToRemove.add(tank);
            }
        }
        for(EnemyTank tank :  tanks){
            for(Bullet bullet : player.getBullets()){
                if(bullet.getRectangle().overlaps(tank.getRectangle())){
                    bullet.setRemove(true);
                    tank.decreaseHealth(bullet.getDamage()/4);
                    if(tank.isDead()){
                        tanksToRemove.add(tank);
                    }
                }
            }
        }

        tanks.removeAll(tanksToRemove);
    }

    public void render(SpriteBatch batch){
        for(EnemyTank tank : tanks){
            tank.render(batch);
        }
    }

}
