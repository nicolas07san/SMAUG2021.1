package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreCounter {

    private final BitmapFont SCORE_FONT = new BitmapFont(Gdx.files.internal("scorefont.fnt"));
    public static int score = 0;

    public ScoreCounter() {
        score = 0;

    }

    public void render(SpriteBatch batch) {
        GlyphLayout layout = new GlyphLayout(SCORE_FONT, "" + score);
        SCORE_FONT.draw(batch, layout, Gdx.graphics.getWidth() / 2f - layout.width / 2,
                Gdx.graphics.getHeight() - layout.height - 10);
    }


    public void dispose(){
        SCORE_FONT.dispose();
    }
}
