package com.mycompany.a2;

public interface IIterator {
	// boolean check if there is more item
	public boolean hasNext();
	
	// return the next object
	public GameObject getNext();
	
	// remove an object from the collection
	public void remove(GameObject gameObject);
	
	// return current object
	public GameObject getCurrent();
}
