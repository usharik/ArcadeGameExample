package com.geekbrains.arcade;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ArcadeGameExample extends ApplicationAdapter {
	private SpriteBatch batch;
	private Hero hero;
	private Texture background;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		Texture walkSheet = new Texture(Gdx.files.internal("player-spritemap-v9.png"));
		background = new Texture(Gdx.files.internal("tile065.png"));
		background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		TextureRegion[][] split = splitRegion(walkSheet, 4, 8);

		Animation<TextureRegion> walkAnimation = new Animation<>(0.07f, split[3]);
		hero = new Hero(split[0][0], split[0][6], split[0][7], walkAnimation, 70f, 120f, 2f);
		batch = new SpriteBatch();
	}

	private TextureRegion[][] splitRegion(Texture texture, int rows, int cols) {
		return TextureRegion.split(texture,
				(texture.getWidth()) / cols,
				(texture.getHeight()) / rows);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

		float deltaTime = Gdx.graphics.getDeltaTime();

		batch.begin();
		batch.draw(background, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hero.render(batch, deltaTime);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
