package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PianoRollView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5047420723890791229L;
	private int viewWidth;
	private int viewHeight;
	
	private int gridWidth;
	private int laneHeight;
	private Point dims;
	
	//frames
	private int numFrames;
	private int frameSizeInQuarterNotes = 64;
	private int gridLines;
	
	//Piano Roll
	private PianoRoll roll;
	
	//Assets
	private BufferedImage keyboardImage;
	
	private int[] noteLaneNumbers = new int[95];
	
	private final Color[] laneColors = new Color[] {
			Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, Color.LIGHT_GRAY,
			Color.WHITE, Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, Color.LIGHT_GRAY, Color.WHITE
	};
	
	public PianoRollView() {
		super();
		
		try {
			keyboardImage = ImageIO.read(new File("C:/users/ryan/desktop/keyboard.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setGridWidth(5);
		addFrame();
		viewWidth = gridLines * gridWidth;
		viewHeight = keyboardImage.getHeight();
		generateNoteLaneNumbers();
		setPreferredSize(new Dimension(viewWidth, viewHeight));
		
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
	
	public void setPianoRoll(PianoRoll roll) {
		this.roll = roll;
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
		generateNoteLaneNumbers();
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
	
	private void generateNoteLaneNumbers() {
		int yStart = 1;
		int octaveCount = 6;
		for(int i = 93; i >= 0; i--) {
			noteLaneNumbers[i] = (yStart * laneHeight) + 7;
			yStart++;
			octaveCount--;
			if(octaveCount == 5 || octaveCount == 0) {
				yStart++;
			}
			if(octaveCount == 0) {
				octaveCount = 12;
			}
		}
	}
	
	private void paintView(Graphics g) {
		paintLanes(g);
		paintGrid(g);
		if(roll != null) {
			roll.paint(g, dims, noteLaneNumbers);
		}
		int viewX = getX();
		if(viewX < 0) {
			viewX *= -1;
		}
		g.drawImage(keyboardImage, viewX, 0, null);
	}
	
	private void paintLanes(Graphics g) {
		int octaveCount = 0;
		final int numLanes = viewHeight / laneHeight;
		for(int i = 0; i < numLanes; i++) {
			g.setColor(laneColors[octaveCount++]);
			if(octaveCount > 11) {
				octaveCount = 0;
			}
			g.fillRect(0, (i * laneHeight), viewWidth, laneHeight);
			
		}
	}
	private void paintGrid(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int currentX;
		for(int i = 0; i < gridLines; i++) {
			currentX = i * gridWidth;
			g.drawLine(currentX, 0, currentX, viewHeight);
		}
	}

}
