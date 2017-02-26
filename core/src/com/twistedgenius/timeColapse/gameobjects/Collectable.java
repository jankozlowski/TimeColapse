package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Collectable extends GameObject{

	private float positionX;
	private float positionY;
	private float animationPoint;
	private transient Animation collectableAnimation;
	private transient TextureRegion[] collectableRegion;  
	private transient TextureRegion currentFrame; 
	
	@Override
	public void update(float delta) {}
	
	public void update(float delta, SpriteBatch gameSpriteBatch) {}

	@Override
	public void dispose() {}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public float getAnimationPoint() {
		return animationPoint;
	}

	public void setAnimationPoint(float animationPoint) {
		this.animationPoint = animationPoint;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Animation getCollectableAnimation() {
		return collectableAnimation;
	}

	public void setCollectableAnimation(Animation collectableAnimation) {
		this.collectableAnimation = collectableAnimation;
	}

	public TextureRegion[] getCollectableRegion() {
		return collectableRegion;
	}

	public void setCollectableRegion(TextureRegion[] collectableRegion) {
		this.collectableRegion = collectableRegion;
	}



}
