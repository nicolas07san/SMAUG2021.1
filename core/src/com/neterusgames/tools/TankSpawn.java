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

public class TankSpawn implements Runnable {

    private Random random = new Random();

    private float timer;
    private float minTimer;
    private float maxTimer;

    private final Player PLAYER;

    private SpriteBatch batch;

    private final ArrayList<EnemyTank> TANKS = new ArrayList<>();
    private final ArrayList<EnemyTank> TANKS_TO_REMOVE = new ArrayList<>();

    private final ArrayList<DeathAnimation> DEATH_ANIM = new ArrayList<>();
    private final ArrayList<DeathAnimation> DEATH_ANIM_TO_REMOVE = new ArrayList<>();

    private final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/tankdie.ogg"));

    private Thread thread;
    private boolean running;

    public TankSpawn(float minTimer, float maxTimer, Player player, SpriteBatch batch){
        this.minTimer = minTimer;
        this.maxTimer = maxTimer;
        this.PLAYER = player;
        this.batch =  batch;

        timer = random.nextFloat() * (maxTimer - minTimer) + minTimer;
    }

    public synchronized void update(float deltaTime, boolean raiseDifficult){
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
                        DEATH_ANIM.add(new DeathAnimation(tank.getX(),tank.getY()));
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

    public synchronized void render(SpriteBatch batch){
        for(EnemyTank tank : TANKS){
            tank.render(batch);
        }
        for(DeathAnimation anim : DEATH_ANIM){
            anim.render(batch);
        }
    }

    public synchronized void dispose() {
        //stop();
        DEATH_SOUND.dispose();
    }

    //Thread

    public void run() {
        System.out.println("Thread Iniciada");
        Gdx.app.postRunnable(new Runnable() {

            public void run() {
                while(running) {
                    System.out.println("Rodando");
                    update(GameScreen.deltaTime, GameScreen.raiseDifficult);
                    render(batch);
                }
            }
        });

        try {
            stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread Parada");
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        if(!running){
            return;
        }
        running = false;
        thread.join();
    }
}
