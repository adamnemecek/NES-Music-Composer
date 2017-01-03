package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.commands.Command;
import com.bibler.awesome.nesmusiccomposer.ui.Keyboard;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRoll;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRollView;

public class CompositionAction implements Command {

	private int actionType;
	private int currentVoice;
	private int currentNoteLength;
	private InputAction inputAction;
	private int keyValue;
	private Note affectedNote;
	
	private MusicStream stream;
	
	public CompositionAction(int actionType, int currentNoteLength, InputAction inputAction, MusicStream stream) {
		this.actionType = actionType;
		this.inputAction = inputAction;
		this.stream = stream;
		this.currentNoteLength = currentNoteLength;
	}
	
	public CompositionAction(int actionType, int currentNoteLength, int keyValue, MusicStream stream) {
		this.actionType = actionType;
		this.keyValue = keyValue;
		this.stream = stream;
		this.currentNoteLength = currentNoteLength;
	}

	public int getActionType() {
		return actionType;
	}
	
	@Override
	public void execute() {
		Object clickedObject = inputAction != null ? inputAction.getClickedObject() : null;
		switch(actionType) {
			case CompositionManager.ADD_NOTE_TO_END:
				if(clickedObject == null) {
					affectedNote = stream.addNoteToEnd(keyValue, currentNoteLength);
				} else if(clickedObject instanceof PianoRollView) {
					affectedNote = stream.addNoteToEnd(inputAction.getInputPos().y, currentNoteLength);
				} 
				break;
		case CompositionManager.ADD_NOTE_IN_PLACE:
			if(clickedObject instanceof PianoRollView) {
				affectedNote = stream.addNoteInPlace(inputAction.getInputPos(), currentNoteLength);
			}
			break;
		}
	}

	@Override
	public void undo() {
		if(affectedNote != null) {
			affectedNote.removeThyself();
		}
	}

	@Override
	public void redo() {
		execute();
	}
}
