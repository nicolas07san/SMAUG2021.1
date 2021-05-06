package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

    private final Player PLAYER;
    private final ArrayList<EnemyLurker> LURKERS = new ArrayList<>();
    private final ArrayList<EnemyLurker> LURKERS_TO_REMOVE = new ArrayList<>();
    private final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/ghostdying.ogg"));

    public LurkerSpawn (float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.PLAYER = player;

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
            LURKERS.add(new EnemyLurker(Gdx.graphics.getWidth(),
                    random.nextInt(Gdx.graphics.getHeight() - 32)));
        }

        for(EnemyLurker lurker : LURKERS){
            lurker.update(deltaTime);
            if(lurker.isRemove()){
                LURKERS_TO_REMOVE.add(lurker);
            }
            if(lurker.getRectangle().overlaps(PLAYER.getRectangle())){
                PLAYER.decreaseHealth(0.2f);
                LURKERS_TO_REMOVE.add(lurker);
            }
        }
        for(EnemyLurker lurker : LURKERS){
            for(Bullet bullet :  PLAYER.getBullets()){
                if(bullet.getRectangle().overlaps(lurker.getRectangle())){
                    lurker.decreaseHealth(bullet.getDamage());
                    bullet.setRemove(true);
                    if(lurker.isDead()){
                        DEATH_SOUND.play(0.2f,1.5f,0.0f);
                        LURKERS_TO_REMOVE.add(lurker);
                        ScoreCounter.score += 350;
                    }
                }
            }
        }
        LURKERS.removeAll(LURKERS_TO_REMOVE);
        LURKERS_TO_REMOVE.clear();
    }

    public void render(SpriteBatch batch){
        for(EnemyLurker lurker : LURKERS){
            lurker.render(batch);
        }
    }

    public void dispose(){
        DEATH_SOUND.dispose();
    }

}
