package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
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

import com.bibler.awesome.nesmusiccomposer.systems.InputManager;

public class MainRollView extends JPanel {
	
	private JScrollPane leftScroll;
	private JScrollPane rightScroll;
	
	private PianoRollView pianoRoll;
	
	private int imageWidth;
	private int imageHeight;
	
	private Keyboard keyboard;
	private InputManager inputManager;
	
	public MainRollView(Color bg) {
		super();
		initialize(bg);
	}
	
	public void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
		pianoRoll.setInputManager(inputManager);
	}
	
	private void initialize(Color bg) {
		setBackground(bg);
		setPreferredSize(new Dimension(800, 500));
		keyboard = new Keyboard();
		imageWidth = keyboard.getPanelWidth();
		imageHeight = keyboard.getPanelHeight();
		pianoRoll = new PianoRollView(imageHeight);
		pianoRoll.setGridWidth(10);
		pianoRoll.setLaneHeight(14);
		leftScroll = new JScrollPane(keyboard, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		//pianoRoll.advanceOneTick();
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

}
