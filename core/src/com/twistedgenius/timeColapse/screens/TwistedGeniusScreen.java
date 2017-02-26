package com.twistedgenius.timeColapse.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.twistedgenius.timeColapse.handlers.Assets;
import com.twistedgenius.timeColapse.handlers.SpriteAcessor;



public class TwistedGeniusScreen implements Screen {
	
	private Sprite menuSprite;
	private SpriteBatch menuSpriteBatch;
	private Texture menuBackground;
	private TweenManager tweenManager;
	
	@Override
	public void show() {
		menuSpriteBatch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAcessor());
		
		menuBackground = new Texture("twistedGenius.png");
		menuSprite = new Sprite(menuBackground);
		menuSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(menuSprite, SpriteAcessor.ALPHA).target(0).start(tweenManager);
		Tween.to(menuSprite, SpriteAcessor.ALPHA,3).target(1).start(tweenManager);
		Tween.to(menuSprite, SpriteAcessor.ALPHA,3).target(0).delay(3).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
		
				 // only for loading level 
				dispose();
			//	Assets.load();
				((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());//new MenuScreen
				
				
			}
		}).start(tweenManager);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		menuSpriteBatch.begin();
		menuSprite.draw(menuSpriteBatch);
		menuSpriteBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		menuSprite.getTexture().dispose();
	//	menuSpriteBatch.dispose();
		
	}

}
