package com.bibler.awesome.nesmusiccomposer.utils;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;
import com.bibler.awesome.nesmusiccomposer.ui.Notebar;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRollVoice;

public class PianoRollVoiceCreator {
	
	public static PianoRollVoice createPianoRollVoice(MusicStream stream) {
		PianoRollVoice voice = new PianoRollVoice(stream.getStreamNumber());
		
		int[] notes = stream.getNotes();
		int currentNoteLengthIndex = 0;
		int currentNoteLength = 0;
		int currentByte;
		int currentNotePosX = 0;
		Notebar note = new Notebar();
		for(int i = 0; i < notes.length; i++) {
			currentByte = notes[i];
			if(currentByte < 0x80) {
				note = new Notebar();
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
