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
	// offsets for pixels (Screen Resolution in main)
	private int tileSize = 100;
	private float destinationX;
	private float destinationY;
	private float clickX = 0;
	private float clickY = 0;
	int speed = 200;
	private int offsetX = tileSize+68;
	private int offsetY = 68;
	
	// Drawing locations
	private float playerX = offsetX + 100;
	private float playerY = offsetY + 100;
	int offset = 1*(speed/100);
//	boolean collide = false;
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Player & Enemies (entities) are reliant on room, and vice versa
	// Room manager handles the link between them
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	private Room room;
	Tile[][] tileLayer;
	EntityPlayer player;
	
	private RoomManager rm;
	//private LinkedList<Entity> entities;
	
	private SpriteSheet tileSheet, rockSheet, enemySheet, mageSheet;

	
	
	private boolean playerPhase = true;
//	private boolean canAttack = false;

	
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
		
		// Debug, spawning, and stuff goes here...
		room = new Room(12,7);
		eo = new EntityObstacle("Rock",3,3,3);
		room.addEntity(eo, 3, 3);
		enemy = new EntityCharger("Charger",100,3,5);
		room.addEntity(enemy, 3, 5);
		
		tileSheet = new SpriteSheet("tileImgs/dTile.png",tileSize,tileSize);
		rockSheet = new SpriteSheet("obstacleImgs/brokenRocks.png", tileSize, tileSize);
		enemySheet = new SpriteSheet("enemies/chargerX.png", tileSize, tileSize);
		mageSheet = new SpriteSheet("enemies/mageIdle.png",tileSize,tileSize);

		
		
		
		player = new EntityPlayer("Hero",100,1,1);
		room.addEntityPlayer(player);
		rm = new RoomManager(room);
//		entities = rm.getEntities();
		
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
		g.drawString("Current Coordinates: \n" + "X: " + playerX + " Y: "+ playerY, 200, 0);
		g.drawString("Mouse clicked at (" + clickX + ", "+ clickY + ")",600, 0);
		g.drawString("Attack: " + attackDirection, 400, 0);
		g.drawString("Player at: ("+ rm.getPlayer().getLocationX() + ", "+ player.getLocationY() + ")",10, 30);
		g.drawString("Hit: "+ entityHit + "!", 10, 50);
		
//		rockSheet.draw(getX(eo.getLocationX()), getY(eo.getLocationY()));
//		enemySheet.draw(enemy.getLocationX()*tileSize+offsetX, enemy.getLocationY()*tileSize+offsetY);
		
		// Draw enemies/items/obstacles (to be done soon)
		for(Entity e: rm.getEntities()){
			if(!(e instanceof EntityPlayer)){
				e.getIdleSheet().draw(getX(e.getLocationX()), getY(e.getLocationY()));
				// HP BAR
				if(!(e instanceof EntityObstacle)){
					g.setColor(Color.black);
					g.fillRect(getX(e.getLocationX()), getY(e.getLocationY())+tileSize-10, 80, 5);
					g.setColor(Color.red);
					g.fillRect(getX(e.getLocationX()), getY(e.getLocationY())+tileSize-10, (int)(80*((double)e.hp/(double)e.maxHp)), 5);
					g.setColor(Color.white);
					g.drawString("HP: "+ e.getHp()+ "/" +e.maxHp , getX(e.getLocationX()), getY(e.getLocationY())+tileSize-20);
				}
			}
			
		}
		

		// Draw player depending on current status
		if(player.isAttacking()){
			char pd = player.getDirection();
			player.getIdleSheet().draw(getX(player.getLocationX()), getY(player.getLocationY()));
			SpriteSheet atk = player.getAttackSheet();
			switch(pd){
				case UP:
					atk.draw(getX(player.getLocationX()), getY(player.getLocationY())-tileSize);
					break;
				case DOWN:
					atk.draw(getX(player.getLocationX()), getY(player.getLocationY())+tileSize);
					break;
				case LEFT:
					atk.draw(getX(player.getLocationX())-tileSize, getY(player.getLocationY()));
					break;
				case RIGHT:
					atk.draw(getX(player.getLocationX())+tileSize, getY(player.getLocationY()));
					break;
			}
		}
		else if(player.isIdle()){
			player.getIdleSheet().draw(getX(player.getLocationX()), getY(player.getLocationY()));
		}
		else{
			player.getMoveSheet().draw(playerX,playerY);
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
		
		// PRESS ENTER TO SKIP TO ENEMY PHASE
		if (playerPhase) {
			if (!player.isIdle()) {
				movePlayer(player.getDirection(), destinationX, destinationY, d);
			} 
			else if (player.isAttacking()) {
				gc.sleep(250);
				player.setAttacking(false);
			} 
			else {
				Input input = gc.getInput();
				setMovePlayer(input);
				getAttack(input);
			} 
		}
		
		// else enemy phase/turn
		else{
			rm.moveEnemies();
		}
		


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
			
			// LEFT
			if(clickX <= playerX && clickY >= playerY && clickY <= playerY+tileSize){
				player.atkMelee(room, LEFT);
				attackDirection = 'L';
			}
			// RIGHT
			else if(clickX >= playerX+tileSize && clickY >= playerY && clickY <= playerY+tileSize){
				player.atkMelee(room, RIGHT);
				attackDirection = 'R';

			}
			// UP
			else if(clickY <= playerY && clickX >= playerX && clickX <= playerX+tileSize){
				player.atkMelee(room, UP);
				attackDirection='U';
			}
			// DOWN
			else if(clickY >= playerY+tileSize && clickX >= playerX && clickX <= playerX+tileSize){
				player.atkMelee(room, DOWN);
				attackDirection='D';
			}
			// UNAVAILABLE
			else{
				attackDirection='X';
			}
			
		}
		// Ranged attack
		else if(input.isMousePressed(input.MOUSE_RIGHT_BUTTON)){
			
		}
	}
	
	// [Drawing] Continue moving one grid in the x/y direction
	public void movePlayer(char direction,float destinationX, float destinationY, int d){
		switch(direction){
		case UP: 
			if(playerY>=destinationY)
				playerY -= speed*d/1000;		
			else{
				playerY = destinationY;
				player.setIdle(true);
			}
			break;
		case DOWN: 			
			if(playerY<=destinationY)
				playerY += speed*d/1000;
			else{
				playerY = destinationY;
				player.setIdle(true);
			}

			break;
		case LEFT: 
			if(playerX>=destinationX)
				playerX -= speed*d/1000;
			else{
				playerX = destinationX;
				player.setIdle(true);
			}
			break;
		case RIGHT:
			if(playerX<=destinationX)
				playerX += speed*d/1000;
			else{
				playerX = destinationX;
				player.setIdle(true);
			}
			break;
		}
	}
	
	
	// Initial movement 'WASD'
	public boolean setMovePlayer(Input input){
		if(input.isKeyPressed(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
			if(rm.movePlayer(UP)){
				destinationY = playerY-tileSize;
				player.setIdle(false);
				player.setDirection(UP);
				return true;
			}
		}
		else if(input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)){
			if(rm.movePlayer(LEFT)){
				destinationX = playerX-tileSize;
				player.setIdle(false);
				player.setDirection(LEFT);
				return true;
			}
		}
		else if(input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)){
			if (rm.movePlayer(DOWN)) {
				destinationY = playerY + tileSize;
				player.setIdle(false);
				player.setDirection(DOWN);
				return true;
			}
		}
		else if(input.isKeyDown(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)){
			if (rm.movePlayer(RIGHT)) {
				destinationX = playerX + tileSize;
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
