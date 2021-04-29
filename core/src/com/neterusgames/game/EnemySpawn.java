package com.neterusgames.game;


import com.badlogic.gdx.Gdx;
import com.neterusgames.entities.BaseEntity;
import com.neterusgames.entities.enemies.EnemyLurker;
import com.neterusgames.entities.enemies.EnemyRanged;
import com.neterusgames.entities.enemies.EnemyTank;

import java.util.ArrayList;
import java.util.Random;

public class EnemySpawn {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;
    private ArrayList<?> enemies;
    private ArrayList<?> enemiesToRemove;

    private String enemyName;

    public EnemySpawn(float minTimer, float maxTimer, String enemyName){

        this.enemyName = enemyName;
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;

        timer = random.nextFloat()*(maxTimer - minTimer) + minTimer;

        if(enemyName.equalsIgnoreCase("Lurker")){
            ArrayList<EnemyLurker> enemies = new ArrayList<>();
            ArrayList<EnemyLurker> enemiesToRemove = new ArrayList<>();
            this.enemies = enemies;
            this.enemiesToRemove = enemiesToRemove;
        }

        else if(enemyName.equalsIgnoreCase("Tank")){
            ArrayList<EnemyTank> enemies = new ArrayList<>();
            ArrayList<EnemyTank> enemiesToRemove = new ArrayList<>();
            this.enemies = enemies;
            this.enemiesToRemove =  enemiesToRemove;
        }

        else if(enemyName.equalsIgnoreCase("Ranged")){
            ArrayList<EnemyRanged> enemies = new ArrayList<>();
            ArrayList<EnemyRanged> enemiesToRemove = new ArrayList<>();
            this.enemies = enemies;
            this.enemiesToRemove = enemiesToRemove;
        }

    }

    public void update(float deltaTime){
        timer -= deltaTime;
        if(timer <= 0){
            timer = random.nextFloat()*(maxTimer - minTimer) + minTimer;
            if(enemyName.equalsIgnoreCase("Lurker")){
                enemies.add(new EnemyLurker(Gdx.graphics.getWidth(),
                        random.nextInt(Gdx.graphics.getHeight()-32)));
            }
        }
    }

}
