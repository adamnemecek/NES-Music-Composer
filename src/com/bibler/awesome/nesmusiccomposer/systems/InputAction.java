package com.bibler.awesome.nesmusiccomposer.systems;

import java.awt.Point;

public class InputAction {
	
	private int inputActionType;
	private Point inputPos;
	private int ID;
	private Object objectClicked;
	
	public InputAction(int inputActionType, Point inputPos, int ID, Object objectClicked) {
		this.inputActionType = inputActionType;
		this.inputPos = inputPos;
		this.ID = ID;
		this.objectClicked = objectClicked;
	}
	
	public int getID() {
		return ID;
	}

	public int getInputActionType() {
		return inputActionType;
	}
	
	public Point getInputPos() {
		return inputPos;
	}
	
	public Object getClickedObject() {
		return objectClicked;
	}
	
	

}
