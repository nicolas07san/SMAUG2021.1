package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.game.Main;

public class ScoreCounter {

    public static int score = 0;

    public ScoreCounter() {
        score = 0;
    }

    public void render(SpriteBatch batch) {
        GlyphLayout layout = new GlyphLayout(Main.FONT, "" + score);
        Main.FONT.draw(batch, layout, Gdx.graphics.getWidth() / 2f - layout.width / 2,
                Gdx.graphics.getHeight() - layout.height - 10);
    }

}
