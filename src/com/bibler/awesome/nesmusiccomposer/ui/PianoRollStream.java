package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Note;

public class PianoRollStream {
	
	private MusicStream stream;
	
	public PianoRollStream(MusicStream stream) {
		this.stream = stream;
	}
	
	public void addNote(Note note) {
		stream.addNote(note);
	}
	
	public void addNote(int notePos, int noteValue, int noteLength) {
		stream.addNote(notePos, noteValue, noteLength);
	}
	
	public void paint(Graphics g, Point dims, int[] laneNums, int currentMarkerX) {
		final ArrayList<Note> notes = stream.getNotes();
		for(Note note : notes) {
			note.paint(g, dims, laneNums, currentMarkerX);
		}
	}
	
	public int getTotalNoteLength() {
		int noteLength = 0;
		final ArrayList<Note> notes = stream.getNotes();
		for(Note note : notes) {
			noteLength += note.getNoteLength();
		}
		return noteLength;
	}

}
