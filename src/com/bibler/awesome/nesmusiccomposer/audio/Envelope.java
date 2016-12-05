package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

public class Envelope {
	
	private ArrayList<Integer> envelopeValues;
	private int currentStep;
	
	
	public Envelope() {
		envelopeValues = new ArrayList<Integer>();
	}
	
	public void addValue(int valueToAdd) {
		envelopeValues.add(valueToAdd);
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

}
