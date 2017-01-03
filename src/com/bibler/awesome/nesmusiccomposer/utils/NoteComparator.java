package com.bibler.awesome.nesmusiccomposer.utils;

import java.util.Comparator;

import com.bibler.awesome.nesmusiccomposer.audio.Note;

public class NoteComparator implements Comparator <Note> {
	
	public NoteComparator() {
		
	}

	@Override
	public int compare(Note note1, Note note2) {
		return note1.getNoteX() - note2.getNoteX();
	}

}
