package com.mycompany.a3;
import java.util.ArrayList;

public class GameObjectCollection implements ICollection{
	private ArrayList<GameObject> gameObjects;
	
	// Constructor
	public GameObjectCollection() {
			gameObjects = new ArrayList<GameObject>();
	}
	
	//	add new object to the list
	@Override
	public void add(GameObject newGameObject) {
		gameObjects.add(newGameObject);
	}
	
	@Override
	public IIterator getIterator() {
		return new GameWorldIterator();
	}
	
	private class GameWorldIterator implements IIterator{
		private int currentIndex;
		
		public GameWorldIterator() {
			currentIndex = -1;		// no item in the list
		}

		// interface IIterator
		@Override
		public boolean hasNext() {
			// if the list is empty
			if(gameObjects.size() <= 0)
				return false;
			// if it's pointing at the last item in the list
			if(currentIndex == gameObjects.size() - 1) {
				return false;
			}
			
			return true;
		}
		
		@Override
		public GameObject getNext() {
			currentIndex++;
			return gameObjects.get(currentIndex);
		}
		
		@Override
		public void remove(GameObject gameObject) {
			gameObjects.remove(gameObject);
		}
		
		@Override 
		public GameObject getCurrent() {
			return gameObjects.get(currentIndex);
		}
	}
	
	
	
}	
