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
	
	public MusicStream() {
		tempoCounter = 0x100;
		/*envelope = new Envelope();
		envelope.addValue(0x09);
		envelope.addValue(0x09);
		envelope.addValue(0x09);
		envelope.addValue(0x09);
		envelope.addValue(0x0A);
		envelope.addValue(0x0a);
		/*envelope.addValue(0x0a);
		envelope.addValue(0x0a);
		envelope.addValue(0x0b);
		envelope.addValue(0x0b);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0B);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x0A);
		envelope.addValue(0x09);
		envelope.addValue(0x09);
		envelope.addValue(0x09);
		envelope.addValue(0x09);
		envelope.addValue(0x08);
		envelope.addValue(0x08);
		envelope.addValue(0x08);
		envelope.addValue(0x08);
		envelope.addValue(0x07);
		envelope.addValue(0x07);
		envelope.addValue(0x06);
		envelope.addValue(0x06);
		envelope.addValue(0x05);
		envelope.addValue(0x05);
		envelope.addValue(0x04);
		envelope.addValue(0x04);
		envelope.addValue(0x03);
		envelope.addValue(0x03);
		envelope.addValue(0x02);
		envelope.addValue(0x01);
		envelope.addValue(0x00);*/
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
	
	public void updateTempo(float tempo) {
		this.tempo = tempo;
	}
	
	public void advanceFrame() {
		if(!enabled) {
			return;
		}
		tempoCounter += tempo;
		if(tempoCounter > 0xFF) {
			tempoCounter = 0;
			advanceNoteCounter();
		}
		if(envelope != null) {
			stream.setVolume(envelope.nextValue());
		}
		framesSinceLastNote++;
	}
	
	private void advanceNoteCounter() {
		streamNoteLengthCounter--;
		if(streamNoteLengthCounter <= 0) {
			streamNoteLengthCounter = currentNoteLength;
			fetchNextByte();
		}
	}
	
	private void fetchNextByte() {
		//System.out.println("last note took: " + framesSinceLastNote + " should take: " + shouldTake);
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

}
