package com.bibler.awesome.nesmusiccomposer.utils;

import java.awt.Color;

import com.bibler.awesome.nesmusiccomposer.audio.Note;

public class NoteFactory {
	
	private static Color[] noteColors = new Color[] {
		Color.BLUE, Color.RED, Color.GREEN	
	};
	
	public static Note createNote(int notePosition, int noteValue, int noteLength, int noteVoice) {
		Note note = new Note();
		note.setColor(noteColors[noteVoice]);
		note.setLength(noteLength);
		note.setNoteValues(notePosition, noteValue);
		return note;
	}

}
