package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.HashMap;
import java.util.Map;

import com.bibler.awesome.nesmusiccomposer.systems.InputAction;

public class NoteTable {
	
	final static int[] notes = new int[] {
			0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x07F1, 0x0780, 0x0713,
		    0x06AD, 0x064D, 0x05F3, 0x059D, 0x054D, 0x0500, 0x04B8, 0x0475, 0x0435, 0x03F8, 0x03BF, 0x0389, // C2-B2 (0x03-0x0E)
		    0x0356, 0x0326, 0x02F9, 0x02CE, 0x02A6, 0x027F, 0x025C, 0x023A, 0x021A, 0x01FB, 0x01DF, 0x01C4, // C3-B3 (0x0F-0x1A)
		    0x01AB, 0x0193, 0x017C, 0x0167, 0x0151, 0x013F, 0x012D, 0x011C, 0x010C, 0x00FD, 0x00EF, 0x00E2, // C4-B4 (0x1B-0x26)
		    0x00D2, 0x00C9, 0x00BD, 0x00B3, 0x00A9, 0x009F, 0x0096, 0x008E, 0x0086, 0x007E, 0x0077, 0x0070, // C5-B5 (0x27-0x32)
		    0x006A, 0x0064, 0x005E, 0x0059, 0x0054, 0x004F, 0x004B, 0x0046, 0x0042, 0x003F, 0x003B, 0x0038, // C6-B6 (0x33-0x3E)
		    0x0034, 0x0031, 0x002F, 0x002C, 0x0029, 0x0027, 0x0025, 0x0023, 0x0021, 0x001F, 0x001D, 0x001B, // C7-B7 (0x3F-0x4A)
		    0x001A, 0x0018, 0x0017, 0x0015, 0x0014, 0x0013, 0x0012, 0x0011, 0x0010, 0x000F, 0x000E, 0x000D, // C8-B8 (0x4B-0x56)
		    0x000C, 0x000C, 0x000B, 0x000A, 0x000A, 0x0009, 0x0008, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000  // C9-F#9 (0x57-0x5D)
	};
	
	final static String[] noteStrings = new String[] {
			"C1", "Cs1", "D1", "Ds1", "E1", "F1", "Fs1", "G1", "Gs1", "A1", "As1", "B1",
			"C2", "Cs2", "D2", "Ds2", "E2", "F2", "Fs2", "G2", "Gs2", "A2", "As2", "B2",
			"C3", "Cs3", "D3", "Ds3", "E3", "F3", "Fs3", "G3", "Gs3", "A3", "As3", "B3",
			"C4", "Cs4", "D4", "Ds4", "E4", "F4", "Fs4", "G4", "Gs4", "A4", "As4", "B4",
			"C5", "Cs5", "D5", "Ds5", "E5", "F5", "Fs5", "G5", "Gs5", "A5", "As5", "B5",
			"C6", "Cs6", "D6", "Ds6", "E6", "F6", "Fs6", "G6", "Gs6", "A6", "As6", "B6",
			"C7", "Cs7", "D7", "Ds7", "E7", "F7", "Fs7", "G7", "Gs7", "A7", "As7", "B7",
			"C8", "Cs8", "D8", "Ds8", "E8", "F8", "Fs8", "G8", "Gs8", "A8", "As8", "B8",
			"C9", "Cs9", "D9", "Ds9", "E9", "F9", "Fs9", "G9", "Gs9", "A9", "As9", "B9",
	};
	
	final static int[] noteLengths = new int[] {
		0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
		0x03, 0x06, 0x0C, 0x18, 0x30,
		0x07, 0x14, 0x0A
	};
	
	private static Map<String, Integer> noteValueMap = new HashMap<String, Integer>();
	
	private static void fillNoteValueMap() {
		noteValueMap.put("note_whole", 5);
		noteValueMap.put("note_half", 4);
		noteValueMap.put("note_quarter", 3);
		noteValueMap.put("note_eighth", 2);
		noteValueMap.put("note_sixteenth", 1);
		noteValueMap.put("note_thirty_second", 0);
		noteValueMap.put("note_whole_dotted", 10);
		noteValueMap.put("note_half_dotted", 9);
		noteValueMap.put("note_quarter_dotted", 8);
		noteValueMap.put("note_eighth_dotted", 7);
		noteValueMap.put("note_sixteenth_dotted", 6);
	}
	public static int getNote(int index) {
		return notes[index];
	}
	
	public static int getNoteLength(int index) {
		return noteLengths[index];
	}
	
	public static int getNoteLengthFromWidth(int width) {
		for(int i = 0; i < noteLengths.length; i++) {
			if(i == width) {
				return i;
			}
		}
		return -1;
	}

	public static int getNote(String message) {
		if(noteValueMap.isEmpty()) {
			fillNoteValueMap();
		}
		return noteValueMap.get(message);
	}
	public static int getKeyFromString(String string) {
		for(int i = 0; i < noteStrings.length; i++) {
			if(noteStrings[i].equalsIgnoreCase(string)) {
				return i;
			}
		}
		return 0;
	}

}
