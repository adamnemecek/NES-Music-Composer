package com.bibler.awesome.nesmusiccomposer.utils;

import java.awt.Color;

import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Note;
import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;

public class MusicStreamCreator {
	
	public static MusicStream createMusicStream(int[] noteValues, int streamNumber) {
		MusicStream stream = new MusicStream(streamNumber);
		
		int currentNoteLengthIndex = 0;
		int currentNoteLength = 0;
		int currentByte;
		int currentNotePosX = 0;
	  
		Note note = new Note();
		Color color = null;
		switch(streamNumber) {
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
		for(int i = 0; i < noteValues.length; i++) {
			currentByte = noteValues[i];
			if(currentByte < 0x80) {
				note = new Note();
				note.setColor(color);
				note.setLength(currentNoteLengthIndex);
				note.setPos(currentNotePosX, currentByte);
				stream.addNote(note);
				currentNotePosX += currentNoteLength;
			} else if(currentByte < 0xA0) {
				currentNoteLengthIndex = currentByte - 0x80;
				currentNoteLength = NoteTable.getNoteLength(currentNoteLengthIndex);
			}
		}
		
		return stream;
		
	}

}
