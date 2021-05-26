package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.enemies.DeathAnimation;
import com.neterusgames.entities.enemies.EnemyLurker;
import com.neterusgames.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class LurkerSpawn implements Runnable {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;

    private final Player PLAYER;

    private final CopyOnWriteArrayList<EnemyLurker> LURKERS = new CopyOnWriteArrayList<>();
    private final ArrayList<EnemyLurker> LURKERS_TO_REMOVE = new ArrayList<>();

    private final CopyOnWriteArrayList<DeathAnimation> DEATH_ANIM = new CopyOnWriteArrayList<>();
    private final ArrayList<DeathAnimation> DEATH_ANIM_TO_REMOVE = new ArrayList<>();

    private final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/ghostdying.ogg"));

    private boolean running = false;

    public LurkerSpawn (float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.PLAYER = player;

        timer = random.nextFloat() * (maxTimer -  minTimer) + minTimer;

    }

    public void update(float deltaTime){

        timer -= deltaTime;
        if(timer <= 0){
            timer =  random.nextFloat() * (maxTimer - minTimer) + minTimer;
            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    LURKERS.add(new EnemyLurker(Gdx.graphics.getWidth(),
                            random.nextInt(Gdx.graphics.getHeight() - 32)));
                }
            });
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

        for(final EnemyLurker lurker : LURKERS){
            for(Bullet bullet :  PLAYER.getBullets()){
                if(bullet.getRectangle().overlaps(lurker.getRectangle())){
                    bullet.setRemove(true);
                    lurker.decreaseHealth(bullet.getDamage());

                    if(lurker.isDead()){
                        LURKERS_TO_REMOVE.add(lurker);
                        ScoreCounter.score += 350;
                        DEATH_SOUND.play(0.2f,1.5f,0.0f);
                        Gdx.app.postRunnable(new Runnable() {
                            public void run() {
                                DEATH_ANIM.add(new DeathAnimation(lurker.getX(),lurker.getY()));
                            }
                        });

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

        LURKERS.removeAll(LURKERS_TO_REMOVE);
        LURKERS_TO_REMOVE.clear();

        DEATH_ANIM.removeAll(DEATH_ANIM_TO_REMOVE);
        DEATH_ANIM_TO_REMOVE.clear();
    }

    public void render(SpriteBatch batch){
        for(EnemyLurker lurker : LURKERS){
            lurker.render(batch);
        }
        for(DeathAnimation anim : DEATH_ANIM){
            anim.render(batch);
        }
    }

    public void raiseDifficult(){
        maxTimer -= 0.1f;
        minTimer -= 0.1f;
        if(minTimer <= 0.3f){
            minTimer = 0.3f;
            maxTimer = 0.6f;
        }
    }

    public void dispose(){
        DEATH_SOUND.dispose();
    }

    //Thread logic

    public void run() {
        System.out.println("Thread LurkerSpawn Iniciada");
        long timeIn60FPS = 1000/60;
        while(running){
            long before = System.currentTimeMillis();
            update(GameScreen.deltaTime);
            long time = System.currentTimeMillis() - before;
            if(time < timeIn60FPS){
                try {
                    Thread.sleep(timeIn60FPS - time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Thread LurkerSpawn Finalizada");
    }

    public void start(){
        if(running){
            return;
        }

        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() throws InterruptedException {
        if(!running){
            return;
        }
        running = false;
        Thread.currentThread().interrupt();
        dispose();
    }

}
