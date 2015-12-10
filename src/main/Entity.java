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
	
	protected String name;
	protected int hp;
	protected int maxHp;
	protected int def;
	protected int atk;
	protected int mp;
	protected int moves;
	protected int attacks;
	// Grid Location (by array)
	protected int locationX;
	protected int locationY;
	// Previous Location
	protected int prevX;
	protected int prevY;
	// Location used for drawing
	protected int drawX;
	protected int drawY;
	protected int finX;
	protected int finY;
	
	protected int speed;
	protected boolean idle = true;
	protected boolean attacking = false;
	
	// 'U' for UP, 'D' for DOWN, 'L' for LEFT, 'R' for RIGHT
	protected char direction = 'R';
		
//	public Room room;
	
	// Room will be used as a 'map'
	public Entity(String name, int hp, int locationX, int locationY){
		this.name = name;
		this.hp = hp;
		maxHp = hp;
		this.locationX = locationX;
		this.locationY = locationY;

		atk = 100;
		def = 50;
		moves = 1;
		attacks = 1;
		speed = 200;
	}
	
	// Moves to x and y coordinate provided, removes a move.
	public boolean move(Room room){
		if(moves > 0){
			// Obtain player location (probably better in enemy entity)
			room.getPlayerX();
			room.getPlayerY();
			
			// Moves once left
			
			//room.moveEntity(this, locationX-1, locationY);
			
			moves--;
			return true;
			// Update map
//			room.moveEntity(this,locationX, locationY);
//			updateRoom(room);
		}
		return false;
//		return room;
	}
	
	public boolean canMove(Room room, int x, int y){
		if(room.entityAt(x, y)) return false;
		return true;
	}
	
	public boolean attack(){
		if(attacks > 0){
			attacks--;
			return true;
		}	
		return false;
	}
	
	public void updateEntity(Room room){
		if(this.defeated()){
			room.deleteEntity(locationX, locationY);
		}
	}
	
	
	// Default method called when hit
	public int onHit(Room room,int atk){
		int dmg = atk-def;
		hp = hp - dmg;
		if(hp <= 0){
			updateEntity(room);
		}
		return dmg;
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
	
	public void setDrawX(int x){
		drawX = x;
	}
	
	public void setDrawY(int y){
		drawY = y;
	}
	
	public void setFinX(int x){
		finX = x;
	}
	
	public void setFinY(int y){
		finY = y;
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
	
	public int getDrawX(){
		return drawX;
	}
	public int getDrawY(){
		return drawY;
	}
	
	public int getFinX(){
		return finX;
	}
	public int getFinY(){
		return finY;
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
