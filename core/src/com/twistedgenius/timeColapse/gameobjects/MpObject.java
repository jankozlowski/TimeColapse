package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.UserData;
import com.twistedgenius.timeColapse.screens.MainGameScreen;

public class MpObject extends Collectable {
	
	public MpObject(){
		
	}
	
	public MpObject(float X, float Y,World world) {
		super();
		
		setTexture(new Texture("mp.png"));
		setAnimationPoint(0);
	    setCollectableRegion(MainGameScreen.createRegion(getCollectableRegion(),getTexture(),48,48,4,10));
		setCollectableAnimation(new Animation(0.05f, getCollectableRegion()));
		
		setSprite(new Sprite(getCollectableRegion()[0]));
		getSprite().setSize(0.48f*ConstantValues.myScale, 0.48f*ConstantValues.myScale);
		getSprite().setPosition(X, Y);
		setPositionX(X-0.48f*ConstantValues.myScale);
		setPositionY(Y-0.48f*ConstantValues.myScale);
		setBodyDef(new BodyDef());
		getBodyDef().type = BodyType.DynamicBody;
		getBodyDef().position.set(X-0.48f*ConstantValues.myScale, Y-0.48f*ConstantValues.myScale);
		setFixture(new FixtureDef());
		getFixture().filter.categoryBits = ConstantValues.BIT_MP;
	//	getFixture().filter.maskBits = ConstantValues.BIT_PLAYER;
		getFixture().density = 0.02f;
		CircleShape shape = new CircleShape();
		shape.setRadius(0.16f*ConstantValues.myScale);
		getFixture().shape = shape;
		setBody(world.createBody(getBodyDef()));
		getBody().createFixture(getFixture());
		shape.dispose();
		getBody().setUserData(new UserData("MpObject", getSprite(), this));

	}

	public void update(float delta, SpriteBatch gameSpriteBatch) {
		setAnimationPoint(getAnimationPoint()+Gdx.graphics.getDeltaTime());           
        setCurrentFrame(getCollectableAnimation().getKeyFrame(getAnimationPoint(), true));
		gameSpriteBatch.draw(getCurrentFrame(),getPositionX()-0.24f*ConstantValues.myScale,getPositionY()-0.24f*ConstantValues.myScale,0.48f*ConstantValues.myScale,0.48f*ConstantValues.myScale);
	}

	public void dispose() {

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
