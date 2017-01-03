package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.audio.Song;

public class PianoRoll {
	
	private PianoRollStream square1Voice;
	private PianoRollStream square2Voice;
	private PianoRollStream triVoice;
	private Song song;
	
	public PianoRoll() {
		
	}
	
	public void setSong(Song song) {
		this.song = song;
		addStream(song.getMusicStream(0));
		addStream(song.getMusicStream(1));
		addStream(song.getMusicStream(2));
	}
	
	private void addStream(MusicStream stream) {
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
	
	public Note checkForNoteClick(int x, int y) {
		Note n = square1Voice.checkForNoteClick(x, y);
		if(n == null) {
			n = square2Voice.checkForNoteClick(x, y);
			if(n == null) {
				n = triVoice.checkForNoteClick(x, y);
			}
		}
		return n;
	}
	
	public int getTotalNoteLength() {
		final int square1VoiceLength = square1Voice != null ? square1Voice.getTotalNoteLength() : 0;
		final int square2VoiceLength = square2Voice != null ? square2Voice.getTotalNoteLength() : 0;
		final int triVoiceLength = triVoice != null ? triVoice.getTotalNoteLength() : 0;
		return Math.max(Math.max(square1VoiceLength, square2VoiceLength), triVoiceLength);
	}
	
	public void paint(Graphics g, Point dims, int[] noteLaneNumbers, int currentMarkerX, int scrollX) {
		square1Voice.paint(g, dims, noteLaneNumbers, currentMarkerX, scrollX);
		square2Voice.paint(g, dims, noteLaneNumbers, currentMarkerX, scrollX);
		triVoice.paint(g, dims, noteLaneNumbers, currentMarkerX, scrollX);
	}
	
	public Note addNoteToEnd(int y, int noteLength, int voice) {
		Note note = null;
		switch(voice) {
		case 0:
			note = square1Voice.addNoteToEnd(y, noteLength);
			break;
		case 1:
			note = square2Voice.addNoteToEnd(y, noteLength);
			break;
		case 2:
			note = triVoice.addNoteToEnd(y, noteLength);
			break;
		}
		return note;
	}
	
	public Note addNoteInPlace(Point inputPos, int currentVoice, int currentNoteLength) {
		Note note = null;
		switch(currentVoice) {
		case 0:
			note = square1Voice.addNoteInPlace(inputPos, currentNoteLength);
			break;
		case 1:
			note = square2Voice.addNoteInPlace(inputPos, currentNoteLength);
			break;
		case 2:
			note = triVoice.addNoteInPlace(inputPos, currentNoteLength);
			break;
		}
		return note;
	}

	public void removeNote(Point inputPos, int currentVoice) {
		switch(currentVoice) {
		case 0:
			square1Voice.removeNote(inputPos);
			break;
		case 1:
			square2Voice.removeNote(inputPos);
			break;
		case 2:
			triVoice.removeNote(inputPos);
			break;
		}
		
	}

	public void registerKeyboard(Keyboard keyboard) {
		square1Voice.registerObjectToNotify(keyboard);
		square2Voice.registerObjectToNotify(keyboard);
		triVoice.registerObjectToNotify(keyboard);
		
	}

}
