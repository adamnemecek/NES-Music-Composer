package com.bibler.awesome.nesmusiccomposer.systems;

import java.awt.event.KeyEvent;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.toolbars.ToolBar;
import com.bibler.awesome.nesmusiccomposer.ui.Keyboard;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRoll;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRollView;
import com.bibler.awesome.nesmusiccomposer.utils.UndoStack;

public class CompositionManager implements Notifiable {
	
	public static final int ADD_NOTE_IN_PLACE = 0x00;
	public static final int ADD_NOTE_TO_END = 0x01;
	public static final int EDIT_NOTE = 0x02;
	
	public static final int FREE_STYLE = 0xFE;
	
	private int currentNoteLength = 8;
	private int currentVoice;
	private int currentEditMode = FREE_STYLE;
	private int currentOctave = 0;
	
	private MusicStream[] streams = new MusicStream[3];
	
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
			case "OCTAVE":
				currentOctave = Integer.parseInt(messagePieces[1]);
				break;
			case "NEW_NOTE_KEYBOARD":
				addNote(NoteTable.getKeyFromString(messagePieces[1] + currentOctave));
				break;
			case "NOTE_EDIT_POS":
				moveNote(Integer.parseInt(messagePieces[1]), inputManager.getCurrentlySelectedNote());
				break;
			case "NOTE_EDIT_LENGTH":
				editNoteLength(Integer.parseInt(messagePieces[1]), inputManager.getCurrentlySelectedNote());
				break;
				
		}
	}
	
	private void moveNote(int keyCode, Note note) {
		NoteEditAction action = null;
		switch(keyCode) {
		case KeyEvent.VK_UP:
			action = new NoteEditAction(note, 0, 1);
			break;
		case KeyEvent.VK_DOWN:
			action = new NoteEditAction(note, 0, -1);
			break;
		case KeyEvent.VK_RIGHT:
			action = new NoteEditAction(note, 1, 0);
			break;
		case KeyEvent.VK_LEFT:
			action = new NoteEditAction(note, -1, 0);
			break;
		}	
		UndoStack.executeAndStore(action);
	}
	
	private void editNoteLength(int keyCode, Note note) {
		NoteLengthEditAction action = null;
		switch(keyCode) {
		case KeyEvent.VK_RIGHT:
			action = new NoteLengthEditAction(note, 1);
			break;
		case KeyEvent.VK_LEFT:
			action = new NoteLengthEditAction(note, -1);
			break;
		}	
		UndoStack.executeAndStore(action);
	}
	
	private void handleToolbarMessage(String message) {
		if(message.contains("note")) {
			updateCurrentNoteLength(message);
		} else if(message.contains("wave")) {
			updateCurrentVoice(message);
		}
	}
	
	private void updateCurrentNoteLength(String message) {
		currentNoteLength = NoteTable.getNote(message);
	}
	
	private void updateCurrentVoice(String message) {
		switch(message) {
		case "button_square_one_wave":
			currentVoice = 0;
			break;
		case "button_square_two_wave":
			currentVoice = 1;
			break;
		case "button_tri_wave":
			currentVoice = 2;
			break;
		case "button_noise_wave":
			currentVoice = 3;
			break;
		}
	}
	
	private void handleKeyboardClick(String message) {
		if(message.contains("KEY_CLICKED")) {
			String[] values = message.split(":");
			int keyValue = Integer.parseInt(values[1]);
			addNote(keyValue);
		}
	}
	
	private void addNote(int keyValue) {
		UndoStack.executeAndStore(new CompositionAction(ADD_NOTE_TO_END, currentNoteLength, keyValue, streams[currentVoice]));
	}
	
	private void addNote(InputAction action) {
		CompositionAction compAction = null;
		MusicStream stream = streams[currentVoice];
		switch(action.getInputActionType()) {
		case InputManager.KEY_TYPED:
			break;
		case InputManager.PIANO_ROLL_CLICKED:
			if(currentEditMode == FREE_STYLE) {
				compAction = new CompositionAction(ADD_NOTE_IN_PLACE, currentNoteLength, action, stream);
			} else {
				compAction = new CompositionAction(ADD_NOTE_TO_END, currentNoteLength, action, stream);
			}
			
			break;
		}
		UndoStack.executeAndStore(compAction);
	}
	
	
	public void takeNotice(String message, Object notifier) {
		if(notifier instanceof InputManager) {
			handleInput(message, notifier);
		} else if(notifier instanceof ToolBar) {
			handleToolbarMessage(message);
		} else if(notifier instanceof Keyboard) {
			handleKeyboardClick(message);
		}
	}

}
