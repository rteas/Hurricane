package main;

public class EntityEnemy extends Entity {
	public EntityEnemy(String name, int hp, int locationX, int locationY){
		super(name, hp, locationX, locationY);
	}
	
	public int hitPlayer(Room room){
		EntityPlayer player = room.getPlayer();
		int dmgDealt = atk-player.def;
		
		player.onHit(room, dmgDealt);
		
		return dmgDealt;
	}
	
	public int move(){
		return 0;
	}
	
	public int attackPlayer(Room room){
		return room.getPlayer().onHit(room, atk);
	}
	
	// Moves enemy randomly using rng 
	// Calls room to move the entity
	public void move(Room room){
		int toMove = (int)(Math.random()*100);
		int dir = toMove % 4;
		switch(dir){
		case 0: room.moveEntity(this, locationX-1, locationY); break;
		case 1: room.moveEntity(this, locationX+1, locationY); break;
		case 2: room.moveEntity(this, locationX, locationY-1); break;
		case 3: room.moveEntity(this, locationX, locationY+1); break;
		}
	}
}
