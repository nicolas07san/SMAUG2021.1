package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.entities.Bullet;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.enemies.DeathAnimation;
import com.neterusgames.entities.enemies.EnemyTank;
import com.neterusgames.game.Main;
import com.neterusgames.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class TankSpawn implements Runnable {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;

    private final Player PLAYER;

    private final CopyOnWriteArrayList<EnemyTank> TANKS = new CopyOnWriteArrayList<>();
    private final ArrayList<EnemyTank> TANKS_TO_REMOVE = new ArrayList<>();

    private final CopyOnWriteArrayList<DeathAnimation> DEATH_ANIM = new CopyOnWriteArrayList< >();
    private final ArrayList<DeathAnimation> DEATH_ANIM_TO_REMOVE = new ArrayList<>();

    private final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/tankdie.ogg"));

    private Thread thread;
    private boolean running;

    public TankSpawn(float minTimer, float maxTimer, Player player){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.PLAYER = player;

        timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
    }

    public void update(float deltaTime, boolean raiseDifficult){
        if(raiseDifficult && minTimer > 0.5f){
            maxTimer -= 0.1f;
            minTimer -= 0.1f;
            if(minTimer <= 0.5f){
                minTimer = 0.5f;
                maxTimer = 1f;
            }
        }

        timer -= deltaTime;
        if(timer <= 0){
            timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    TANKS.add(new EnemyTank(random.nextInt(Gdx.graphics.getWidth() - 32),
                            Gdx.graphics.getHeight()));
                }
            });
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

        for(final EnemyTank tank : TANKS){
            for(Bullet bullet : PLAYER.getBullets()){
                if(bullet.getRectangle().overlaps(tank.getRectangle())){
                    bullet.setRemove(true);
                    tank.decreaseHealth(bullet.getDamage()/2);

                    if(tank.isDead()){
                        TANKS_TO_REMOVE.add(tank);
                        ScoreCounter.score += 500;
                        DEATH_SOUND.play(0.2f,1.0f,0.0f);
                        Gdx.app.postRunnable(new Runnable() {
                            public void run() {
                                DEATH_ANIM.add(new DeathAnimation(tank.getX(),tank.getY()));
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

    public void dispose() {
        //stop();
        DEATH_SOUND.dispose();
    }

    //Thread

    public void run() {
        System.out.println("Thread TankSpawn Iniciada");
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
        System.out.println("Thread TankSpawn Finalizada");
    }

    public void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
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
