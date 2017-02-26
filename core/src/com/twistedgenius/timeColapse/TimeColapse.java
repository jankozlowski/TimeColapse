package com.twistedgenius.timeColapse;

import com.badlogic.gdx.Game;
import com.twistedgenius.timeColapse.screens.TwistedGeniusScreen;

public class TimeColapse extends Game {
	
	@Override
	public void create () {
		setScreen(new TwistedGeniusScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
