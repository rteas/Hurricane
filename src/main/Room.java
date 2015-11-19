package main;

import java.util.LinkedList;

public class Room {
	
	private EntityPlayer player;
	
	private Tile[][] tileLayer;
	private Entity[][] entityLayer;
	private int xTiles;
	private int yTiles;

	// Initialize with tiles, player location
	public Room(int xTiles, int yTiles){
		this.xTiles = xTiles;
		this.yTiles = yTiles;
		
		tileLayer = new Tile[xTiles][yTiles];
		entityLayer = new Entity[xTiles][yTiles];
		
		for(int i=0;i<xTiles;i++){
			for(int j=0;j<yTiles;j++){				
				tileLayer[i][j] = new Tile(1);
			}
		}
	}
	
	// Add player 
	public void addEntityPlayer(EntityPlayer entity){
		player = entity;
	}
	
	// If there is not an entity, add it
	public boolean addEntity(Entity entity, int x, int y){		
		if(entityAt(x,y)) {
			return false;
		}
		entityLayer[x][y] = entity;
		
		return true;
	}
	
	// If there is an entity, delete it	
	public void deleteEntity(int x, int y){
		if(entityAt(x,y)){
			entityLayer[x][y] = null;
		}
	}
	
	public boolean collidesWith(int x, int y){
		if(entityAt(x,y)) return true;
		return false;
	}
	
	public Entity getCollision(int x, int y){
		if(entityAt(x,y)) {
			return entityLayer[x][y];
		}
		return null;
	}

	// Move entity to specified coordinates if 
	// there is no collision or the entity is within the bounds of the room
	public boolean moveEntity(Entity entity, int x, int y){
		if(x > xTiles-1 || x < 0 || y > yTiles-1 || y < 0) {
			return false;
		}
		else if(collidesWith(x,y)){
			return false;
		}
		else{
			deleteEntity(entity.getLocationX(),entity.getLocationY());
			addEntity(entity,x,y);
			return true;
		}
	}
	
	// Checks if there is something there
	public boolean entityAt(int x, int y){
		if(entityLayer[x][y] == null) return false;
		else return true;
	}
	
	public Entity getEntity(int x, int y){
		if(entityAt(x,y))
			return entityLayer[x][y];
		return null;
	}
	
	public int xMax(){
		return xTiles;
	}
	
	public int yMax(){
		return yTiles;
	}
	
	public Tile[][] getTileLayer(){
		return tileLayer;
	}
	
	public Entity[][] getEntityLayer(){
		return entityLayer;
	}
	
	public int getTile(int x, int y){
		return tileLayer[x][y].getTile();
	}
	
	//=============PLAYER SPECIFIC=========

	// Returns players location (x and y coordinates)
	
	public int getPlayerX(){
		if(player != null)
			return player.getLocationX();
		else return -1;
	}
	
	public int getPlayerY(){
		if(player != null)
			return player.getLocationY();
		else return -1;
	}
	
	
}
