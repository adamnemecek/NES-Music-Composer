package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.systems.ClockRunner;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class Song implements Notifiable {
	
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
	
	public void playSong() {
			}
	
	public void stopSong() {
		frame.resetSong();
		for(MusicStream stream : streams) {
			stream.resetStream();
		}
	}
	
	public void pauseSong() {
		
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
		}
		
	}

}
