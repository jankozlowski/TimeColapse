package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.UserData;
import com.twistedgenius.timeColapse.screens.MainGameScreen;

public class HealthUp extends Collectable{
	
	public HealthUp(World world,float X, float Y) {
		super();

		setTexture(new Texture("healthUp.png"));
		setAnimationPoint(0);
		setCollectableRegion(MainGameScreen.createRegion(getCollectableRegion(),getTexture(),72,72,10,15));
		setCollectableAnimation(new Animation(0.025f, getCollectableRegion()));
		
		setSprite(new Sprite(getCollectableRegion()[0]));
		getSprite().setSize(getCollectableRegion()[0].getRegionWidth()*ConstantValues.myScale / ConstantValues.PPM, getCollectableRegion()[0].getRegionHeight()*ConstantValues.myScale / ConstantValues.PPM);
		getSprite().setPosition(X, Y);
		setPositionX(X-0.72f*ConstantValues.myScale);
		setPositionY(Y-0.72f*ConstantValues.myScale);
		setBodyDef(new BodyDef());
		getBodyDef().type = BodyType.DynamicBody;
		getBodyDef().position.set(X-0.72f*ConstantValues.myScale, Y-0.72f*ConstantValues.myScale);
		setFixture(new FixtureDef());
		getFixture().filter.categoryBits = ConstantValues.BIT_MP;
	//	getFixture().filter.maskBits = ConstantValues.BIT_PLAYER;
		getFixture().density = 0.02f;
		CircleShape shape = new CircleShape();
		shape.setRadius(0.36f*ConstantValues.myScale);
		getFixture().shape = shape;
		setBody(world.createBody(getBodyDef()));
		getBody().createFixture(getFixture());
		shape.dispose();
		getBody().setUserData(new UserData("HealthUp", getSprite(), this));

	}

	public void update(float delta, SpriteBatch gameSpriteBatch) {
		setAnimationPoint(getAnimationPoint()+Gdx.graphics.getDeltaTime());     
		setCurrentFrame(getCollectableAnimation().getKeyFrame(getAnimationPoint(), true));
		gameSpriteBatch.draw(getCurrentFrame(),getPositionX()-0.36f*ConstantValues.myScale,getPositionY()-0.36f*ConstantValues.myScale,0.72f*ConstantValues.myScale,0.72f*ConstantValues.myScale);
}
	
	public void update(float delta) {

	}

	public void dispose() {

	}
	
	
}
