package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Entity {
	
	private SpriteSheet idleSheet;
	{
		try{
			idleSheet = new SpriteSheet("tileImgs/dTile.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	public String name;
	public int hp;
	public int def;
	public int atk;
	public int mp;
	public int moves;
	public int attacks;
	// Formerly locationX, locationY
	public int locationX;
	public int locationY;
	public int speed;
	public boolean idle = true;
	public boolean attacking = false;
	
	// 'U' for UP, 'D' for DOWN, 'L' for LEFT, 'R' for RIGHT
	public char direction = 'R';
		
//	public Room room;
	
	// Room will be used as a 'map'
	public Entity(String name, int hp, int locationX, int locationY){
		this.name = name;
		this.hp = hp;
		this.locationX = locationX;
		this.locationY = locationY;

		atk = 100;
		def = 50;
		moves = 1;
		attacks = 1;
		speed = 200;
	}
	
	// Moves to x and y coordinate provided, removes a move.
	public void move(Room room){
		if(moves > 0){
			// Obtain player location (probably better in enemy entity)
			room.getPlayerX();
			room.getPlayerY();
			moves--;
			
			// Update map
//			room.moveEntity(this,locationX, locationY);
//			updateRoom(room);
		}
//		return room;
	}
	
	public boolean canMove(Room room, int x, int y){
		return true;
	}
	
	public void attack(){
		if(attacks > 0){
			attacks--;
		}	
	}
	
	public void updateEntity(Room room){
		if(this.defeated()){
			room.deleteEntity(locationX, locationY);
		}
	}
	
	
	// Default method called when hit
	public void onHit(Room room,int atk){
		hp = hp - (atk-def);
		if(hp <= 0){
			updateEntity(room);
		}
	}
	
	public boolean defeated(){
		return hp <= 0;
	}
	
	// Setters
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	public void setLocationX(int x){
		this.locationX = x;
	}
	
	public void setLocationY(int y){
		this.locationY = y;
	}
	
	public void setIdle(boolean b){
		idle = b;
	}
	public void setAttacking(boolean b){
		attacking = b;
	}
	
	// Getters
	
	public SpriteSheet getIdleSheet(){
		return idleSheet;
	}
	public char getDirection(){
		return direction;
	}
	
	public boolean isIdle(){
		return idle;
	}
	
	public boolean isAttacking(){
		return attacking;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHp(){
		return hp;
	}
	
	public int getMoves(){
		return moves;
	}
	
	public int getAttacks(){
		return attacks;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public int getLocationX(){
		return locationX;
	}
	
	public int getLocationY(){
		return locationY;
	}
	/*
	public void updateRoom(Room room){
		this.room = room;
	}
	
	public Room getRoom(){
		return room;
	}
	*/
	
}
