package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.neterusgames.entities.Player;
import com.neterusgames.entities.Rock;
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


    public GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );

        rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;
    }

    public void render(float delta) {
        //Update entities
        player.update(delta);

        rockSpawnTimer -= delta;
        if(rockSpawnTimer <= 0){
            rockSpawnTimer = random.nextFloat() * (maxRockTimer - minRockTimer) + minRockTimer;
            rocks.add(new Rock(random.nextInt(Gdx.graphics.getWidth() - 32),
                    Gdx.graphics.getHeight()));
        }

        for(Rock rock : rocks){
            rock.update(delta);
            if(rock.isRemove()){
                rocksToRemove.add(rock);

            }

        }

        for (Rock rock : rocks){
            if(rock.getCircle().overlaps(player.getCircle())){
                rocksToRemove.add(rock);
            }
        }

        rocks.removeAll(rocksToRemove);

        //Render entities
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        player.render(main.batch);

        for(Rock rock : rocks){
            rock.render(main.batch);
        }

        System.out.println("Player Circle = " + player.getCircle().toString());

        main.batch.end();
    }

    public void dispose() {
        main.batch.dispose();
    }
}
