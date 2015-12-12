package main;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


// Three main methods
// init: the initializer
// render: the drawer/rendering 
// update: game processing

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
	private float playerX;
	private float playerY;
	private float enemyX;
	private float enemyY;
	private float enemyDestX;
	private float enemyDestY;

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
	
	private SpriteSheet tileSheet;

	
	// For enemy drawing
	private boolean enemyMoving = false;
	private boolean enemyAttacking = false;
	private boolean enemyInitialized = false;

	
	// Test
	EntityObstacle eo;
	EntityEnemy enemy;
	EntityEnemy enemy2;
	EntityEnemy rat;
	//public String entityHit = "Nothing";
	
	// Transforms array -> pixel coordinates for drawing
	public int getX(int x){
		return x*tileSize+offsetX;
	}
	public int getY(int y){
		return y*tileSize+offsetY;
	}
	
	// Music/Sound
	private Music bgm;
	private Sound swing;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
		
		bgm = new Music("music/bgm.ogg");
		bgm.setVolume(0.5f);
		bgm.loop();
		
		swing = new Sound("music/swing.ogg");
		
		// Debug, spawning, and stuff goes here...
		room = new Room(11,7);
		eo = new EntityObstacle("Rock",3,3,3);
		room.addEntity(eo);
		enemy = new EntityCharger("Charger",100,3,5);
		room.addEntity(enemy);
		enemy2 = new EntityCharger("Charger",100,4,4);
		room.addEntity(enemy2);
		rat = new EntityRat("Rat", 100, 1,5);
		room.addEntity(rat);
		
		
		tileSheet = new SpriteSheet("tileImgs/dTile.png",tileSize,tileSize);

		
		player = new EntityPlayer("Hero",100,1,1);
		room.addEntityPlayer(player);
		playerX = getX(player.locationX);
		playerY = getY(player.locationY);
		rm = new RoomManager(room);
		room = rm.getRoom();
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
		if(rm.isPlayerTurn()) g.drawString("Phase: player", 200, 40);
		else g.drawString("Phase: enemy", 200, 40);
		g.drawString("HP: "+ player.getHp(), 10, 60);
//		g.drawString("Hit: "+ entityHit + "!", 10, 50);
		
//		rockSheet.draw(getX(eo.getLocationX()), getY(eo.getLocationY()));
//		enemySheet.draw(enemy.getLocationX()*tileSize+offsetX, enemy.getLocationY()*tileSize+offsetY);
		
		// Draw player depending on current status
		// Attacking at the bottom 
		if(player.isIdle()){
			player.getIdleSheet().draw(getX(player.getLocationX()), getY(player.getLocationY()));
		}
		else{
			player.getMoveSheet().draw(playerX,playerY);
		}
		
		// Draw enemies/items/obstacles (to be done soon)
		for(Entity e: room.getEntities()){
			if(!(e instanceof EntityPlayer)){

				if(!rm.isPlayerTurn() && e instanceof EntityEnemy){
					if (!e.isIdle()) {
						int ex = e.getDrawX();
						int ey = e.getDrawY();
						e.getIdleSheet().draw(ex, ey);
						
						// hp bar
						g.setColor(Color.black);
						g.fillRect(ex + 5, ey + tileSize - 10, tileSize - 10, 5);
						g.setColor(Color.red);
						g.fillRect(ex + 5, ey + tileSize - 10,
								(int) ((tileSize - 10) * ((double) e.hp / (double) e.maxHp)), 5);
						g.setColor(Color.white);
						// Debug
						//g.drawString("HP: "+ e.getHp()+ "/" +e.maxHp , getX(e.getLocationX()), getY(e.getLocationY())+tileSize-20);
					}
					else if (e.isIdle()){
						e.getIdleSheet().draw(getX(e.getLocationX()), getY(e.getLocationY()));
						if(!(e instanceof EntityObstacle)){
							// Hp bar
							g.setColor(Color.black);
							g.fillRect(getX(e.getLocationX())+5, getY(e.getLocationY())+tileSize-10, tileSize-10, 5);
							g.setColor(Color.red);
							g.fillRect(getX(e.getLocationX())+5, getY(e.getLocationY())+tileSize-10, (int)((tileSize-10)*((double)e.hp/(double)e.maxHp)), 5);
							g.setColor(Color.white);
							// Debug
//							g.drawString("HP: "+ e.getHp()+ "/" +e.maxHp , getX(e.getLocationX()), getY(e.getLocationY())+tileSize-20);
						}
					}
					if(e.isAttacking()){
							if(e instanceof EntityEnemy){
								EntityEnemy en = (EntityEnemy) e;
								SpriteSheet atk = en.getAttackSheet();
								char d = en.getDirection();
								if(en.isAttacking()){
									e.getIdleSheet().draw(getX(e.getLocationX()), getY(e.getLocationY()));
									switch(d){
									case UP:
										atk.draw(getX(en.getLocationX()), getY(en.getLocationY())-tileSize);
										break;
									case DOWN:
										atk.draw(getX(en.getLocationX()), getY(en.getLocationY())+tileSize);
										break;
									case LEFT:
										atk.draw(getX(en.getLocationX())-tileSize, getY(en.getLocationY()));
										break;
									case RIGHT:
										atk.draw(getX(en.getLocationX())+tileSize, getY(en.getLocationY()));
										break;
									}
								}
								// HP
								g.setColor(Color.black);
								g.fillRect(getX(e.getLocationX())+5, getY(e.getLocationY())+tileSize-10, tileSize-10, 5);
								g.setColor(Color.red);
								g.fillRect(getX(e.getLocationX())+5, getY(e.getLocationY())+tileSize-10, (int)((tileSize-10)*((double)e.hp/(double)e.maxHp)), 5);
								g.setColor(Color.white);
								
							}
					}
					
				}
				else{
					e.getIdleSheet().draw(getX(e.getLocationX()), getY(e.getLocationY()));
					
					if(!(e instanceof EntityObstacle)){
						g.setColor(Color.black);
						g.fillRect(getX(e.getLocationX())+5, getY(e.getLocationY())+tileSize-10, tileSize-10, 5);
						g.setColor(Color.red);
						g.fillRect(getX(e.getLocationX())+5, getY(e.getLocationY())+tileSize-10, (int)((tileSize-10)*((double)e.hp/(double)e.maxHp)), 5);
						g.setColor(Color.white);
						// Debug
//						g.drawString("HP: "+ e.getHp()+ "/" +e.maxHp , getX(e.getLocationX()), getY(e.getLocationY())+tileSize-20);
					}
				}

//				e.getIdleSheet().draw(getX(e.getLocationX()), getY(e.getLocationY()));
				
				// HP BAR
				
				
				
			}
			
		}
		if(player.isAttacking()){
			swing.play();
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
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		// TODO Auto-generated method stub
		
		// PRESS ENTER TO SKIP TO ENEMY PHASE
		if (rm.isPlayerTurn()) {
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

			
			/* WARNING COMPLEX */
			// Basically try to cover all the cases of
			// Enemies moving and attacking independently
			// Along with proper rendering flags
			// Actually pretty hard to draw in fake time...
			
			// enemies moving and attacking
			
			

			if(enemyMoving && enemyAttacking){
				for(Entity e: room.getEntities()){
					if(e instanceof EntityEnemy){
						EntityEnemy en = (EntityEnemy)e;
						en = (EntityEnemy)e;
						moveEnemy(en, d);
						
						if(e.isIdle() && en.canAttack(room)){
							swing.play();
							en.hitPlayer(room);
							en.setAttacking(true);
						}
						
					}
				}
			}
			// Move enemies by drawing
			else if(enemyMoving){
				for(Entity e: room.getEntities()){
					if(e instanceof EntityEnemy){
						EntityEnemy en = (EntityEnemy)e;
						en = (EntityEnemy)e;
						moveEnemy(en, d);
					}
				}
				if(!enemyMoving){
					for(Entity e: room.getEntities()){
						if (e instanceof EntityEnemy) {
							EntityEnemy en = (EntityEnemy) e;
							if (en.canAttack(room)) {
								swing.play();
								en.hitPlayer(room);
								enemyAttacking = true;
								en.setAttacking(true);
							} 
						}
					}
					if(!enemyAttacking){
						rm.startPlayerTurn();
					}
				}
			}
			else if(enemyAttacking){
				for(Entity e: room.getEntities()){
					if(e instanceof EntityEnemy && e.isAttacking()){
						swing.play();
						gc.sleep(200);
						e.setAttacking(false);
					}
				}
				enemyAttacking = false;
				
				if(!enemyMoving){
					rm.startPlayerTurn();
				}
			}

			// Actual movement on grid (moves once)
			else{
				for(Entity e : room.getEntities()){
					if(e instanceof EntityEnemy){
						EntityEnemy en = (EntityEnemy)e;
						
						if(en.canAttack(room)){
							
							en.hitPlayer(room);
							en.setAttacking(true);
							enemyAttacking = true;
							
						}
						else if(en.move(room)){
							setMoveEnemy(en);
							en.setIdle(false);
						}
					}
				}
				if(enemyMoving == false && enemyAttacking == false){
					rm.startPlayerTurn();
				}
			}
			
			
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
				attackDirection = LEFT;
			}
			// RIGHT
			else if(clickX >= playerX+tileSize && clickY >= playerY && clickY <= playerY+tileSize){
				player.atkMelee(room, RIGHT);
				attackDirection = RIGHT;

			}
			// UP
			else if(clickY <= playerY && clickX >= playerX && clickX <= playerX+tileSize){
				player.atkMelee(room, UP);
				attackDirection= UP;
			}
			// DOWN
			else if(clickY >= playerY+tileSize && clickX >= playerX && clickX <= playerX+tileSize){
				player.atkMelee(room, DOWN);
				attackDirection= DOWN;
			}
			// INVALID INPUT
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
	
	
	public void moveEnemy(EntityEnemy e, int d){
		if (!e.isIdle()) {
			char enemyDirection = e.getDirection();

			int drawY = e.getDrawY();
			int drawX = e.getDrawX();
			int finY = e.getFinY();
			int finX = e.getFinX();
			switch (enemyDirection) {
			case UP:
				if (drawY >= finY) {
					e.setDrawY(e.getDrawY() - e.speed * d / 1000);
				} else {
					e.setDrawY(finY);
					e.setIdle(true);
					enemyMoving = false;
				}
				break;
			case DOWN:
				if (drawY <= finY) {
					e.setDrawY(e.getDrawY() + e.speed * d / 1000);
				} else {
					e.setDrawY(finY);
					e.setIdle(true);
					enemyMoving = false;
				}
				break;
			case LEFT:
				if (drawX >= finX) {
					e.setDrawX(e.getDrawX() - e.speed * d / 1000);
				} else {
					e.setDrawX(finX);
					e.setIdle(true);
					enemyMoving = false;
				}
				break;
			case RIGHT:
				if (drawX <= finX) {
					e.setDrawX(e.getDrawX() + e.speed * d / 1000);
				} else {
					e.setDrawX(finX);
					e.setIdle(true);
					enemyMoving = false;
				}
				break;
			}
		}
	}
	
	public void setMoveEnemy(EntityEnemy e){
		e.setIdle(false);
		enemyMoving = true;
		e.setDrawX(getX(e.prevX));
		e.setDrawY(getY(e.prevY));
		e.setFinX(getX(e.getLocationX()));
		e.setFinY(getY(e.getLocationY()));
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
		else if(input.isKeyPressed(Input.KEY_ENTER)){
			rm.endPlayerTurn();
		}
		else{
			player.setIdle(true);
		}
		return false;
	}

}
