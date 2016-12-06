package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class PianoRoll {
	
	private PianoRollVoice square1Voice;
	private PianoRollVoice square2Voice;
	private PianoRollVoice triVoice;
	
	public PianoRoll() {
		square1Voice = new PianoRollVoice(0);
		square2Voice = new PianoRollVoice(1);
		triVoice = new PianoRollVoice(2);
	}
	
	public void setVoice(PianoRollVoice voice, int index) {
		switch(index) {
		case 0:
			square1Voice = voice;
		case 1:
			square2Voice = voice;
		case 2:
			triVoice = voice;
		}
	}
	
	public PianoRollVoice getVoice(int voiceIndex) {
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
	
	public void paint(Graphics g, Point dims) {
		square1Voice.paint(g, dims);
		square2Voice.paint(g, dims);
		triVoice.paint(g, dims);
	}

}
