package com.algonquincollege.megh0011.todolist;
/**
 * Brief description / purpose of the class.
 * 
 * @author Meghna Meghna (megh0011)
 * @version 1.0
 * 
 */
public class todoTable {
	private long todoID;
	private String todo;
	
	public long gettodoID() {
		return todoID;
	}
	public void settodoID(long todoID) {
		this.todoID = todoID;
	}
	public String gettodo() {
		return todo;
	}
	public void settodo(String todo) {
		this.todo = todo;
	}

	@Override
	public String toString() {
		return todo;
	}
} 
