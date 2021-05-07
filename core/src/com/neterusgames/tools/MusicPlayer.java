package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

    private final Music menuMusic;

    public MusicPlayer(){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu-music.ogg"));
    }

    public void playMusic(){
        if(menuMusic.isPlaying()){

        }else{
            menuMusic.play();
        }

    }

    public void dispose(){
        menuMusic.dispose();
    }

}
