package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InstrumentAndEffectsView extends JPanel {
	
	public InstrumentAndEffectsView() {
		super();
		setBackground(Color.RED);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for(int i = 0; i < 400; i++) {
			g.drawLine(i * 20, 0, i * 20, 200);
		}
	}

}
