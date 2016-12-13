package com.bibler.awesome.nesmusiccomposer.audio;

import java.awt.Point;
import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.commands.UpdateNoteCommand;
import com.bibler.awesome.nesmusiccomposer.utils.UndoStack;

public class MusicStream {
	
	private int currentNoteLength;
	private int streamNoteLengthCounter;
	private int currentPeriod;
	private int currentVolumeEnvelope;
	private int frameCounter;
	
	private Envelope envelope;
	
	private ArrayList<Note> notes = new ArrayList<Note>();
	
	private boolean enabled = true;
	
	private WaveGenerator stream;
	private int streamIndex;
	
	public MusicStream(int streamIndex) {
		this.streamIndex = streamIndex;
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public void addNote(int notePos, int noteValue, int noteLength) {
		Note note = findNoteByValue(notePos);
		if(note == null) {
			note = new Note();
			note.setLength(noteLength);
			note.setNoteValues(notePos, noteValue);
			System.out.println("creating a note");
			slotNote(note);
		} else {
			System.out.println("Setting an note");
			UpdateNoteCommand c = new UpdateNoteCommand(note, new Point(note.getNoteX(), noteValue));
			UndoStack.executeAndStore(c);
		}
		
	}
	
	public void resetStream() {
		frameCounter = 0;
	}
	
	private void slotNote(Note note) {
		int noteToCompareX;
		int noteX = note.getNoteX();
		int currentIndex = 0;
		for(Note noteToCompare : notes) {
			noteToCompareX = noteToCompare.getNoteX();
			if(noteX <= noteToCompareX) {
				notes.add(currentIndex, note);
				return;
			}
			currentIndex++;
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
	
	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}
	
	public void setStream(WaveGenerator stream) {
		this.stream = stream;
	}
	
	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}
	
	public void advanceFrame() {
		if(!enabled) {
			return;
		}
		if(envelope != null) {
			stream.setVolume(envelope.nextValue());
		}
	}
	
	public void advanceOneTick() {
		advanceNoteCounter();
	}
	
	private void advanceNoteCounter() {
		streamNoteLengthCounter--;
		if(streamNoteLengthCounter <= 0) {
			streamNoteLengthCounter = currentNoteLength;
			fetchNextByte();
		}
	}
	
	private void fetchNextByte() {
		Note nextNote = notes.get(frameCounter++);
		processNote(nextNote);
		processNoteLength(nextNote);
		if(frameCounter >= notes.size()) {
			frameCounter = 0;
		}
	}
	
	private void processNote(Note note) {
		if(envelope != null) {
			envelope.reset();
		}
		stream.setPeriod(note.getNotePeriod());
	}
	
	private void processNoteLength(Note note) {
		int noteLength = note.getNoteLength();
		currentNoteLength = noteLength;
		streamNoteLengthCounter = currentNoteLength;
	}

	public int getStreamIndex() {
		return streamIndex;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}

}
