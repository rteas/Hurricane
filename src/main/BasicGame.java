package main;

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
	
	
	// Starting location (of sprite)
	private float x = 100;
	private float y = 100;
	private int tileSize = 100;
	private float destinationX;
	private float destinationY;
	private float clickX = 0;
	private float clickY = 0;
	int speed = 200;
	private float offsetX = 200;
	private float offsetY = 0;
	int offset = 1*(speed/100);
//	boolean collide = false;
	private Room room;
	Tile[][] tileLayer;
	
	EntityPlayer player;
	
	private SpriteSheet protagRight, pIdleRight,
	protagLeft, pIdleLeft,
	protagUp, pIdleUp,
	protagDown, pIdleDown,
	slashRight, slashLeft, slashUp, slashDown, tileSheet;
	
	private Animation walkRight, walkLeft, walkDown, walkUp, rightAtk;
	
	private boolean playerPhase = true;
	private boolean idle = true;
	private boolean attack = false;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		room = new Room(7,7,tileSize);
		tileSheet = new SpriteSheet("tileImgs/dTileSmooth.png",tileSize,tileSize);
		//player = new EntityPlayer("Hero",100,1,1, room);
		
		// square = new Rectangle(25,25, 200,200);
		
		// Protagonist Sprite and Animations
		gc.setVSync(true);
		protagRight = new SpriteSheet("protagImg/walkRight.png",100,100);
		pIdleRight = new SpriteSheet("protagImg/idleRight.png",100,100);
		walkRight = new Animation(protagRight, 150);

		protagLeft = new SpriteSheet("protagImg/walkLeft.png",100,100);
		pIdleLeft = new SpriteSheet("protagImg/idleLeft.png",100,100);
		walkLeft = new Animation(protagLeft, 150);
		
		protagDown = new SpriteSheet("protagImg/walkDown.png",100,100);
		pIdleDown = new SpriteSheet("protagImg/idleDown.png",100,100);
		walkDown = new Animation(protagDown, 150);
		
		protagUp = new SpriteSheet("protagImg/walkUp.png",100,100);
		pIdleUp = new SpriteSheet("protagImg/idleUp.png",100,100);
		walkUp = new Animation(protagUp, 150);
		
		
		slashRight = new SpriteSheet("protagImg/slashRight.png",100,100);
		slashLeft = new SpriteSheet("protagImg/slashLeft.png",100,100);
		slashUp = new SpriteSheet("protagImg/slashUp.png",100,100);
		slashDown = new SpriteSheet("protagImg/slashDown.png",100,100);

		//===============================================================================================
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		// Grid
		g.drawRect(x, y, tileSize, tileSize);
		tileLayer = room.getTileLayer();
		for(int i=0;i<room.xTiles;i++){
			for(int j=0;j<room.yTiles;j++){
				tileSheet.draw(i*tileSize+offsetX, j*tileSize+offsetY);
			}
		}
		
		// Status/Debug
		g.drawString("Current Coordinates: \n" + "X: " + x + " Y: "+ y, 200, 0);
		g.drawString("Mouse clicked at (" + clickX + ", "+ clickY + ")",600, 0);
		g.drawString("Attack: " + attackDirection, 400, 0);
		
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
		else if(idle){
			switch(direction){
			case UP: pIdleUp.draw(x,y);break;
			case DOWN: pIdleDown.draw(x,y);break;
			case LEFT: pIdleLeft.draw(x,y);break;
			case RIGHT: pIdleRight.draw(x,y);break;
			}
		}
		else{
			switch(direction){
			case UP: walkUp.draw(x,y);break;
			case DOWN: walkDown.draw(x,y);break;
			case LEFT: walkLeft.draw(x,y);break;
			case RIGHT: walkRight.draw(x,y);break;
			}
		}
		
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
			if (!idle) {
				moveGrid(direction, destinationX, destinationY, d);
			} else if (attack) {
				gc.sleep(500);
				attack = false;
			} else {
				Input input = gc.getInput();
				setMove(input, d);
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
				attackDirection = 'L';
				attack = true;
			}
			else if(clickX >= x+tileSize && clickY >= y && clickY <= y+tileSize){
				attackDirection = 'R';
				attack = true;
			}
			else if(clickY <= y && clickX >= x && clickX <= x+tileSize){
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
	public void moveGrid(char direction,float destinationX, float destinationY, int d){
		switch(direction){
		case UP: 
			if(y>=destinationY)
				y -= speed*d/1000;		
			else{
				y = destinationY;
				idle=true;
			}
			break;
		case DOWN: 			
			if(y<=destinationY)
				y += speed*d/1000;
			else{
				y = destinationY;
				idle=true;
			}

			break;
		case LEFT: 
			if(x>=destinationX)
				x -= speed*d/1000;
			else{
				x = destinationX;
				idle=true;
			}
			break;
		case RIGHT:
			if(x<=destinationX)
				x += speed*d/1000;
			else{
				x = destinationX;
				idle=true;
			}
			break;
		}
	}
	
	
	// Initial movement 'WASD'
	public void setMove(Input input, int d){
		if(input.isKeyDown(Input.KEY_W)){
			destinationY = y-tileSize;
			idle = false;
			setDirection(UP);
		}
		else if(input.isKeyDown(Input.KEY_A)){
			destinationX = x-tileSize;
			idle = false;
			setDirection(LEFT);
		}
		else if(input.isKeyDown(Input.KEY_S)){
			destinationY = y+tileSize;
			idle = false;
			setDirection(DOWN);
		}
		else if(input.isKeyDown(Input.KEY_D)){
			destinationX = x+tileSize;
			idle = false;
			setDirection(RIGHT);
		}
		else{
			idle = true;
		}
	}

}
