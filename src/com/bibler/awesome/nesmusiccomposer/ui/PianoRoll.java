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
	
	public void paint(Graphics g, Point dims) {
		square1Voice.paint(g, dims);
		square2Voice.paint(g, dims);
		triVoice.paint(g, dims);
	}

}
