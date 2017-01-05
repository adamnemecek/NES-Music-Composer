package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

public class InstrumentAndEffectsView extends JPanel {
	
	private int gridWidth;
	private int panelHeight;
	private int numGridLines;
	
	public InstrumentAndEffectsView(int panelHeight) {
		super();
		this.panelHeight = panelHeight;
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {
				numGridLines = getPreferredSize().width / gridWidth;
			}

			@Override
			public void componentShown(ComponentEvent arg0) {}
			
		});
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(new Dimension(preferredSize.width, panelHeight));
	}
	
	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
		numGridLines = getPreferredSize().width / gridWidth;
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for(int i = 0; i < numGridLines; i++) {
			g.drawLine(i * gridWidth, 0, i * gridWidth, panelHeight);
		}
	}

}
