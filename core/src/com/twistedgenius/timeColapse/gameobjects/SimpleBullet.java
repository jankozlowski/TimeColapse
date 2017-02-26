package com.twistedgenius.timeColapse.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.UserData;
import com.twistedgenius.timeColapse.screens.MainGameScreen;

public class SimpleBullet extends EnemyBullet{

	
	public SimpleBullet(){
		super();
	}
	
	public SimpleBullet(float bulletX, float bulletY, World world) {
		super("simplebullet.png", bulletX, bulletY);
				setEnemyBulletSpeed(0.000498f*ConstantValues.myScale);
				setEnemyBulletFirepower(25);
				setType("SimpleBullet");
				
				CircleShape bulletShape = new CircleShape();
				bulletShape.setRadius(0.08f*ConstantValues.myScale);
				getFixture().shape = bulletShape;
				
				setBody(world.createBody(getBodyDef()));
				getBody().createFixture(getFixture());
				bulletShape.dispose();
				getBody().setBullet(true);
				getBody().setUserData(new UserData("enemyBullet",getSprite(),this));
			//	getBody().applyLinearImpulse(-MainGameScreen.getPlayer().getX()/100f*getEnemyBulletSpeed(), -MainGameScreen.getPlayer().getY()/100f*getEnemyBulletSpeed(), 0, 0, true);
				
				float width = Gdx.graphics.getWidth()/100f/2f;

				//	getBody().applyLinearImpulse((MainGameScreen.getPlayer().getX()-width+(bulletX-width))/10000, -0.0005f, 0, 0, true);
//getBody().applyForceToCenter(((MainGameScreen.getPlayer().getX()-bulletX)+1.28f)/100f, -0.02f, true);
				bulletX-=1.28f;
				bulletY-=2.56f;
				float sh = (float) ((MainGameScreen.getPlayer().getX() - bulletX)/Math.sqrt(((MainGameScreen.getPlayer().getX() - bulletX) * (MainGameScreen.getPlayer().getX() - bulletX)) + ((MainGameScreen.getPlayer().getY() - bulletY) *(MainGameScreen.getPlayer().getY() - bulletY ))));
				float sh2 = (float) ((MainGameScreen.getPlayer().getY() - bulletY)/Math.sqrt(((MainGameScreen.getPlayer().getX() - bulletX) * (MainGameScreen.getPlayer().getX() - bulletX)) + ((MainGameScreen.getPlayer().getY() - bulletY) *(MainGameScreen.getPlayer().getY() - bulletY ))));
				
				
				
				getBody().applyForceToCenter(sh/40, sh2/40, true);
				
				System.out.println(MainGameScreen.getPlayer().getX());
				System.out.println(width);
	}
///
	@Override
	public void update(float delta) {
		setBulletX(getBody().getPosition().x);
		setBulletY(getBody().getPosition().y);
		setAngel(getBody().getAngle());
		setVelocity(getBody().getLinearVelocity());
		
	/*	for(EnemyBullet bullet : bullets ){
			bullet.setBulletX(bullet.getBody().getPosition().x);
			bullet.setBulletY(bullet.getBody().getPosition().y);
			bullet.setAngel(bullet.getBody().getAngle());
			bullet.setVelocity(bullet.getBody().getLinearVelocity());
		}*/
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
