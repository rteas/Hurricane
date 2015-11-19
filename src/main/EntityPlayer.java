package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityPlayer extends Entity{
	
	private SpriteSheet protagRight, pIdleRight, protagLeft, pIdleLeft,
						protagUp, pIdleUp, protagDown, pIdleDown;

	{
		try{
			SpriteSheet protagRight = new SpriteSheet("protagImg/walkRight.png",100,100);
			SpriteSheet pIdleRight = new SpriteSheet("protagImg/idleRight.png",100,100);
			Animation walkRight = new Animation(protagRight, 150);
		
			SpriteSheet protagLeft = new SpriteSheet("protagImg/walkLeft.png",100,100);
			SpriteSheet pIdleLeft = new SpriteSheet("protagImg/idleLeft.png",100,100);
			Animation walkLeft = new Animation(protagLeft, 150);
			
			SpriteSheet protagDown = new SpriteSheet("protagImg/walkDown.png",100,100);
			SpriteSheet pIdleDown = new SpriteSheet("protagImg/idleDown.png",100,100);
			Animation walkDown = new Animation(protagDown, 150);
			
			SpriteSheet protagUp = new SpriteSheet("protagImg/walkUp.png",100,100);
			SpriteSheet pIdleUp = new SpriteSheet("protagImg/idleUp.png",100,100);
			Animation walkUp = new Animation(protagUp, 150);
			
			
			SpriteSheet slashRight = new SpriteSheet("protagImg/slashRight.png",100,100);
			SpriteSheet slashLeft = new SpriteSheet("protagImg/slashLeft.png",100,100);
			SpriteSheet slashUp = new SpriteSheet("protagImg/slashUp.png",100,100);
			SpriteSheet slashDown = new SpriteSheet("protagImg/slashDown.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	
	public EntityPlayer(String name, int hp, int locationX, int locationY, Room room) {
		super(name, hp, locationX, locationY, room);
	}
	
	public SpriteSheet getSpriteSheet(char direction){
		return protagRight;
	}
	
	public Room move(char direction){
		int x = 0;
		int y = 0;
		switch(direction){
		
		}
		room.moveEntity(this, x, y);
		return room;
	}
	

}
