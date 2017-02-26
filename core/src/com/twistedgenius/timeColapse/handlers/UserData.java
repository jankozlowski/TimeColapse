package com.twistedgenius.timeColapse.handlers;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    Sprite sprite;
    Object thisObject;

    public UserData(){}

    public UserData(String name, Sprite sprite, Object thisObject) {
        this.name = name;
        this.sprite = sprite;
        this.thisObject = thisObject;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Object getThisObject() {
		return thisObject;
	}

	public void setThisObject(Object thisObject) {
		this.thisObject = thisObject;
	}

}