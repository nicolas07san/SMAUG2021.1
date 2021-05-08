package com.neterusgames.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neterusgames.entities.BaseEntity;

public class DeathAnimation extends BaseEntity {

    public static final float FRAME_LENGTH = 0.1f;

    private final Animation<TextureRegion> ANIM;
    private float stateTime;

    public boolean remove;

    public DeathAnimation(float x, float y, String path){
        super(x,y);
        stateTime = 0;
        if(getTexture() == null){
            setTexture(new Texture(path));
        }

        setWidth(32);
        setHeight(32);

        ANIM = createAnimation(FRAME_LENGTH);

    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if(ANIM.isAnimationFinished(stateTime)){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(ANIM.getKeyFrame(stateTime),getX(), getY(),getWidth(),getHeight());
    }
}
