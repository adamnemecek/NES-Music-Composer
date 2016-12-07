package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;

public class PianoRollVoice {
	
	private ArrayList<Notebar> notes;
	private int voiceNumber;
	
	public PianoRollVoice(int voiceNumber) {
		this.voiceNumber = voiceNumber;
		notes = new ArrayList<Notebar>();
	}
	
	public void addNote(Notebar note) {
		notes.add(note);
	}
	
	public void paint(Graphics g, Point dims, int[] laneNums) {
		for(Notebar note : notes) {
			note.paint(g, dims, laneNums);
		}
	}
	
	public MusicStream outputMusicStream() {
		MusicStream stream = new MusicStream();
		Collections.sort(notes, new NotebarComparator());
		for(Notebar note : notes) {
			System.out.println(note.toString());
		}
		return stream;
	}
	
	
	private class NotebarComparator implements Comparator<Notebar> {

		@Override
		public int compare(Notebar o1, Notebar o2) {
			return o1.getNoteX() - o2.getNoteX();
		}
		
	}

}
