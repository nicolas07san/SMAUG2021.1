package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreCounter {
    private int score;
    private BitmapFont font;

    public ScoreCounter(){

        score = 0;
        font =  new BitmapFont(Gdx.files.internal("fonts/mainFont.fnt"));

    }

    public void render(SpriteBatch batch){

    }
}
