package main;

import java.util.LinkedList;

// Upper level of managing what happens in the room and the interactions
public class RoomManager {
	private Room room;
	private EntityPlayer player;
	
	// Add all the entities from room
	public RoomManager(Room room){
		this.room = room;
		this.player = room.getPlayer();

	}
	
	// moves the player
	public boolean movePlayer(char direction){

		if(player.move(room,direction)){

			return true;
		}
		return false;
	}
	
	// Player atk conditions (depreciated, see EntityPlayer)
	
/*	public String playerAtk(Entity entity){
		// player hits enemy
		if(entity instanceof EntityEnemy){
//			EntityEnemy e = (EntityEnemy) entity;
			Entity e = entity;
			e.onHit(room,player.atk);
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
*/
	
	
	// Update map for enemy, Execute enemy move, update room (entity move() default returns Room)
	public boolean moveEnemies(){
		for(Entity e : room.getEntities()){
			e.move(room);
		}
		return true;
	}
	
	
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
