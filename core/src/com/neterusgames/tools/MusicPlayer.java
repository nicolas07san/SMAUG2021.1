package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer implements Runnable {

    private final Music music;
    private boolean running = false;
    private Thread thread;

    public MusicPlayer(){
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/game-music.ogg"));
    }

    public void playMusic(){
        if(!(music.isPlaying())){
            music.setVolume(0.2f);
            music.setLooping(true);
            music.play();
        }
    }

    public void dispose(){
        music.dispose();
    }

    //Thread logic
    public void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        System.out.println("Thread Musica Iniciada");
        playMusic();
    }

    public void stop() throws InterruptedException {
        if(!running){
            return;
        }
        running = false;
        thread.join(100);
        dispose();
        System.out.println("Thread Musica Finalizada");
    }
}
