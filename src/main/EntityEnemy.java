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
}
