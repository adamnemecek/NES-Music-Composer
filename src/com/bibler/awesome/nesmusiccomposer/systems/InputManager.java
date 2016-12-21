package com.bibler.awesome.nesmusiccomposer.systems;

import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;

public class InputManager implements Notifier {
	
	public static final int KEY_TYPED = 0xFE;
	public static final int VIRTUAL_KEYBOARD_CLICKED = 0xFF;
	public static final int PIANO_ROLL_CLICKED = 0x100;
	
	private ArrayList<InputAction> actions = new ArrayList<InputAction>();
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	public void registerObjectToNotify(Notifiable objectToNotify) {
		objectsToNotify.add(objectToNotify);
	}

	
	public void registerInputAction(InputAction action) {
		actions.add(action);
		action.setId(actions.size() - 1);
		switch(action.getInputActionType()) {
		case PIANO_ROLL_CLICKED:
			notify("NEW_NOTE:" + action.getID());
			break;
		}
	}
	
	public InputAction getPendingAction(int actionID) {
		for(InputAction action : actions) {
			if(action.getID() == actionID) {
				actions.remove(action);
				return action;
			}
		}
		return null;
	}

	@Override
	public void notify(String messageToSend) {
		for(Notifiable objectToNotify : objectsToNotify) {
			objectToNotify.takeNotice(messageToSend, this);
		}
	}
	

}
