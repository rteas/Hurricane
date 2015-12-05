package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityPlayer extends Entity{
	
	private SpriteSheet protagRight, idleRight, protagLeft, idleLeft,
						protagUp, idleUp, protagDown, idleDown,
						slashRight, slashLeft, slashUp, slashDown;
	private Animation walkRight, walkLeft, walkDown, walkUp;

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
	
	
	public EntityPlayer(String name, int hp, int locationX, int locationY) {
		super(name, hp, locationX, locationY);
	}
	
	// Returns spritesheet based on direction the character is facing (see parent class for directions)
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
	
	// x and y are the coordinates of the location of this entity
	// also obtain through this.getLocationX() / this.getLocationY()
	public Entity atkMelee(Room room, char direction){
		this.setDirection(direction);
		this.setAttacking(true);
		if (meleeHit(room, direction)) {
			switch (direction) {
			case 'U':
				if (room.entityAt(locationX, locationY - 1)) {
					Entity e = room.getEntity(locationX, locationY - 1);
					e.onHit(room, atk);
					return e;
				}
				return null;
			case 'D':
				if (room.entityAt(locationX, locationY + 1)) {
					Entity e = room.getEntity(locationX, locationY + 1);
					e.onHit(room, atk);
					return e;
				}
				return null;
			case 'L':
				if (room.entityAt(locationX - 1, locationY)) {
					Entity e = room.getEntity(locationX - 1, locationY);
					e.onHit(room, atk);
					return e;
				}
				return null;
			case 'R':
				if (room.entityAt(locationX + 1, locationY)) {
					Entity e = room.getEntity(locationX + 1, locationY);
					e.onHit(room, atk);
					return e;
				}
				return null;
				
			default:
				return null;
			}
			
		}
		else return null;
	}
	
	public boolean meleeHit(Room room, char direction){
		switch(direction){
		case 'U': 
			return room.entityAt(locationX, locationY-1);
		case 'D': 
			return room.entityAt(locationX, locationY+1);
		case 'L': 
			return room.entityAt(locationX-1, locationY);
		case 'R': 
			return room.entityAt(locationX+1, locationY);
		}
		return false;
	}
	
	public boolean move(Room room, char direction){
		this.direction = direction;
		
		switch(direction){
			case 'U':
				if(room.moveEntity(this, this.getLocationX(), this.getLocationY()-1)){
					moves--;
					return true;
				}
				break;
			case 'D':
				if(room.moveEntity(this, this.getLocationX(), this.getLocationY()+1)){
					moves--;
					return true;
				}
				break;
			case 'L':
				if(room.moveEntity(this, this.getLocationX()-1, this.getLocationY())){
					moves--;
					return true;
				}
				break;
			case 'R':
				if(room.moveEntity(this, this.getLocationX()+1, this.getLocationY())){
					moves--;
					return true;
				}
				break;
	
			}
		return false;
		
	}
	
	

}
