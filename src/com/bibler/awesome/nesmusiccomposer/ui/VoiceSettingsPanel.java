package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VoiceSettingsPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1933056246324297616L;
	private int panelWidth;
	private int panelHeight;
	
	private JLabel squareOneLabel;
	private JLabel squareTwoLabel;
	private JLabel triLabel;
	private JLabel noiseLabel;
	
	private JComboBox squareOneEnableBox;
	private JComboBox squareTwoEnableBox;
	private JComboBox triEnableBox;
	private JComboBox noiseEnableBox;
	
	
	public VoiceSettingsPanel(int panelWidth, int panelHeight) {
		super();
		setDimensions(panelWidth, panelHeight);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void setDimensions(int panelWidth, int panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}

}
