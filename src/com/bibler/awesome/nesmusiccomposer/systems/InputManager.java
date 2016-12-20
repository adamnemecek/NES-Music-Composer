package com.bibler.awesome.nesmusiccomposer.systems;

import java.util.ArrayList;

public class InputManager {
	
	public static final int KEY_TYPED = 0xFE;
	public static final int VIRTUAL_KEYBOARD_CLICKED = 0xFF;
	public static final int PIANO_ROLL_CLICKED = 0x100;
	
	private ArrayList<InputAction> actions = new ArrayList<InputAction>();
	
	public InputAction getPendingAction(int actionID) {
		for(InputAction action : actions) {
			if(action.getID() == actionID) {
				return action;
			}
		}
		return null;
	}
	

}
