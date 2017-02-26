package com.twistedgenius.timeColapse.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.twistedgenius.timeColapse.handlers.Assets;
import com.twistedgenius.timeColapse.handlers.ConstantValues;

public class Background {

	private TiledMap map; 
	private OrthogonalTiledMapRenderer renderer;
	private float scale;
	
	public Background() {
		scale = Gdx.graphics.getWidth()/76800f;
		map = Assets.manager.get(Assets.maps,TiledMap.class);
		renderer = new OrthogonalTiledMapRenderer(map,scale);
	}

	public void update() {
	}

	public void render(OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.render();
	}

	public void dispose() {
		map.dispose();
		renderer.dispose();
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(OrthogonalTiledMapRenderer renderer) {
		this.renderer = renderer;
	}

}
