package com.bibler.awesome.nesmusiccomposer.audio;

public class MusicStream {
	
	private int tempo = 0x3c;
	private int tempoCounter;
	private int currentNoteLength;
	private int streamNoteLengthCounter;
	private int currentPeriod;
	private int currentVolumeEnvelope;
	private int frameCounter;
	private int framesSinceLastNote;
	private int shouldTake;
	
	private int periodLowRegister = 2;
	private int periodHighRegister = 3;
	
	private int[] notes = new int[] {
			0x83, 0x29, 0x87, 0x28, 0x81, 0x26,
			0x88, 0x24, 0x82, 0x22, 0x83, 0x21,
			0x1F, 0x88, 0x1D, 0x82, 0x24, 0x88,
			0x26, 0x82, 0x26, 0x88, 0x28, 0x82,
			0x28, 0x88, 0x29, 0x82, 0x5E, 0x82,
			0x29, 0x29, 0x28, 0x26, 0x24, 0x87,
			0x24, 0x81, 0x22, 0x82, 0x21, 0x29,
			0x29, 0x28, 0x26, 0x24, 0x87, 0x24,
			0x81, 0x22, 0x82, 0x21, 0x21, 0x21,
			0x21, 0x21, 0x81, 0x21, 0x22, 0x88,
			0x24, 0x81, 0x22, 0x21, 0x82, 0x1F,
			0x1F, 0x1F, 0x81, 0x1F, 0x21, 0x88,
			0x22, 0x81, 0x21, 0x1F, 0x82, 0x1D,
			0x83, 0x29, 0x82, 0x26, 0x87, 0x24,
			0x81, 0x22, 0x82, 0x21, 0x22, 0x83,
			0x21, 0x1F, 0x84, 0x1D
	};
	private boolean enabled = true;
	
	private WaveGenerator stream;
	
	public MusicStream() {
		tempoCounter = 0x100;
	}
	
	public void setStream(WaveGenerator stream) {
		this.stream = stream;
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
		stream.write(periodLowRegister, note & 0xFF);
		stream.write(periodHighRegister, note >> 8 & 0xFF);
	}
	
	private void processNoteLength(int nextByte) {
		int noteLength = NoteTable.getNoteLength(nextByte - 0x80);
		currentNoteLength = noteLength;
		streamNoteLengthCounter = currentNoteLength;
		shouldTake = (int) (currentNoteLength * (Math.ceil(0x100 / tempo)));
	}

}
