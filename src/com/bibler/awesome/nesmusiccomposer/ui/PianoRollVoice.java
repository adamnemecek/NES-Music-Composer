package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;

public class PianoRollVoice {
	
	private ArrayList<Note> notes;
	private int voiceNumber;
	
	public PianoRollVoice(int voiceNumber) {
		this.voiceNumber = voiceNumber;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public void addNote(int notePos, int noteValue, int noteLength) {
		Note note = findNoteByValue(notePos);
		if(note == null) {
			Note bar = new Note();
			bar.setLength(noteLength);
			bar.setNoteValues(notePos, noteValue);
			notes.add(bar);
		} else {
			note.setLength(noteLength);
			note.setNoteValues(notePos, noteValue);
		}
	}
	
	private Note findNoteByValue(int notePos) {
		for(Note note : notes) {
			if(note.posFallsWithin(notePos)) {
				return note;
			}
		}
		return null;
	}
	
	public void paint(Graphics g, Point dims, int[] laneNums, int currentMarkerX) {
		for(Note note : notes) {
			note.paint(g, dims, laneNums, currentMarkerX);
		}
	}
	
	public MusicStream outputMusicStream() {
		MusicStream stream = new MusicStream(voiceNumber);
		Collections.sort(notes, new NotebarComparator());
		for(Note note : notes) {
			System.out.println(note.toString());
		}
		return stream;
	}
	
	public int getTotalNoteLength() {
		int noteLength = 0;
		for(Note note : notes) {
			noteLength += note.getNoteLength();
		}
		return noteLength;
	}
	
	
	private class NotebarComparator implements Comparator<Note> {

		@Override
		public int compare(Note o1, Note o2) {
			return o1.getNoteX() - o2.getNoteX();
		}
		
	}

}
