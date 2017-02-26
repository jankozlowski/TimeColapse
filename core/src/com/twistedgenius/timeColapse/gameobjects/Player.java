package com.twistedgenius.timeColapse.gameobjects;


import java.util.ArrayList;

import aurelienribon2.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.KryoSerializable;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.UserData;
import com.twistedgenius.timeColapse.screens.MainGameScreen;

public class Player {

	private transient Sprite playerSprite;
	private transient Texture playerTexture;
	private transient BodyDef playerBodyDef;
	private transient FixtureDef playerFixture;
	private transient Body playerBody;
	private ArrayList<PlayerBullet> bullets;
	private transient ParticleEffect engine;
	float myScale = ConstantValues.myScale;
//	public transient Sound shotSound;
	
	private float moveSpeed;
	private double timeToShoot;
	private double rateOfFire;
	private float hitPoints;
	private float armor;
	private int firePowerLevel;
	
	private transient World world;
	private boolean gameOver;
	
	private float lastX, lastY;
	private float mp;
	
	private boolean change = false, once=false;

	float forceX, forceY;
	float bulletSpeed=0.00198f*myScale;
	
	private float animationPoint;
	private transient Animation moveLeftAnimation;
	private transient Animation moveRightAnimation;
	private transient TextureRegion[] moveLeftRegion;  
	private transient TextureRegion[] moveRightRegion;  
	boolean left;
	boolean right;
	
	private transient TextureRegion currentFrame; 
	int soundFire=0;
	
	//values for serialization
	private String selectedPlane;
	private float x;
	private float y;
	private Vector2 linearVelocity;
	private short categoryBits;
	private short maskBits;
	
	
	
	public Player(){
	

	}
	
	public Player(World world) {
	
		
		animationPoint=0;
		currentFrame = new TextureRegion(new Texture("alienspaceship.png"));
		left = false;
		right = false;
		
		rateOfFire=0.12f;  //lower faster
		timeToShoot=0;
		moveSpeed = 180;
		hitPoints =100;
		armor = 1;
		firePowerLevel=1;
		mp=100;
		bullets = new ArrayList<PlayerBullet>();
		this.world = world;
		//
		playerTexture = new Texture("alienspaceship.png");
		playerBodyDef = new BodyDef();
		playerBodyDef.type= BodyType.DynamicBody;
		
		playerBodyDef.position.set(Gdx.graphics.getWidth()/(2f*ConstantValues.PPM)-(playerTexture.getWidth()*myScale/(2*ConstantValues.PPM)), (Gdx.graphics.getHeight()*myScale/(2f*ConstantValues.PPM))-(playerTexture.getHeight()*myScale/(2*ConstantValues.PPM))-2);
		
		playerFixture = new FixtureDef();
		playerFixture.filter.categoryBits = ConstantValues.BIT_PLAYER;
		playerFixture.filter.maskBits = ConstantValues.BIT_ENEMY | ConstantValues.BIT_MP;

		
		playerBody = world.createBody(playerBodyDef);
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("polygons/alienspaceship.json"));
		loader.attachFixture(playerBody, "alienspaceship", playerFixture, 1.35f*myScale);
				
		
		playerSprite = new Sprite(playerTexture);
		
		playerSprite.setSize(playerTexture.getWidth()*myScale/ConstantValues.PPM,playerTexture.getHeight()*myScale/ConstantValues.PPM);
		
		
		playerBody.setUserData(new UserData("player",playerSprite,this));
		playerBody.setLinearVelocity(new Vector2(0,ConstantValues.worldSpeed));
	
		selectedPlane="Foker";
		
		createPlayerEngine();
	    
		moveLeftRegion= MainGameScreen.createRegion(moveLeftRegion, new Texture("moveLeft.png"), 140, 140, 3, 10);
		moveRightRegion= MainGameScreen.createRegion(moveLeftRegion, new Texture("moveRight.png"), 140, 140, 3, 10);
		         
		moveLeftAnimation = new Animation(0.01f,moveLeftRegion);
		moveRightAnimation = new Animation(0.01f,moveRightRegion);


		//	shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.wav"));
		
	}

	public void update(float delta, float topCameraY, Batch gameSpriteBatch) {
		
		
//movment logic (probably)	
		if (Gdx.input.isTouched()) {
			soundFire++;
			
			float inputX = Gdx.input.getX()/ConstantValues.PPM;
			float inputY = Gdx.input.getY()/ConstantValues.PPM-0.72f*myScale;

			float playerPositionX = playerBody.getPosition().x+(playerTexture.getWidth()*myScale/ConstantValues.PPM)/2;
			float playerPositionY = playerBody.getPosition().y;
			
			float difrenceY = topCameraY-inputY;

			//why did i build this code? Its makes fligth shity
		//	if(inputX < 1.28*myScale && inputY > ConstantValues.screenWidth/100 || inputX>6.5&&inputY > 10.80){  //1.28 10.80 6.5 10.80
			//	return;
			//}
			
		if(change==false){
			lastX = inputX;
			lastY = inputY;
			change=true;
		}
		
		if(lastX!=inputX || lastY != inputY){
			once=false;
			lastX = inputX;
			lastY = inputY;
			playerBody.setLinearVelocity(new Vector2(0,ConstantValues.worldSpeed));
			
		}	
				forceX=inputX-playerPositionX;
				forceY=difrenceY-playerPositionY;
				
				
				
		
				if(once ==false){
					playerBody.applyForceToCenter(forceX*moveSpeed,forceY*moveSpeed, true);
					once=true;
				}
				if((forceX <0.005f && forceX>-0.005f) ||(forceY <0.005f && forceY >-0.005f)){
					playerBody.setLinearVelocity(new Vector2(0,ConstantValues.worldSpeed));	
				}
				
				

//tilt animation
				float whenTilt = 0.2f;
				
				if(forceX<-whenTilt && left==false){
					left = true;
					right=false;
					animationPoint=0;
				}
				else if(forceX>whenTilt && right==false){
					left=false;
					animationPoint=0;
				//	playerSprite.setRegion(moveLeftRegion[0]);
					right=true;
				}
				else if(forceX>-whenTilt && forceX<whenTilt){
					returnTiltToNormal();
				}
				
				if(left && !(forceX>-whenTilt && forceX<whenTilt)){
					animationPoint+=Gdx.graphics.getDeltaTime();           
			        currentFrame = moveLeftAnimation.getKeyFrame(animationPoint, false);
			        playerSprite.setRegion(currentFrame);
			        //	gameSpriteBatch.draw(currentFrame,x-0.70f,y-0.70f,1.40f,1.40f);
				}
				if(right && !(forceX>-whenTilt && forceX<whenTilt)){
					animationPoint+=Gdx.graphics.getDeltaTime();           
			        currentFrame = moveRightAnimation.getKeyFrame(animationPoint, false);
			        playerSprite.setRegion(currentFrame);
			        //	gameSpriteBatch.draw(currentFrame,x-0.70f,y-0.70f,1.40f,1.40f);
				}
				
//shooting logic				
				
			if(timeToShoot<=0){
			//	{System.out.println(Math.random()/100);}
				timeToShoot=rateOfFire;
				
				
				if(firePowerLevel==1){
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(2*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
				/*	if(soundFire%4==0){
						shotSound.play(0.1f);
					}*/
					
				}
				else if(firePowerLevel==2){
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale*3/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
				
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
					
				}
				else if(firePowerLevel==3){
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(2*ConstantValues.PPM), playerBody.getPosition().y+(playerTexture.getHeight()*myScale+40)/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale*3/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));

				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				}
				else if(firePowerLevel==4){
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(2*ConstantValues.PPM), playerBody.getPosition().y+(playerTexture.getHeight()*myScale+40)/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale*3/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/ConstantValues.PPM, playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
				

				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				}
				else {
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(2*ConstantValues.PPM), playerBody.getPosition().y+(playerTexture.getHeight()+40)/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale*3/(4*ConstantValues.PPM), playerBody.getPosition().y+playerTexture.getHeight()/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x+playerTexture.getWidth()*myScale/ConstantValues.PPM, playerBody.getPosition().y+playerTexture.getHeight()/(2*ConstantValues.PPM),bulletSpeed));
					bullets.add(new PlayerBullet(world,playerBody.getPosition().x, playerBody.getPosition().y+playerTexture.getHeight()*myScale/(2*ConstantValues.PPM),bulletSpeed));
				
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				//	shotSound.play(0.1f);
				}
			}
			
			
		}
		else{
			change=false;
			once=false;
			playerBody.setLinearVelocity(new Vector2(0,ConstantValues.worldSpeed));
			returnTiltToNormal();
		}
		
		timeToShoot=timeToShoot-delta;
		
		
		engine.setPosition(playerBody.getPosition().x+0.70f*myScale, playerBody.getPosition().y);
		
		
	

		
		
		
//data for serialization
		x=playerBody.getPosition().x;
		y=playerBody.getPosition().y;
		linearVelocity = playerBody.getLinearVelocity();
		categoryBits = playerBody.getFixtureList().get(0).getFilterData().categoryBits;
		maskBits = playerBody.getFixtureList().get(0).getFilterData().maskBits;
		
		for(PlayerBullet bullet : bullets ){
			bullet.setBulletX(bullet.getBody().getPosition().x);
			bullet.setBulletY(bullet.getBody().getPosition().y);
		}
		
		
		
		
		
		
	}
	private void returnTiltToNormal(){
		if(animationPoint>0){
			animationPoint-=2*Gdx.graphics.getDeltaTime(); 
			if(animationPoint<0){
				animationPoint=0;
			}
		}
		if(left==true){
			currentFrame = moveLeftAnimation.getKeyFrame(animationPoint, false);
		}
		else if(right==true){
			currentFrame = moveRightAnimation.getKeyFrame(animationPoint, false);
		}
		playerSprite.setRegion(currentFrame);
	}
	
	private void createPlayerEngine(){
		engine = new ParticleEffect();
		engine.load(Gdx.files.internal("engine.p"), Gdx.files.internal(""));
		engine.setPosition(x, y);
		float pScale = 0.01f*myScale;
		
	    float scaling = engine.getEmitters().get(0).getScale().getHighMax();
	    engine.getEmitters().get(0).getScale().setHigh(scaling * pScale);

	    scaling =engine.getEmitters().get(0).getScale().getLowMax();
	    engine.getEmitters().get(0).getScale().setLow(scaling * pScale);

	    scaling = engine.getEmitters().get(0).getVelocity().getHighMax();
	    engine.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

	    scaling =engine.getEmitters().get(0).getVelocity().getLowMax();
	    engine.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
	    
	    engine.start();
	}
		

	public void render(float delta) {
	
	}

	public void dispose() {
		playerSprite.getTexture().dispose();
	}

	public Sprite getPlayerSprite() {
		return playerSprite;
	}

	public void setPlayerSprite(Sprite playerSprite) {
		this.playerSprite = playerSprite;
	}

	public Texture getPlayerTexture() {
		return playerTexture;
	}

	public void setPlayerTexture(Texture playerTexture) {
		this.playerTexture = playerTexture;
	}

	public BodyDef getPlayerBodyDef() {
		return playerBodyDef;
	}

	public void setPlayerBodyDef(BodyDef playerBodyDef) {
		this.playerBodyDef = playerBodyDef;
	}

	public FixtureDef getPlayerFixture() {
		return playerFixture;
	}

	public void setPlayerFixture(FixtureDef playerFixture) {
		this.playerFixture = playerFixture;
	}

	public Body getPlayerBody() {
		return playerBody;
	}

	public void setPlayerBody(Body playerBody) {
		this.playerBody = playerBody;
	}

	public ArrayList<PlayerBullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<PlayerBullet> bullets) {
		this.bullets = bullets;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public double getTimeToShoot() {
		return timeToShoot;
	}

	public void setTimeToShoot(double timeToShoot) {
		this.timeToShoot = timeToShoot;
	}

	public double getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(double rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public float getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(float hitPoints) {
		this.hitPoints = hitPoints;
	}

	public float getArmor() {
		return armor;
	}

	public void setArmor(float armor) {
		this.armor = armor;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public float getMp() {
		return mp;
	}

	public void setMp(float mp) {
		this.mp = mp;
	}

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public int getFirePowerLevel() {
		return firePowerLevel;
	}

	public void setFirePowerLevel(int firePowerLevel) {
		this.firePowerLevel = firePowerLevel;
	}

	public String getSelectedPlane() {
		return selectedPlane;
	}

	public void setSelectedPlane(String selectedPlane) {
		this.selectedPlane = selectedPlane;
	}

	
	public float getLastX() {
		return lastX;
	}

	public void setLastX(float lastX) {
		this.lastX = lastX;
	}

	public float getLastY() {
		return lastY;
	}

	public void setLastY(float lastY) {
		this.lastY = lastY;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public boolean isOnce() {
		return once;
	}

	public void setOnce(boolean once) {
		this.once = once;
	}

	public float getForceX() {
		return forceX;
	}

	public void setForceX(float forceX) {
		this.forceX = forceX;
	}

	public float getForceY() {
		return forceY;
	}

	public void setForceY(float forceY) {
		this.forceY = forceY;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}



	public short getCategoryBits() {
		return categoryBits;
	}

	public void setCategoryBits(short categoryBits) {
		this.categoryBits = categoryBits;
	}

	public short getMaskBits() {
		return maskBits;
	}

	public void setMaskBits(short maskBits) {
		this.maskBits = maskBits;
	}


	public Vector2 getLinearVelocity() {
		return linearVelocity;
	}

	public void setLinearVelocity(Vector2 linearVelocity) {
		this.linearVelocity = linearVelocity;
	}

	public ParticleEffect getEngine() {
		return engine;
	}

	public void setEngine(ParticleEffect engine) {
		this.engine = engine;
	}

	/*
	@Override
	public void write(Json json) {
		x=playerBody.getPosition().x;
		y=playerBody.getPosition().y;
		linearVelocity = playerBody.getLinearVelocity();
		categoryBits = playerBody.getFixtureList().get(0).getFilterData().categoryBits;
		maskBits = playerBody.getFixtureList().get(0).getFilterData().maskBits;
		
		for(PlayerBullet bullet : bullets ){
			bullet.setBulletX(bullet.getBody().getPosition().x);
			bullet.setBulletY(bullet.getBody().getPosition().y);
		}
		
		json.writeFields(this);
	
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		json.readFields(this, jsonData);
	}*/
	
	/*public Player copy(Kryo arg0) {
		
		x=playerBody.getPosition().x;
		y=playerBody.getPosition().y;
		linearVelocity = playerBody.getLinearVelocity();
		categoryBits = playerBody.getFixtureList().get(0).getFilterData().categoryBits;
		maskBits = playerBody.getFixtureList().get(0).getFilterData().maskBits;
		
		for(PlayerBullet bullet : bullets ){
			bullet.setBulletX(bullet.getBody().getPosition().x);
			bullet.setBulletY(bullet.getBody().getPosition().y);
		}
		
		
		return this;
	}*/

}
