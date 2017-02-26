package com.twistedgenius.timeColapse.gameobjects;

import java.util.ArrayList;

import aurelienribon2.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json.Serializable;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.UserData;

public abstract class Enemy extends GameObject {


	protected transient ArrayList<EnemyBullet>  bullets;
	
	private float moveSpeed;
	private float timeToShoot;
	private float rateOfFire;
	private float hitPoints;
	private float armor;
	private float enemyX;
	private float enemyY;
	private float angel;
	private Vector2 velocity;
	private String type;
	
	
	protected transient static World world;
	
	Enemy(){}
	
	Enemy(Texture textureLocation, String borderLocation, String borderName, float moveSpeed, float rateOfFire, float hitPoints, float armor, World world ){
		super();
		this.rateOfFire=rateOfFire;  
		this.timeToShoot=0;
		this.moveSpeed = moveSpeed;
		this.armor = armor;
		this.hitPoints = hitPoints;

		
		Enemy.world = world;
		
		setBodyDef(new BodyDef());
		getBodyDef().type= BodyType.DynamicBody;
		setFixture(new FixtureDef());
		getFixture().filter.categoryBits = ConstantValues.BIT_ENEMY;
		getFixture().filter.maskBits = ConstantValues.BIT_PLAYER;
		getFixture().density=100;
		setBody(world.createBody(getBodyDef()));
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(borderLocation));
		loader.attachFixture(getBody(), borderName, getFixture(), 1.0f*ConstantValues.myScale);
				
		setTexture(textureLocation);
		setSprite(new Sprite(getTexture()));
		getSprite().setOriginCenter();
		//getSprite().setRotation(3.14f);
		getSprite().setSize(getTexture().getWidth()*ConstantValues.myScale/ConstantValues.PPM, getTexture().getHeight()*ConstantValues.myScale/ConstantValues.PPM);
		
		getBody().setUserData(new UserData("enemy",getSprite(),this));
		bullets = new ArrayList<EnemyBullet>();
	}
	
	
	public abstract void update(float delta);
	public abstract void dispose();
	
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
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


	public float getTimeToShoot() {
		return timeToShoot;
	}


	public void setTimeToShoot(float timeToShoot) {
		this.timeToShoot = timeToShoot;
	}


	public float getRateOfFire() {
		return rateOfFire;
	}


	public void setRateOfFire(float rateOfFire) {
		this.rateOfFire = rateOfFire;
	}


	public ArrayList<EnemyBullet> getBullets() {
		return bullets;
	}


	public void setBullets(ArrayList<EnemyBullet> bullets) {
		this.bullets = bullets;
	}


	public float getEnemyX() {
		return enemyX;
	}


	public void setEnemyX(float enemyX) {
		this.enemyX = enemyX;
	}


	public float getEnemyY() {
		return enemyY;
	}


	public void setEnemyY(float enemyY) {
		this.enemyY = enemyY;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAngel() {
		return angel;
	}

	public void setAngel(float angel) {
		this.angel = angel;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	
}
