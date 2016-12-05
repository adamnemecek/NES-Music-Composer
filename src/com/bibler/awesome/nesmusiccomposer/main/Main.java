package com.bibler.awesome.nesmusiccomposer.main;

import java.io.File;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Song;
import com.bibler.awesome.nesmusiccomposer.systems.ClockRunner;
import com.bibler.awesome.nesmusiccomposer.utils.SongLoader;

public class Main {
	
	public static void main(String[] args) {
		MusicStream square1 = new MusicStream();
		Song song = new Song();
		song.addStream(square1);
		APU apu = new APU();
		square1.setStream(apu.getChannel(0));
		apu.setSong(song);
		ClockRunner runner = new ClockRunner();
		runner.setAPU(apu);
		runner.runEmulator();
	}

}
