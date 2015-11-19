package main;

public class Entity {
	private String name;
	private int hp;
	private int def;
	private int atk;
	private int mp;
	private int moves;
	private int attacks;
	private int locationX;
	private int locationY;
	private int speed;
	// 'U' for UP, 'D' for DOWN, 'L' for LEFT, 'R' for RIGHT
	private char direction;
		
	public Room room;
	
	// Room will be used as a 'map'
	public Entity(String name, int hp, int locationX, int locationY, Room room){
		this.name = name;
		this.hp = hp;
		this.locationX = locationX;
		this.locationY = locationY;
		this.room = room;
		moves = 1;
		attacks = 1;
		speed = 200;
	}
	
	// Moves to x and y coordinate provided, removes a move.
	public Room move(char d){
		if(moves > 0){
			// Obtain player location (probably better in enemy entity)
			room.getPlayerX();
			room.getPlayerY();
			moves--;
			
			// Update map
			room.moveEntity(this,locationX, locationY);
			updateRoom(room);
		}
		return room;
	}
	
	public void attack(){
		if(attacks > 0){
			attacks--;
		}	
	}
	
	
	// Getters
	
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
	
	public void updateRoom(Room room){
		this.room = room;
	}
	
	public Room getRoom(){
		return room;
	}
	
}
