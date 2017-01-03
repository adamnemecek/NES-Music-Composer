package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	
	public StatusPanel(Color bg) {
		super();
		initialize(bg);
	}
	
	private void initialize(Color bg) {
		setPreferredSize(new Dimension(800, 50));
		setBackground(bg);
	}

}
