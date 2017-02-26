package com.twistedgenius.timeColapse.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.twistedgenius.timeColapse.gameobjects.Enemy;
import com.twistedgenius.timeColapse.gameobjects.HealthUp;
import com.twistedgenius.timeColapse.gameobjects.MpObject;
import com.twistedgenius.timeColapse.gameobjects.Player;
import com.twistedgenius.timeColapse.gameobjects.EnemyBullet;
import com.twistedgenius.timeColapse.gameobjects.PlayerBullet;
import com.twistedgenius.timeColapse.gameobjects.PowerUp;
import com.twistedgenius.timeColapse.screens.MainGameScreen;

public class MyContactListener implements ContactListener{

	 
	 
	@Override
	public void beginContact(Contact contact) {
		
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
				
		UserData UDa = (UserData) a.getBody().getUserData();
		UserData UDb = (UserData) b.getBody().getUserData();
		
		if(UDa ==null || UDb == null){
			return;
		}
		
		if(UDa.getName().equals("playerBullet")&&UDb.getName().equals("enemy")){
			enemyPlayerBullet(UDa,UDb);
		}
		else if(UDa.getName().equals("enemy")&&UDb.getName().equals("playerBullet")){
			enemyPlayerBullet(UDb,UDa);
		}
		if(UDa.getName().equals("player")&&UDb.getName().equals("enemy")){
			enemyPlayer(UDa,UDb);
		}
		else if(UDa.getName().equals("enemy")&&UDb.getName().equals("player")){
			enemyPlayer(UDb,UDa);
		}
		if(UDa.getName().equals("player")&&UDb.getName().equals("enemyBullet")){
			enemyBulletPlayer(UDa,UDb);
		}
		else if(UDa.getName().equals("enemyBullet")&&UDb.getName().equals("player")){
			enemyBulletPlayer(UDb,UDa);
		}
		if(UDa.getName().equals("MpObject")&&UDb.getName().equals("player")){
			mpPlayer(UDb,UDa);
		}
		else if(UDa.getName().equals("player")&&UDb.getName().equals("MpObject")){
			mpPlayer(UDa,UDb);
		}
		if(UDa.getName().equals("PowerUp")&&UDb.getName().equals("player")){
			powerUpPlayer(UDb,UDa);
		}
		else if(UDa.getName().equals("player")&&UDb.getName().equals("PowerUp")){
			powerUpPlayer(UDa,UDb);
		}
		if(UDa.getName().equals("HealthUp")&&UDb.getName().equals("player")){
			healthUpPlayer(UDb,UDa);
		}
		else if(UDa.getName().equals("player")&&UDb.getName().equals("HealthUp")){
			healthUpPlayer(UDa,UDb);
		}
	
		
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
/*	public void playSound(ArrayList<Music> soundArray, String sound, float volume){
		if(soundArray.size()==0){
			soundArray.add(Gdx.audio.newMusic(Gdx.files.internal("sounds/"+sound)));
			soundArray.get(0).setVolume(volume);
			soundArray.get(0).play();
		}
		else{
			int originalSize = soundArray.size();
			for(int size=0; size<originalSize; size++){
				if(soundArray.get(size).isPlaying()){
					if(size !=originalSize-1){
						continue;
					}
					else{
						soundArray.add(Gdx.audio.newMusic(Gdx.files.internal("sounds/"+sound)));
						soundArray.get(originalSize).setVolume(volume);
						soundArray.get(originalSize).play();
						break;
					}
				}
				else{
					soundArray.get(size).setVolume(volume);
					soundArray.get(size).play();
					break;
				}
			}
		}
	}
	*/
	
	public void enemyPlayerBullet(UserData playerBulletData, UserData enemyData){
		PlayerBullet bullet=(PlayerBullet) playerBulletData.getThisObject();
		if(bullet.isDeleteBody()==false){
			Enemy enemy = (Enemy) enemyData.getThisObject();
			enemy.setHitPoints(enemy.getHitPoints()-bullet.getFirePower());
			if(enemy.getHitPoints()<=0){
				
				MainGameScreen.explosionAnimations.add(new Animation(0.015f,MainGameScreen.explosionRegion));
				MainGameScreen.explosionAnimationTimer.add(new Float(0));
				MainGameScreen.destroyedX.add(enemy.getEnemyX());
				MainGameScreen.destroyedY.add(enemy.getEnemyY());
				MainGameScreen.sounds.getExplosionSound().play(0.4f);
				enemy.setDeleteBody(true);
			}
		}
		bullet.setDeleteBody(true);
		
	}
	public void enemyPlayer(UserData playerData, UserData enemyData){
		Enemy enemy = (Enemy) enemyData.getThisObject();
		Player player = (Player) playerData.getThisObject();
		if(enemy.isDeleteBody()==false){
			player.setHitPoints(player.getHitPoints()-15);
	
			MainGameScreen.explosionAnimations.add(new Animation(0.015f,MainGameScreen.explosionRegion));
			MainGameScreen.explosionAnimationTimer.add(new Float(0));
			MainGameScreen.destroyedX.add(enemy.getEnemyX());
			MainGameScreen.destroyedY.add(enemy.getEnemyY());
			MainGameScreen.sounds.getHitSound().play(0.4f);
			MainGameScreen.sounds.getExplosionSound().play(0.4f);
		}
		enemy.setDeleteBody(true);
		if(player.getHitPoints()<=0){
			player.setGameOver(true);
		}
		
	}
	
	public void enemyBulletPlayer(UserData playerData, UserData enemyBulletData){
		EnemyBullet enemybullet = (EnemyBullet) enemyBulletData.getThisObject();
		if(enemybullet.isDeleteBody()==false){
			Player player = (Player) playerData.getThisObject();
			player.setHitPoints(player.getHitPoints()-4);
			MainGameScreen.sounds.getHitSound().play(0.70f);
			if(player.getHitPoints()<=0){
				player.setGameOver(true);
			}
		}
		enemybullet.setDeleteBody(true);
		
	}

	public void mpPlayer(UserData playerData, UserData mpData){
		MpObject mp = (MpObject) mpData.getThisObject();
		if(mp.isDeleteBody()==false){			
			Player player = (Player) playerData.getThisObject();
			player.setMp(player.getMp()+0.02f);
			if(player.getMp()>=100){
				player.setMp(100);
			}
			MainGameScreen.sounds.getMpSound().play(0.4f);
		}
		mp.setDeleteBody(true);
	}
	
	private void powerUpPlayer(UserData playerData, UserData PowerUpData) {
		PowerUp powerUp = (PowerUp) PowerUpData.getThisObject();
		if(powerUp.isDeleteBody()==false){
			Player player = (Player) playerData.getThisObject();
			player.setFirePowerLevel((player.getFirePowerLevel()+1));
			MainGameScreen.sounds.getPowerUpSound().play(0.55f);
			if(player.getFirePowerLevel()>5){
				player.setFirePowerLevel(5);
			}
		}
		powerUp.setDeleteBody(true);
	}
	
	private void healthUpPlayer(UserData playerData, UserData healthUpData) {
		HealthUp healthUp = (HealthUp) healthUpData.getThisObject();
		if(healthUp.isDeleteBody()==false){
			Player player = (Player) playerData.getThisObject();
			player.setHitPoints((player.getHitPoints()+30));
			
			MainGameScreen.sounds.getRepairSound().play(0.5f);
			if(player.getHitPoints()>100){
				player.setHitPoints(100);
			}
		}
		healthUp.setDeleteBody(true);
	}
}
