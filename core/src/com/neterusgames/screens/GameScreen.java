package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.neterusgames.entities.*;
import com.neterusgames.entities.enemies.EnemyLurker;
import com.neterusgames.entities.enemies.EnemyRanged;
import com.neterusgames.entities.enemies.EnemyTank;
import com.neterusgames.entities.enemies.Rock;
import com.neterusgames.game.Main;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends BaseScreen{

    private Player player;

    private Random random = new Random();

    //Rock logic variables
    private float rockSpawnTimer;
    private float maxRockTimer = 0.6f;
    private float minRockTimer = 0.1f;
    private ArrayList<Rock> rocks = new ArrayList<>();
    private ArrayList<Rock> rocksToRemove = new ArrayList<>();

    //EnemyTank logic variables
    private float tankSpawnTimer;
    private float maxTankTimer = 2f;
    private float minTankTimer = 0.5f;
    private ArrayList<EnemyTank> tanks = new ArrayList<>();
    private ArrayList<EnemyTank> tanksToRemove = new ArrayList<>();

    //Enemy Lurker logic variables
    private float lurkerSpawnTimer;
    private float maxLurkerTimer = 1f;
    private float minLurkerTimer = 0.5f;
    private ArrayList<EnemyLurker> lurkers = new ArrayList<>();
    private ArrayList<EnemyLurker> lurkersToRemove = new ArrayList<>();

    //Enemy Ranged logic variables
    private float rangedSpawnTimer;
    private float maxRangedTimer = 1f;
    private float minRangedTimer = 0.5f;
    private ArrayList<EnemyRanged> rangers = new ArrayList<>();
    private ArrayList<EnemyRanged> rangersToRemove = new ArrayList<>();

    public GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );

        rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;
        tankSpawnTimer = random.nextFloat() * (maxTankTimer - minTankTimer) + minTankTimer;
        lurkerSpawnTimer = random.nextFloat() * (maxLurkerTimer - minLurkerTimer) + minLurkerTimer;
        rangedSpawnTimer = random.nextFloat() * (maxRangedTimer - minRangedTimer) + minRangedTimer;
    }

    public void render(float delta) {
        // Update entities
        player.update(delta);

        // Rock Spawn Code
        rockSpawnTimer -= delta;
        if(rockSpawnTimer <= 0){
            rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;
            rocks.add(new Rock(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        // Enemy Tank Spawn Code
        tankSpawnTimer -= delta;
        if(tankSpawnTimer <= 0){
            tankSpawnTimer = random.nextFloat() * (maxTankTimer - minTankTimer) + minTankTimer;
            tanks.add(new EnemyTank(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        // Enemy Lurker Spawn Code
        lurkerSpawnTimer -= delta;
        if(lurkerSpawnTimer <= 0){
            lurkerSpawnTimer =  random.nextFloat() * (maxLurkerTimer - minLurkerTimer) + minLurkerTimer;
            lurkers.add(new EnemyLurker(Gdx.graphics.getWidth(),
                    random.nextInt(Gdx.graphics.getHeight() - 32)));
        }

        // Enemy Ranged Spawn Code
        rangedSpawnTimer -= delta;
        if(rangedSpawnTimer <= 0){
            rangedSpawnTimer = random.nextFloat() * (maxRangedTimer - minRangedTimer) + minRangedTimer;
            rangers.add(new EnemyRanged(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        // Rock logic
        for(Rock rock : rocks){
            rock.update(delta);
            if(rock.isRemove()){
                rocksToRemove.add(rock);
            }
            if(rock.getRectangle().overlaps(player.getRectangle())){
                player.decreaseHealth(0.1f);
                rocksToRemove.add(rock);
            }
        }
        for (Rock rock : rocks){
            for(Bullet bullet : player.getBullets()){
                if(bullet.getRectangle().overlaps(rock.getRectangle())){
                    rocksToRemove.add(rock);
                    bullet.setRemove(true);
            }

            }
        }

        // Enemy tank logic
        for(EnemyTank tank : tanks){
            tank.update(delta);
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

        // Enemy lurker logic
        for(EnemyLurker lurker : lurkers){
            lurker.update(delta);
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
                    lurker.decreaseHealth(bullet.getDamage()/2);
                    bullet.setRemove(true);
                    if(lurker.isDead()){
                        lurkersToRemove.add(lurker);
                    }
                }
            }
        }

        //Enemy ranged logic
        for(EnemyRanged ranged : rangers){
            ranged.update(delta);
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
                    if(ranged.isDead()){
                        rangersToRemove.add(ranged);
                    }
                    ranged.decreaseHealth(bullet.getDamage());
                    bullet.setRemove(true);
                }
            }
            for(Bullet bullet : ranged.getBullets()){
                if(bullet.getRectangle().overlaps(player.getRectangle())){
                    bullet.setRemove(true);
                    player.decreaseHealth(0.2f);
                }
            }
        }

        rocks.removeAll(rocksToRemove);
        tanks.removeAll(tanksToRemove);
        lurkers.removeAll(lurkersToRemove);
        rangers.removeAll(rangersToRemove);

        //Render entities
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        player.render(main.batch);

        for(Rock rock : rocks){
            rock.render(main.batch);
        }

        for(EnemyTank tank : tanks){
            tank.render(main.batch);
        }

        for(EnemyLurker lurker : lurkers){
            lurker.render(main.batch);
        }

        for(EnemyRanged ranged : rangers){
            ranged.render(main.batch);
        }

        System.out.println(player.getClass());

        main.batch.end();
    }

    public void dispose() {
        main.batch.dispose();
    }
}
