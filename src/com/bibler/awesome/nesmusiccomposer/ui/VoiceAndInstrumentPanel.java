package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VoiceAndInstrumentPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5599448721041384349L;
	private InstrumentAndEffectsView instrumentAndEffectsView;
	private VoiceSettingsPanel settingsPanel;
	
	private JScrollPane instrumentScroll;
	
	
	public VoiceAndInstrumentPanel(Color bg) {
		super();
		initialize(bg);
	}
	
	public InstrumentAndEffectsView getInstrumentAndEffectsView() {
		return instrumentAndEffectsView;	
	}
	
	private void initialize(Color bg) {
		
		setLayout(new BorderLayout());
		instrumentAndEffectsView = new InstrumentAndEffectsView(250);
		settingsPanel = new VoiceSettingsPanel(220, 250, bg);
		instrumentScroll = new JScrollPane(instrumentAndEffectsView, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(settingsPanel, BorderLayout.WEST);
		add(instrumentScroll, BorderLayout.CENTER);
	}
	
	public VoiceSettingsPanel getSettingsPanel() {
		return settingsPanel;
	}
	
	public JScrollPane getInstrumentScroll() {
		return instrumentScroll;
	}
}
