package com.bibler.awesome.nesmusiccomposer.utils;

import java.awt.Color;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;
import com.bibler.awesome.nesmusiccomposer.ui.Note;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRollVoice;

public class PianoRollVoiceCreator {
	
	public static PianoRollVoice createPianoRollVoice(MusicStream stream) {
		PianoRollVoice voice = new PianoRollVoice(stream.getStreamNumber());
		
		int[] notes = stream.getNotes();
		int currentNoteLengthIndex = 0;
		int currentNoteLength = 0;
		int currentByte;
		int currentNotePosX = 0;
		Note note = new Note();
		Color color = null;
		switch(stream.getStreamNumber()) {
		case 0:
			color = Color.BLUE;
			break;
		case 1:
			color = Color.RED;
			break;
		case 2:
			color = Color.GREEN;
			break;
	}
		for(int i = 0; i < notes.length; i++) {
			currentByte = notes[i];
			if(currentByte < 0x80) {
				note = new Note();
				note.setColor(color);
				note.setLength(currentNoteLengthIndex);
				note.setPos(currentNotePosX, currentByte);
				voice.addNote(note);
				currentNotePosX += currentNoteLength;
			} else if(currentByte < 0xA0) {
				currentNoteLengthIndex = currentByte - 0x80;
				currentNoteLength = NoteTable.getNoteLength(currentNoteLengthIndex);
			}
		}
		
		return voice;
		
	}

}
