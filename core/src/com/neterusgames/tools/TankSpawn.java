package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.enemies.DeathAnimation;
import com.neterusgames.entities.enemies.EnemyTank;
import com.neterusgames.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class TankSpawn {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;

    private final Player PLAYER;

    private final ArrayList<EnemyTank> TANKS = new ArrayList<>();
    private final ArrayList<EnemyTank> TANKS_TO_REMOVE = new ArrayList<>();

    private final ArrayList<DeathAnimation> DEATH_ANIM = new ArrayList<>();
    private final ArrayList<DeathAnimation> DEATH_ANIM_TO_REMOVE = new ArrayList<>();

    private final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/tankdie.ogg"));

    public TankSpawn(float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.PLAYER = player;

        timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
    }

    public void update(float deltaTime,float difficult, boolean raiseDifficult){
        if(raiseDifficult && minTimer > 0.3f){
            maxTimer -= difficult;
            minTimer -= difficult;
            if(minTimer <= 0.3f){
                minTimer = 0.3f;
                maxTimer = 0.6f;
            }
        }

        timer -= deltaTime;
        if(timer <= 0){
            timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
            TANKS.add(new EnemyTank(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        for(EnemyTank tank : TANKS){
            tank.update(deltaTime);
            if(tank.isRemove()){
                TANKS_TO_REMOVE.add(tank);
            }
            if(tank.getRectangle().overlaps(PLAYER.getRectangle())){
                PLAYER.decreaseHealth(0.3f);
                TANKS_TO_REMOVE.add(tank);
            }
        }
        for(EnemyTank tank : TANKS){
            for(Bullet bullet : PLAYER.getBullets()){
                if(bullet.getRectangle().overlaps(tank.getRectangle())){
                    bullet.setRemove(true);
                    tank.decreaseHealth(bullet.getDamage()/2);
                    if(tank.isDead()){
                        TANKS_TO_REMOVE.add(tank);
                        ScoreCounter.score += 500;
                        DEATH_SOUND.play(0.2f,1.0f,0.0f);
                        DEATH_ANIM.add(new DeathAnimation(tank.getX(),tank.getY(),"entities/tank-death.png"));
                    }
                }
            }
        }

        for(DeathAnimation anim : DEATH_ANIM){
            anim.update(deltaTime);
            if(anim.remove){
                DEATH_ANIM_TO_REMOVE.add(anim);
            }
        }

        TANKS.removeAll(TANKS_TO_REMOVE);
        TANKS_TO_REMOVE.clear();

        DEATH_ANIM.removeAll(DEATH_ANIM_TO_REMOVE);
        DEATH_ANIM_TO_REMOVE.clear();
    }

    public void render(SpriteBatch batch){
        for(EnemyTank tank : TANKS){
            tank.render(batch);
        }
        for(DeathAnimation anim : DEATH_ANIM){
            anim.render(batch);
        }
    }

    public void dispose(){
        DEATH_SOUND.dispose();
    }

}
