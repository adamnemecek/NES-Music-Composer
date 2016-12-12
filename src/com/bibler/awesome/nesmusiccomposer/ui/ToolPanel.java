package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.bibler.awesome.nesmusiccomposer.toolbars.ToolBar;

public class ToolPanel extends JPanel {
	
	private ToolBar toolbar;
	
	public ToolPanel() {
		super();
		initialize();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(800, 50));
		toolbar = new ToolBar(700, 25, null);
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.WEST);
	}

}
