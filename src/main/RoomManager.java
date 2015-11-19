package main;

import java.util.LinkedList;

public class RoomManager {
	private Room room;
	private EntityPlayer player;
	private LinkedList<Entity> entities;
	
	// Add all the entities from room
	public RoomManager(Room room, EntityPlayer player){
		this.room = room;
		this.player = player;
		
		entities = new LinkedList<Entity>();
		for(int x=0;x<room.xMax();x++){
			for(int y=0;y<room.yMax();y++){
				if(room.entityAt(x, y)){
					entities.add(this.room.getEntity(x, y));
				}
			}
		}
		room.addEntityPlayer(this.player);
	}
	
	// moves the player
	public boolean movePlayer(char direction){
		player.updateRoom(room);
		if(player.move(direction)){
			room = player.getRoom();
			return true;
		}
		return false;
	}
	
	// Update map for enemy, Execute enemy move, update room (entity move() default returns Room)
	public boolean moveEnemies(){
		for(Entity e : entities){
			e.updateRoom(room);
			room = e.move();
		}
		return true;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public LinkedList<Entity> getEntities(){
		return entities;
	}
	
	public EntityPlayer getPlayer(){
		return player;
	}

}
