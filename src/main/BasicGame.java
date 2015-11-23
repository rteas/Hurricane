package main;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BasicGame extends BasicGameState {
	
	public static int id = 1;
	
	// used to deterimine direction
	public static final char UP = 'U', DOWN = 'D', LEFT='L', RIGHT='R';
	
	private char attackDirection = 'N';
//	private Rectangle square;
//	private Rectangle obstacle;
	
	// Starting location/initialization
	private int tileSize = 100;
	private float destinationX;
	private float destinationY;
	private float clickX = 0;
	private float clickY = 0;
	int speed = 200;
	private int offsetX = 2*tileSize;
	private int offsetY = 50;
	
	private float x = offsetX + 100;
	private float y = offsetY + 100;
	int offset = 1*(speed/100);
//	boolean collide = false;
	
	
	private Room room;
	Tile[][] tileLayer;
	EntityPlayer player;
	
	private RoomManager rm;
	private LinkedList<Entity> entities;
	
	private SpriteSheet tileSheet, rockSheet, enemySheet, mageSheet;

	
	
	private boolean playerPhase = true;
	private boolean canAttack = false;

	
	// Test
	EntityObstacle eo;
	EntityEnemy enemy;
	public String entityHit = "Nothing";
	
	// Transforms array -> pixel coordinates for drawing
	public int getX(int x){
		return x*tileSize+offsetX;
	}
	public int getY(int y){
		return y*tileSize+offsetY;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		room = new Room(10,7);
		eo = new EntityObstacle("Rock",3,3,3, room);
		room.addEntity(eo, 3, 3);
		enemy = new EntityEnemy("Charger",100,3,5,room);
		room.addEntity(enemy, 3, 5);
		
		tileSheet = new SpriteSheet("tileImgs/dTile.png",tileSize,tileSize);
		rockSheet = new SpriteSheet("obstacleImgs/brokenRocks.png", tileSize, tileSize);
		enemySheet = new SpriteSheet("enemies/chargerX.png", tileSize, tileSize);
		mageSheet = new SpriteSheet("enemies/mageIdle.png",tileSize,tileSize);

		
		
		
		player = new EntityPlayer("Hero",100,1,1, room);
		rm = new RoomManager(room, player);
		entities = rm.getEntities();
		
		player = rm.getPlayer();
		// square = new Rectangle(25,25, 200,200);
		
		// Protagonist Sprite and Animations loaded in EntityPlayer
		gc.setVSync(true);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		// Grid
		// Generate/Draw tiles
		tileLayer = room.getTileLayer();
		for(int x=0;x<room.xMax();x++){
			for(int y=0;y<room.yMax();y++){
				tileSheet.draw(getX(x), getY(y));
			}
		}
		
		// Status/Debug
		g.drawString("Current Coordinates: \n" + "X: " + x + " Y: "+ y, 200, 0);
		g.drawString("Mouse clicked at (" + clickX + ", "+ clickY + ")",600, 0);
		g.drawString("Attack: " + attackDirection, 400, 0);
		g.drawString("Player at: ("+ rm.getPlayer().getLocationX() + ", "+ player.getLocationY() + ")",10, 30);
		g.drawString("Hit: "+ entityHit + "!", 10, 50);
		
		rockSheet.draw(getX(eo.getLocationX()), getY(eo.getLocationY()));
		enemySheet.draw(enemy.getLocationX()*tileSize+offsetX, enemy.getLocationY()*tileSize+offsetY);
		
		// Draw enemies/items/obstacles (to be done soon)
		for(Entity e: rm.getEntities()){
			
		}
		
		
		
		// Draw player depending on current status
		if(player.isAttacking()){
			char pd = player.getDirection();
			player.getIdleSheet().draw(getX(player.getLocationX()), getY(player.getLocationY()));
			SpriteSheet atk = player.getAttackSheet();
			switch(pd){
				case UP:
					atk.draw(getX(player.getLocationX()), getY(player.getLocationY())-100);
					break;
				case DOWN:
					atk.draw(getX(player.getLocationX()), getY(player.getLocationY())+100);
					break;
				case LEFT:
					atk.draw(getX(player.getLocationX())-100, getY(player.getLocationY()));
					break;
				case RIGHT:
					atk.draw(getX(player.getLocationX())+100, getY(player.getLocationY()));
					break;
			}
		}
		else if(player.isIdle()){
			player.getIdleSheet().draw(getX(player.getLocationX()), getY(player.getLocationY()));
		}
		else{
			player.getMoveSheet().draw(x,y);
		}
		
		// Some tutorial stuff
		/*
		square = new Rectangle(x,y, 200,200);
		obstacle = new Rectangle(350,350,50,50);
		g.drawString("X: "+ x + "Y: " + y,50,50);
		g.setColor(Color.cyan);
		g.draw(square);
		g.draw(obstacle);
		*/		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		// TODO Auto-generated method stub
		if (playerPhase) {
			if (!player.isIdle()) {
				movePlayer(player.getDirection(), destinationX, destinationY, d);
			} 
			else if (player.isAttacking()) {
				gc.sleep(500);
				player.setAttacking(false);
			} 
			else {
				Input input = gc.getInput();
				setMovePlayer(input);
				getAttack(input);
			} 
		}
		// else enemy phase/turn
		


	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	
	// Helper methods
	
	public void getAttack(Input input){
		
		// Melee attack
		if(input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
			
			clickX = input.getMouseX();
			clickY = input.getMouseY();
			// Decide direction
			
			if(clickX <= x && clickY >= y && clickY <= y+tileSize){
				player.setAttacking(true);
				player.setDirection(LEFT);
				Entity e = player.atkMelee(LEFT);
				entityHit = rm.playerAtk(e);
				attackDirection = 'L';
			}
			else if(clickX >= x+tileSize && clickY >= y && clickY <= y+tileSize){
				player.setAttacking(true);
				player.setDirection(RIGHT);
				Entity e = player.atkMelee(RIGHT);
				entityHit = rm.playerAtk(e);
				attackDirection = 'R';
			}
			else if(clickY <= y && clickX >= x && clickX <= x+tileSize){
				player.setAttacking(true);
				player.setDirection(UP);
				Entity e = player.atkMelee(UP);
				entityHit = rm.playerAtk(e);
				attackDirection='U';
			}
			else if(clickY >= y+tileSize && clickX >= x && clickX <= x+tileSize){
				player.setAttacking(true);
				player.setDirection(DOWN);
				Entity e = player.atkMelee(DOWN);
				entityHit = rm.playerAtk(e);
				attackDirection='D';
			}
			else{
				attackDirection='X';
			}
			
		}
		// Ranged attack
		else if(input.isMousePressed(input.MOUSE_RIGHT_BUTTON)){
			
		}
	}
	
	// Continue moving one grid in the x/y direction
	public void movePlayer(char direction,float destinationX, float destinationY, int d){
		switch(direction){
		case UP: 
			if(y>=destinationY)
				y -= speed*d/1000;		
			else{
				y = destinationY;
				player.setIdle(true);
			}
			break;
		case DOWN: 			
			if(y<=destinationY)
				y += speed*d/1000;
			else{
				y = destinationY;
				player.setIdle(true);
			}

			break;
		case LEFT: 
			if(x>=destinationX)
				x -= speed*d/1000;
			else{
				x = destinationX;
				player.setIdle(true);
			}
			break;
		case RIGHT:
			if(x<=destinationX)
				x += speed*d/1000;
			else{
				x = destinationX;
				player.setIdle(true);
			}
			break;
		}
	}
	
	
	// Initial movement 'WASD'
	public boolean setMovePlayer(Input input){
		if(input.isKeyPressed(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
			if(rm.movePlayer(UP)){
				destinationY = y-tileSize;
				player.setIdle(false);
				player.setDirection(UP);
				return true;
			}
		}
		else if(input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)){
			if(rm.movePlayer(LEFT)){
				destinationX = x-tileSize;
				player.setIdle(false);
				player.setDirection(LEFT);
				return true;
			}
		}
		else if(input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)){
			if (rm.movePlayer(DOWN)) {
				destinationY = y + tileSize;
				player.setIdle(false);
				player.setDirection(DOWN);
				return true;
			}
		}
		else if(input.isKeyDown(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)){
			if (rm.movePlayer(RIGHT)) {
				destinationX = x + tileSize;
				player.setIdle(false);
				player.setDirection(RIGHT);
				return true;
			}
		}
		// End turn
		else if(input.isKeyDown(Input.KEY_ENTER)){
			playerPhase = false;
		}
		else{
			player.setIdle(true);
		}
		return false;
	}

}
