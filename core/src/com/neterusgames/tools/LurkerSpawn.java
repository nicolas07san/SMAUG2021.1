package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.enemies.EnemyLurker;

import java.util.ArrayList;
import java.util.Random;

public class LurkerSpawn {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;
    private Player player;
    private ArrayList<EnemyLurker> lurkers = new ArrayList<>();
    private ArrayList<EnemyLurker> lurkersToRemove = new ArrayList<>();

    public LurkerSpawn (float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.player = player;

        timer = random.nextFloat() * (maxTimer -  minTimer) + minTimer;

    }

    public void update(float deltaTime, float difficult){
        if(minTimer >= 0.3f){
            maxTimer = maxTimer - difficult;
            minTimer = minTimer - difficult;
        }

        timer -= deltaTime;
        if(timer <= 0){
            timer =  random.nextFloat() * (maxTimer - minTimer) + minTimer;
            lurkers.add(new EnemyLurker(Gdx.graphics.getWidth(),
                    random.nextInt(Gdx.graphics.getHeight() - 32)));
        }

        for(EnemyLurker lurker : lurkers){
            lurker.update(deltaTime);
            if(lurker.isRemove()){
                lurkersToRemove.add(lurker);
            }
            if(lurker.getRectangle().overlaps(player.getRectangle())){
                player.decreaseHealth(0.2f);
                lurkersToRemove.add(lurker);
            }
        }
        for(EnemyLurker lurker : lurkers){
            for(Bullet bullet :  player.getBullets()){
                if(bullet.getRectangle().overlaps(lurker.getRectangle())){
                    lurker.decreaseHealth(bullet.getDamage());
                    bullet.setRemove(true);
                    if(lurker.isDead()){
                        lurkersToRemove.add(lurker);
                        ScoreCounter.score += 350;
                    }
                }
            }
        }

        lurkers.removeAll(lurkersToRemove);
    }

    public void render(SpriteBatch batch){
        for(EnemyLurker lurker : lurkers){
            lurker.render(batch);
        }
    }

}
