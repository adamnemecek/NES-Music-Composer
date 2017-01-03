package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.commands.Command;

public class NoteLengthEditAction implements Command {
	
	private Note note;
	private int xUpdate;
	
	public NoteLengthEditAction(Note note, int xUpdate) {
		this.note = note;
		this.xUpdate = xUpdate;
	}

	@Override
	public void execute() {
		note.updateNoteLength(xUpdate);
	}

	@Override
	public void undo() {
		note.updateNoteLength(-xUpdate);
	}

	@Override
	public void redo() {
		execute();
	}

}
