package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.neterusgames.entities.*;
import com.neterusgames.entities.enemies.*;
import com.neterusgames.game.Main;
import com.neterusgames.tools.LurkerSpawn;
import com.neterusgames.tools.RangedSpawn;
import com.neterusgames.tools.ScoreCounter;
import com.neterusgames.tools.TankSpawn;

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

    private TankSpawn tankSpawn;
    private RangedSpawn rangedSpawn;
    private LurkerSpawn lurkerSpawn;
    private ScoreCounter scoreCounter;

    public GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );
        tankSpawn = new TankSpawn(0.8f,2f, player);
        rangedSpawn = new RangedSpawn(0.5f, 1f, player);
        lurkerSpawn = new LurkerSpawn(0.3f,0.6f,player);
        scoreCounter = new ScoreCounter();

        rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;

    }

    public void render(float delta) {
        // Update entities
        player.update(delta);

        tankSpawn.update(delta);
        rangedSpawn.update(delta);
        lurkerSpawn.update(delta);

        // Rock Spawn Code
        rockSpawnTimer -= delta;
        if(rockSpawnTimer <= 0){
            rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;
            rocks.add(new Rock(random.nextInt(Gdx.graphics.getWidth() - 32),
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
            for(Bullet bullet : player.getBullets()) {
                if (bullet.getRectangle().overlaps(rock.getRectangle())) {
                    rocksToRemove.add(rock);
                    bullet.setRemove(true);
                }
            }
        }

        rocks.removeAll(rocksToRemove);

        if(player.isDead()){
            main.setScreen(new GameOverScreen(main, ScoreCounter.score));
        }

        //Render entities
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        main.scrollingBackground.updateAndRender(delta, main.batch);

        for(Rock rock : rocks){
            rock.render(main.batch);
        }

        tankSpawn.render(main.batch);
        lurkerSpawn.render(main.batch);
        rangedSpawn.render(main.batch);
        player.render(main.batch);

        scoreCounter.render(main.batch);

        System.out.println(player.getClass());

        main.batch.end();
    }

    public void dispose() {
        main.batch.dispose();
    }

}
