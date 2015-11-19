package main;

import java.util.LinkedList;

// Tree of rooms
// Currently linkedList
public class Level {
	LinkedList<Room> rooms;
	
	public Level(Room room){
		rooms.add(room);
	}
	
	public void addRoom(Room room){
		rooms.add(room);
	}
}
