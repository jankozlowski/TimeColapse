package com.twistedgenius.timeColapse.screens;

// all scale are made for 768x1280 screen 
// textureWidth/768 and textureHeight/1280


import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.twistedgenius.timeColapse.handlers.Assets;
import com.twistedgenius.timeColapse.handlers.SpriteAcessor;

public class MenuScreen implements Screen{

	private Sprite menuSprite;
	private Sprite creditSprite;
	private SpriteBatch menuSpriteBatch;
	private SpriteBatch creditSpriteBatch;
	private Texture level1;
	private Texture level2;
	private Texture level3;
	private Texture levelSelectHud;
	private Texture credits;
	private Texture creditsAnimationTexture;
	private Texture googleAnimationTexture;
	private Texture linesAnimationTexture;
	private Texture topRing;
	private Texture menuBackground;
	private Texture backgroundFog;
	private Texture empty;
	
	private ScrollPane scroll;
	private TweenManager tweenManager;
	private Stage stage;
	private Music background;
	private boolean showCredits;
	MenuScreen mscreen = this;
	
    private TextureRegion[] creditRegion;
    private TextureRegion currentFrame;
	private Animation creditAnimation;	
	private float creditAnimationTime=0;
	
    private TextureRegion[] googleRegion;
    private Animation googleAnimation;	
	private float googleAnimationTime=0;
	
    private TextureRegion[] linesRegion;
    private Animation linesAnimation;	
	private float linesAnimationTime=0;
	SpriteBatch threadBatch = new SpriteBatch();
float screenWidth;
float screenHeight;
	
	
	float radius=0;
	
	@Override
	public void show() {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		creditsAnimationTexture = new Texture("creditButton.png");
		creditRegion = MainGameScreen.createRegion(creditRegion, creditsAnimationTexture, 160, 220, 3, 10);
		creditAnimation = new Animation(0.1f, creditRegion);
		
		googleAnimationTexture = new Texture("googlePlay.png");
		googleRegion = MainGameScreen.createRegion(googleRegion, googleAnimationTexture, 280, 220, 5, 10);
		googleAnimation = new Animation(0.08f, googleRegion);
		
		linesRegion = new TextureRegion[30];
		for(int a=0; a<30;a++){
			linesRegion[a] =new TextureRegion(new Texture("backgroundgrid/backgroundLines"+a+".png")); 
		}
		linesAnimation = new Animation(0.06f, linesRegion);
		
		menuSpriteBatch = new SpriteBatch();
		creditSpriteBatch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAcessor());
		
		menuBackground = new Texture("backgroundLines.png");
		level1 = new Texture("level1.png");
		level2 = new Texture("level2.png");
		level3 = new Texture("level3.png");
		
		levelSelectHud = new Texture("levelHud.png");
		topRing = new Texture("topRing.png");
		credits = new Texture("credits.png");
		backgroundFog = new Texture("backgroundFog.png");
		menuSprite = new Sprite(menuBackground);
		creditSprite = new Sprite(credits);
		empty =  new Texture("empty.png");
		showCredits=false;
		menuSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		creditSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(menuSprite, SpriteAcessor.ALPHA).target(0).start(tweenManager);
		Tween.to(menuSprite, SpriteAcessor.ALPHA,3).target(1).delay(3).start(tweenManager);
		Tween.set(creditSprite, SpriteAcessor.ALPHA).target(0).start(tweenManager);
		
		background = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.ogg"));
		
		stage = new Stage();
		

		
		Table content = new Table();
		
		Image cat = new Image(empty);

		cat.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (showCredits == false) {
					showCredits = true;
					Tween.to(creditSprite, SpriteAcessor.ALPHA,1).target(1).start(tweenManager);
				} else {
					showCredits = false;
					
					Tween.to(creditSprite, SpriteAcessor.ALPHA,1).target(0).start(tweenManager);
				}
			}
		});
	
		Image gp = new Image(empty);
		
		gp.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.net.openURI("https://play.google.com/store/apps/developer?id=TwistedGenius");
				
			}
		});
		
		cat.setBounds(screenWidth-screenWidth*0.208f,0,screenWidth*0.208f,screenWidth*0.208f*1.375f); //1.375  textureHeight/textureWidth
		gp.setBounds(0,0,screenWidth*0.3645f,screenWidth*0.3645f*0.785714f);
		
		Image l1 = new Image(level1);
	//	l1.setSize(800, 800);
		l1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				background.stop();
				background.dispose();
				
				Assets.load();
				((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreen());
				dispose();
			}
			
		});
		
		Image l2 = new Image(level2);
	//	l2.setSize(800, 800);
		//l2.pack();
		//l2.setBounds(0, 0, 800, 800);
		Image l3 = new Image(level3);
	//	l3.setSize(800,800);
		
	//	content.setWidth(900);
		
		content.add(l1).padBottom(screenHeight*0.18f).padTop(screenHeight/2-screenWidth*0.651f/2).width(screenWidth*0.651f).height(screenWidth*0.651f);
		content.row();
		content.add(l2).padBottom(screenHeight*0.2f).width(screenWidth*0.651f).height(screenWidth*0.651f);
		content.row();
		content.add(l3).padBottom(screenHeight/2-screenWidth*0.651f/2).width(screenWidth*0.651f).height(screenWidth*0.651f);
		content.row();
		
		//content.setFillParent(true);
		
		ScrollPane scroll = new ScrollPane(content);
		scroll.setBounds(screenWidth/2-(screenWidth*0.651f)/2, 0, screenWidth*0.651f, screenHeight);
		//
		
		//Table table = new Table();
		//table.add(scroll).fill().expand();
	
		stage.addActor(scroll);
		stage.addActor(cat);
		stage.addActor(gp);
		

		//content.setDebug(true);
		//table.setDebug(true);
		Gdx.input.setInputProcessor(stage);
		background.play();
	}

	@Override
	public void render(float delta) {
		if(!background.isPlaying()){
			background.play();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		radius+=(9*delta);
		creditAnimationTime += Gdx.graphics.getDeltaTime(); 
		googleAnimationTime += Gdx.graphics.getDeltaTime(); 
		linesAnimationTime += Gdx.graphics.getDeltaTime(); 
		
		menuSpriteBatch.begin();
		//menuSprite.draw(menuSpriteBatch);

		currentFrame = linesAnimation.getKeyFrame(linesAnimationTime, false);
		menuSpriteBatch.draw(currentFrame,0,0,screenWidth,screenHeight);
		currentFrame = googleAnimation.getKeyFrame(googleAnimationTime, true);
		menuSpriteBatch.draw(currentFrame,0,0,screenWidth*0.3645f,screenWidth*0.3645f*0.785714f);
		menuSpriteBatch.draw(backgroundFog,0,0,screenWidth,screenHeight);
		menuSpriteBatch.draw(topRing,screenWidth-screenWidth*0.335f,screenHeight-screenWidth*0.335f,screenWidth*0.335f/2,screenWidth*0.335f/2,screenWidth*0.335f,screenWidth*0.335f,1,1,radius,0,0,topRing.getWidth(),topRing.getHeight(),false,false);
		menuSpriteBatch.draw(levelSelectHud,screenWidth/2-(screenWidth*0.785f)/2,screenHeight/2-(screenWidth*0.785f)/2,screenWidth*0.785f,screenWidth*0.785f);
    
		
	    currentFrame = creditAnimation.getKeyFrame(creditAnimationTime, false);
		menuSpriteBatch.draw(currentFrame,screenWidth-screenWidth*0.208f,0,screenWidth*0.208f,screenWidth*0.208f*1.375f);
		
		
		if(creditAnimationTime>13){
			creditAnimationTime=0;
		}
		
		if(linesAnimationTime>6){
			linesAnimationTime=0;
		}
		
		menuSpriteBatch.end();
		stage.act(delta);
		stage.draw();
		creditSpriteBatch.begin();
		creditSprite.draw(creditSpriteBatch);
		creditSpriteBatch.end();
		
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
		menuSpriteBatch.dispose();
		menuSprite.getTexture().dispose();
		level1.dispose();
		
		
	}

}
