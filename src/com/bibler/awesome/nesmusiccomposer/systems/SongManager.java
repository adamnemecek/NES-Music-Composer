package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.audio.Song;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;

public class SongManager implements Notifiable {
	
	private Song currentSong;
	private ClockRunner clockRunner;
	private APU apu;
	
	public SongManager() {
		this.clockRunner = new ClockRunner();
		this.apu = new APU();
		clockRunner.setAPU(apu);
		setCurrentSong(new Song());
	}
	
	public APU getAPU() {
		return apu;
	}
	
	public void setCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
		apu.registerObjectToNotify(currentSong);
	}
	
	private void play() {
		//currentSong.playSong();
		clockRunner.resumeEmulator();
	}
	
	private void pause() {
		//currentSong.pauseSong();
		clockRunner.resumeEmulator();
	}
	
	private void stop() {
		//currentSong.stopSong();
		clockRunner.pauseEmulator();
		apu.flushMixer();
	}

	@Override
	public void takeNotice(String message, Object Notifier) {
		switch(message) {
		case "PLAY":
			play();
			break;
		case "PAUSE":
			pause();
			break;
		case "STOP":
			stop();
			break;
		}
	}

}
