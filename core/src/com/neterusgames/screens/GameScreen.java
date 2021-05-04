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

    private TankSpawn tankSpawn;
    private RangedSpawn rangedSpawn;
    private LurkerSpawn lurkerSpawn;
    private ScoreCounter scoreCounter;

    private float difficult = 0;
    private int scoreMeter = 3000;

    public GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );
        tankSpawn = new TankSpawn(1f,2f, player);
        rangedSpawn = new RangedSpawn(0.75f, 1.5f, player);
        lurkerSpawn = new LurkerSpawn(0.5f,1f,player);
        scoreCounter = new ScoreCounter();

    }

    public void render(float delta) {
        if(ScoreCounter.score >= scoreMeter){
            scoreMeter += scoreMeter;
            difficult += 0.1f;
        }

        // Update entities
        player.update(delta);

        tankSpawn.update(delta, difficult);
        rangedSpawn.update(delta, difficult);
        lurkerSpawn.update(delta, difficult);

        if(player.isDead()){
            main.setScreen(new GameOverScreen(main, ScoreCounter.score));
        }

        //Render entities
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        tankSpawn.render(main.batch);
        lurkerSpawn.render(main.batch);
        rangedSpawn.render(main.batch);
        player.render(main.batch);

        scoreCounter.render(main.batch);

        main.batch.end();
    }

    public void dispose() {
        super.dispose();
        player.dispose();
    }

}
