package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

public class Song {
	
	private ArrayList<MusicStream> streams;
	
	public Song() {
		streams = new ArrayList<MusicStream>();
	}
	
	public void addStream(MusicStream stream) {
		streams.add(stream);
	}
	
	public void frame() {
		for(MusicStream stream : streams) {
			stream.advanceFrame();
		}
	}
	
	public void updateTempo(float newTempo) {
		for(MusicStream stream : streams) {
			stream.updateTempo(newTempo);
		}
	}

}
