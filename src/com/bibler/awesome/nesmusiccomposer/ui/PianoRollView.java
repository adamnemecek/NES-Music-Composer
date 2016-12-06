package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class PianoRollView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5047420723890791229L;
	private int width;
	private int height;
	
	private int gridWidth;
	private int laneHeight;
	private Point dims;
	
	//frames
	private int numFrames;
	private int frameSizeInQuarterNotes = 64;
	private int gridLines;
	
	//Piano Roll
	private PianoRoll roll;
	
	public PianoRollView() {
		super();
		width = 800;
		height = 500;
		setPreferredSize(new Dimension(800, 500));
		addFrame();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					repaint();
					try {
						Thread.sleep(100);
					} catch(InterruptedException e) {}
				}
			}
		});
		t.start();
	}
	
	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
		if(dims == null) {
			dims = new Point(gridWidth, laneHeight);
		} else {
			dims.x = gridWidth;
		}
	}
	
	public void setLaneHeight(int laneHeight) {
		this.laneHeight = laneHeight;
		if(dims == null) {
			dims = new Point(gridWidth, laneHeight);
		} else {
			dims.y = laneHeight;
		}
	}
	
	public void addFrame() {
		numFrames++;
		gridLines = numFrames * frameSizeInQuarterNotes * 8;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintView(g);
	}
	
	private void paintView(Graphics g) {
		paintGrid(g);
		if(roll != null) {
			roll.paint(g, dims);
		}
	}
	
	private void paintGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		int currentX;
		for(int i = 0; i < gridLines; i++) {
			currentX = i * gridWidth;
			g.drawLine(currentX, 0, currentX, height);
		}
	}

}
