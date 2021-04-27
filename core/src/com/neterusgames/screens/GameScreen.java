package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.neterusgames.entities.*;
import com.neterusgames.game.Main;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends BaseScreen{

    private Player player;

    private Random random = new Random();

    //Rock logic variables
    private float rockSpawnTimer;
    private float maxRockTimer = 0.6f;
    private float minRockTimer = 0.3f;
    private ArrayList<Rock> rocks = new ArrayList<>();
    private ArrayList<Rock> rocksToRemove = new ArrayList<>();

    //EnemyTank logic variables
    private float tankSpawnTimer;
    private float maxTankTimer = 0.6f;
    private float minTankTimer = 0.3f;
    private ArrayList<EnemyTank> tanks = new ArrayList<>();
    private ArrayList<EnemyTank> tanksToRemove = new ArrayList<>();

    //Enemy Lurker logic variables
    private float lurkerSpawnTimer;
    private float maxLurkerTimer = 0.6f;
    private float minLurkerTimer = 0.3f;
    private ArrayList<EnemyLurker> lurkers = new ArrayList<>();
    private ArrayList<EnemyLurker> lurkersToRemove = new ArrayList<>();

    public GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );

        rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;
        tankSpawnTimer = random.nextFloat() * (maxTankTimer - minTankTimer) + minTankTimer;
        lurkerSpawnTimer = random.nextFloat() * (maxLurkerTimer - minLurkerTimer) + minLurkerTimer;
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

        for(Rock rock : rocks){
            rock.update(delta);
            if(rock.isRemove() || rock.getRectangle().overlaps(player.getRectangle())){
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

        for(EnemyTank tank : tanks){
            if(tank.isRemove() || tank.getRectangle().overlaps(player.getRectangle())){
                tanksToRemove.add(tank);
            }
        }
        for(EnemyTank tank :  tanks){
            for(Bullet bullet : player.getBullets()){
                if(bullet.getRectangle().overlaps(tank.getRectangle())){
                    tanksToRemove.add(tank);
                    bullet.setRemove(true);
                }
            }
        }

        for(EnemyLurker lurker : lurkers){
            if(lurker.isRemove() || lurker.getRectangle().overlaps(player.getRectangle())){
                lurkersToRemove.add(lurker);
            }
        }
        for(EnemyLurker lurker : lurkers){
            for(Bullet bullet :  player.getBullets()){
                if(bullet.getRectangle().overlaps(lurker.getRectangle())){
                    lurkersToRemove.add(lurker);
                    bullet.setRemove(true);
                }
            }
        }

        rocks.removeAll(rocksToRemove);
        tanks.removeAll(tanksToRemove);
        lurkers.removeAll(lurkersToRemove);

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

        System.out.println("Player Rectangle = " + player.getRectangle().toString());

        main.batch.end();
    }

    public void dispose() {
        main.batch.dispose();
    }
}
