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
	
//	private Rectangle square;
//	private Rectangle obstacle;
	
	private char direction = 'R';
	
	// Starting location (of sprite)
	private float x = 100;
	private float y = 100;
	
//	boolean collide = false;
	
	private SpriteSheet protagRight, pIdleRight,
	protagLeft, pIdleLeft,
	protagUp, pIdleUp,
	protagDown, pIdleDown;
	
	private Animation walkRight, walkLeft, walkDown, walkUp;
	
	private boolean still = true;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
		// square = new Rectangle(25,25, 200,200);
		
		// Protagonist Sprite and Animations
		
		protagRight = new SpriteSheet("protagImg/walkRight.png",100,100);
		pIdleRight = new SpriteSheet("protagImg/idleRight.png",100,100);
		walkRight = new Animation(protagRight, 200);

		protagLeft = new SpriteSheet("protagImg/walkLeft.png",100,100);
		pIdleLeft = new SpriteSheet("protagImg/idleLeft.png",100,100);
		walkLeft = new Animation(protagLeft, 200);
		
		protagDown = new SpriteSheet("protagImg/walkDown.png",100,100);
		pIdleDown = new SpriteSheet("protagImg/idleDown.png",100,100);
		walkDown = new Animation(protagDown, 250);
		
		protagUp = new SpriteSheet("protagImg/walkUp.png",100,100);
		pIdleUp = new SpriteSheet("protagImg/idleUp.png",100,100);
		walkUp = new Animation(protagUp, 250);
		
		//===============================================================================================
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		if(still){
			switch(direction){
			case UP: pIdleUp.draw(x,y);break;
			case DOWN: pIdleDown.draw(x,y);break;
			case LEFT: pIdleLeft.draw(x,y);break;
			case RIGHT: pIdleRight.draw(x,y);break;
			}
			//pIdleRight.draw(x, y);
		}
		else{
			switch(direction){
			case UP: walkUp.draw(x,y);break;
			case DOWN: walkDown.draw(x,y);break;
			case LEFT: walkLeft.draw(x,y);break;
			case RIGHT: walkRight.draw(x,y);break;
			}
			//walkRight.draw(x,y);
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
		
		// 'WASD' MOVEMENT
		Input input = gc.getInput();		
		
		if(input.isKeyDown(Input.KEY_W)){
			still = false;
			y -= 200/1000.0f*d;
			setDirection(UP);
		}
		else if(input.isKeyDown(Input.KEY_A)){
			still = false;
			x -= 200/1000.0f*d;
			setDirection(LEFT);
		}
		else if(input.isKeyDown(Input.KEY_D)){
			x += 200/1000.0f*d;
			still = false;
			setDirection(RIGHT);
		}
		else if(input.isKeyDown(Input.KEY_S)){
			y += 200/1000.0f*d;
			still = false;
			setDirection(DOWN);
		}
		else{
			still = true;
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

}
