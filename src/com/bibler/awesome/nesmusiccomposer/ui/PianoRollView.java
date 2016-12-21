package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bibler.awesome.nesmusiccomposer.systems.InputAction;
import com.bibler.awesome.nesmusiccomposer.systems.InputManager;

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
	
	//Parent
	private JScrollPane scrollPane;
	
	private int[] noteLaneNumbers = new int[95];
	
	private final Color[] laneColors = new Color[] {
			Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, Color.LIGHT_GRAY,
			Color.WHITE, Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, Color.LIGHT_GRAY, Color.WHITE
	};
	
	private Color gridLineColor = new Color(12,12,12,50);
	
	private int currentMarkerX;
	
	private Rectangle bounds;
	
	private InputManager inputManager;
	
	public PianoRollView(int height) {
		super();
		addMouseListener(new PianoRollMouseListener());
		setAutoscrolls(true);
		setGridWidth(5);
		addFrame();
		viewWidth = gridLines * gridWidth;
		viewHeight = height;
		generateNoteLaneNumbers();
		setPreferredSize(new Dimension(viewWidth, viewHeight));
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					
					repaint();
					if(currentMarkerX > 250) {
						scrollParent();
					}
					try {
						Thread.sleep(10);
					} catch(InterruptedException e) {}
				}
			}
		});
		t.start();
	}
	
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
	public void setPianoRoll(PianoRoll roll) {
		this.roll = roll;
		//updateViewWidth(roll.getTotalNoteLength());
		updateViewWidth(350);
	}
	
	private void updateViewWidth(int totalNoteWidth) {
		viewWidth = gridWidth * totalNoteWidth;
		setPreferredSize(new Dimension(viewWidth, viewHeight));
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
	
	public void advanceOneTick() {
		currentMarkerX += gridWidth;
		if(currentMarkerX >= viewWidth) {
			resetSong();
		}
	}
	
	public void resetSong() {
		currentMarkerX = 0;
		scrollPane.getHorizontalScrollBar().setValue(0);
	}
	
	public void addNoteToEnd(Point inputPoint, int currentVoice, int currentNoteLength) {
		//final int y = ((viewHeight - inputPoint.y) / laneHeight) - 9;
		roll.addNoteToEnd(inputPoint.y, currentNoteLength, currentVoice);
	}
	
	private void scrollParent() {
		scrollPane.getHorizontalScrollBar().setValue(currentMarkerX - 250); 
	}
	
	private void handleMouseClick(MouseEvent e) {
		final int x = e.getX() / gridWidth;
		final int y = ((viewHeight - e.getY()) / laneHeight) - 9;
		//roll.addNote(x, y, currentLength, currentVoice);
		inputManager.registerInputAction(new InputAction(InputManager.PIANO_ROLL_CLICKED, new Point(x, y), 0, this));
	}
	
	private boolean scrolledDown;
	
	@Override
	public void paintComponent(Graphics g) {
		if(!scrolledDown) {
			scrollPane.getVerticalScrollBar().setValue(1270);
			scrolledDown = true;
		}
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
			roll.paint(g, dims, noteLaneNumbers, currentMarkerX);
		}
		paintMarker(g);
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
			g.setColor(gridLineColor);
			g.drawLine(0, (i * laneHeight), viewWidth, (i * laneHeight));
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
	
	private void paintMarker(Graphics g) {
		g.setColor(Color.RED);
		g.drawLine(currentMarkerX, 0, currentMarkerX, viewHeight);
	}
	
	private class PianoRollMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			handleMouseClick(arg0);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}

	public void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
	}

}
