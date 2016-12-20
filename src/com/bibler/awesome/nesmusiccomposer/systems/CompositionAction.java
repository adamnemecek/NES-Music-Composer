package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.commands.Command;
import com.bibler.awesome.nesmusiccomposer.ui.Keyboard;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRollView;

public class CompositionAction implements Command {

	private int actionType;
	private int currentVoice;
	private int currentNoteLength;
	private InputAction inputAction;
	
	public CompositionAction(int actionType, int currentVoice, int currentNoteLength, InputAction inputAction) {
		this.actionType = actionType;
		this.inputAction = inputAction;
		this.currentVoice = currentVoice;
		this.currentNoteLength = currentNoteLength;
	}
	
	public int getActionType() {
		return actionType;
	}
	
	@Override
	public void execute() {
		switch(actionType) {
		case CompositionManager.ADD_NOTE_TO_END:
			Object clickedObject = inputAction.getClickedObject();
			if(clickedObject instanceof PianoRollView) {
				PianoRollView view = (PianoRollView) inputAction.getClickedObject();
				view.addNoteToEnd(inputAction.getInputPos(), currentVoice, currentNoteLength);
			} else if(clickedObject instanceof Keyboard) {
				
			}
			break;
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}
}
