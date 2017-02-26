package com.twistedgenius.timeColapse.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {

	public static final AssetManager manager = new AssetManager();
	
	public static final String spaceShipHpFull = "alienspaceshiphpf.png";
	public static final String spaceShipHpEmpty = "alienspaceshiphpe.png";
	public static final String hpFullTexture = "fokerRev.png";
	public static final String hpEmptyTexture = "fokerBlack.png";
	public static final String foker = "foker.png";
	public static final String speedUp = "speedUp.png";
	public static final String immortality = "imortality.png";
	public static final String slowDown = "slowDown.png";
	public static final String stopDown = "stopTime.png";
	public static final String trueForm = "trueForm.png";
	public static final String fokerSelect = "fokerSelect.png";
	public static final String planeSelect = "planeSelect.png";
	public static final String mp1Texture = "grad2.png";
	public static final String mp2Texture = "mpBar2.png";
	public static final String noData = "nodata.png";
	public static final String eve = "eve.png";
	public static final String pfalz = "pfalz_aIb.png";
	public static final String tableBackground = "panel.png";
	public static final String explosion = "explosion.png";
	public static final String powerFull = "powermeterfull.png";
	public static final String powerEmpty = "powermeterempty.png";
	public static final String mpgrad = "mpBar1.png";
	
	public static final String explosionSound = "sounds/badexplosion.mp3";
	public static final String backgroundMusic = "sounds/extremeAction.ogg";
	public static final String timeCollapse = "sounds/timecolapse.mp3";
	public static final String hitSound = "sounds/hit.mp3";
	public static final String powerUpSound = "sounds/powerup.mp3";
	public static final String repairSound = "sounds/selfrepair.mp3";
	public static final String mpSound = "sounds/mp.mp3";
	
	
	public static final String maps = "maps/prologmap.tmx";

	public static final String AlienBoss = "alienship.png";
	public static final String BlueCarrier = "bluecarrier.png";
	
	
	public static void load(){
		
		manager.load(spaceShipHpFull, Texture.class);
		manager.load(spaceShipHpEmpty, Texture.class);
		manager.load(hpFullTexture, Texture.class);
		manager.load(hpEmptyTexture, Texture.class);
		manager.load(foker, Texture.class);
		manager.load(speedUp, Texture.class);
		manager.load(immortality, Texture.class);
		manager.load(slowDown, Texture.class);
		manager.load(stopDown, Texture.class);
		manager.load(trueForm, Texture.class);
		manager.load(fokerSelect, Texture.class);
		manager.load(planeSelect, Texture.class);
		manager.load(mp1Texture, Texture.class);
		manager.load(mp2Texture, Texture.class);
		manager.load(noData, Texture.class);
		manager.load(eve, Texture.class);
		manager.load(pfalz, Texture.class);
		manager.load(tableBackground, Texture.class);
		manager.load(explosion , Texture.class);
		manager.load(powerFull, Texture.class);
		manager.load(powerEmpty, Texture.class);
		manager.load(spaceShipHpFull, Texture.class);
		manager.load(mpgrad, Texture.class);
		
		manager.load(AlienBoss, Texture.class);
		manager.load(BlueCarrier, Texture.class);
		
		manager.load(explosionSound, Sound.class);
		manager.load(repairSound, Sound.class);
		manager.load(powerUpSound, Sound.class);
		manager.load(hitSound, Sound.class);
		manager.load(timeCollapse, Sound.class);
		manager.load(mpSound, Sound.class);
		manager.load(backgroundMusic, Music.class);
		
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load(maps, TiledMap.class);
		
		
		
	}
	
	public static void dispose(){
		manager.dispose();
	}
	
}
