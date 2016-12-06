package com.bibler.awesome.nesmusiccomposer.audio;


public class APU {
	
	private PulseWaveGenerator pulseOne;
	private PulseWaveGenerator pulseTwo;
	private TriangleWaveGenerator triOne;
	private NoiseWaveGenerator noiseOne;
	private PulseWaveGenerator metronome;
	
	private Song song;
	
	private float[] channelVolumes = new float[] {.5f, .5f, .5f, .5f, .5f, .85f};
	
	private boolean pulseOneEnabled = true;
	private boolean pulseTwoEnabled = true;
	private boolean triEnabled = true;
	private boolean noiseEnabled = true;
	private boolean metronomeEnabled = true;
	private boolean filteringOn = false;
	
	private int cycles = 0;
	private int outputSamples;
	private double smoothedValue;
	private double newValue;
	private double smoothing = .5;
	private int totalAPUCycles;
	private int sampleIndex;
	private int samplesPerFrame;
	
	private Mixer mixer;
	
	private int cpuDivider;
	
	private int frameCounter;
	private int frameCounterMode;
	private int frameStep;
	private boolean disableFrameInterrupt;
	
	int[] pulseOneSamples = new int[512];
	int[] pulseTwoSamples = new int[512];
	int[] triSamples = new int[512];
	int[] noiseSamples = new int[512];
	int[] metronomeSamples = new int[512];
	
	private int[] sampleIndices = new int[5];
	
	private int remainder;
	private double accumulator;
	private int apuCycle;
	
	private int sampleRate = 1786860 / 44100;
	private int volumeMultiplier;
	private int bitRate;
	
	private final int FRAME_DIVIDER_PERIOD = 7456;
	
	
	public APU() {
		pulseOne = new PulseWaveGenerator(false);
		pulseTwo = new PulseWaveGenerator(true);
		triOne = new TriangleWaveGenerator();
		noiseOne = new NoiseWaveGenerator();
		metronome = new PulseWaveGenerator(false);
		mixer = new Mixer();
		frameCounter = FRAME_DIVIDER_PERIOD;
		frameStep = 1;
		updateAudioParams(16);
	}
	
	public void setSong(Song song) {
		this.song = song;
	}
	
	public void updateAudioParams(int bitRate) {
		this.bitRate = bitRate;
		if(bitRate == 8) {
			volumeMultiplier = 0xFF;
		} else if(bitRate == 16) {
			volumeMultiplier = 32768;
		}
		mixer.updateParameters(bitRate);
		samplesPerFrame = (int) (44100 / 59.9);
		pulseOneSamples = new int[samplesPerFrame];
		pulseTwoSamples = new int[samplesPerFrame];
		triSamples = new int[samplesPerFrame];
		noiseSamples = new int[samplesPerFrame];
		metronomeSamples = new int[samplesPerFrame];
	}
	
	
	public void clock() {
		triOne.clock();
		if((cycles & 1) == 0) {
			apuClock();
			if(cycles % sampleRate == 0) {
				newValue = getSamples();
				if(bitRate == 16 && filteringOn) {
					newValue = lowpass_filter(highpass_filter((int) newValue));
				}
				mixer.outputSample((int) newValue);
				outputSamples++;
			}
		}
		cycles++;
	}
	
	private void apuClock() {
		pulseOne.clock();
		pulseTwo.clock();
		noiseOne.clock();
		metronome.clock();
		totalAPUCycles++;
	}
	
public void stepFrame() {
		
		switch(frameStep) {
		case 2:
			clockSweepUnits();
			break;
		case 4:
			if(frameCounterMode == 0) {
				clockSweepUnits();
				frameStep = 0;
				totalAPUCycles = 0;
			}
			break;
		case 5:
			if(frameCounterMode == 1) {
				clockSweepUnits();
				frameStep = 0;
				totalAPUCycles = 0;
			}
			break;
		}
		frameStep++;
	}
	
	private void clockSweepUnits() {
		pulseOne.clockSweepUnit();
		pulseTwo.clockSweepUnit();
	}
	
	public void updateChannelVolume(int channel, float volume) {
		channelVolumes[channel] = volume;
	}
	
	public void finishFrame() {
		mixer.flushSamples();
		song.frame();
		cycles = 0;
		outputSamples = 0;
	}
	
	public void reset() {
		frameCounter = FRAME_DIVIDER_PERIOD;
		pulseOne.reset();
		pulseTwo.reset();
		triOne.reset();
		noiseOne.reset();
		metronome.reset();
		frameStep = 1;
	}
	
	public void setChannelEnabled(int channel, boolean enabled) {
		switch(channel) {
		case 0:
			pulseOneEnabled = enabled;
			break;
		case 1:
			pulseTwoEnabled = enabled;
			break;
		case 2:
			triEnabled = enabled;
			break;
		case 3:
			noiseEnabled = enabled;
			break;
		case 4:
			metronomeEnabled = enabled;
			break;
		}
	}
	
	private int dckiller;
	private int lpaccum;
	
	 private int highpass_filter(int sample) {
	        //for killing the dc in the signal
	        sample += dckiller;
	        dckiller -= sample >> 8;//the actual high pass part
	        dckiller += (sample > 0 ? -1 : 1);//guarantees the signal decays to exactly zero
	        return sample;
	    }

	    private int lowpass_filter(int sample) {
	        sample += lpaccum;
	        lpaccum -= sample * 0.9;
	        return lpaccum;
	    }
	
	
	private int getSamples() {
		pulseOneSamples[sampleIndex] = (int) ((pulseOneEnabled ? pulseOne.getSample() : 0) * channelVolumes[0]);
		pulseTwoSamples[sampleIndex] = (int) ((pulseTwoEnabled ? pulseTwo.getSample() : 0) * channelVolumes[1]);
		triSamples[sampleIndex] = (int) ((triEnabled ? triOne.getSample() : 0) * channelVolumes[2]);
		noiseSamples[sampleIndex] = (int) ((noiseEnabled ? noiseOne.getSample() : 0) * channelVolumes[3]);
		metronomeSamples[sampleIndex] = (int) ((metronomeEnabled ? metronome.getSample() : 0) * channelVolumes[4]);
		double total = (.00752 * (pulseOneSamples[sampleIndex] + pulseTwoSamples[sampleIndex])) 
				+ ((0.00851 * triSamples[sampleIndex]) + (noiseSamples[sampleIndex] * .00494) + (metronomeSamples[sampleIndex] * .0037));
		total *= volumeMultiplier;
		sampleIndex++;
		if(sampleIndex >= samplesPerFrame) {
			sampleIndex = 0;
		}
		return (int) total; 
	}
	
	public byte[] getFrame() {
		return mixer.getFrame();
	}
	
	public int[][] getSampleBuffers() {
		return new int[][] {
			pulseOneSamples, pulseTwoSamples, triSamples, noiseSamples
		};
	}
	
	public boolean bufferHasLessThan(int samples) {
		return mixer.bufferHasLessThan(samples);
	}
	
	public float getBufferUsage() {
		return mixer.getBufferUsage();
	}
	
	public void enableAudio(boolean enable) {
		mixer.enableAudio(enable);
	}
	
	public void kill() {
		mixer.kill();
	}
	
	public WaveGenerator getChannel(int channelToGet) {
		switch(channelToGet) {
		case 0:
			return pulseOne;
		case 1:
			return pulseTwo;
		case 2:
			return triOne;
		case 3:
			return noiseOne;
		case 4:
			return metronome;
		}
		return null;
	}

}
