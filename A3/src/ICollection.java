package com.mycompany.a3;

public interface ICollection {
	// add new game object to the collection
	public void add(GameObject newGameObject);
	
	// return iterator for going through the collection
	public IIterator getIterator();
}
