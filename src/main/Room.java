package main;

import java.util.LinkedList;

public class Room {
	
	private EntityPlayer player;
	
	// Stores enemy entities, obstacles, and items
	/*
	private LinkedList<EntityEnemy> enemies = new LinkedList<EntityEnemy>();
	private LinkedList<EntityObstacle> obstacles = new LinkedList<EntityObstacle>();
	private LinkedList<EntityItem> items = new LinkedList<EntityItem>();
	*/
	
	private LinkedList<Entity> entities = new LinkedList<Entity>();
	
	private Tile[][] tileLayer;
	private Entity[][] entityLayer;
	private int xTiles;
	private int yTiles;

	// Initialize with tiles, player location
	// Can initialize with entities here as well
	// Use add entity calls (see below)
	public Room(int xTiles, int yTiles){
		this.xTiles = xTiles;
		this.yTiles = yTiles;
		
		tileLayer = new Tile[xTiles][yTiles];
		entityLayer = new Entity[xTiles][yTiles];
		
		for(int x=0;x<xTiles;x++){
			for(int y=0;y<yTiles;y++){				
				tileLayer[x][y] = new Tile(1);
			}
		}
	}
	
	// General entity
	public boolean addEntity(Entity entity, int x, int y){
		if(entityAt(x,y)) {
			return false;
		}
		entityLayer[x][y] = entity;
		entities.add(entity);

		return true;
	}
	
	// Add player 
	public boolean addEntityPlayer(EntityPlayer entity){
		player = entity;
		if(entityAt(player.getLocationX(),player.getLocationY())){
			return false;
		}
		entityLayer[player.getLocationX()][player.getLocationY()] = player;
		return true;
	}
	
	/* Can all be stored in the same data structure
	 * 
	 * 
	// If there is not an entity, add an Obstacle Entity
	public boolean addObstacleEntity(EntityObstacle obstacle, int x, int y){		
		if(entityAt(x,y)) {
			return false;
		}
		entityLayer[x][y] = obstacle;
		obstacles.add(obstacle);
		
		return true;
	}
	
	// If there is not an entity, add an Enemy Entity
	public boolean addEnemyEntity(EntityEnemy enemy, int x, int y){		
		if(entityAt(x,y)) {
			return false;
		}
		entityLayer[x][y] = enemy;
		enemies.add(enemy);
		
		return true;
	}
	
	public boolean addItemEntity(EntityItem item, int x, int y){
		if(entityAt(x,y)) {
			return false;
		}
		entityLayer[x][y] = item;
		items.add(item);
		
		return true;
	
	}
	*/
	
	

	// If there is an entity, delete it	
	public void deleteEntity(int x, int y){
		if(entityAt(x,y)){
			Entity rem = entityLayer[x][y];
			entityLayer[x][y] = null;
			for(int i=0;i<entities.size();i++){
				Entity check = entities.get(i);
				if(check.getLocationX() == rem.getLocationX() && check.getLocationY() == rem.getLocationY())
					entities.remove(i);
			}
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
	
	// Checks surroundings if there is something (up/down/left/right)
	public boolean entityAround(Entity e){
		int xLoc = e.getLocationX();
		int yLoc = e.getLocationY();
		
		// Check around (left, right, up, down) if there is an entity. If so, return true
		
		return false;
	}
	
	// Checks if there is something there
	public boolean entityAt(int x, int y){
		if(x > xTiles-1 || x < 0 || y > yTiles-1 || y < 0) return false;
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
	
	// Getters
	
	public int getTile(int x, int y){
		return tileLayer[x][y].getTile();
	}
	
	/*
	public LinkedList<EntityEnemy> getEnemies(){
		return enemies;
	}
	
	public LinkedList<EntityObstacle> getObstacles(){
		return obstacles;
	}
	
	public LinkedList<EntityItem> getItems(){
		return items;
	}
	*/
	
	public LinkedList<Entity> getEntities(){
		return entities;
	}
	
	//=============PLAYER SPECIFIC=========

	// Returns players location (x and y coordinates)
	
	public EntityPlayer getPlayer(){
		return player;
	}
	
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
