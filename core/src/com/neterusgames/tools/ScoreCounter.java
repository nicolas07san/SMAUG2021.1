package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.screens.BaseScreen;

public class ScoreCounter {

    public static int score = 0;
    //private final static BitmapFont SCORE_FONT = new BitmapFont(Gdx.files.internal("fonts/scorefont.fnt"));

    public ScoreCounter() {
        score = 0;

    }

    public void render(SpriteBatch batch) {
        GlyphLayout layout = new GlyphLayout(BaseScreen.SCORE_FONT, "" + score);
        BaseScreen.SCORE_FONT.draw(batch, layout, Gdx.graphics.getWidth() / 2f - layout.width / 2,
                Gdx.graphics.getHeight() - layout.height - 10);
    }


}
