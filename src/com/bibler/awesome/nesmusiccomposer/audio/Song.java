package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;
import com.bibler.awesome.nesmusiccomposer.systems.ClockRunner;
import com.bibler.awesome.nesmusiccomposer.systems.SongManager;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class Song implements Notifiable {
	
	private MusicStream[] streams = new MusicStream[3];
	
	//private MainFrame frame;
	private int tempo = 0x3c;
	private int tempoCounter;
	private int songLength;
	private int frameCounter;
	private SongManager songManager;
	
	public Song() {
		streams[0] = new MusicStream(0);
		streams[1] = new MusicStream(1);
		streams[2] = new MusicStream(2);
		for(int i = 0; i < streams.length; i++) {
			streams[i].setSong(this);
		}
		determineSongLength();
	}
	
	public void setSongManager(SongManager songManager) {
		this.songManager = songManager;
	}
	
	public void addStream(MusicStream stream) {
		streams[stream.getStreamIndex()] = stream;
		stream.setSong(this);
		determineSongLength();
	}
	
	public MusicStream getMusicStream(int streamIndex) {
		return streams[streamIndex];
	}
	
	private void playSong() {}
	
	private void stopSong() {
		resetSong();
	}
	
	private void pauseSong() {}
	
	public boolean frame() {
		boolean advanceOneTick = false;
		tempoCounter += tempo;
		if(tempoCounter > 0xFF) {
			tempoCounter = 0;
			advanceOneTick = true;
			frameCounter++;
		}
		MusicStream stream;
		for(int i = 0; i < streams.length; i++) {
			stream = streams[i];
			stream.advanceFrame();
			if(advanceOneTick) {
				stream.advanceOneTick();
			}
		}
		if(frameCounter >= songLength) {
			resetSong();
		}
		return advanceOneTick;
	}
	
	public void resetSong() {
		for(MusicStream stream : streams) {
			stream.resetStream();
		}
		frameCounter = 0;
		songManager.resetSong();
	}
	
	public void updateSongLength(int candidateLength) {
		if(candidateLength > songLength) {
			songLength = candidateLength;
		}
	}
	
	private void determineSongLength() {
		for(int i = 0; i < streams.length; i++) {
			if(streams[i].getStreamLength() > songLength) {
				songLength = streams[i].getStreamLength();
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

	public void muteChannel(String channel, boolean mute) {
		MusicStream stream = null;
		switch(channel) {
		case "Square 1":
			stream = streams[0];
			break;
		case "Square 2":
			stream = streams[1];
			break;
		case "Tri":
			stream = streams[2];
			break;
		}
		stream.mute(mute);
	}

	public void updateChannelVolume(String channel, String volume) {
		MusicStream stream = null;
		switch(channel) {
		case "Square 1":
			stream = streams[0];
			break;
		case "Square 2":
			stream = streams[1];
			break;
		case "Tri":
			stream = streams[2];
			break;
		}
		stream.updateChannelVolume(Float.parseFloat(volume));
	}
}
