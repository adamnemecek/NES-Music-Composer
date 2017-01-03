package com.bibler.awesome.nesmusiccomposer.audio;

import java.util.ArrayList;

public class InstrumentManager {
	
	private Instrument defaultInstrument;
	
	private ArrayList<Instrument> instruments = new ArrayList<Instrument>();
	
	public InstrumentManager() {
		defaultInstrument = new Instrument(-1, "DEFAULT");
		defaultInstrument.addValue(0);
		defaultInstrument.addDuty(0);
	}
	
	public void addInstrument(Instrument instrument) {
		instruments.add(instrument);
	}
	
	public Instrument getInstrument(int currentFrame) {
		for(Instrument instrument : instruments) {
			if(instrument.checkInFrame(currentFrame)) {
				return instrument;
			}
		}
		return defaultInstrument;
	}

}
