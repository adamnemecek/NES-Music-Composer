package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;
import com.bibler.awesome.nesmusiccomposer.systems.ClockRunner;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class Song implements Notifiable {
	
	private MusicStream[] streams = new MusicStream[3];
	
	//private MainFrame frame;
	private int tempo = 0x3c;
	private int tempoCounter;
	
	
	public Song() {
		streams[0] = new MusicStream(0);
		streams[1] = new MusicStream(1);
		streams[2] = new MusicStream(2);
	}
	
	public void addStream(MusicStream stream) {
		streams[stream.getStreamIndex()] = stream;
	}
	
	public MusicStream getMusicStream(int streamIndex) {
		return streams[streamIndex];
	}
	
	private void playSong() {}
	
	private void stopSong() {
		for(MusicStream stream : streams) {
			stream.resetStream();
		}
	}
	
	private void pauseSong() {}
	
	private void frame() {
		boolean advanceOneTick = false;
		tempoCounter += tempo;
		if(tempoCounter > 0xFF) {
			tempoCounter = 0;
			advanceOneTick = true;
		}
		for(MusicStream stream : streams) {
			stream.advanceFrame();
			if(advanceOneTick) {
				stream.advanceOneTick();
			}
		}
	}

	@Override
	public void takeNotice(String message, Object Notifier) {
		switch(message) {
		case "PLAY":
			playSong();
			break;
		case "PAUSE":
			pauseSong();
			break;
		case "STOP":
			stopSong();
			break;
		case "FRAME":
			if(Notifier instanceof APU) {
				frame();
			}
			break;
		}	
	}
}
