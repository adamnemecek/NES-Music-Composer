package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class SongPanel extends JPanel {
	
	public SongPanel(Color bg) {
		super();
		initialize(bg);
	}
	
	private void initialize(Color bg) {
		setPreferredSize(new Dimension(200, 500));
		setBackground(bg);
	}

}
