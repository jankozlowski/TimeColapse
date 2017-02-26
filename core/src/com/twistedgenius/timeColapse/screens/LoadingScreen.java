package com.twistedgenius.timeColapse.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.twistedgenius.timeColapse.handlers.Assets;

public class LoadingScreen implements Screen {

	SpriteBatch loadingSpriteBatch;
	Texture loadingText;
	Texture loadingRing1;
	Texture loadingRing2;
	Sprite loadingTextSprite;
	float rotation1=0;
	float rotation2=0;
	float textAlpha=0;
	boolean downAlpha=false;
	int aditionalTime=0;
	
	@Override
	public void show() {
		loadingSpriteBatch = new SpriteBatch();
		
		loadingText= new Texture("loadingTekst.png");
		loadingRing1 = new Texture("loadingRing1.png");
		loadingRing2 = new Texture("loadingRing2.png");
		loadingTextSprite = new Sprite(loadingText);
		loadingTextSprite.setBounds(Gdx.graphics.getWidth()/2-Gdx.graphics.getWidth()*0.665f/2,Gdx.graphics.getHeight()/2-Gdx.graphics.getWidth()*0.665f/2,Gdx.graphics.getWidth()*0.665f,Gdx.graphics.getWidth()*0.665f);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// TODO Auto-generated method stub
		
		rotation1+=Gdx.graphics.getDeltaTime()*180;
		rotation2+=Gdx.graphics.getDeltaTime()*360;
		
		if(downAlpha==true){
			textAlpha-=Gdx.graphics.getDeltaTime();
		}
		else{
			textAlpha+=Gdx.graphics.getDeltaTime();
		}
		loadingTextSprite.setAlpha(textAlpha);
		
		if(textAlpha<=0){
			downAlpha=false;
		}
		else if(textAlpha>=100){
			downAlpha=true;
		}
		
		loadingSpriteBatch.begin();
		loadingTextSprite.draw(loadingSpriteBatch);
		loadingSpriteBatch.draw(loadingRing1,Gdx.graphics.getWidth()/2-loadingRing1.getWidth()/2,Gdx.graphics.getHeight()/2-loadingRing1.getHeight()/2,
				loadingRing1.getWidth()/2,loadingRing1.getHeight()/2,loadingRing1.getWidth(),loadingRing1.getHeight(),Gdx.graphics.getWidth()*0.665f/512,Gdx.graphics.getWidth()*0.665f/512,-rotation2,0,0,loadingRing1.getWidth(),loadingRing1.getHeight(),false,false);
		
		loadingSpriteBatch.draw(loadingRing2,Gdx.graphics.getWidth()/2-loadingRing2.getWidth()/2,Gdx.graphics.getHeight()/2-loadingRing2.getHeight()/2,
				loadingRing2.getWidth()/2,loadingRing2.getHeight()/2,loadingRing2.getWidth(),loadingRing2.getHeight(),Gdx.graphics.getWidth()*0.665f/512,Gdx.graphics.getWidth()*0.665f/512,rotation1,0,0,loadingRing2.getWidth(),loadingRing2.getHeight(),false,false);
		
		
		
		loadingSpriteBatch.end();
		
		if(!Assets.manager.update()){
			
		}
		else{
			aditionalTime++;
			if(aditionalTime>100){
				dispose();
			((Game) Gdx.app.getApplicationListener()).setScreen(new MainGameScreen());
			//
			}
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
