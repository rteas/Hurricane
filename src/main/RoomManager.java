package main;

import java.util.LinkedList;

// Upper level of managing what happens in the room and the interactions
public class RoomManager {
	private Room room;
	private EntityPlayer player;
	private boolean playerTurn = true;
	
	// Add all the entities from room
	public RoomManager(Room room){
		this.room = room;
		this.player = room.getPlayer();

	}
	
	// moves the player
	public boolean movePlayer(char direction){

		return (player.move(room,direction));
		
	}
	
	public void endPlayerTurn(){
		playerTurn = false;
	}
	
	public void startPlayerTurn(){
		playerTurn = true;
		for(Entity e : room.getEntities()){
			if(e instanceof EntityEnemy){
				EntityEnemy en = (EntityEnemy)e;
				en.refresh();
			}
		}
	}
	
	public boolean isPlayerTurn(){
		return playerTurn;
	}
	
	// Update map for enemy, Execute enemy move, update room (entity move() default returns Room)
	// Done in BasicGame: update()
	/*
	public boolean moveEnemies(){
		for(Entity e : room.getEntities()){
			if(e instanceof EntityEnemy){
				e.move(room);
			}
		}
		startPlayerTurn();
		return true;
	}
	*/
	
	
	// Changes the room
	public Room changeRoom(Room room){
		Room old = this.room;
		this.room = room;
		return old;
	}
	
	
	// Getters
	public Room getRoom(){
		return room;
	}
	
	public LinkedList<Entity> getEntities(){
		return room.getEntities();
	}
	
	public EntityPlayer getPlayer(){
		return player;
	}
	

}
