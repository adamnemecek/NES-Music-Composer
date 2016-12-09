package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;

public class PianoRoll {
	
	private PianoRollStream square1Voice;
	private PianoRollStream square2Voice;
	private PianoRollStream triVoice;
	
	public PianoRoll() {
		
	}
	
	public void addStream(MusicStream stream) {
		switch(stream.getStreamIndex()) {
		case 0:
			square1Voice = new PianoRollStream(stream);
		case 1:
			square2Voice = new PianoRollStream(stream);
		case 2:
			triVoice = new PianoRollStream(stream);
		}
	}
	
	public PianoRollStream getVoice(int voiceIndex) {
		switch(voiceIndex) {
		case 0:
			return square1Voice;
		case 1:
			return square2Voice;
		case 2:
			return triVoice;
		}
		return null;
	}
	
	public int getTotalNoteLength() {
		final int square1VoiceLength = square1Voice != null ? square1Voice.getTotalNoteLength() : 0;
		final int square2VoiceLength = square2Voice != null ? square2Voice.getTotalNoteLength() : 0;
		final int triVoiceLength = triVoice != null ? triVoice.getTotalNoteLength() : 0;
		return Math.max(Math.max(square1VoiceLength, square2VoiceLength), triVoiceLength);
	}
	
	public void paint(Graphics g, Point dims, int[] noteLaneNumbers, int currentMarkerX) {
		square1Voice.paint(g, dims, noteLaneNumbers, currentMarkerX);
		square2Voice.paint(g, dims, noteLaneNumbers, currentMarkerX);
		triVoice.paint(g, dims, noteLaneNumbers, currentMarkerX);
	}

	public void addNote(int x, int y, int currentLength, int currentVoice) {
		switch(currentVoice) {
		case 0:
			square1Voice.addNote(x, y, currentLength);
			break;
		case 1:
			square2Voice.addNote(x, y, currentLength);
			break;
		case 3:
			triVoice.addNote(x, y, currentLength);
			break;
		}
		
	}

}
