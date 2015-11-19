package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityPlayer extends Entity{
	
	private SpriteSheet protagRight, idleRight, protagLeft, idleLeft,
						protagUp, idleUp, protagDown, idleDown,
						slashRight, slashLeft, slashUp, slashDown;
	private Animation walkRight, walkLeft, walkDown, walkUp;
	
	private char direction = 'R';

	{
		try{
			protagRight = new SpriteSheet("protagImg/walkRight.png",100,100);
			idleRight = new SpriteSheet("protagImg/idleRight.png",100,100);
			walkRight = new Animation(protagRight, 150);
		
			protagLeft = new SpriteSheet("protagImg/walkLeft.png",100,100);
			idleLeft = new SpriteSheet("protagImg/idleLeft.png",100,100);
			walkLeft = new Animation(protagLeft, 150);
			
			protagDown = new SpriteSheet("protagImg/walkDown.png",100,100);
			idleDown = new SpriteSheet("protagImg/idleDown.png",100,100);
			walkDown = new Animation(protagDown, 150);
			
			protagUp = new SpriteSheet("protagImg/walkUp.png",100,100);
			idleUp = new SpriteSheet("protagImg/idleUp.png",100,100);
			walkUp = new Animation(protagUp, 150);
			
			
			slashRight = new SpriteSheet("protagImg/slashRight.png",100,100);
			slashLeft = new SpriteSheet("protagImg/slashLeft.png",100,100);
			slashUp = new SpriteSheet("protagImg/slashUp.png",100,100);
			slashDown = new SpriteSheet("protagImg/slashDown.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	
	public EntityPlayer(String name, int hp, int locationX, int locationY, Room room) {
		super(name, hp, locationX, locationY, room);
	}
	
	
	public SpriteSheet getIdleSheet(){
		switch(direction){
			case 'U': return idleUp;
			case 'D': return idleDown;
			case 'L': return idleLeft;
			default: return idleRight;
		}
	}
	
	public Animation getMoveSheet(){
		switch(direction){
			case 'U': return walkUp;
			case 'D': return walkDown;
			case 'L': return walkLeft;
			default: return walkRight;
		}
	}
	
	public SpriteSheet getAttackSheet(){
		switch(direction){
			case 'U': return slashUp;
			case 'D': return slashDown;
			case 'L': return slashLeft;
			default: return slashRight;
		}
	}
	
	public boolean move(char direction){
		this.direction = direction;
		
		switch(direction){
			case 'U':
				if(room.moveEntity(this, this.getLocationX(), this.getLocationY()-1)){
					setLocationY(this.getLocationY()-1);
					moves--;
					return true;
				}
				break;
			case 'D':
				if(room.moveEntity(this, this.getLocationX(), this.getLocationY()+1)){
					setLocationY(this.getLocationY()+1);
					moves--;
					return true;
				}
				break;
			case 'L':
				if(room.moveEntity(this, this.getLocationX()-1, this.getLocationY())){
					setLocationX(this.getLocationX()-1);
					moves--;
					return true;
				}
				break;
			case 'R':
				if(room.moveEntity(this, this.getLocationX()+1, this.getLocationY())){
					setLocationX(this.getLocationX()+1);
					moves--;
					return true;
				}
				break;
	
			}
		return false;
		
	}
	
	

}
