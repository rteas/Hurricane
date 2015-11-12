package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame{

	public Main(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		AppGameContainer appgc;
		
		try 
		{
			appgc = new AppGameContainer(new Main("Prisoner of Anorahh")); // Set title
			appgc.setDisplayMode(1366, 768, false); // Display parameters
			appgc.setAlwaysRender(true); // Constant rendering
			appgc.start();
		} catch (SlickException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
		addState(new BasicGame());
		
	}

}
