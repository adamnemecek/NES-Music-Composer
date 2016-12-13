package com.bibler.awesome.nesmusiccomposer.systems;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class ClockRunner implements Runnable {
	
	
	private final int STARTED = 0;
	private final int IDLE = 1;
	private final int RUNNING = 2;
	private final int PAUSED = 3;
	
	
	private boolean pause;
	private boolean running;
	private boolean started;
	private boolean playing;
	
	private Object pauseLock = new Object();
	private Thread thread;
	
	public ClockRunner() {
	}
	
	public void startEmulator() {
		if(thread == null) {
			setupThread();
		}
		pauseEmulator();
		started = true;
	}
	
	private void setupThread() {
		running = true;
		thread = new Thread(this);
		pauseEmulator();
		thread.start();
	}
	
	public void pauseEmulator() {
		pause = true;
		playing = false;
	}
	
	public void resumeEmulator() {
		if(!started) {
			startEmulator();
		}
		pause = false;
		synchronized(pauseLock) {
			pauseLock.notifyAll();
		}
		playing = true;
	}
	
	// Dependent Systems
	private APU apu;
	
	// Cycle variables
	private int totalClocks;
	
	// Framerate variables
	private long initialFrameTime;
	private long lastFrameTime;
	private long frameRate = (long) (1000 / 59.9);
	private long frameTimeAfterSleep;
	private long totalFrameTime;
	private int frameCount;
	private double averageFrameRate;
	
	public void setAPU(APU apu) {
		this.apu = apu;
	}
	
	public void cycle() {
		if(totalClocks % 3 == 0) {
			apu.clock();
		}
		
		if(totalClocks == 89490 / 4) {
			apu.stepFrame();
			totalClocks = 0;
		}
		
		totalClocks++;
	}
	
	private void runFrame() {
		for(int i = 0; i < 89342; i++) {
			cycle();
		}
		frame();
		totalClocks = 0;
	}
	
	public void frame() {
		apu.finishFrame();
		initialFrameTime = System.currentTimeMillis() - lastFrameTime;
		if(initialFrameTime < frameRate) {
			try {
				
				Thread.sleep((long) (frameRate - initialFrameTime));
			} catch(InterruptedException e) {}
		}
		if(lastFrameTime != 0) {
			frameTimeAfterSleep = System.currentTimeMillis() - lastFrameTime;
			totalFrameTime += frameTimeAfterSleep;
			frameCount++;
			averageFrameRate = 1000.0 / (totalFrameTime / frameCount);
			
		}
		lastFrameTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		while(running) {
			if(pause) {
				synchronized(pauseLock) {
					try {
						pauseLock.wait();
					} catch(InterruptedException e) {}
				}
			}
			runFrame();
		}
	}

}
