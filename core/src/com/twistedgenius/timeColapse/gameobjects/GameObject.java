package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class GameObject {

	private transient Sprite Sprite;
	private transient Texture Texture;
	private transient BodyDef BodyDef;
	private transient FixtureDef Fixture;
	private transient Body Body;
	private boolean deleteBody;
	
	GameObject(){
		deleteBody = false;
	}
	
	public Sprite getSprite() {
		return Sprite;
	}
	public void setSprite(Sprite sprite) {
		Sprite = sprite;
	}
	public Texture getTexture() {
		return Texture;
	}
	public void setTexture(Texture texture) {
		Texture = texture;
	}
	public BodyDef getBodyDef() {
		return BodyDef;
	}
	public void setBodyDef(BodyDef bodyDef) {
		BodyDef = bodyDef;
	}
	public FixtureDef getFixture() {
		return Fixture;
	}
	public void setFixture(FixtureDef fixture) {
		Fixture = fixture;
	}
	public Body getBody() {
		return Body;
	}
	public void setBody(Body body) {
		Body = body;
	}
	public boolean isDeleteBody() {
		return deleteBody;
	}
	public void setDeleteBody(boolean deleteBody) {
		this.deleteBody = deleteBody;
	}

	public abstract void update(float delta);
	public abstract void dispose();
}
