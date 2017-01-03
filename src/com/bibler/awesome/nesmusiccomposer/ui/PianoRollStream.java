package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;

public class PianoRollStream implements Notifier {
	
	private MusicStream stream;
	
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	public PianoRollStream(MusicStream stream) {
		this.stream = stream;
	}
	
	public void registerObjectToNotify(Notifiable objectToNotify) {
		objectsToNotify.add(objectToNotify);
	}
	
	public void addNote(Note note) {
		stream.addNote(note);
	}
	
	public Note addNoteToEnd(int y, int noteLength) {
		return stream.addNoteToEnd(y, noteLength);	
	}
	
	public Note addNoteInPlace(Point inputPos, int currentNoteLength) {
		return stream.addNoteInPlace(inputPos, currentNoteLength);
	}
	
	public void removeNote(Point inputPos) {
		stream.removeNote(inputPos);	
	}
	
	public Note checkForNoteClick(int x, int y) {
		return stream.checkForNoteClick(x, y);
	}
	
	public void paint(Graphics g, Point dims, int[] laneNums, int currentMarkerX, int scrollX) {
		final ArrayList<Note> notes = stream.getNotes();
		for(Note note : notes) {
			if(note.paint(g, dims, laneNums, currentMarkerX, scrollX)) {
				notify("PAINT_NOTE:" + note.getNoteProperties().y);
			}
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

	@Override
	public void notify(String messageToSend) {
		for(Notifiable objectToNotify : objectsToNotify) {
			objectToNotify.takeNotice(messageToSend, this);
		}	
	}

}
