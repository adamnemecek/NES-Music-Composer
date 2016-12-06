package com.bibler.awesome.nesmusiccomposer.audio;

public class Note {
	
	private int noteLength;
	private int note;
	
	public Note(int noteLength, int note) {
		this.noteLength = noteLength;
		this.note = note;
	}
	
	public void setNoteLength(int noteLength) {
		this.noteLength = noteLength;
	}
	
	public int getNoteLength() {
		return noteLength;
	}
	
	public void setNote(int note) {
		this.note = note;
	}
	
	public int getNote() {
		return note;
	}

}
