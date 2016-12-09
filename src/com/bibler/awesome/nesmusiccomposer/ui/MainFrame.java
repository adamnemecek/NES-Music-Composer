package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -4596167093653505485L;
	private MainRollView view;
	
	public MainFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		view = new MainRollView();
		add(view);
		pack();
		setVisible(true);
	}
	
	public void advanceOneTick() {
		view.advanceOneTick();
	}
	
	public PianoRollView getPianoRollView() {
		return view.getPianoRollView();
	}

}
