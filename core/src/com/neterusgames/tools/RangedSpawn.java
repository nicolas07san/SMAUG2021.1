package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.enemies.DeathAnimation;
import com.neterusgames.entities.enemies.EnemyRanged;
import com.neterusgames.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class RangedSpawn implements Runnable {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;

    private final Player PLAYER;
    private CopyOnWriteArrayList<EnemyRanged> RANGERS = new CopyOnWriteArrayList<>();
    private ArrayList<EnemyRanged> RANGERS_TO_REMOVE = new ArrayList<>();

    private final CopyOnWriteArrayList<DeathAnimation> DEATH_ANIM = new CopyOnWriteArrayList<>();
    private final ArrayList<DeathAnimation> DEATH_ANIM_TO_REMOVE = new ArrayList<>();

    private final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/slimedeath.ogg"));

    private boolean running = false;

    public RangedSpawn(float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.PLAYER = player;

        timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
    }

    public void update(float deltaTime, boolean raiseDifficult){
        if(raiseDifficult &&  minTimer > 0.4f){
            maxTimer -= 0.1f;
            minTimer -= 0.1f;
            if(minTimer <= 0.4f){
                minTimer = 0.4f;
                maxTimer = 0.8f;
            }
        }

        timer -= deltaTime;
        if(timer <= 0){
            timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    RANGERS.add(new EnemyRanged(random.nextInt(Gdx.graphics.getWidth() - 32),
                            Gdx.graphics.getHeight()));
                }
            });
        }

        for(EnemyRanged ranged : RANGERS){
            ranged.update(deltaTime);
            if(ranged.isRemove()){
                RANGERS_TO_REMOVE.add(ranged);
            }
            if(ranged.getRectangle().overlaps(PLAYER.getRectangle())){
                PLAYER.decreaseHealth(0.1f);
                RANGERS_TO_REMOVE.add(ranged);
            }
        }

        for(final EnemyRanged ranged : RANGERS){
            for(Bullet bullet : PLAYER.getBullets()){
                if(bullet.getRectangle().overlaps(ranged.getRectangle())){
                    bullet.setRemove(true);
                    ranged.decreaseHealth(bullet.getDamage()*1.5f);

                    if(ranged.isDead()){
                        RANGERS_TO_REMOVE.add(ranged);
                        ScoreCounter.score += 200;
                        DEATH_SOUND.play(0.8f,1.0f,0.0f);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                DEATH_ANIM.add(new DeathAnimation(ranged.getX(),ranged.getY()));
                            }
                        });
                    }
                }
            }

            for(Bullet bullet : ranged.getBullets()){
                if(bullet.getRectangle().overlaps(PLAYER.getRectangle())){
                    bullet.setRemove(true);
                    PLAYER.decreaseHealth(0.2f);
                }
            }
        }

        for(DeathAnimation anim : DEATH_ANIM){
            anim.update(deltaTime);
            if(anim.remove){
                DEATH_ANIM_TO_REMOVE.add(anim);
            }
        }

        RANGERS.removeAll(RANGERS_TO_REMOVE);
        RANGERS_TO_REMOVE.clear();

        DEATH_ANIM.removeAll(DEATH_ANIM_TO_REMOVE);
        DEATH_ANIM_TO_REMOVE.clear();
    }

    public void render(SpriteBatch batch){
        for(EnemyRanged ranged : RANGERS){
            ranged.render(batch);
        }
        for(DeathAnimation anim : DEATH_ANIM){
            anim.render(batch);
        }
    }

    public void dispose(){
        DEATH_SOUND.dispose();
    }

    //Thread logic

    public void run() {
        System.out.println("Thread RangedSpawn Iniciada");
        long timeIn60FPS = 1000/60;
        while(running){
            long before = System.currentTimeMillis();
            update(GameScreen.deltaTime, GameScreen.raiseDifficult);
            long time = System.currentTimeMillis() - before;
            if(time < timeIn60FPS){
                try {
                    Thread.sleep(timeIn60FPS - time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Thread RangedSpawn Finalizada");
    }

    public void start(){
        if(running){
            return;
        }
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        if(!running){
            return;
        }
        running = false;
        Thread.currentThread().interrupt();
        dispose();
    }

}
