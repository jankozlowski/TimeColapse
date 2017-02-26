package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.UserData;

public class PlayerBullet extends GameObject {


	
	private float bulletX;
	private float bulletY;

	private float bulletSpeed;
	private boolean deleteBody;
	private float firePower;

	public PlayerBullet(){
		
	}
	
	public PlayerBullet(World world, float X, float Y, float bulletSpeed) {
		
		
		setTexture(new Texture("bullet.png"));
		bulletX = X;
		float random =(float)Math.random()*0.5f;
		bulletY = Y+random;
		setSprite(new Sprite(getTexture()));
		getSprite().setSize(getTexture().getWidth()*ConstantValues.myScale/ConstantValues.PPM, getTexture().getHeight()*ConstantValues.myScale/ConstantValues.PPM);
		getSprite().setPosition(bulletX, bulletY);
		this.bulletSpeed=bulletSpeed;
		deleteBody=false;
		firePower=25;
		
		setBodyDef(new BodyDef());
		getBodyDef().type= BodyType.DynamicBody;
		getBodyDef().position.set(bulletX, bulletY);
		setFixture(new FixtureDef());
		getFixture().filter.categoryBits = ConstantValues.BIT_PLAYER;
		getFixture().filter.maskBits = ConstantValues.BIT_ENEMY;
		getFixture().density=0.02f;
		CircleShape bulletShape = new CircleShape();
		bulletShape.setRadius(0.04f*ConstantValues.myScale);
		getFixture().shape = bulletShape;
		setBody(world.createBody(getBodyDef()));
		getBody().createFixture(getFixture());
		bulletShape.dispose();
		getBody().setBullet(true);
		getBody().setUserData(new UserData("playerBullet",getSprite(),this));
		getBody().applyLinearImpulse(0, bulletSpeed, 0, 0, true);
		
	}
	
	public void update(float delta){
		
	}
	
	
	public void render(SpriteBatch sb) {
		
	}

	public void dispose() {
		getSprite().getTexture().dispose();
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

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public boolean isDeleteBody() {
		return deleteBody;
	}

	public void setDeleteBody(boolean deleteBody) {
		this.deleteBody = deleteBody;
	}

	public float getFirePower() {
		return firePower;
	}

	public void setFirePower(float firePower) {
		this.firePower = firePower;
	}






}
