package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

public class Instrument {
	
	private ArrayList<Integer> envelopeValues;
	private int currentStep;
	private int currentDuty;
	
	private int instrumentStartPos;
	private int instrumentEffectiveLength;
	
	private int instrumentID;
	private String instrumentName;
	
	private ArrayList<Integer> instrumentDuties;
	
	
	public Instrument(int instrumentID, String instrumentName) {
		this.instrumentID = instrumentID;
		this.instrumentName = instrumentName;
		envelopeValues = new ArrayList<Integer>();
		instrumentDuties = new ArrayList<Integer>();
	}
	
	public void setStartPos(int instrumentStartPos) {
		this.instrumentStartPos = instrumentStartPos;
	}
	
	public void setInstrumentEffectiveLength(int instrumentEffectiveLength) {
		this.instrumentEffectiveLength = instrumentEffectiveLength;
	}
	
	public void setValues(int[] values) {
		envelopeValues.clear();
		for(Integer i : values) {
			envelopeValues.add(i);
		}
		instrumentEffectiveLength = Integer.MAX_VALUE;
	}
	
	public void setDuty(int[] duties) {
		instrumentDuties.clear();
		for(Integer i : duties) {
			instrumentDuties.add(i);
		}
	}
	
	public void addValue(int valueToAdd) {
		envelopeValues.add(valueToAdd);
	}
	
	public void addDuty(int dutyToAdd) {
		instrumentDuties.add(dutyToAdd);
	}
	
	public void reset() {
		currentStep = 0;
	}
	
	public int nextValue() {
		final int value = envelopeValues.get(currentStep++);
		if(currentStep >= envelopeValues.size()) {
			currentStep = envelopeValues.size() - 1;
		}
		return value;
	}
	
	public int nextDuty() {
		final int duty = instrumentDuties.get(currentDuty++);
		if(currentDuty >= instrumentDuties.size()) {
			currentDuty = 0;
		}
		return duty;
	}
	
	public int getInstrumentStartPos() {
		return instrumentStartPos;
	}
	
	public int getInstrumentEffectiveLength() {
		return instrumentEffectiveLength;
	}

	public boolean checkInFrame(int currentFrame) {
		return currentFrame >= instrumentStartPos && currentFrame <= (instrumentStartPos + instrumentEffectiveLength);
	}

	public String getInstrumentName() {
		return instrumentName;
	}

}
