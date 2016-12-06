package com.bibler.awesome.nesmusiccomposer.ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private PianoRollView view;
	
	public MainFrame() {
		view = new PianoRollView();
		view.setGridWidth(5);
		view.setLaneHeight(15);
		add(view);
		pack();
		setVisible(true);
	}
	
	public PianoRollView getPianoRollView() {
		return view;
	}

}
