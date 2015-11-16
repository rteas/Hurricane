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
	
	public String attackDirection = "null";
//	private Rectangle square;
//	private Rectangle obstacle;
	
	private char direction = 'R';
	
	// Starting location (of sprite)
	private float x = 100;
	private float y = 100;
	private int tileSize = 100;
	private float destinationX;
	private float destinationY;
	private float clickX = 0;
	private float clickY = 0;
	
	
//	boolean collide = false;
	
	private SpriteSheet protagRight, pIdleRight,
	protagLeft, pIdleLeft,
	protagUp, pIdleUp,
	protagDown, pIdleDown,
	slashRight, slashLeft, slashUp, slashDown;
	
	private Animation walkRight, walkLeft, walkDown, walkUp, rightAtk;
	
	private boolean idle = true;
	private boolean attack = false;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
		// square = new Rectangle(25,25, 200,200);
		
		// Protagonist Sprite and Animations
		
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
		
		// Status/Debug
		g.drawString("Current Coordinates: \n" + "X: " + x + " Y: "+ y, 200, 0);
		g.drawString("Mouse clicked at (" + clickX + ", "+ clickY + ")",600, 0);
		g.drawString("Attack: " + attackDirection, 400, 0);
		// Grid
		g.drawRect(x, y, tileSize, tileSize);
		
		if(attack){
			if(attackDirection == "Right"){
				pIdleRight.draw(x,y);
				slashRight.draw(x+100, y);
				direction = RIGHT;
			}
			else if(attackDirection == "Left"){
				pIdleLeft.draw(x,y);
				slashLeft.draw(x-100,y);
				direction = LEFT;
			}
			else if(attackDirection == "Up"){
				pIdleUp.draw(x,y);
				slashUp.draw(x,y-100);
				direction = UP;
			}
			else{
				pIdleDown.draw(x,y);
				slashDown.draw(x,y+100);
				direction = DOWN;
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
		
		// 'WASD' MOVEMENT
		if(attack){
			gc.sleep(500);
			attack = false;
		}
		if(idle){
			Input input = gc.getInput();
			setMove(input, d);
			getAttack(input);
		}
		else{
			moveGrid(direction,destinationX,destinationY,d);
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
				attackDirection = "Left";
				attack = true;
			}
			else if(clickX >= x+tileSize && clickY >= y && clickY <= y+tileSize){
				attackDirection = "Right";
				attack = true;
			}
			else if(clickY <= y && clickX >= x && clickX <= x+tileSize){
				attackDirection="Up";
				attack = true;
			}
			else if(clickY >= y+tileSize && clickX >= x && clickX <= x+tileSize){
				attackDirection="Down";
				attack = true;
			}
			else{
				attackDirection="Unaccepted";
			}
			
		}
	}
	
	// Continue moving one grid in the x/y direction
	public void moveGrid(char direction,float destinationX, float destinationY, int d){
		switch(direction){
		case UP: 
			if(y>=destinationY)
				y -= 200/1000.0f*d;		
			else
				idle=true;
			break;
		case DOWN: 			
			if(y<=destinationY)
				y += 200/1000.0f*d;
			else
				idle=true;

			break;
		case LEFT: 
			if(x>=destinationX)
				x -= 200/1000.0f*d;
			else
				idle=true;
			break;
		case RIGHT:
			if(x<=destinationX)
				x += 200/1000.0f*d;
			else
				idle=true;
			break;
		}
	}
	
	
	// Initial movement
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
