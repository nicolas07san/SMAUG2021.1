package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.enemies.EnemyRanged;

import java.util.ArrayList;
import java.util.Random;

public class RangedSpawn {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;
    private Player player;
    private ArrayList<EnemyRanged> rangers = new ArrayList<>();
    private ArrayList<EnemyRanged> rangersToRemove = new ArrayList<>();

    public RangedSpawn(float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.player = player;

        timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
    }

    public void update(float deltaTime, float difficult){
        if(minTimer >= 0.3f){
            maxTimer = maxTimer - difficult;
            minTimer = minTimer - difficult;
        }

        timer -= deltaTime;
        if(timer <= 0){
            timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
            rangers.add(new EnemyRanged(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        for(EnemyRanged ranged : rangers){
            ranged.update(deltaTime);
            if(ranged.isRemove()){
                rangersToRemove.add(ranged);
            }
            if(ranged.getRectangle().overlaps(player.getRectangle())){
                player.decreaseHealth(0.1f);
                rangersToRemove.add(ranged);
            }
        }

        for(EnemyRanged ranged : rangers){
            for(Bullet bullet : player.getBullets()){
                if(bullet.getRectangle().overlaps(ranged.getRectangle())){

                    ranged.decreaseHealth(bullet.getDamage()*1.5f);
                    bullet.setRemove(true);
                    if(ranged.isDead()){
                        rangersToRemove.add(ranged);
                        ScoreCounter.score += 200;
                    }
                }
            }
            for(Bullet bullet : ranged.getBullets()){
                if(bullet.getRectangle().overlaps(player.getRectangle())){
                    bullet.setRemove(true);
                    player.decreaseHealth(0.2f);
                }
            }
        }
        rangers.removeAll(rangersToRemove);
    }

    public void render(SpriteBatch batch){
        for(EnemyRanged ranged : rangers){
            ranged.render(batch);
        }
    }


}
