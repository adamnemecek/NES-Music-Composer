package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class Song {
	
	private ArrayList<MusicStream> streams;
	
	private MainFrame frame;
	private int tempo = 0x3c;
	private int tempoCounter;
	
	public Song() {
		streams = new ArrayList<MusicStream>();
	}
	
	public void setMainFrame(MainFrame frame) {
		this.frame = frame;
	}
	
	public void addStream(MusicStream stream) {
		streams.add(stream);
	}
	
	public void frame() {
		boolean advanceOneTick = false;
		tempoCounter += tempo;
		if(tempoCounter > 0xFF) {
			tempoCounter = 0;
			advanceOneTick = true;
			frame.advanceOneTick();
		}
		for(MusicStream stream : streams) {
			stream.advanceFrame();
			if(advanceOneTick) {
				stream.advanceOneTick();
			}
		}
	}

}
