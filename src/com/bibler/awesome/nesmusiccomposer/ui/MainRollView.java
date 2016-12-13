package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class MainRollView extends JPanel {
	
	private JScrollPane leftScroll;
	private JScrollPane rightScroll;
	
	private ImagePanel imagePanel;
	private PianoRollView pianoRoll;
	
	private int imageWidth;
	private int imageHeight;
	
	public MainRollView() {
		super();
		initialize();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(800, 500));
		imagePanel = new ImagePanel(new File("C:/Users/rbibl/workspace/NES-Music-Composer/files/keyboard.png"));
		imageWidth = imagePanel.getPanelWidth();
		imageHeight = imagePanel.getPanelHeight();
		pianoRoll = new PianoRollView(imageHeight);
		pianoRoll.setGridWidth(10);
		pianoRoll.setLaneHeight(14);
		leftScroll = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rightScroll = new JScrollPane(pianoRoll);
		leftScroll.setPreferredSize(new Dimension(imageWidth, 500));
		rightScroll.setPreferredSize(new Dimension(800 - imageWidth, 500));
		leftScroll.getVerticalScrollBar().setModel(rightScroll.getVerticalScrollBar().getModel());
		pianoRoll.setScrollPane(rightScroll);
		setLayout(new BorderLayout());
		add(leftScroll, BorderLayout.LINE_START);
		add(rightScroll, BorderLayout.CENTER);
	}
	
	public PianoRollView getPianoRollView() {
		return pianoRoll;
	}
	
	public void advanceOneTick() {
		pianoRoll.advanceOneTick();
	}
	
	private class ImagePanel extends JPanel {
		private BufferedImage image;
		
		private int panelWidth;
		private int panelHeight;
		
		public ImagePanel(File f) {
			super();
			loadImage(f);
			setPreferredSize(new Dimension(panelWidth, panelHeight));
		}
		
		public int getPanelWidth() {
			return panelWidth;
		}
		
		public int getPanelHeight() {
			return panelHeight;
		}
		
		private void loadImage(File f) {
			try {
				image = ImageIO.read(f);
				panelWidth = image.getWidth();
				panelHeight = image.getHeight();
			} catch(IOException e) {}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}

}
