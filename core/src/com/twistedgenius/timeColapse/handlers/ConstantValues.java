package com.twistedgenius.timeColapse.handlers;

import com.badlogic.gdx.Gdx;

public class ConstantValues {

	//Pixels Per Meter
	public final static float PPM = 100;
	public static float worldSpeed = 1.5f;
    
	public final static float screenWidth = Gdx.graphics.getWidth();
    public final static float screenHeight = Gdx.graphics.getHeight();
    public final static float myScale = screenWidth/768f;
	
	public final static short BIT_PLAYER = 2;
	public final static short BIT_ENEMY = 4;
	public final static short BIT_MP = 8;
}
