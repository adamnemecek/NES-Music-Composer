package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

public class VoiceSettingsPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1933056246324297616L;
	private int panelWidth;
	private int panelHeight;
	
	private VoiceStatusPanel squareOnePanel;
	private VoiceStatusPanel squareTwoPanel;
	private VoiceStatusPanel triPanel;
	private VoiceStatusPanel noisePanel;
	
	
	public VoiceSettingsPanel(int panelWidth, int panelHeight) {
		super();
		setDimensions(panelWidth, panelHeight);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initializeGrid();
	}
	
	public void setDimensions(int panelWidth, int panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}
	
	private void initializeGrid() {
		initializeElements();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(squareOnePanel);
		add(squareTwoPanel);
		add(triPanel);
		add(noisePanel);
		
	}
	
	public void initializeElements() {
		final int statusWidth = panelWidth;
		final int statusHeight = panelHeight / 4;
		
		squareOnePanel = new VoiceStatusPanel(statusWidth, statusHeight, "Square 1");
		squareTwoPanel = new VoiceStatusPanel(statusWidth, statusHeight, "Square 2");
		triPanel = new VoiceStatusPanel(statusWidth, statusHeight, "Tri");
		noisePanel = new VoiceStatusPanel(statusWidth, statusHeight, "Noise");
		
	}
	
	
	private class VoiceStatusPanel extends JPanel {
		
		private JLabel voiceLabel;
		private JCheckBox voiceBox;
		private JSlider voiceSlider;
		private JPanel boxSliderPanel;
		
		protected VoiceStatusPanel(int panelWidth, int panelHeight, String name) {
			super();
			initialize(panelWidth, panelHeight, name);
		}
		
		private void initialize(int panelWidth, int panelHeight, String name) {
			setPreferredSize(new Dimension(panelWidth, panelHeight));
			voiceLabel = new JLabel(name);
			voiceBox = new JCheckBox();
			voiceSlider = new JSlider();
			boxSliderPanel = new JPanel();
			boxSliderPanel.setLayout(new BorderLayout());
			
			setLayout(new BorderLayout());
			add(voiceLabel, BorderLayout.WEST);
			
			add(boxSliderPanel, BorderLayout.SOUTH);
			
			boxSliderPanel.add(voiceBox, BorderLayout.WEST);
			boxSliderPanel.add(voiceSlider, BorderLayout.EAST);
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}

}
