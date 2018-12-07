package com.geekbrains.arcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hero {

    private final TextureRegion standTexture;
    private final TextureRegion jumpUpTexture;
    private final TextureRegion jumpDownTexture;
    private final Animation<TextureRegion> walkAnimation;
    private final float runningSpeed;
    private final float jumpSpeed;
    private final float gravity;

    private float stateTime;
    private float x;
    private float y;

    private float vx = 0;
    private float vy = 0;
    private float direction = 1;
    private boolean jumping;

    public Hero(TextureRegion standTexture,
                TextureRegion jumpUpTexture, TextureRegion jumpDownTexture,
                Animation<TextureRegion> walkAnimation, float runningSpeed, float jumpSpeed, float gravity) {
        this.standTexture = standTexture;
        this.jumpUpTexture = jumpUpTexture;
        this.jumpDownTexture = jumpDownTexture;
        this.walkAnimation = walkAnimation;
        this.runningSpeed = runningSpeed;
        this.jumpSpeed = jumpSpeed;
        this.gravity = gravity;
    }

    public void render(SpriteBatch spriteBatch, float deltaTime) {
        stateTime += deltaTime;

        TextureRegion currentFrame;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !jumping) {
            vx = runningSpeed;
            direction = 1f;
            currentFrame = handleJump(walkAnimation.getKeyFrame(stateTime, true));
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !jumping) {
            vx = -runningSpeed;
            direction = -1f;
            currentFrame = handleJump(walkAnimation.getKeyFrame(stateTime, true));
        } else {
            if (vx != 0 && !jumping) {
                vx = 0f;
            }
            currentFrame = handleJump(standTexture);
        }

        x += deltaTime * vx;
        y += deltaTime * vy;
        spriteBatch.draw(currentFrame, x, y,
                currentFrame.getRegionWidth() / 2f, currentFrame.getRegionHeight() / 2f,
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight(),
                direction, 1f,
                0f);
    }

    private TextureRegion handleJump(TextureRegion currentTexture) {
        if (y > 0) {
            vy -= gravity;
            return vy > 0 ? jumpUpTexture : jumpDownTexture;
        } else if (y < 0) {
            vy = 0;
            y = 0;
            jumping = false;
            return currentTexture;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vy = jumpSpeed;
            jumping = true;
            return jumpUpTexture;
        }
        return currentTexture;
    }
}
