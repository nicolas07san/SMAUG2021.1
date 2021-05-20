package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.neterusgames.game.Main;
import com.neterusgames.tools.MusicPlayer;

public class MainMenuScreen extends BaseScreen {

    private final Texture PLAY_BUTTON_INACTIVE = new Texture("buttons/play-inactive.png");
    private final Texture PLAY_BUTTON_ACTIVE = new Texture("buttons/play-active.png");
    private final int PLAY_BUTTON_X = Main.WIDTH/2 - (PLAY_BUTTON_INACTIVE.getWidth()*Main.SCALE)/2;
    private final int PLAY_BUTTON_Y = 150;;

    private final Texture H2PLAY_BUTTON_INACTIVE = new Texture("buttons/h2p-inactive.png");
    private final Texture H2PLAY_BUTTON_ACTIVE = new Texture("buttons/h2p-active.png");
    private final int H2PLAY_BUTTON_X = Main.WIDTH/2 - (H2PLAY_BUTTON_INACTIVE.getWidth()*Main.SCALE)/2;
    private final int H2PLAY_BUTTON_Y = 100;

    private final Texture CREDITS_BUTTON_INACTIVE = new Texture("buttons/credits-inactive.png");
    private final Texture CREDITS_BUTTON_ACTIVE = new Texture("buttons/credits-active.png");
    private final int CREDITS_BUTTON_X = Main.WIDTH/2 - (CREDITS_BUTTON_INACTIVE.getWidth()*Main.SCALE)/2;
    private final int CREDITS_BUTTON_Y = 50;

    private final Texture EXIT_BUTTON_INACTIVE = new Texture("buttons/exit-inactive.png");
    private final Texture EXIT_BUTTON_ACTIVE = new Texture("buttons/exit-active.png");
    private final int EXIT_BUTTON_X = Main.WIDTH - (EXIT_BUTTON_INACTIVE.getWidth()*Main.SCALE) - 20;
    private final int EXIT_BUTTON_Y = 50;

    private final Texture BANNER = new Texture("banners/mainmenu-banner.png");
    private final Texture LOGO =  new Texture("banners/logo.png");

    private final int HIGHSCORE;

    private final GlyphLayout LAYOUT;

    public MainMenuScreen(Main main){
        super(main);

        Preferences pref = Gdx.app.getPreferences("neterusgames.shmup");
        HIGHSCORE = pref.getInteger("highscore");
        LAYOUT = new GlyphLayout(Main.FONT, "" + HIGHSCORE);

    }

    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        main.batch.draw(BANNER,Gdx.graphics.getWidth()/2f - BANNER.getWidth()/2f,
                Gdx.graphics.getHeight() - BANNER.getHeight() - 20);

        main.batch.draw(LOGO,20,20,LOGO.getWidth()*SCALE,LOGO.getHeight()*SCALE);

        Main.FONT.draw(main.batch, LAYOUT, Gdx.graphics.getWidth() / 2f - LAYOUT.width / 2,
                300);

        //playButton logic
        drawButton(PLAY_BUTTON_INACTIVE, PLAY_BUTTON_ACTIVE, PLAY_BUTTON_X, PLAY_BUTTON_Y,
                new GameScreen(main), main, false);

        //h2pButton logic
        drawButton(H2PLAY_BUTTON_INACTIVE, H2PLAY_BUTTON_ACTIVE, H2PLAY_BUTTON_X, H2PLAY_BUTTON_Y,
                new HowToPlayScreen(main), main,false);

        //creditsButton logic
        drawButton(CREDITS_BUTTON_INACTIVE, CREDITS_BUTTON_ACTIVE, CREDITS_BUTTON_X, CREDITS_BUTTON_Y,
                new CreditsScreen(main), main,false);

        //exitButton logic
        drawButton(EXIT_BUTTON_INACTIVE, EXIT_BUTTON_ACTIVE, EXIT_BUTTON_X, EXIT_BUTTON_Y,
                new MainMenuScreen(main),main,true);

        main.batch.end();

    }

    public void dispose() {
        super.dispose();

        PLAY_BUTTON_INACTIVE.dispose();
        PLAY_BUTTON_ACTIVE.dispose();

        H2PLAY_BUTTON_INACTIVE.dispose();
        H2PLAY_BUTTON_ACTIVE.dispose();

        CREDITS_BUTTON_INACTIVE.dispose();
        CREDITS_BUTTON_ACTIVE.dispose();

        EXIT_BUTTON_INACTIVE.dispose();
        EXIT_BUTTON_INACTIVE.dispose();

        BANNER.dispose();
        LOGO.dispose();

        System.out.println("Menu Screen Disposed");

    }
}
