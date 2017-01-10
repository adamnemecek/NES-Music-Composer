package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import com.bibler.awesome.nesmusiccomposer.audio.Song;

public class InstrumentAndEffectsView extends JPanel {
	
	private int gridWidth;
	private int panelHeight;
	private int panelWidth;
	private int numGridLines;
	private int laneHeight;
	
	private Song song;
	
	private Color[] channelColors = new Color[] {
		new Color(0, 0, 255, 175), 
		new Color(255, 0, 0, 175),
		new Color(0, 255, 0, 175),
		new Color(0xFF, 0xAE, 0x3C, 175)
	};
	
	
	
	public InstrumentAndEffectsView(int panelHeight) {
		super();
		this.panelHeight = panelHeight;
		laneHeight = panelHeight / 4;
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {
				numGridLines = getPreferredSize().width / gridWidth;
				panelWidth = getPreferredSize().width;
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
		for(int i = 0; i < 4; i++) {
			g.setColor(channelColors[i]);
			g.drawRect(0, i * laneHeight, panelWidth, laneHeight);
		}
		g.setColor(Color.BLACK);
		for(int i = 0; i < numGridLines; i++) {
			g.drawLine(i * gridWidth, 0, i * gridWidth, panelHeight);
		}
		
	}

}
