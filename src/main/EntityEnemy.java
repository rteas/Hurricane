package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityEnemy extends Entity {
	
	protected SpriteSheet idleUp, idleDown, idleLeft, idleRight,
						slashRight, slashLeft, slashUp, slashDown;
	{
		try{
			idleUp = new SpriteSheet("tileImgs/dtile.png",100,100);
			idleDown = new SpriteSheet("tileImgs/dtile.png",100,100);
			idleLeft = new SpriteSheet("tileImgs/dtile.png",100,100);
			idleRight = new SpriteSheet("tileImgs/dtile.png",100,100);
			
			slashRight = new SpriteSheet("enemies/enemyRight.png",100,100);
			slashLeft = new SpriteSheet("enemies/enemyLeft.png",100,100);
			slashUp = new SpriteSheet("enemies/enemyUp.png",100,100);
			slashDown = new SpriteSheet("enemies/enemyDown.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	
	public EntityEnemy(String name, int hp, int locationX, int locationY){
		super(name, hp, locationX, locationY);
	}
	
	public SpriteSheet getIdleSheet(){
		switch(direction){
			case 'U': return idleUp;
			case 'D': return idleDown;
			case 'L': return idleLeft;
			default: return idleRight;
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
	
	public boolean canAttack(Room room){
		if (attacks>0) {
			int playerX = room.getPlayerX();
			int playerY = room.getPlayerY();
			if (playerX == locationX && playerY == locationY + 1) {
				this.setDirection('D');
				attacks--;
				return true;
			}
			if (playerX == locationX && playerY == locationY - 1) {
				this.setDirection('U');
				attacks--;
				return true;
			}
			if (playerY == locationY && playerX == locationX + 1) {
				this.setDirection('R');
				attacks--;
				return true;
			}
			if (playerY == locationY && playerX == locationX - 1) {
				this.setDirection('L');
				attacks--;
				return true;
			}
			return false;
		}
		return false;
	}
	
	public int hitPlayer(Room room){
		EntityPlayer player = room.getPlayer();
		
		return player.onHit(atk);
	}
	
	
	// Moves enemy randomly using rng 
	// Calls room to move the entity
	
	
	public boolean move(Room room){
		int toMove = (int)(Math.random()*100);
		int dir = toMove % 4;
		switch(dir){
		case 0: return room.moveEntity(this, locationX-1, locationY);
		case 1: return room.moveEntity(this, locationX+1, locationY); 
		case 2: return room.moveEntity(this, locationX, locationY-1); 
		case 3: return room.moveEntity(this, locationX, locationY+1); 
		}
		return false;
	}
	
	public void refresh(){
		attacks = 1;
	}
}
