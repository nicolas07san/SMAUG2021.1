package com.neterusgames.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neterusgames.screens.GameScreen;

public class ScrollingBackground implements Runnable {

    private final int SPEED = 150;

    private final Texture TEXTURE = new Texture("background.png");
    private float y1;
    private float y2;
    private float scale;

    //Thread
    private boolean running = false;
    private Thread thread;

    public ScrollingBackground(){
        y1 = 0;
        y2 = TEXTURE.getHeight();
        scale = 1;
    }

    public void update(float deltaTime){

        y1 -= SPEED*deltaTime;
        y2 -= SPEED*deltaTime;

        if(y1 + TEXTURE.getHeight() * scale <= 0){
            y1 = y2+ TEXTURE.getHeight()*scale;
        }
        if(y2 + TEXTURE.getHeight() * scale <= 0){
            y2 = y1+ TEXTURE.getHeight()*scale;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(TEXTURE,0,y1, Gdx.graphics.getWidth(), TEXTURE.getHeight()*scale);
        batch.draw(TEXTURE,0,y2, Gdx.graphics.getWidth(), TEXTURE.getHeight()*scale);
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch){
        update(deltaTime);
        render(batch);
    }

    public void dispose(){
        TEXTURE.dispose();
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
        System.out.println("Thread Background Iniciada");
        long timeIn60FPS = 1000/60;
        while(running){
            long before = System.currentTimeMillis();
            update(GameScreen.deltaTime);
            long time = System.currentTimeMillis() - before;
            if(time < timeIn60FPS){
                try {
                    Thread.sleep(timeIn60FPS - time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Thread Background Finalizada");
    }

    public void stop() throws InterruptedException {
        if(!running){
            return;
        }
        running = false;
        Thread.currentThread().interrupt();
        dispose();
    }
}
