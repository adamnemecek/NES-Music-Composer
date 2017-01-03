package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.bibler.awesome.nesmusiccomposer.toolbars.ToolBar;

public class ToolPanel extends JPanel {
	
	private ToolBar toolbar;
	
	public ToolPanel(Color bg) {
		super();
		initialize(bg);
	}
	
	private void initialize(Color bg) {
		setPreferredSize(new Dimension(800, 50));
		toolbar = new ToolBar(700, 25, null, bg);
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.WEST);
		setBackground(bg);
	}

	public ToolBar getToolbar() {
		return toolbar;
	}

}
