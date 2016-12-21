package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRollView;
import com.bibler.awesome.nesmusiccomposer.utils.UndoStack;

public class CompositionManager implements Notifiable {
	
	public static final int ADD_NOTE_IN_PLACE = 0x00;
	public static final int ADD_NOTE_TO_END = 0x01;
	public static final int EDIT_NOTE = 0x02;
	
	private int currentNoteLength = 8;
	private int currentVoice;
	
	private MusicStream[] streams = new MusicStream[3];;
	private int[] currentStreamIndexes = new int[3];
	
	public CompositionManager() {
		
	}
	
	public void setStream(MusicStream stream, int streamNum) {
		streams[streamNum] = stream;
	}
	
	private void handleInput(String message, Object notifier) {
		final InputManager inputManager = (InputManager) notifier;
		String[] messagePieces = message.split(":");
		switch(messagePieces[0]) {
		case "EDIT":
			break;
		case "NEW_NOTE":
			addNote(inputManager.getPendingAction(Integer.parseInt(messagePieces[1])));
			break;
		}
	}
	
	private void addNote(InputAction action) {
		CompositionAction compAction = null;
		switch(action.getInputActionType()) {
		case InputManager.KEY_TYPED:
			break;
		case InputManager.PIANO_ROLL_CLICKED:
			compAction = new CompositionAction(ADD_NOTE_TO_END, currentNoteLength, currentVoice, action);
			break;
		case InputManager.VIRTUAL_KEYBOARD_CLICKED:
			compAction = new CompositionAction(ADD_NOTE_TO_END, currentNoteLength, currentVoice, action);
			break;
		}
		UndoStack.executeAndStore(compAction);
	}
	
	
	public void takeNotice(String message, Object notifier) {
		if(notifier instanceof InputManager) {
			handleInput(message, notifier);
		}
	}

}
