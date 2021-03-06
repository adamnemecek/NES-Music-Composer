package com.bibler.awesome.nesmusiccomposer.systems;

import java.util.ArrayList;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.audio.Song;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;

public class SongManager implements Notifier, Notifiable {
	
	private Song currentSong;
	private ClockRunner clockRunner;
	private APU apu;
	
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	public SongManager() {
		this.clockRunner = new ClockRunner();
		this.apu = new APU();
		clockRunner.setAPU(apu);
		setCurrentSong(new Song());
		apu.registerObjectToNotify(this);
	}
	
	public void registerObjectToNotify(Notifiable objectToNotify) {
		objectsToNotify.add(objectToNotify);
	}
	
	public APU getAPU() {
		return apu;
	}
	
	public void setCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
		currentSong.setSongManager(this);
	}
	
	private void play() {
		clockRunner.resumeEmulator();
	}
	
	private void pause() {
		clockRunner.resumeEmulator();
	}
	
	private void stop() {
		clockRunner.pauseEmulator();
		apu.flushMixer();
	}
	
	private void nextFrame() {
		boolean advanceOneTick = currentSong.frame();
		if(advanceOneTick) {
			notify("ADVANCE_TICK");
		}
	}
	
	public void resetSong() {
		notify("RESET");
	}
	
	private void muteChannel(String channel, boolean mute) {
		currentSong.muteChannel(channel, mute);
	}
	
	private void updateChannelVolume(String channel, String volume) {
		currentSong.updateChannelVolume(channel, volume);
	}

	@Override
	public void takeNotice(String message, Object Notifier) {
		String[] messages = message.split(":");
		switch(messages[0]) {
		case "PLAY":
			play();
			break;
		case "PAUSE":
			pause();
			break;
		case "STOP":
			stop();
			break;
		case "FRAME":
			if(Notifier instanceof APU) {
				nextFrame();
			}
			break;
		case "MUTE":
			muteChannel(messages[1], true);
			break;
		case "UNMUTE":
			muteChannel(messages[1], false);
			break;
		case "VOLUME":
			updateChannelVolume(messages[1], messages[2]);
		}
	}

	@Override
	public void notify(String messageToSend) {
		for(Notifiable objectToNotify : objectsToNotify) {
			objectToNotify.takeNotice(messageToSend, this);
		}
	}

}
