package com.bibler.awesome.nesmusiccomposer.audio;

public class PulseWaveGenerator extends WaveGenerator {
	
	private int envelope = 7;
	private boolean sweepEnabled;
	private int sweepPeriod;
	private boolean sweepNegate;
	private boolean sweepReloadFlag;
	private boolean pulseTwo;
	private int sweepShift;
	private int sweepDivider;
	private int duty;
	private int currentTimer;
	private int currentStep;
	public PulseWaveGenerator(boolean pulseTwo) {
		this.pulseTwo = pulseTwo;
	}
	
	@Override
	public void reset() {
		envelope = 7;
		sweepEnabled = false;
		sweepPeriod = 0;
		sweepNegate = false;
		sweepReloadFlag = false;
		sweepShift = 0;
		sweepDivider = 0;
		duty = 0;
		currentTimer = 0;
		currentStep = 7;
	}
	
	
	public void clockSweepUnit() {
		if(sweepDivider > 0) {
			sweepDivider--;
		} else {
			shiftSweepUnit();
			
		}
		if(sweepReloadFlag) {
			sweepReloadFlag = false;
			shiftSweepUnit();
		}
	}
	
	private void shiftSweepUnit() {
		sweepDivider = sweepPeriod / 2;
		int result = timer  >> sweepShift;
		if(sweepNegate) {
			result = -result;
			if(pulseTwo) {
				result--;
			}
		}
		result += timer;
		if(result < 0x7FF && timer > 8) {
			if(sweepShift != 0 && sweepEnabled) {
				timer = result;
			}
		} else {
			sweepEnabled = false;
		}
	}
	
	@Override
	public void write(int register, int data) {
		switch(register) {
		case 1:
			sweepEnabled = (data >> 7 & 1) == 1;
			sweepPeriod = ((data & 0x70) >> 4) + 1;
			sweepNegate = (data >> 3 & 1) == 1;
			sweepShift = data & 7;
			sweepReloadFlag = true;
			break;
		}
	}
	
	@Override
	public int clock() {
		if(currentTimer == 0) {
			currentTimer = timer;
			currentStep--;
			if(currentStep < 0) {
				currentStep = 7;
			}
		} else {
			currentTimer--;
		}
		return 0;
	}
	
	@Override
	public int getSample() {
		final int dutyLevel = (duty >> currentStep & 1);
		final int sample = (envelope * dutyLevel);
		return sample;
	}
	
	@Override
	public void setVolume(int volume) {
		envelope = volume;
	}
	
	@Override
	public void setPeriod(int timer) {
		this.timer = timer;
	}
	
	@Override
	public void setDuty(int duty) {
		switch(duty) {
			case 0:
				this.duty = 0b1000000;
				break;
			case 1:
				this.duty = 0b1100000;
				break;
			case 2:
				this.duty = 0b1111000;
				break;
			case 3:
				this.duty = 0b10011111;
				break;
		}
	}

}
