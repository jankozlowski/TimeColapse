package com.twistedgenius.timeColapse.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.twistedgenius.timeColapse.handlers.Assets;
import com.twistedgenius.timeColapse.screens.MainGameScreen;

public class Foker extends Enemy {

	
	
	//bulletFixture.filter.categoryBits = ConstantValues.BIT_ENEMY;
	public Foker(){
		super();
	}
	
	public Foker(float X, float Y, World world) {
		super(Assets.manager.get(Assets.BlueCarrier, Texture.class), "polygons/bluecarrier.json","bluecarrier", 10, 1.4f, 100, 1, world);
		setType("bluecarrier");
		getBody().setTransform(X, Y,0);
		
	}

	public void update(float delta){
		if(getTimeToShoot()<=0){
				setTimeToShoot(getRateOfFire());
				bullets.add(new SimpleBullet(getBody().getPosition().x,getBody().getPosition().y,world));
				MainGameScreen.enemyBullets.add(bullets.get(bullets.size()-1));
			}
		setTimeToShoot(getTimeToShoot() - delta);
		
		setEnemyX(getBody().getPosition().x);
		setEnemyY(getBody().getPosition().y);
		setAngel(getBody().getAngle());
		setVelocity(getBody().getLinearVelocity());
		

	}

	public void dispose() {
		
	}

	public ArrayList<EnemyBullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<EnemyBullet> bullets) {
		this.bullets = bullets;
	}

	/*@Override
	public void write(Json json) {
		
		setEnemyX(getBody().getPosition().x);
		setEnemyY(getBody().getPosition().y);
		
		for(EnemyBullet bullet : bullets ){
			bullet.setBulletX(bullet.getBody().getPosition().x);
			bullet.setBulletY(bullet.getBody().getPosition().y);
		}
		
		
		json.writeFields(this);
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		json.readFields(this, jsonData);
	}*/
	
	
	
}
