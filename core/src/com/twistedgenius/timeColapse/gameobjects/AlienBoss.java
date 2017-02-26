package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.twistedgenius.timeColapse.handlers.Assets;

public class AlienBoss extends Enemy{

	
	public AlienBoss(){
		super();
	}
	
	public AlienBoss(float X, float Y, World world) {
		super(Assets.manager.get(Assets.AlienBoss, Texture.class), "polygons/bluecarrier.json","bluecarrier", 10, 1.4f, 100, 1, world);
		setType("bluecarrier");
		getBody().setTransform(X, Y,0);
		
	}
	
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
