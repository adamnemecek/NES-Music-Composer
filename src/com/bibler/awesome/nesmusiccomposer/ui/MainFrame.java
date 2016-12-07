package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -4596167093653505485L;
	private PianoRollView view;
	
	public MainFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		view = new PianoRollView();
		view.setGridWidth(10);
		view.setLaneHeight(14);
		JScrollPane scroll = new JScrollPane(view);
		scroll.setPreferredSize(new Dimension(800, 500));
		add(scroll);
		pack();
		setVisible(true);
	}
	
	public PianoRollView getPianoRollView() {
		return view;
	}

}
