package com.neterusgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.neterusgames.game.Main;

public class MainMenuScreen extends BaseScreen {

    private final static Texture PLAY_BUTTON_INACTIVE = new Texture("buttons/play-inactive.png");
    private final static Texture PLAY_BUTTON_ACTIVE = new Texture("buttons/play-active.png");
    private final static int PLAY_BUTTON_X = Main.WIDTH/2 - (PLAY_BUTTON_INACTIVE.getWidth()*Main.SCALE)/2;
    private final static int PLAY_BUTTON_Y = 150;;

    private final static Texture H2PLAY_BUTTON_INACTIVE = new Texture("buttons/h2p-inactive.png");
    private final static Texture H2PLAY_BUTTON_ACTIVE = new Texture("buttons/h2p-active.png");
    private final static int H2PLAY_BUTTON_X = Main.WIDTH/2 - (H2PLAY_BUTTON_INACTIVE.getWidth()*Main.SCALE)/2;
    private final static int H2PLAY_BUTTON_Y = 100;

    private final static Texture CREDITS_BUTTON_INACTIVE = new Texture("buttons/credits-inactive.png");
    private final static Texture CREDITS_BUTTON_ACTIVE = new Texture("buttons/credits-active.png");
    private final static int CREDITS_BUTTON_X = Main.WIDTH/2 - (CREDITS_BUTTON_INACTIVE.getWidth()*Main.SCALE)/2;
    private final static int CREDITS_BUTTON_Y = 50;

    private final static Texture EXIT_BUTTON_INACTIVE = new Texture("buttons/exit-inactive.png");
    private final static Texture EXIT_BUTTON_ACTIVE = new Texture("buttons/exit-active.png");
    private final static int EXIT_BUTTON_X = Main.WIDTH - (EXIT_BUTTON_INACTIVE.getWidth()*Main.SCALE) - 20;
    private final static int EXIT_BUTTON_Y = 50;;

    private int  highscore;
    private final static BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/scorefont.fnt"));

    public MainMenuScreen(Main main){
        super(main);

        Preferences pref = Gdx.app.getPreferences("neterusgames.shmup");
        highscore = pref.getInteger("highscore");

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();

        super.render(delta);

        GlyphLayout layout = new GlyphLayout(font, "" + highscore);
        font.draw(main.batch, layout, Gdx.graphics.getWidth() / 2f - layout.width / 2,
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

        System.out.println("Main Menu Screen Running");

        main.batch.end();
    }

    public void dispose() {
        super.dispose();
    }
}
