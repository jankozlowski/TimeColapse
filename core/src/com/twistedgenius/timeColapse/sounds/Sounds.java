package com.twistedgenius.timeColapse.sounds;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.twistedgenius.timeColapse.handlers.Assets;

public class Sounds {
	
	Music backgroundMusic;
	Sound timeCollapse;
	Sound explosionSound;
	Sound hitSound;
	Sound powerUpSound;
	Sound repairSound;
	Sound mpSound;
	
	public Sounds(){

		explosionSound = Assets.manager.get(Assets.explosionSound,Sound.class);
		
		hitSound = Assets.manager.get(Assets.hitSound,Sound.class);
		powerUpSound = Assets.manager.get(Assets.powerUpSound,Sound.class);
		repairSound = Assets.manager.get(Assets.repairSound,Sound.class);
		mpSound = Assets.manager.get(Assets.mpSound,Sound.class);
		backgroundMusic = Assets.manager.get(Assets.backgroundMusic,Music.class);;
	//	backgroundMusic.setVolume(0.3f);
		timeCollapse = Assets.manager.get(Assets.timeCollapse,Sound.class);
	}

	public Music getBackgroundMusic() {
		return backgroundMusic;
	}

	public void setBackgroundMusic(Music backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}

	

	public void dispose() {
		 backgroundMusic.dispose();
		 timeCollapse.dispose();
		 explosionSound.dispose();
		 hitSound.dispose();
		 powerUpSound.dispose();
		 repairSound.dispose();
		 mpSound.dispose();
	}

	public Sound getTimeCollapse() {
		return timeCollapse;
	}

	public void setTimeCollapse(Sound timeCollapse) {
		this.timeCollapse = timeCollapse;
	}

	public Sound getExplosionSound() {
		return explosionSound;
	}

	public void setExplosionSound(Sound explosionSound) {
		this.explosionSound = explosionSound;
	}

	public Sound getHitSound() {
		return hitSound;
	}

	public void setHitSound(Sound hitSound) {
		this.hitSound = hitSound;
	}

	public Sound getPowerUpSound() {
		return powerUpSound;
	}

	public void setPowerUpSound(Sound powerUpSound) {
		this.powerUpSound = powerUpSound;
	}

	public Sound getRepairSound() {
		return repairSound;
	}

	public void setRepairSound(Sound repairSound) {
		this.repairSound = repairSound;
	}

	public Sound getMpSound() {
		return mpSound;
	}

	public void setMpSound(Sound mpSound) {
		this.mpSound = mpSound;
	}
	
}
