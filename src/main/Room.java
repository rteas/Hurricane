package main;

import java.util.LinkedList;

public class Room {
	
	private EntityPlayer player;
	
	private Tile[][] tileLayer;
	private Entity[][] entityLayer;
	private int tileSize;
	int xTiles;
	int yTiles;

	// Initialize with tiles, player location
	public Room(int xTiles, int yTiles, int tileSize){
		this.xTiles = xTiles;
		this.yTiles = yTiles;
		
		tileLayer = new Tile[xTiles][yTiles];
		entityLayer = new Entity[xTiles][yTiles];
		
		this.tileSize = tileSize;
		for(int i=0;i<xTiles;i++){
			for(int j=0;j<yTiles;j++){				
				tileLayer[i][j] = new Tile(1);
			}
		}
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

	// Move entity to specified coordinates if 
	// there is no collision or the entity is within the bounds of the room
	public boolean moveEntity(Entity entity, int x, int y){
		if(entityAt(x,y) || x > xTiles || y > yTiles) 
			return false;
		
		deleteEntity(entity.getLocationX(),entity.getLocationY());
		addEntity(entity,x,y);
		return true;
	}
	
	// Checks if there is something there
	public boolean entityAt(int x, int y){
		if(entityLayer[x][y] == null) return false;
		else return true;
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
		return player.getLocationX();
	}
	
	public int getPlayerY(){
		return player.getLocationY();
	}
	
	
}
