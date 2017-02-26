package com.twistedgenius.timeColapse.screens;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import com.esotericsoftware.minlog.Log;

import com.rits.cloning.Cloner;
import com.twistedgenius.timeColapse.background.Background;
import com.twistedgenius.timeColapse.gameobjects.AlienBoss;
import com.twistedgenius.timeColapse.gameobjects.Collectable;
import com.twistedgenius.timeColapse.gameobjects.Enemy;
import com.twistedgenius.timeColapse.gameobjects.EnemyBullet;
import com.twistedgenius.timeColapse.gameobjects.Foker;
import com.twistedgenius.timeColapse.gameobjects.GameObject;
import com.twistedgenius.timeColapse.gameobjects.HealthUp;
import com.twistedgenius.timeColapse.gameobjects.MpObject;
import com.twistedgenius.timeColapse.gameobjects.Player;
import com.twistedgenius.timeColapse.gameobjects.PlayerBullet;
import com.twistedgenius.timeColapse.gameobjects.PowerUp;
import com.twistedgenius.timeColapse.gameobjects.SimpleBullet;
import com.twistedgenius.timeColapse.handlers.Assets;
import com.twistedgenius.timeColapse.handlers.ConstantValues;
import com.twistedgenius.timeColapse.handlers.MyContactListener;
import com.twistedgenius.timeColapse.handlers.UserData;
import com.twistedgenius.timeColapse.sounds.Sounds;

//
public class MainGameScreen implements Screen, InputProcessor {

	private SpriteBatch gameSpriteBatch;
	private SpriteBatch mpSpriteBatch;
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera camera;
	
	private static Player player;
	private Background background;
	public static Sounds sounds;
	private ArrayList<Enemy> enemies;
	private ArrayList<Collectable> powerCollection;
	public static ArrayList<EnemyBullet> enemyBullets;
	public static ArrayList<Animation> explosionAnimations;
	public static ArrayList<Float> explosionAnimationTimer;
	public static ArrayList<Float> destroyedX;
	public static ArrayList<Float> destroyedY;
	private Foker enemy;
	private Array<Body> bodies = new Array<Body>();

	
	private BodyDef topRefBodyDef;
    private Body topRefBody;
    private float topRefPositionY;
    private BodyDef bottomRefBodyDef;
    private Body bottomRefBody;
    private float bottomRefPositionY;
    private float slowSpeed = 1f;
    private float stopSpeed = 1f;
    boolean pauseGame=false;
    
    private float fadeInOutSpeed = 0.5f;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    float myScale = screenWidth/768f;
    
    private int gameplay =0;

    float number =0.00f;
    
	FPSLogger logger = new FPSLogger();
	Texture spaceShipHpFull = Assets.manager.get(Assets.spaceShipHpFull, Texture.class);
	Texture spaceShipHpEmpty = Assets.manager.get(Assets.spaceShipHpEmpty, Texture.class);
    Texture hpFullTexture = Assets.manager.get(Assets.hpFullTexture, Texture.class);
    Texture hpEmptyTexture = Assets.manager.get(Assets.hpEmptyTexture, Texture.class);
    Texture foker = Assets.manager.get(Assets.foker, Texture.class);
    Texture speedUp = Assets.manager.get(Assets.speedUp, Texture.class);
    Texture immortality = Assets.manager.get(Assets.immortality, Texture.class);
    Texture slowDown = Assets.manager.get(Assets.slowDown, Texture.class);
    Texture stopDown = Assets.manager.get(Assets.stopDown, Texture.class);
    Texture trueForm = Assets.manager.get(Assets.trueForm, Texture.class);
    Texture fokerSelect = Assets.manager.get(Assets.fokerSelect, Texture.class);
    Texture planeSelect = Assets.manager.get(Assets.planeSelect, Texture.class);
    Texture mp1Texture = Assets.manager.get(Assets.mp1Texture, Texture.class);
    Texture mp2Texture = Assets.manager.get(Assets.mp2Texture, Texture.class);
    Texture noData = Assets.manager.get(Assets.noData, Texture.class);
    Texture eve = Assets.manager.get(Assets.eve, Texture.class);
    Texture pfalz = Assets.manager.get(Assets.pfalz, Texture.class);
    Texture tableBackground = Assets.manager.get(Assets.tableBackground, Texture.class);
    Texture explosion = Assets.manager.get(Assets.explosion, Texture.class);
    Texture powerFull = Assets.manager.get(Assets.powerFull, Texture.class);
    Texture powerEmpty = Assets.manager.get(Assets.powerEmpty, Texture.class);
    Texture mpgrad = Assets.manager.get(Assets.mpgrad, Texture.class);
    
    
    TextureRegion[] eveRegion;
    public static TextureRegion[] explosionRegion;
	
    
    ShaderProgram shader;
    long slowTime;
    long stopTime;
    long speedTime;
    long imortalityTime;
    
    long deltaCount=0;

    boolean slowTimeActive=false;
    boolean stopTimeActive=false;
    boolean speedTimeActive=false;
    boolean imortalityTimeActive=false;
    
    
    
    boolean visibleTable = false;
    boolean changing = false;
    float pause = 1;
    Table table = new Table();
    Stage stage = new Stage();
    Skin skin = new Skin();
    
    
    
    
    private ArrayList<Player> playerPositionKryo;
    private ArrayList<ArrayList<Enemy>> enemyPositionKryo;
    private ArrayList<ArrayList<EnemyBullet>> enemyBulletsKryo;
    private ArrayList<ArrayList<MpObject>> mpKryo;

	int bulletcount=0;
	int someNumber = 0;
    boolean reverseTimeActivation = false;
    
    Animation eveAnimation;
    float stateTime=0;  
    TextureRegion currentFrame =new TextureRegion();  
    
   Vector3 cameraPosition = new Vector3();
    int timesfor=0;
    
   Kryo kryo = new Kryo();

  //  Json json = new Json();
    
    @Override
	public void show() {
    	

    	FieldSerializer<?> playerSerializer = new FieldSerializer<Player>(kryo, Player.class);
    	playerSerializer.setCopyTransient(false);
		FieldSerializer<?> playerBulletSerializer = new FieldSerializer<PlayerBullet>(kryo, PlayerBullet.class);
		playerBulletSerializer.setCopyTransient(false);
		FieldSerializer<?> EnemySerializer = new FieldSerializer<Enemy>(kryo, Enemy.class);
		EnemySerializer.setCopyTransient(false);
		FieldSerializer<?> EnemyBulletSerializer = new FieldSerializer<EnemyBullet>(kryo, EnemyBullet.class);
		EnemyBulletSerializer.setCopyTransient(false);
		FieldSerializer<?> fokerSerializer = new FieldSerializer<Foker>(kryo, Foker.class);
		fokerSerializer.setCopyTransient(false);
		FieldSerializer<?> SimpleBulletSerializer = new FieldSerializer<SimpleBullet>(kryo, SimpleBullet.class);
		SimpleBulletSerializer.setCopyTransient(false);
		FieldSerializer<?> MpSerializer = new FieldSerializer<MpObject>(kryo, MpObject.class);
		MpSerializer.setCopyTransient(false);
		
		
		kryo.register(Player.class, playerSerializer);
		kryo.register(PlayerBullet.class, playerBulletSerializer);
		kryo.register(Enemy.class, EnemySerializer);
		kryo.register(EnemyBullet.class, EnemyBulletSerializer);
		kryo.register(Foker.class, fokerSerializer);
		kryo.register(SimpleBullet.class, SimpleBulletSerializer);
		kryo.register(MpObject.class, MpSerializer);
    	
    	
//		FieldSerializer<?> serilizer = (FieldSerializer<?>) kryo.getDefaultSerializer(FieldSerializer.class);
//		serilizer.setCopyTransient(false);
		
    	playerPositionKryo = new ArrayList<Player>();
    	enemyBullets = new ArrayList<EnemyBullet>();
    	enemyPositionKryo = new ArrayList<ArrayList<Enemy>>();
    	enemyBulletsKryo = new ArrayList<ArrayList<EnemyBullet>>();
    	mpKryo = new ArrayList<ArrayList<MpObject>>();
    	
    	ShaderProgram.pedantic=false;
		shader = new ShaderProgram(Gdx.files.internal("shader/mpshader.vsh"), Gdx.files.internal("shader/mpshader.fsh"));
		
		mpSpriteBatch = new SpriteBatch();
		
		mpSpriteBatch.setShader(shader);
		
		
		gameSpriteBatch = new SpriteBatch();
		world = new World(new Vector2(0,0),true);
		world.setContactListener(new MyContactListener());
		b2dr = new Box2DDebugRenderer();
		camera = new OrthographicCamera(screenWidth/ConstantValues.PPM, screenHeight/ConstantValues.PPM);
		
		background = new Background();
		sounds = new Sounds();
		setPlayer(new Player(world));
	
		explosionAnimations = new ArrayList<Animation>();
		explosionAnimationTimer = new ArrayList<Float>();
		destroyedX = new ArrayList<Float>();
		destroyedY = new ArrayList<Float>();
		
		powerCollection = new ArrayList<Collectable>();
		
		PowerUp power1 = new PowerUp(world,2,10);
		PowerUp power2 = new PowerUp(world,2,15);
		PowerUp power3 = new PowerUp(world,2,20);
		PowerUp power4 = new PowerUp(world,2,25);
		PowerUp power5 = new PowerUp(world,2,30);
		HealthUp healthUp = new HealthUp(world,2,35);
	
		powerCollection.add(power1);
		powerCollection.add(power2);
		powerCollection.add(power3);
		powerCollection.add(power4);
		powerCollection.add(power5);
		powerCollection.add(healthUp);
		
    	//TextButton test = new TextButton("test");
    	
    	Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
    	pixmap.setColor(new Color(0,0,0,0.6f));
    	pixmap.fill();
    	
    	Image[] powerImages = new Image[24];
    	
    	for(int imageNum=0; imageNum<24; imageNum++){
    		Image powerImage = null;
    		
    		if(imageNum!=0 && imageNum!=1 && imageNum!=2 && imageNum!=3 && imageNum!=4 && imageNum!=5 ){
    		 powerImage = new Image(noData);
    		}
    		else if(imageNum==0){
    			powerImage= new Image(speedUp);
    		}
    		else if(imageNum==1){
    			powerImage= new Image(slowDown);
    		}
    		else if(imageNum==2){
    			powerImage= new Image(stopDown);
    		}
    		else if(imageNum==3){
    			powerImage= new Image(immortality);
    		}
    		else if(imageNum==4){
    			powerImage= new Image(trueForm);
    		}
    		else if(imageNum==5){
    			powerImage= new Image(fokerSelect);
    		}

    		powerImages[imageNum] = powerImage;
    	}
    
    	table.setBounds(0, 0,(int) (screenWidth-0.05*screenWidth), (int)(screenHeight-0.2*screenHeight));
    	table.setPosition((int)(0.05*screenWidth/2), (int)(0.2*screenHeight/2)+screenHeight*0.046875f);
    	
    
	     
	     imageListeners(powerImages);
	     
    
     table.setTouchable(Touchable.childrenOnly);
    
     table.add(powerImages[0]).pad(2).padTop(screenHeight*0.135f).padBottom(screenHeight*0.105f).width(128*myScale).height(128*myScale);
    	table.add(powerImages[1]).pad(2).padTop(screenHeight*0.135f).padBottom(screenHeight*0.105f).width(128*myScale).height(128*myScale);
    	table.add(powerImages[2]).pad(2).padTop(screenHeight*0.135f).padBottom(screenHeight*0.105f).width(128*myScale).height(128*myScale);
    	table.add(powerImages[3]).pad(2).padTop(screenHeight*0.135f).padBottom(screenHeight*0.105f).width(128*myScale).height(128*myScale);
    	table.add(powerImages[4]).pad(2).padTop(screenHeight*0.135f).padBottom(screenHeight*0.105f).width(128*myScale).height(128*myScale);
    	table.row();
    	table.add(powerImages[5]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[6]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[7]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[8]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[9]).pad(0).width(128*myScale).height(120*myScale);
    	table.row();
    	
    	Table row3 = new Table();
    	row3.add(powerImages[11]).pad(0).width(128*myScale).height(120*myScale);
    	row3.add(powerImages[12]).pad(0).width(128*myScale).height(120*myScale);
    	row3.add(powerImages[13]).pad(0).width(128*myScale).height(120*myScale);
    	row3.add(powerImages[14]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(row3).center().colspan(5);
   // 	powerImages[11].scaleBy(0.4f);
   // 	powerImages[12].scaleBy(0.4f);
   // 	powerImages[13].scaleBy(0.4f);
   // 	powerImages[14].scaleBy(0.4f);
  //  	HorizontalGroup vg = new HorizontalGroup();
   // 	vg.addActor(powerImages[11]);
   // 	vg.addActor(powerImages[12]);
   // 	vg.addActor(powerImages[13]);
   // 	vg.addActor(powerImages[14]);
 //   	table.add(vg).center().colspan(5).height(120*myScale);

    	
    	
    	table.row();
    	table.add(powerImages[15]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[16]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[17]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[18]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(powerImages[19]).pad(0).width(128*myScale).height(120*myScale);
    	table.row();
    	
    	
    	Table row5 = new Table();
    	row5.add(powerImages[20]).pad(0).width(128*myScale).height(120*myScale);
    	row5.add(powerImages[21]).pad(0).width(128*myScale).height(120*myScale);
    	row5.add(powerImages[22]).pad(0).width(128*myScale).height(120*myScale);
    	row5.add(powerImages[23]).pad(0).width(128*myScale).height(120*myScale);
    	table.add(row5).center().colspan(5);
    	
    	
    	//HorizontalGroup hg = new HorizontalGroup();
    //	hg.addActor(powerImages[20]);
    //	hg.addActor(powerImages[21]);
    //	hg.addActor(powerImages[22]);
   // 	hg.addActor(powerImages[23]);
   // 	table.add(hg).center().colspan(5).height(120*myScale);
    	//table.add(powerImages[20]).pad(3);
    	//table.add(powerImages[21]).pad(3);
    	//table.add(powerImages[22]).pad(3);
    	//table.add(powerImages[23]).pad(3);
    	
    	
    //	table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
    	table.setBackground(new TextureRegionDrawable(new TextureRegion(tableBackground)));
    //	table.pad(screenWidth*0.105f,screenHeight*0.03125f,0,screenHeight*0.03125f);
    	table.pad(0,screenHeight*0.03125f,0,screenHeight*0.03125f);
    //	table.setDebug(true);
    	table.top();
    	table.addAction(Actions.fadeOut(0.2f));
    	stage.addActor(table);
    	topRefPositionY=screenHeight/ConstantValues.PPM;
		bottomRefPositionY=0f;
		
		
		
		topRefBodyDef = new BodyDef();
		topRefBodyDef.type= BodyType.StaticBody;
		topRefBodyDef.position.set(screenWidth/ConstantValues.PPM, screenHeight/ConstantValues.PPM);
		topRefBody = world.createBody(topRefBodyDef);
		
		FixtureDef topFixture = new FixtureDef();
		CircleShape topShape = new CircleShape();
		topShape .setRadius(0.5f);
		topFixture.shape = topShape;
		topRefBody.createFixture(topFixture);
		
		bottomRefBodyDef = new BodyDef();
		bottomRefBodyDef.type= BodyType.StaticBody;
		bottomRefBodyDef.position.set(0, 0);
		bottomRefBody = world.createBody(bottomRefBodyDef);
		
		FixtureDef bottomFixture = new FixtureDef();
		CircleShape bottomShape = new CircleShape();
		bottomShape.setRadius(0.5f);
		bottomFixture.shape =bottomShape;
		bottomRefBody.createFixture(bottomFixture);
	
		enemies = new ArrayList<Enemy>();

		InputProcessor inputProcessorOne = this;
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		
		inputMultiplexer.addProcessor(inputProcessorOne);
		inputMultiplexer.addProcessor(stage);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
	slowTime = System.currentTimeMillis();
	stopTime = System.currentTimeMillis();
	speedTime = System.currentTimeMillis();
	

    explosionRegion = createRegion(explosionRegion, explosion, 128, 128, 6, 8);
    eveRegion = createRegion(eveRegion, eve, 128, 128, 10, 10);
	
		eveAnimation = new Animation(0.05f, eveRegion);
		
		

	}
    
    public static TextureRegion[] createRegion(TextureRegion[] outputRegion, Texture texture, int sizeX, int sizeY, int row, int column){
    	
    	TextureRegion[][] tmp = TextureRegion.split(texture, sizeX, sizeY);
    	outputRegion = new TextureRegion[row*column];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
            	outputRegion[index++] = tmp[i][j];
            }
        }
		return outputRegion;
    	
    }
    
    public void imageListeners(Image imageListeners[]){
    	imageListeners[0].addListener(new ClickListener(){
        	 @Override
        	public void clicked(InputEvent event, float x, float y) {
        		 super.clicked(event, x, y);
        		 
        		 if(getPlayer().getMp()>=0.2f){
        			 getPlayer().setMp(getPlayer().getMp()-0.2f);
	        		 getPlayer().setMoveSpeed(getPlayer().getMoveSpeed()+270f);
	        		 speedTime = System.currentTimeMillis();
	        		 speedTimeActive=true;
	        		 table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
	       		 }
        	 }
         });
    	imageListeners[1].addListener(new ClickListener(){
        	 @Override
        	public void clicked(InputEvent event, float x, float y) {
        		
        		 super.clicked(event, x, y);
        		 if(getPlayer().getMp()>=0.4f){
        			 getPlayer().setMp(getPlayer().getMp()-0.4f);
	        		 slowSpeed=0.3f;
	      			 slowTime = System.currentTimeMillis();
	      			 slowTimeActive=true;
	        		 table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
	        	}
        	}
         });
    	imageListeners[2].addListener(new ClickListener(){
        	 @Override
        	public void clicked(InputEvent event, float x, float y) {
        		
        		 super.clicked(event, x, y);
        		 if(getPlayer().getMp()>=0.6f){
        			getPlayer().setMp(getPlayer().getMp()-0.6f);
	        		stopSpeed=0.001f;
	     			stopTime = System.currentTimeMillis();
	     			stopTimeActive=true;
	     			getPlayer().setMoveSpeed(getPlayer().getMoveSpeed()+180000000);
	     		//	player.setRateOfFire(player.getRateOfFire()/1000);
	     			for(PlayerBullet bull: getPlayer().getBullets()){
	     				bull.getBody().applyLinearImpulse(0, 1.98f, 0, 0, true);
	     			}
	     			getPlayer().setBulletSpeed(getPlayer().getBulletSpeed()*1000);
	     			table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
        		 }
        	}
         });
    	imageListeners[3].addListener(new ClickListener(){
        	 @Override
        	public void clicked(InputEvent event, float x, float y) {
        		
        		 super.clicked(event, x, y);
        		 if(getPlayer().getMp()>=0.8f){
         			getPlayer().setMp(getPlayer().getMp()-0.8f);
	        		imortalityTime = System.currentTimeMillis();
	      			imortalityTimeActive=true;
	      			Filter filterer = new Filter();
	      			filterer.categoryBits = ConstantValues.BIT_PLAYER;
	      			filterer.maskBits = ConstantValues.BIT_MP;
	      			
	      			for(int num=0; num<getPlayer().getPlayerBody().getFixtureList().size ;num++){
	      				getPlayer().getPlayerBody().getFixtureList().get(num).setFilterData(filterer);
	      			}
	      			table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
	        	 }
        	}
         });
    	imageListeners[4].addListener(new ClickListener(){
        	 @Override
        	public void clicked(InputEvent event, float x, float y) {
        		
        		 super.clicked(event, x, y);
        		 if(getPlayer().getMp()>=1f){
          			getPlayer().setMp(getPlayer().getMp()-1f);
        			table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
        		 }
        	}
         });
    	imageListeners[5].addListener(new ClickListener(){
       	 @Override
       	public void clicked(InputEvent event, float x, float y) {
       		
       		 super.clicked(event, x, y);
       		
       		 getPlayer().getPlayerSprite().setTexture(foker);
       		 table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
       		
       	}
        });
    	imageListeners[6].addListener(new ClickListener(){
       	 @Override
       	public void clicked(InputEvent event, float x, float y) {
       		
       		 super.clicked(event, x, y);
       		 getPlayer().getPlayerSprite().setTexture(pfalz);
       		 table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
       		
       	}
        });
    }
	
	public void vKeyFormation(float xPosition,float yPosition, int count, int speed){
		for(int plane=0; plane<count;plane++){
			
			if(plane==0){
				enemy = new Foker(screenWidth/(2f*ConstantValues.PPM)+0.64f*myScale,yPosition+plane*myScale,world);	
				addEnemyToList(enemy, enemies,speed);
			}
			else{
				enemy = new Foker(screenWidth/(2f*ConstantValues.PPM)+(0.64f*myScale)+plane*myScale,yPosition+plane*myScale,world);
				addEnemyToList(enemy, enemies,speed);
				enemy = new Foker(screenWidth/(2f*ConstantValues.PPM)+(0.64f*myScale)-plane*myScale,yPosition+plane*myScale,world);
				addEnemyToList(enemy, enemies,speed);
			}	
		}		
	}

	public void addEnemyToList(Enemy enemy, ArrayList<Enemy> enemiesList, int speed){
		enemy.getBody().setTransform(enemy.getBody().getPosition().x, enemy.getBody().getPosition().y, 3.14f);
		enemy.getBody().applyForceToCenter(0, -speed, true);
//		enemy.getBody().applyTorque(20, true);
//	enemy.getBody().applyAngularImpulse(30, true);
		enemiesList.add(enemy);
		
		
	}
	
	public  <T> void removeBodySafely(ArrayList<T> inputList){
		ArrayList<T> temporaryList = new ArrayList<T>(inputList);
		
		for( T object : temporaryList){
			GameObject gameObject = (GameObject) object;
			
			deletePlayerBulletsConditions(gameObject);
			deleteMpAndBulletsConditions(gameObject);
			deleteEnemyConditions(gameObject);
			
			if(gameObject.isDeleteBody()){
				if(gameObject instanceof Enemy && reverseTimeActivation==false){
					powerCollection.add(new MpObject(gameObject.getBody().getPosition().x,gameObject.getBody().getPosition().y,world));
				}
				inputList.remove(gameObject);
				world.destroyBody(gameObject.getBody());
			}
		}
		temporaryList.clear();
	}
	
	public void deletePlayerBulletsConditions(GameObject gameObject){
		if(gameObject instanceof PlayerBullet){
			if(gameObject.getBody().getPosition().y>topRefBody.getPosition().y){
				gameObject.setDeleteBody(true);
			}
		}
	}
	
	public void deleteEnemyConditions(GameObject gameObject){
		if(gameObject instanceof Enemy){
		//	removeBodySafely(((Enemy) gameObject).getBullets()); //to delete
			
			if(reverseTimeActivation==false){
				gameObject.update(Gdx.graphics.getDeltaTime()*slowSpeed*pause*stopSpeed);
			}
				
			if(gameObject.getBody().getPosition().y<topRefBody.getPosition().y-(screenHeight/ConstantValues.PPM)){
				gameObject.setDeleteBody(true);

			}
			
		}
	}
	
	public void deleteMpAndBulletsConditions(GameObject gameObject){
		if(gameObject instanceof EnemyBullet || gameObject instanceof MpObject ){//|| gameObject instanceof PowerUp
			gameObject.update(Gdx.graphics.getDeltaTime()*slowSpeed*pause*stopTime);
			
			if(gameObject.getBody().getPosition().y>topRefBody.getPosition().y+1&&gameObject.getBody().getPosition().y<900){
				gameObject.setDeleteBody(true);
			}
			if(gameObject.getBody().getPosition().y<bottomRefBody.getPosition().y-1){
				gameObject.setDeleteBody(true);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> void limitArray(ArrayList<T> array, Object copyObject){
		if(array.size()>1000){
			array.remove(0);
			array.add((T) kryo.copy(copyObject));
		}
		else{
			array.add((T) kryo.copy(copyObject));
		}
	}
	
	
	
	
	@Override
	public void render(float delta) {
		
		if(!sounds.getBackgroundMusic().isPlaying()){
			sounds.getBackgroundMusic().play();
		}
		deltaCount++;
		bulletcount=0;
		
	//System.out.println(playerPositionKryo.size());
	
		if(reverseTimeActivation==false && deltaCount%2==0){//
		//	limitArray(playerPositionKryo,player);
		//	limitArray(enemyPositionKryo,enemies);
		//	limitArray(enemyBulletsKryo,enemyBullets);
		//	limitArray(mpKryo,powerCollection);
	}
		
		if(slowTime+10000 < System.currentTimeMillis()&&slowTimeActive==true){
			slowSpeed=1;
			slowTimeActive=false;
		}
		if(speedTime+10000 < System.currentTimeMillis()&&speedTimeActive==true){
			getPlayer().setMoveSpeed(getPlayer().getMoveSpeed()-270);
			speedTimeActive=false;
		}
		if(stopTime+10000 < System.currentTimeMillis()&&stopTimeActive==true){
			stopSpeed=1;
			getPlayer().setMoveSpeed(getPlayer().getMoveSpeed()-180000000);
		//	player.setRateOfFire(player.getRateOfFire()*1000);
		//	for(PlayerBullet bull: player.getBullets()){
		//		bull.getBody().applyLinearImpulse(0, -1.98f, 0, 0, true);
		//	}
			getPlayer().setBulletSpeed(getPlayer().getBulletSpeed()/1000);
			stopTimeActive=false;
		}
		
		if(imortalityTime+10000 < System.currentTimeMillis()&&imortalityTimeActive==true){

  			Filter filter = new Filter();
  			filter.maskBits = ConstantValues.BIT_ENEMY | ConstantValues.BIT_MP;
  			filter.categoryBits = ConstantValues.BIT_PLAYER;
  			for(int num=0; num<getPlayer().getPlayerBody().getFixtureList().size ;num++){
  				getPlayer().getPlayerBody().getFixtureList().get(num).setFilterData(filter);
  			}
 			 imortalityTimeActive=false;
		}

			delta = Gdx.graphics.getDeltaTime() * slowSpeed*stopSpeed*pause;

		
		
		if(!getPlayer().isGameOver()){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
			shader.begin();



shader.setUniformi("u_texture1", 1);
Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE1);
mpgrad.bind();



Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
mp1Texture.bind();

shader.setUniformf("u_resolution", (int)(128*myScale),(int)(myScale*128));
			shader.setUniformf("u_time", number);
			shader.setUniformf("u_mp", getPlayer().getMp());
			shader.setUniformf("u_scale", 1/myScale);
			shader.setUniformf("u_resolution", (int)(128*myScale),(int)(myScale*128));
		

			shader.end();
			
			
			topRefPositionY+=ConstantValues.worldSpeed*delta;
			topRefBody.setTransform(screenWidth/ConstantValues.PPM, topRefPositionY, 0);
			bottomRefPositionY+=ConstantValues.worldSpeed*delta;
			bottomRefBody.setTransform(0, bottomRefPositionY, 0);	
			
			if(topRefPositionY>20 && gameplay==0){
				AlienBoss myAlien = new AlienBoss(8,25,world);	
				addEnemyToList(myAlien, enemies,1);
				gameplay++;
			}
			
			if(topRefPositionY>40 && gameplay==0){
				vKeyFormation(0,12,4,150);
			
				gameplay++;
			}
			else if(topRefPositionY>50 && gameplay==1){
				vKeyFormation(0,55,4,1500);
				gameplay++;
			}//
				else if(topRefPositionY>60 && gameplay==2){
				vKeyFormation(0,65,4,1500);
				gameplay++;
			}
			else if(topRefPositionY>55 && gameplay==3){
				vKeyFormation(0,55,3,1500);
				gameplay++;
			}
			else if(topRefPositionY>60 && gameplay==4){
				vKeyFormation(0,75,3,1500);
				gameplay++;
			}
			else if(topRefPositionY>70 && gameplay==5){
				vKeyFormation(0,75,3,1500);
				gameplay=9;
			}

			else if(topRefPositionY>80 && gameplay==9){
				vKeyFormation(0,85,4,110);
				gameplay++;
			}
			else if(topRefPositionY>85 && gameplay==10){
				vKeyFormation(0,90,4,110);
				gameplay++;
			}
			else if(topRefPositionY>90 && gameplay==11){
				vKeyFormation(0,95,4,110);
				gameplay++;
			}
			else if(topRefPositionY>95 && gameplay==12){
				vKeyFormation(0,100,4,110);
				gameplay++;
			}
			else if(topRefPositionY>100 && gameplay==13){
				vKeyFormation(0,105,4,110);
				gameplay++;
			}
			
			else if(topRefPositionY>105 && gameplay==14){
				vKeyFormation(0,110,4,110);
				gameplay++;
			}
			else if(topRefPositionY>120 && gameplay==15){
				vKeyFormation(0,122,4,110);
				gameplay++;
			}
			else if(topRefPositionY>122 && gameplay==16){
				vKeyFormation(0,124,4,110);
				gameplay++;
			}
			else if(topRefPositionY>124 && gameplay==17){
				vKeyFormation(0,126,4,110);
				gameplay++;
			}
			else if(topRefPositionY>126 && gameplay==18){
				vKeyFormation(0,128,4,110);
				gameplay++;
			}
			
			background.render(camera);
			
			if (pauseGame==true && timesfor<4) {
				camera.translate(0, bottomRefPositionY/2f, 0);
				timesfor++;
				if(timesfor==3){
					pauseGame=false;
					timesfor=0;
				}
			} else {
				camera.translate(0, ConstantValues.worldSpeed * delta, 0);
			}
			camera.update();

			world.step(delta, 6, 2);
		
			removeBodySafely(getPlayer().getBullets());
			removeBodySafely(enemies);
			removeBodySafely(enemyBullets);
			removeBodySafely(powerCollection);
	
			
			gameSpriteBatch.setProjectionMatrix(camera.combined);
			gameSpriteBatch.begin();
			
			if(reverseTimeActivation==false){
				
				getPlayer().update(Gdx.graphics.getDeltaTime() * slowSpeed*pause, topRefBody.getPosition().y,gameSpriteBatch);
				
		}
			
			else{
				if(getPlayer().getMp()>0){
					//reverseTimeFunction();
					getPlayer().setMp(getPlayer().getMp()-0.05f);
				}
				else{
					//reverseTime();
				}
			
			}
		
			for(Collectable powerObject: powerCollection){
				powerObject.update(delta,gameSpriteBatch);
			}

			
			int cycle = 0;
		//	System.out.println(explosionAnimationTimer.size());
			for(int size=0; size<explosionAnimationTimer.size(); size++){
				Float number = explosionAnimationTimer.get(size);
				number+=Gdx.graphics.getDeltaTime();
				explosionAnimationTimer.set(size, number);
				
			}
			
			for(Animation anime: explosionAnimations){
				gameSpriteBatch.draw(anime.getKeyFrame(explosionAnimationTimer.get(cycle), false),destroyedX.get(cycle)-1.28f,destroyedY.get(cycle++)-1.28f,1.28f,1.28f);
			}
			
			
			world.getBodies(bodies);
			
			for(Body body:bodies){
				UserData bodyUserData;
				if(body.getUserData() != null){
					bodyUserData = (UserData) body.getUserData();
					Sprite sprite =  bodyUserData.getSprite();
					
					if(bodyUserData.getName().equals("player")){
						sprite.setPosition(body.getPosition().x  , body.getPosition().y );
					}
					else if(bodyUserData.getName().equals("PowerUp")||bodyUserData.getName().equals("HealthUp")){
					//	sprite.setPosition(body.getPosition().x-0.32f  , body.getPosition().y-0.32f );
					}
					else if(bodyUserData.getName().equals("MpObject")){
				//		sprite.setPosition(body.getPosition().x-0.16f  , body.getPosition().y-0.16f );
						
					}
					else{
						sprite.setPosition(body.getPosition().x  , body.getPosition().y );
						sprite.setOrigin(0,0);
						sprite.setRotation(body.getAngle()*57.2957f);
					}
					
					
					
				//	sprite.setOrigin(0,0);
					//sprite.setRotation(body.getAngle()*57.2957f);
					if(!bodyUserData.getName().equals("MpObject")&&!bodyUserData.getName().equals("PowerUp")&&!bodyUserData.getName().equals("HealthUp")){
					sprite.draw(gameSpriteBatch);
					//	Texture spriteTexture = sprite.getTexture();
					//	gameSpriteBatch.draw(sprite, sprite.getX(), sprite.getY(),spriteTexture.getWidth()/100,spriteTexture.getHeight()/100); 
								//0, 0, spriteTexture.getWidth()/100, spriteTexture.getHeight()/100, ConstantValues.myScale, ConstantValues.myScale, 0,0,0,(int)spriteTexture.getWidth()/100,(int)spriteTexture.getHeight()/100,false,false);
					//
			//			System.out.println("x" + sprite.getX());
			//			System.out.println("y" + sprite.getY());
						
						
						
					//	gameSpriteBatch.draw(loadingRing2,Gdx.graphics.getWidth()/2-loadingRing2.getWidth()/2,Gdx.graphics.getHeight()/2-loadingRing2.getHeight()/2,
					//			loadingRing2.getWidth()/2,loadingRing2.getHeight()/2,loadingRing2.getWidth(),loadingRing2.getHeight(),Gdx.graphics.getWidth()*0.665f/512,Gdx.graphics.getWidth()*0.665f/512,rotation1,0,0,loadingRing2.getWidth(),loadingRing2.getHeight(),false,false);
					}
				}
			}	
			
			float healthPercentage = getPlayer().getHitPoints()/100;
		
			
			gameSpriteBatch.draw(spaceShipHpEmpty, (screenWidth/ConstantValues.PPM)-0.96f*myScale,bottomRefPositionY+0.32f*myScale,spaceShipHpEmpty.getWidth()*myScale/ConstantValues.PPM,spaceShipHpEmpty.getHeight()*myScale/ConstantValues.PPM);
			gameSpriteBatch.draw(spaceShipHpFull, (screenWidth/ConstantValues.PPM)-0.32f*myScale,bottomRefPositionY+0.32f*myScale,
					0.0f, 0.0f,healthPercentage* spaceShipHpFull.getWidth()*myScale/ConstantValues.PPM, spaceShipHpFull.getHeight()*myScale/ConstantValues.PPM, 
					1, 1, 90, 0, 0, 
					(int)(healthPercentage*spaceShipHpFull.getWidth()*myScale), (int) (spaceShipHpFull.getHeight()*myScale), false, true);
			gameSpriteBatch.draw(mp2Texture,(screenWidth/ConstantValues.PPM)-(mp1Texture.getWidth()*myScale/ConstantValues.PPM),bottomRefPositionY,1.28f*myScale,1.28f*myScale );
			
			
			
			stateTime += Gdx.graphics.getDeltaTime();           
	        currentFrame = eveAnimation.getKeyFrame(stateTime, true);
			gameSpriteBatch.draw(currentFrame,0,bottomRefPositionY,1.28f*myScale,1.28f*myScale);
			
			getPlayer().getEngine().draw(gameSpriteBatch, Gdx.graphics.getDeltaTime());
			
			
			powerUpmeter(speedTimeActive,speedTime,speedUp,1);
			powerUpmeter(slowTimeActive,slowTime,slowDown,2);
			powerUpmeter(stopTimeActive,stopTime,stopDown,3);
			powerUpmeter(imortalityTimeActive,imortalityTime,immortality,4);		
			
			gameSpriteBatch.end();
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
			mpSpriteBatch.setProjectionMatrix(camera.combined);
			mpSpriteBatch.begin();
			
			mpSpriteBatch.draw(mp1Texture,(screenWidth/ConstantValues.PPM)-1.28f*myScale,bottomRefPositionY,
					0.0f, 0.0f,1.28f*myScale,1.28f*myScale, 
					1, 1, 0, 0, 0, 
					(int)(128*myScale), (int)(128*myScale), true, false);
	
			mpSpriteBatch.end();
			number+=0.025f;
			if(number==1.00f){
				number=0.00f;
			}
			if(getPlayer().getMp()>=1.0f){
				getPlayer().setMp(1.001f);
			}
		
		//	b2dr.render(world, camera.combined);
//logger.log();
		if(topRefPositionY>150){
			((Game)Gdx.app.getApplicationListener()).setScreen(new MainGameScreen());
			
		}

		}
		
		else{
			dispose();
			((Game)Gdx.app.getApplicationListener()).setScreen(new MainGameScreen());
			
		}
	}
	
	public void powerUpmeter(boolean powerupBoolean, long powerTime, Texture powerTexture,int position){
		if(powerupBoolean){
			float part = (powerTime+10000-System.currentTimeMillis())/10000f;
			
			gameSpriteBatch.draw(powerTexture,0,topRefPositionY-0.40f*position,0.40f,0.40f);
			gameSpriteBatch.draw(powerEmpty,0.40f,topRefPositionY-(0.38f*position),3.0f,0.24f);
			gameSpriteBatch.draw(powerFull, 0.40f,topRefPositionY-(0.38f*position),
					0, 0, part * 3.0f, 0.24f, 
					1, 1, 0, 0, 0, 
					(int)(part * 384), 24, false, false);
		}
	}

	private void reverseTimeFunction() {
		try{
			//Kryo
			//Player
			
				Player playerCopy = playerPositionKryo.get(playerPositionKryo.size()-1);
				
				for(PlayerBullet bullet : getPlayer().getBullets()){
					bullet.setDeleteBody(true);
				}
				for(PlayerBullet bullet: playerCopy.getBullets()){
					getPlayer().getBullets().add(new PlayerBullet(world,bullet.getBulletX(),bullet.getBulletY(),bullet.getBulletSpeed()));
				}
				
				getPlayer().setMp(playerCopy.getMp());
				getPlayer().setHitPoints(playerCopy.getHitPoints());
				getPlayer().setBulletSpeed(playerCopy.getBulletSpeed());
				getPlayer().setFirePowerLevel(playerCopy.getFirePowerLevel());
				getPlayer().setForceX(playerCopy.getForceX());
				getPlayer().setForceY(playerCopy.getForceY());
				getPlayer().setArmor(playerCopy.getArmor());
				getPlayer().setCategoryBits((playerCopy.getCategoryBits()));
				getPlayer().setChange(playerCopy.isChange());
				getPlayer().setGameOver(playerCopy.isGameOver());
				getPlayer().setLastX(playerCopy.getLastX());
				getPlayer().setLastY(playerCopy.getLastY());
				getPlayer().setLinearVelocity((playerCopy.getLinearVelocity()));
				getPlayer().setMaskBits(playerCopy.getMaskBits());
				getPlayer().setMoveSpeed(playerCopy.getMoveSpeed());
				getPlayer().setOnce(playerCopy.isOnce());
				getPlayer().setRateOfFire(playerCopy.getRateOfFire());
				getPlayer().setSelectedPlane(playerCopy.getSelectedPlane());
				getPlayer().setTimeToShoot(playerCopy.getTimeToShoot());
				getPlayer().setX(playerCopy.getX());
				getPlayer().setY(playerCopy.getY());
				getPlayer().getPlayerBody().setTransform(playerCopy.getX(), playerCopy.getY(), 0);
				playerPositionKryo.remove(playerPositionKryo.size()-1);
				
				
				//Enemies
			
				ArrayList<Enemy> enemiesCopy = enemyPositionKryo.get(enemyPositionKryo.size()-1);			
				
				int enemyGet=0;
		
				int biggerSize = 0;
				if(enemies.size()>enemiesCopy.size()){
					biggerSize=enemies.size();
				}
				else{
					biggerSize=enemiesCopy.size();
				}

				for(int counter=0; counter<biggerSize; counter++){
				
					Enemy enemy = null;
					if(counter < enemiesCopy.size()){
						enemy = enemiesCopy.get(counter); 
					}
					if(enemies.size()<enemiesCopy.size()&&enemyGet>=enemies.size()){
						enemies.add(new Foker(enemy.getEnemyX(),enemy.getEnemyY(),world));//fabryka obiektów
						enemies.get(enemies.size()-1).getBody().setLinearVelocity(enemy.getVelocity());
						enemies.get(enemies.size()-1).getBody().setTransform(enemy.getEnemyX(), enemy.getEnemyY(), enemy.getAngel());
						enemies.get(enemies.size()-1).getBody().setLinearVelocity(enemy.getVelocity());
						enemies.get(enemies.size()-1).setTimeToShoot(enemy.getTimeToShoot());
						enemies.get(enemies.size()-1).setEnemyX(enemy.getEnemyX());
						enemies.get(enemies.size()-1).setEnemyY(enemy.getEnemyY());
						enemies.get(enemies.size()-1).setHitPoints(enemy.getHitPoints());
						enemies.get(enemies.size()-1).setMoveSpeed(enemy.getMoveSpeed());
						enemies.get(enemies.size()-1).setRateOfFire(enemy.getRateOfFire());
						enemies.get(enemies.size()-1).setVelocity(enemy.getVelocity());
						enemies.get(enemies.size()-1).setTimeToShoot(enemy.getTimeToShoot());
						enemyGet++;
				
					}
					else if((enemies.size()>enemiesCopy.size())&&(enemyGet>=enemiesCopy.size())){
						enemyGet++;
						enemies.get(enemies.size()-1).getBody().setTransform(1000, 1000,0);
						enemies.get(enemies.size()-1).getBody().setLinearVelocity(0,0);
					}
					else{
						enemies.get(enemyGet).getBody().setTransform(enemy.getEnemyX(), enemy.getEnemyY(), enemy.getAngel());
						enemies.get(enemyGet).getBody().setLinearVelocity(enemy.getVelocity());
						enemies.get(enemyGet).setTimeToShoot(enemy.getTimeToShoot());
						enemies.get(enemyGet).setEnemyX(enemy.getEnemyX());
						enemies.get(enemyGet).setEnemyY(enemy.getEnemyY());
						enemies.get(enemyGet).setHitPoints(enemy.getHitPoints());
						enemies.get(enemyGet).setMoveSpeed(enemy.getMoveSpeed());
						enemies.get(enemyGet).setRateOfFire(enemy.getRateOfFire());
						enemies.get(enemyGet).setVelocity(enemy.getVelocity());
						enemyGet++;
					}
				}
			
				//Bullets
				
				ArrayList<EnemyBullet> enemiesBulletsCopy = enemyBulletsKryo.get(enemyBulletsKryo.size()-1);			
				
				int bulletGet=0;
		
				int biggerSizeb = 0;
				if(enemyBullets.size()>enemiesBulletsCopy.size()){
					biggerSizeb=enemyBullets.size();
				}
				else{
					biggerSizeb=enemiesBulletsCopy.size();
				}

				for(int counter=0; counter<biggerSizeb; counter++){
				
					EnemyBullet enemyBullet = null;
					if(counter < enemiesBulletsCopy.size()){
						enemyBullet = enemiesBulletsCopy.get(counter); 
					}
					if(enemyBullets.size()<enemiesBulletsCopy.size()&&bulletGet>=enemyBullets.size()){
						enemyBullets.add(new SimpleBullet(enemyBullet.getBulletX(),enemyBullet.getBulletY(),world));//fabryka obiektów
						enemyBullets.get(enemyBullets.size()-1).getBody().setTransform(enemyBullet.getBulletX(),enemyBullet.getBulletY(), enemyBullet.getAngel());
						enemyBullets.get(enemyBullets.size()-1).getBody().setLinearVelocity(0, -enemyBullet.getEnemyBulletSpeed());
						enemyBullets.get(enemyBullets.size()-1).setEnemyBulletFirepower(enemyBullet.getEnemyBulletFirepower());
						enemyBullets.get(enemyBullets.size()-1).setEnemyBulletSpeed(enemyBullet.getEnemyBulletSpeed());
						bulletGet++;
					}
					else if((enemyBullets.size()>enemiesBulletsCopy.size())&&(bulletGet>=enemiesBulletsCopy.size())){
						bulletGet++;
						enemyBullets.get(enemyBullets.size()-1).getBody().setTransform(800, 800,0);
						//enemyBullets.get(enemyBullets.size()-1).getBody().setLinearVelocity(0,0);
					}
					else{
						enemyBullets.get(bulletGet).getBody().setTransform(enemyBullet.getBulletX(), enemyBullet.getBulletY(), enemyBullet.getAngel());
						enemyBullets.get(bulletGet).getBody().setLinearVelocity(enemyBullet.getVelocity()); //??
						enemyBullets.get(bulletGet).setAngel(enemyBullet.getAngel());
						enemyBullets.get(bulletGet).setBulletX(enemyBullet.getBulletX());
						enemyBullets.get(bulletGet).setBulletY(enemyBullet.getBulletY());
						enemyBullets.get(bulletGet).setEnemyBulletFirepower(enemyBullet.getEnemyBulletFirepower());
						enemyBullets.get(bulletGet).setEnemyBulletSpeed(enemyBullet.getEnemyBulletSpeed());
						bulletGet++;
					}
				}
				
				//mp
				
				ArrayList<MpObject> mpCopy = mpKryo.get(mpKryo.size()-1);			
				
				int mpGet=0;
		
				int biggerSizeMp = 0;
				if(powerCollection.size()>mpCopy.size()){
					biggerSizeMp=powerCollection.size();
				}
				else{
					biggerSizeMp=mpCopy.size();
				}

				for(int counter=0; counter<biggerSizeMp; counter++){
				
					MpObject mpObject = null;
					if(counter < mpCopy.size()){
						mpObject = mpCopy.get(counter); 
					}
					if(powerCollection.size()<mpCopy.size()&&mpGet>=powerCollection.size()){
						powerCollection.add(new MpObject(mpObject.getPositionX(),mpObject.getPositionY(),world));//fabryka obiektów
						powerCollection.get(powerCollection.size()-1).getBody().setTransform(mpObject.getPositionX(),mpObject.getPositionY(), 0);
						mpGet++;
					}
					else if((powerCollection.size()>mpCopy.size())&&(mpGet>=mpCopy.size())){
						mpGet++;
						powerCollection.get(powerCollection.size()-1).getBody().setTransform(800, 800,0);
						//enemyBullets.get(enemyBullets.size()-1).getBody().setLinearVelocity(0,0);
					}
					else{
						powerCollection.get(mpGet).getBody().setTransform(mpObject.getPositionX(), mpObject.getPositionY(), 0);
						powerCollection.get(mpGet).setPositionX(mpObject.getPositionX());
						powerCollection.get(mpGet).setPositionY(mpObject.getPositionY());
						mpGet++;
					}
				}

				enemyPositionKryo.remove(enemyPositionKryo.size()-1);
				enemyBulletsKryo.remove(enemyBulletsKryo.size()-1);
				mpKryo.remove(mpKryo.size()-1);
					
			}
		
		catch(Exception NullPointerException){}
		
	}

	@Override
	public void resize(int width, int height) {
		camera.position.set(screenWidth/(2*ConstantValues.PPM), screenHeight/(2*ConstantValues.PPM), 0);
		camera.update();
	}

	@Override
	public void pause() {
		pauseGame=true;
	
		cameraPosition = camera.position;
	//	System.out.println(delta);
	}

	@Override
	public void resume() {

		
	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		sounds.getBackgroundMusic().stop();
	//	gameSpriteBatch.dispose();
	//	player.dispose();
	//	background.dispose();
	//	sounds.dispose();

	//	world.dispose();
	//	b2dr.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		
		if(visibleTable==false &&changing==false){
			if(screenX< 100 && screenY>screenHeight-100 ){

				changing = true;
				table.addAction(Actions.sequence(changeGameState(),Actions.fadeIn(fadeInOutSpeed),changeChanging())); 	
			}
		}
		else if(visibleTable==true && changing==false){
		if(screenX< 100 && screenY>screenWidth-100){
			changing = true;
			table.addAction(Actions.sequence(Actions.fadeOut(fadeInOutSpeed),changeGameState())); 
			}
		}
		
	
		
		if(screenX>screenWidth - 100 && screenY>screenHeight-100 ){
			reverseTime();
		}//
		
		if(visibleTable==false){
			return true;
		}
		else{
			return false;
		}
	}

	private void reverseTime() {

		/*//ConstantValues.worldSpeed-=2*ConstantValues.worldSpeed;
		for(int a=0; a<playerPosition.size(); a++){
		myXY myxy = playerPosition.get(playerPosition.size()-1);
		player.getPlayerBody().setTransform(myxy.getX(), myxy.getY(), 0);
		playerPosition.remove(playerPosition.size()-1);
		}*/
		if(reverseTimeActivation==false){
		reverseTimeActivation=true;
		ConstantValues.worldSpeed=-3f;
		}
		else{
			reverseTimeActivation=false;
			ConstantValues.worldSpeed=1.5f;
		}
		
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public RunnableAction changeGameState(){
	RunnableAction run = new RunnableAction();
	run.setRunnable(new Runnable() {
	    @Override
	    public void run() {
	        if(pause==1){
	        	pause=0.00001f;
	        	visibleTable=true;

	        }
	        else{
	        	pause=1;
	        	visibleTable=false;
	        	changing = false;

	        }
	    }
	});
	return run;
	}
	
	public RunnableAction changeChanging(){
		RunnableAction run = new RunnableAction();
		run.setRunnable(new Runnable() {
		    @Override
		    public void run() {
		       
		        	changing = false;
		       		    }
		});
		return run;
		}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		MainGameScreen.player = player;
	}



}
