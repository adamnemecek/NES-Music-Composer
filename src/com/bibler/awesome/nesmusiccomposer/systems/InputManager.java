package com.bibler.awesome.nesmusiccomposer.systems;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;

public class InputManager implements Notifier {
	
	public static final int KEY_TYPED = 0xFE;
	public static final int VIRTUAL_KEYBOARD_CLICKED = 0xFF;
	public static final int PIANO_ROLL_CLICKED = 0x100;
	public static final int NOTE_CLICKED = 0x101;
	public static final int NOTE_CLEARED = 0x102;
	
	private ArrayList<InputAction> actions = new ArrayList<InputAction>();
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	private Note currentlySelectedNote;
	
	public InputManager() {
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
			  @Override
			  public boolean dispatchKeyEvent(final KeyEvent e) {
				  return processKey(e);
			  }
			};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
	}
	
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
		case NOTE_CLICKED:
			currentlySelectedNote = (Note) action.getClickedObject();
			break;
		case NOTE_CLEARED:
			currentlySelectedNote = null;
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
	
	private boolean processKey(KeyEvent e) {
		if(e.getID() != KeyEvent.KEY_RELEASED) {
			return false;
		}
		switch(e.getKeyCode()) {
		case KeyEvent.VK_1:
		case KeyEvent.VK_2:
		case KeyEvent.VK_3:
		case KeyEvent.VK_4:
		case KeyEvent.VK_5:
		case KeyEvent.VK_6:
		case KeyEvent.VK_7:
		case KeyEvent.VK_8:
		case KeyEvent.VK_9:
			notify("OCTAVE:" + Integer.parseInt("" + e.getKeyChar()));
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_B:
		case KeyEvent.VK_C:
		case KeyEvent.VK_D:
		case KeyEvent.VK_E:
		case KeyEvent.VK_F:
		case KeyEvent.VK_G:
			String note = ("" + e.getKeyChar()).toUpperCase();
			if(e.isControlDown()) {
				note += "s";
			}
			notify("NEW_NOTE_KEYBOARD:" + note);
			break;
		case KeyEvent.VK_NUMPAD0:	// Whole Note
		case KeyEvent.VK_NUMPAD1:	// Half Note
		case KeyEvent.VK_NUMPAD2:	// Quarter Note
		case KeyEvent.VK_NUMPAD3:	// Eighth Note	
		case KeyEvent.VK_NUMPAD4:	// Sixteenth Note
		case KeyEvent.VK_NUMPAD5:	// Thirty second note
			notify("CHANGE_NOTE:" + Integer.parseInt("" + e.getKeyChar()));
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_LEFT:
			if(e.isControlDown()) {
				notify("NOTE_EDIT_LENGTH:" + e.getKeyCode());
			} else {
				notify("NOTE_EDIT_POS:" + e.getKeyCode());
			}
			break;
		}
		return true;
	}

	public Note getCurrentlySelectedNote() {
		return currentlySelectedNote;
	}
	
	@Override
	public void notify(String messageToSend) {
		for(Notifiable objectToNotify : objectsToNotify) {
			objectToNotify.takeNotice(messageToSend, this);
		}
	}
	

}
