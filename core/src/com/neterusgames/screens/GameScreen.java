package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.neterusgames.entities.*;
import com.neterusgames.game.Main;
import com.neterusgames.tools.*;

public class GameScreen extends BaseScreen{

    public static float deltaTime;
    public static boolean raiseDifficult = false;

    private final Player PLAYER;

    private final TankSpawn TANK_SPAWN;
    private final RangedSpawn RANGED_SPAWN;
    private final LurkerSpawn LURKER_SPAWN;
    private final ScoreCounter SCORE_COUNTER;

    private final ScrollingBackground SCROLLING_BACKGROUND =  new ScrollingBackground();
    private final  MusicPlayer MUSIC_PLAYER =  new MusicPlayer();

    private int scoreMeter = 2500;
    private int playerUpgradeMeter = 5000;

    public GameScreen(Main main){

        super(main);
        PLAYER = new Player(Gdx.graphics.getWidth()/2f - 16, 15 );
        TANK_SPAWN = new TankSpawn(2f,2.5f, PLAYER);
        RANGED_SPAWN = new RangedSpawn(1.5f, 2f, PLAYER);
        LURKER_SPAWN = new LurkerSpawn(1f,1.5f, PLAYER);
        SCORE_COUNTER = new ScoreCounter();

    }

    public void show(){
        MUSIC_PLAYER.start();

    }

    public void render(float delta) {
        deltaTime = delta;

        SCROLLING_BACKGROUND.start();

        if(ScoreCounter.score >= scoreMeter){
            scoreMeter += scoreMeter;
            raiseDifficult = true;
        }

        if(ScoreCounter.score >= playerUpgradeMeter){
            PLAYER.upgrade();
            playerUpgradeMeter += playerUpgradeMeter;
        }

        // Update entities
        PLAYER.update(delta);

        TANK_SPAWN.start();
        //TANK_SPAWN.update(delta, raiseDifficult);
        RANGED_SPAWN.update(delta, raiseDifficult);
        LURKER_SPAWN.update(delta, raiseDifficult);

        raiseDifficult = false;

        if(PLAYER.isDead()){
            try {
                TANK_SPAWN.stop();
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

        SCROLLING_BACKGROUND.render(main.batch);

        TANK_SPAWN.render(main.batch);
        LURKER_SPAWN.render(main.batch);
        RANGED_SPAWN.render(main.batch);
        PLAYER.render(main.batch);

        SCORE_COUNTER.render(main.batch);

        main.batch.end();
    }

    public void dispose() {
        super.dispose();
        TANK_SPAWN.dispose();
        PLAYER.dispose();
        try {
            MUSIC_PLAYER.stop();
            SCROLLING_BACKGROUND.stop();
            TANK_SPAWN.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Game Screen Disposed");
    }

}
