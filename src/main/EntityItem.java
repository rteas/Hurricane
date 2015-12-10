package main;

// Also the treasure
public class EntityItem extends Entity{
	public EntityItem(String name, int hp, int locationX, int locationY){
		super(name,hp,locationX,locationY);
	}
	
	public int onHit(Entity entity){
		if(entity instanceof EntityPlayer){
			// switch items
		}
		return 1;
	}
}
