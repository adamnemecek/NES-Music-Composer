package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.commands.Command;

public class NoteEditAction implements Command {
	
	private Note note;
	private int xUpdate;
	private int yUpdate;
	
	public NoteEditAction(Note note, int xUpdate, int yUpdate) {
		this.note = note;
		this.xUpdate = xUpdate;
		this.yUpdate = yUpdate;
	}

	@Override
	public void execute() {
		note.updateNoteValues(xUpdate, yUpdate);
	}

	@Override
	public void undo() {
		note.updateNoteValues(-xUpdate, -yUpdate);
	}

	@Override
	public void redo() {
		execute();
	}

}
