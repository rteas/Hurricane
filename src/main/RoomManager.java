package main;

import java.util.LinkedList;

// Upper level of managing what happens in the room and the interactions
public class RoomManager {
	private Room room;
	private EntityPlayer player;
	
	// Add all the entities from room
	public RoomManager(Room room, EntityPlayer player){
		this.room = room;
		this.player = player;

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
	
	// Player atk conditions
	public String playerAtk(Entity entity){
		// player hits enemy
		if(entity instanceof EntityEnemy){
			entity.onHit(player.atk);
			updateEntity(entity);
			return entity.getName();
		}
		// player hits item
		else if(entity instanceof EntityItem){
			room.deleteEntity(entity.getLocationX(), entity.getLocationY());
			return entity.getName();
		}
		// player hits obstacle
		else if(entity instanceof EntityObstacle){
			return entity.getName();
		}
		else return "X";
	}
	
	public void enemyAtk(Entity entity){
		
	}
	
	// Update map for enemy, Execute enemy move, update room (entity move() default returns Room)
	public boolean moveEnemies(){
		for(Entity e : room.getEntities()){
			e.updateRoom(room);
			room = e.move();
		}
		return true;
	}
	
	// Updates data on entities
	public void updateEntity(Entity e){
		int x = e.getLocationX();
		int y = e.getLocationY();
		room.deleteEntity(x, y);
		if(!e.defeated()) room.addEntity(e, x, y);
	}
	
	// Changes the room
	public Room changeRoom(Room room){
		Room old = this.room;
		this.room = room;
		return old;
	}
	
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
