package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.neterusgames.entities.*;
import com.neterusgames.entities.enemies.*;
import com.neterusgames.game.Main;
import com.neterusgames.tools.*;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends BaseScreen{

    public static float deltaTime;
    public static boolean raiseDifficult = false;

    private Player player;

    private TankSpawn tankSpawn;
    private RangedSpawn rangedSpawn;
    private LurkerSpawn lurkerSpawn;
    private ScoreCounter scoreCounter;

    private MusicPlayer musicPlayer =  new MusicPlayer();

    private int scoreMeter = 2500;
    private int playerUpgradeMeter = 5000;

    public GameScreen(Main main){
        super(main);
        player = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );
        tankSpawn = new TankSpawn(2f,2.5f, player, main.batch);
        rangedSpawn = new RangedSpawn(1.5f, 2f, player);
        lurkerSpawn = new LurkerSpawn(1f,1.5f,player);
        scoreCounter = new ScoreCounter();

    }

    public void show(){
        musicPlayer.playMusic();
    }

    public void render(float delta) {
        deltaTime = delta;

        if(ScoreCounter.score >= scoreMeter){
            scoreMeter += scoreMeter;
            raiseDifficult = true;
        }

        if(ScoreCounter.score >= playerUpgradeMeter){
            player.upgrade();
            playerUpgradeMeter += playerUpgradeMeter;
        }

        // Update entities
        player.update(delta);

        //tankSpawn.update(delta, raiseDifficult);
        rangedSpawn.update(delta, raiseDifficult);
        lurkerSpawn.update(delta, raiseDifficult);

        raiseDifficult = false;

        if(player.isDead()){
            try {
                tankSpawn.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dispose();
            main.setScreen(new GameOverScreen(main, ScoreCounter.score));
        }

        //Render entities
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        tankSpawn.start();

        //tankSpawn.render(main.batch);
        lurkerSpawn.render(main.batch);
        rangedSpawn.render(main.batch);
        player.render(main.batch);

        scoreCounter.render(main.batch);

        main.batch.end();
    }

    public void dispose() {
        super.dispose();
        tankSpawn.dispose();
        player.dispose();
        scoreCounter.dispose();
        musicPlayer.dispose();
        System.out.println("Game Screen Disposed");
    }

}
