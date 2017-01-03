package com.bibler.awesome.nesmusiccomposer.audio;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.bibler.awesome.nesmusiccomposer.commands.UpdateNoteCommand;
import com.bibler.awesome.nesmusiccomposer.utils.NoteComparator;
import com.bibler.awesome.nesmusiccomposer.utils.NoteFactory;
import com.bibler.awesome.nesmusiccomposer.utils.UndoStack;

public class MusicStream {
	
	private int currentNoteLength;
	private int streamNoteLengthCounter;
	private int frameCounter;
	
	private InstrumentManager instrumentManager = new InstrumentManager();
	
	private Instrument currentInstrument;
	
	private ArrayList<Note> notes = new ArrayList<Note>();
	
	private boolean enabled = true;
	
	private WaveGenerator stream;
	private int streamIndex;
	
	private int currentEndPos;
	private int currentNotePos;
	private boolean moreNotes = true;
	private int currentFrameNumber;
	private Song song;
	
	private NoteComparator comparator = new NoteComparator();
	
	public MusicStream(int streamIndex) {
		this.streamIndex = streamIndex;
	}
	
	public void setSong(Song song) {
		this.song = song;
	}
	
	public int getStreamLength() {
		return currentEndPos;
	}
	
	public void addInstrument(Instrument inst) {
		instrumentManager.addInstrument(inst);
	}
	
	public void addNote(Note note) {
		notes.add(note);
		calculateCurrentEndPos();
		if(song != null) {
			song.updateSongLength(currentEndPos);
		}
		note.setParentStream(this);
	}
	
	public Note addNoteToEnd(int y, int noteLength) {
		System.out.println("Added note at pos " + currentEndPos + " and value " + y);
		Note note = NoteFactory.createNote(currentEndPos, y, noteLength, getStreamIndex());
		addNote(note);
		return note;
	}
	
	public Note addNoteInPlace(Point notePos, int noteLength) {
		Note note = NoteFactory.createNote(notePos.x, notePos.y, noteLength, getStreamIndex());
		if(checkForFit(note)) {
			addNote(note);
			return note;
		}
		return null;
	}
	
	private boolean checkForFit(Note note) {
		boolean fit = true;
		int noteToCheckStartX;
		int noteToCheckEndX;
		int noteX = note.getNoteX();
		int noteEndX = note.getNoteLength() + noteX;
		for(Note noteToCheck : notes) {
			noteToCheckStartX = noteToCheck.getNoteX();
			noteToCheckEndX = noteToCheckStartX + noteToCheck.getNoteLength();
			if(noteX >= noteToCheckStartX && noteX < noteToCheckEndX) {
				fit = false;
				break;
			} else if(noteEndX > noteToCheckStartX && noteEndX < noteToCheckEndX) {
				fit = false;
				break;
			}
		}
		return fit;
	}
	
	public void removeNote(Point inputPos) {
		for(Note note : notes) {
			if(note.matchesValues(inputPos)) {
				notes.remove(note);
				break;
			}
		}
		calculateCurrentEndPos();
	}
	
	public void removeNote(Note note) {
		notes.remove(note);
		calculateCurrentEndPos();
	}
	
	public void updateTotalLength() {
		calculateCurrentEndPos();
		song.updateSongLength(getStreamLength());
	}
	
	private void orderNotes() {
		Collections.sort(notes, comparator);
	}
	
	private void calculateCurrentEndPos() {
		orderNotes();
		currentEndPos = 0;
		currentEndPos = notes.get(notes.size() - 1).getNoteEndPos();
	}
	
	public void resetStream() {
		frameCounter = 0;
		streamNoteLengthCounter = 0;
		currentNoteLength = 0;
		moreNotes = true;
		currentNotePos = 0;
		currentFrameNumber = 0;
		advanceInstrumentCounter();
	}
	
	public void setNotes(ArrayList<Note> notes) {
		for(Note note : notes) {
			addNote(note);
		}
	}
	
	public void setStream(WaveGenerator stream) {
		this.stream = stream;
	}
	
	public void setEnvelope(Instrument envelope) {
		this.currentInstrument = envelope;
	}
	
	public void advanceFrame() {
		if(!enabled) {
			return;
		}
		if(currentInstrument != null) {
			final int vol = currentInstrument.nextValue();
			stream.setVolume(vol);
			stream.setDuty(currentInstrument.nextDuty());
		}
	}
	
	public void advanceOneTick() {
		advanceNoteCounter();
		advanceInstrumentCounter();
		currentFrameNumber++;
	}
	
	private void advanceNoteCounter() {
		streamNoteLengthCounter--;
		if(streamNoteLengthCounter <= 0) {
			streamNoteLengthCounter = currentNoteLength;
			fetchNextByte();
		}
		currentNotePos++;
	}
	
	private void advanceInstrumentCounter() {
		currentInstrument = instrumentManager.getInstrument(currentFrameNumber);
		System.out.println("Updated instrument to: " + currentInstrument.getInstrumentName());
	}
	
	private void fetchNextByte() {
		if(frameCounter >= notes.size()) {
			setRest();
		} else {
			Note nextNote = notes.get(frameCounter);
			if(nextNote.getNoteX() == currentNotePos) {
				processNote(nextNote);
				processNoteLength(nextNote);
				frameCounter++;
			} else {
				setRest();
			}
		}
	}
	
	private void setRest() {
		stream.setPeriod(0);
		currentNoteLength = 0;
		streamNoteLengthCounter = 0;
	}
	
	private void processNote(Note note) {
		if(currentInstrument != null) {
			currentInstrument.reset();
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

	public Note checkForNoteClick(int x, int y) {
		for(Note n : notes) {
			if(n.checkForClick(x, y)) {
				return n;
			}
		}
		return null;
	}

}
