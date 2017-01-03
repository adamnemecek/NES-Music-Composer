package com.bibler.awesome.nesmusiccomposer.main;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.audio.Instrument;
import com.bibler.awesome.nesmusiccomposer.audio.MusicStream;
import com.bibler.awesome.nesmusiccomposer.audio.Song;
import com.bibler.awesome.nesmusiccomposer.systems.ClockRunner;
import com.bibler.awesome.nesmusiccomposer.systems.CompositionManager;
import com.bibler.awesome.nesmusiccomposer.systems.InputManager;
import com.bibler.awesome.nesmusiccomposer.systems.SongManager;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;
import com.bibler.awesome.nesmusiccomposer.ui.PianoRoll;
import com.bibler.awesome.nesmusiccomposer.utils.MusicStreamCreator;

public class Main {
	
	private static float tempo = 0x3C;
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		SongManager manager = new SongManager();
		manager.registerObjectToNotify(frame);
		setupDemoSong(frame, manager);
		//setupNewSong(frame, manager, new MusicStream(0), new MusicStream(1), new MusicStream(2));
	}
	
	private static void setupDemoSong(MainFrame frame, SongManager manager) {
		MusicStream square1 = MusicStreamCreator.createMusicStream(new int[] {
				0x83, 0x32, 0x87, 0x31, 0x81, 0x2f, 0x88, 0x2d, 
				0x82, 0x2b, 0x83, 0x2a, 0x28, 0x88, 0x26, 0x82, 
				0x2d, 0x88, 0x2f, 0x82, 0x2f, 0x88, 0x31, 0x82, 
				0x31, 0x88, 0x32, 0x82, 0x67, 0x82, 0x32, 0x32, 
				0x31, 0x2f, 0x2d, 0x87, 0x2d, 0x81, 0x2b, 0x82, 
				0x2a, 0x32, 0x32, 0x31, 0x2f, 0x2d, 0x87, 0x2d, 
				0x81, 0x2b, 0x82, 0x2a, 0x2a, 0x2a, 0x2a, 0x2a, 
				0x81, 0x2a, 0x2b, 0x88, 0x2d, 0x81, 0x2b, 0x2a, 
				0x82, 0x28, 0x28, 0x28, 0x81, 0x28, 0x2a, 0x88, 
				0x2b, 0x81, 0x2a, 0x28, 0x82, 0x26, 0x83, 0x32, 
				0x82, 0x2f, 0x87, 0x2d, 0x81, 0x2b, 0x82, 0x2a, 
				0x2b, 0x83, 0x2a, 0x28, 0x84, 0x26
		}, 0);
        MusicStream square2 = MusicStreamCreator.createMusicStream(new int[] {
        		0x83, 0x1a, 0x87, 0x1a, 0x81, 0x1a, 0x88, 0x1a, 
        		0x82, 0x13, 0x83, 0x15, 0x15, 0x88, 0x1a, 0x82, 
        		0x1e, 0x88, 0x1f, 0x82, 0x1f, 0x88, 0x21, 0x82, 
        		0x21, 0x88, 0x1a, 0x82, 0x67, 0x82, 0x1a, 0x83, 
        		0x1a, 0x1a, 0x88, 0x1a, 0x82, 0x1a, 0x83, 0x1a, 
        		0x1a, 0x88, 0x1a, 0x82, 0x67, 0x88, 0x67, 0x82, 
        		0x1a, 0x1a, 0x1a, 0x1a, 0x1a, 0x88, 0x21, 0x82, 
        		0x15, 0x15, 0x15, 0x15, 0x15, 0x88, 0x1a, 0x82, 
        		0x1a, 0x88, 0x1a, 0x82, 0x13, 0x83, 0x15, 0x15, 
        		0x84, 0x1a
        }, 1);
		MusicStream tri = MusicStreamCreator.createMusicStream(new int[] {
				0x83, 0x2a, 0x87, 0x2d, 0x81, 0x2b, 0x88, 0x2a, 
				0x82, 0x28, 0x83, 0x26, 0x25, 0x88, 0x26, 0x82, 
				0x26, 0x88, 0x2b, 0x82, 0x2b, 0x88, 0x28, 0x82, 
				0x28, 0x88, 0x2a, 0x82, 0x67, 0x82, 0x2a, 0x2a, 
				0x2d, 0x2b, 0x2a, 0x87, 0x2a, 0x81, 0x28, 0x82, 
				0x26, 0x2a, 0x2a, 0x2d, 0x2b, 0x2a, 0x87, 0x2a, 
				0x81, 0x28, 0x82, 0x26, 0x26, 0x26, 0x26, 0x26, 
				0x81, 0x26, 0x28, 0x88, 0x2a, 0x81, 0x28, 0x26, 
				0x82, 0x25, 0x25, 0x25, 0x81, 0x25, 0x26, 0x88, 
				0x28, 0x81, 0x26, 0x25, 0x82, 0x26, 0x83, 0x2a, 
				0x82, 0x2b, 0x87, 0x2a, 0x81, 0x28, 0x82, 0x26, 
				0x28, 0x83, 0x26, 0x25, 0x84, 0x26
		}, 2);
		setupNewSong(frame, manager, square1, square2, tri);
	}
	
	private static void setupNewSong(MainFrame frame, SongManager manager, MusicStream square1, MusicStream square2, MusicStream tri) {
		
		PianoRoll roll = new PianoRoll();
		
		Song song = new Song();
		song.addStream(square1);
		song.addStream(square2);
		song.addStream(tri);
		
        APU	apu = manager.getAPU();
		Instrument inst = setupInstrumentOne();
		Instrument inst1 = setupInstrumentTwo();
		square1.setStream(apu.getChannel(0));
		square2.setStream(apu.getChannel(1));
		tri.setStream(apu.getChannel(2));
		
		square1.addInstrument(inst);
		square2.addInstrument(inst);
		
		square1.addInstrument(inst1);
		square2.addInstrument(inst1);
		apu.setSong(song);
		
		roll.setSong(song);
		frame.getPianoRollView().setPianoRoll(roll);
		manager.setCurrentSong(song);
		frame.getToolbar().registerObjectToNotify(manager);
		
		CompositionManager compManager = new CompositionManager();
		compManager.setStream(square1, 0);
		compManager.setStream(square2, 1);
		compManager.setStream(tri, 2);
		InputManager inputManager = new InputManager();
		inputManager.registerObjectToNotify(compManager);
		inputManager.registerObjectToNotify(frame.getPianoRollView());
		inputManager.registerObjectToNotify(frame.getToolbar());
		frame.getMainRollView().setInputManager(inputManager);
		frame.getKeyboard().registerObjectToNotify(compManager);
		frame.getToolbar().registerObjectToNotify(compManager);
		frame.getPianoRollView().registerKeyboard(frame.getKeyboard());
	}
	
	private static Instrument setupInstrumentOne() {
		Instrument inst = new Instrument(1, "01");
		int[] values = new int[] {
				7, 8, 9, 10, 10, 11, 11, 11, 
				10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
				9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
				8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
				7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
				6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
				5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0
		};
		
		inst.setValues(values);
		inst.addDuty(1);
		inst.setInstrumentEffectiveLength(100);
		return inst;
	}
	
	private static Instrument setupInstrumentTwo() {
		Instrument inst = new Instrument(2, "02");
		int[] values = new int[] {
				0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 
				5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6,
				5, 5, 5, 5, 5, 5, 5, 7, 7, 7, 7, 9, 9, 9, 9, 4, 4, 4, 4, 4
		};
		
		inst.setValues(values);
		inst.addDuty(1);
		inst.setInstrumentEffectiveLength(100);
		inst.setStartPos(101);
		return inst;
	}

}
