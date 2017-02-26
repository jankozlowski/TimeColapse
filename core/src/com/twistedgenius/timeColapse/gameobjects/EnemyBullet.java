package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.twistedgenius.timeColapse.handlers.ConstantValues;

public abstract class EnemyBullet extends GameObject{

	private float enemyBulletSpeed;
	private float enemyBulletFirepower;
	private float bulletX;
	private float bulletY;
	private float angel;
	private Vector2 velocity;
	private String Type;
	
	public EnemyBullet(){}
	
	public EnemyBullet(String textureLocation,float X, float Y) {
		super();
	
		X-=0.64f;
		Y-=1.28f;
		bulletX=X;
		bulletY=Y;
		setTexture(new Texture(textureLocation));
		setSprite(new Sprite(getTexture()));
		getSprite().setSize(getTexture().getWidth()*ConstantValues.myScale/ConstantValues.PPM, getTexture().getHeight()*ConstantValues.myScale/ConstantValues.PPM);
		getSprite().setPosition(X, Y);
		setBodyDef(new BodyDef());
		getBodyDef().type= BodyType.DynamicBody;
		getBodyDef().position.set(X, Y);
		setFixture(new FixtureDef());
		getFixture().filter.categoryBits = ConstantValues.BIT_ENEMY;
		getFixture().filter.maskBits = ConstantValues.BIT_PLAYER;
		getFixture().density=0.01f;
	}


	public float getEnemyBulletSpeed() {
		return enemyBulletSpeed;
	}

	public void setEnemyBulletSpeed(float enemyBulletSpeed) {
		this.enemyBulletSpeed = enemyBulletSpeed;
	}


	public float getEnemyBulletFirepower() {
		return enemyBulletFirepower;
	}

	public void setEnemyBulletFirepower(float enemyBulletFirepower) {
		this.enemyBulletFirepower = enemyBulletFirepower;
	}


	public float getBulletX() {
		return bulletX;
	}


	public void setBulletX(float bulletX) {
		this.bulletX = bulletX;
	}


	public float getBulletY() {
		return bulletY;
	}


	public void setBulletY(float bulletY) {
		this.bulletY = bulletY;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
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
