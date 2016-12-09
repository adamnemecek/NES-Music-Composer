package com.bibler.awesome.nesmusiccomposer.commands;

import java.awt.Point;

import com.bibler.awesome.nesmusiccomposer.audio.Note;

public class UpdateNoteCommand implements Command {
	
	private Note note;
	private Point newNoteProperties;
	private Point oldNoteProperties;
	
	public UpdateNoteCommand(Note note, Point noteProperties) {
		this.note = note;
		this.newNoteProperties = noteProperties;
		this.oldNoteProperties = note.getNoteProperties();
	}

	@Override
	public void execute() {
		note.setNoteValues(newNoteProperties.x, newNoteProperties.y);
	}

	@Override
	public void undo() {
		note.setNoteValues(oldNoteProperties.x, oldNoteProperties.y);
	}

	@Override
	public void redo() {
		execute();
	}

}
