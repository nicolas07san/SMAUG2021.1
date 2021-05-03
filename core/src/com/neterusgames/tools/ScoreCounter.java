package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreCounter {

    public static int score = 0;
    private BitmapFont font;

    public ScoreCounter() {
        score = 0;
        font = new BitmapFont(Gdx.files.internal("fonts/scorefont.fnt"));

    }

    public void render(SpriteBatch batch) {
        GlyphLayout layout = new GlyphLayout(font, "" + score);
        font.draw(batch, layout, Gdx.graphics.getWidth() / 2f - layout.width / 2,
                Gdx.graphics.getHeight() - layout.height - 10);
    }


}
