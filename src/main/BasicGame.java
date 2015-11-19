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
	
	private char direction = 'R';
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
	private float offsetX = 200;
	private float offsetY = 0;
	
	private float x = offsetX + 100;
	private float y = offsetY + 100;
	int offset = 1*(speed/100);
//	boolean collide = false;
	
	
	private Room room;
	Tile[][] tileLayer;
	EntityPlayer player;
	
	private RoomManager rm;
	private LinkedList<Entity> entities;
	
	private SpriteSheet slashRight, slashLeft, slashUp, slashDown, tileSheet, rockSheet;
	
	
	private boolean playerPhase = true;

	private boolean attack = false;
	
	// Test
	EntityObstacle eo;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		room = new Room(10,7);
		eo = new EntityObstacle("Rock",3,3,3, room);
		room.addEntity(eo, 3, 3);
		tileSheet = new SpriteSheet("tileImgs/dTileSmooth.png",tileSize,tileSize);
		rockSheet = new SpriteSheet("obstacleImgs/rock.png", tileSize, tileSize);
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
				tileSheet.draw(x*tileSize+offsetX, y*tileSize+offsetY);
			}
		}
		
		// Status/Debug
		g.drawString("Current Coordinates: \n" + "X: " + x + " Y: "+ y, 200, 0);
		g.drawString("Mouse clicked at (" + clickX + ", "+ clickY + ")",600, 0);
		g.drawString("Attack: " + attackDirection, 400, 0);
		g.drawString("Player at: ("+rm.getRoom().getPlayerX() + ", "+ rm.getRoom().getPlayerY() + ")",10, 30);
		rockSheet.draw(eo.getLocationX()*tileSize+offsetX, eo.getLocationY()*tileSize+offsetY);
		
		// Draw enemies/items/obstacles (to be done soon)
		for(Entity e: rm.getEntities()){
			
		}
		
		
		
		// Draw player
		if(player.isIdle()){
			player.getIdleSheet().draw(player.getLocationX()*tileSize+offsetX, player.getLocationY()*tileSize+offsetY);
			if(player.isAttacking()){
				player.getAttackSheet().draw(clickX, clickY);
			}
		}
		else{
			player.getMoveSheet().draw(x,y);
		}
		
		/*
		if(attack){
			if(attackDirection == 'R'){
				pIdleRight.draw(x,y);
				slashRight.draw(x+tileSize, y);
				direction = attackDirection;
			}
			else if(attackDirection == 'L'){
				pIdleLeft.draw(x,y);
				slashLeft.draw(x-tileSize,y);
				direction = attackDirection;
			}
			else if(attackDirection == 'U'){
				pIdleUp.draw(x,y);
				slashUp.draw(x,y-tileSize);
				direction = attackDirection;
			}
			else{
				pIdleDown.draw(x,y);
				slashDown.draw(x,y+tileSize);
				direction = attackDirection;
			}
		}
		*/

		
		/*
		if(attackDirection == "Right"){
			rightAtk.draw(x+100, y);
			attackDirection = "null";
		}
		*/

		
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
				movePlayer(direction, destinationX, destinationY, d);
			} else if (player.isAttacking()) {
				gc.sleep(500);
				player.setAttacking(false);
			} else {
				Input input = gc.getInput();
				setMovePlayer(input);
				getAttack(input);
			} 
		}


	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	
	// Helper methods
	
	// Sets the direction of the character 
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	public void getAttack(Input input){
		if(input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
			
			clickX = input.getMouseX();
			clickY = input.getMouseY();
			// Decide direction
			
			if(clickX <= x && clickY >= y && clickY <= y+tileSize){
				player.setAttacking(true);
				player.setDirection(LEFT);
				attackDirection = 'L';
				attack = true;
			}
			else if(clickX >= x+tileSize && clickY >= y && clickY <= y+tileSize){
				player.setAttacking(true);
				player.setDirection(RIGHT);
				attackDirection = 'R';
				attack = true;
			}
			else if(clickY <= y && clickX >= x && clickX <= x+tileSize){
				player.setAttacking(true);
				player.setDirection(UP);
				attackDirection='U';
				attack = true;
			}
			else if(clickY >= y+tileSize && clickX >= x && clickX <= x+tileSize){
				attackDirection='D';
				attack = true;
			}
			else{
				attackDirection='X';
			}
			
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
		if(input.isKeyDown(Input.KEY_W)){
			if(rm.movePlayer(UP)){
				destinationY = y-tileSize;
				player.setIdle(false);
				setDirection(UP);
				return true;
			}
		}
		else if(input.isKeyDown(Input.KEY_A)){
			if(rm.movePlayer(LEFT)){
				destinationX = x-tileSize;
				player.setIdle(false);
				setDirection(LEFT);
				return true;
			}
		}
		else if(input.isKeyDown(Input.KEY_S)){
			if (rm.movePlayer(DOWN)) {
				destinationY = y + tileSize;
				player.setIdle(false);
				setDirection(DOWN);
				return true;
			}
		}
		else if(input.isKeyDown(Input.KEY_D)){
			if (rm.movePlayer(RIGHT)) {
				destinationX = x + tileSize;
				player.setIdle(false);
				setDirection(RIGHT);
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
