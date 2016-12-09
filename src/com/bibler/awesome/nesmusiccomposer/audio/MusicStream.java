package com.bibler.awesome.nesmusiccomposer.audio;

public class MusicStream {
	
	private float tempo = 0x3c;
	private int tempoCounter;
	private int currentNoteLength;
	private int streamNoteLengthCounter;
	private int currentPeriod;
	private int currentVolumeEnvelope;
	private int frameCounter;
	private int framesSinceLastNote;
	private int shouldTake;
	private Envelope envelope;
	
	private int periodLowRegister = 0x0A;
	private int periodHighRegister = 0x0B;
	
	private int[] notes;
	
	private boolean enabled = true;
	
	private WaveGenerator stream;
	private int streamIndex;
	
	public MusicStream(int streamIndex) {
		this.streamIndex = streamIndex;
	}
	
	public void setNotes(int[] notes) {
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
		framesSinceLastNote++;
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
		framesSinceLastNote = 0;
		int nextByte = notes[frameCounter++];
		if(nextByte < 0x80) {
			processNote(nextByte);
		} else if(nextByte < 0xA0) {
			processNoteLength(nextByte);
			fetchNextByte();
		}
		if(frameCounter >= notes.length) {
			frameCounter = 0;
		}
	}
	
	private void processNote(int nextByte) {
		int note = NoteTable.getNote(nextByte);
		if(envelope != null) {
			envelope.reset();
		}
		stream.setPeriod(note);
	}
	
	private void processNoteLength(int nextByte) {
		int noteLength = NoteTable.getNoteLength(nextByte - 0x80);
		currentNoteLength = noteLength;
		streamNoteLengthCounter = currentNoteLength;
		shouldTake = (int) (currentNoteLength * (Math.ceil(0x100 / tempo)));
	}

	public int getStreamNumber() {
		return streamIndex;
	}
	
	public int[] getNotes() {
		return notes;
	}

}
