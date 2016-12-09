package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainPanel extends JPanel {
	
	private JSplitPane middlePane;
	private MainRollView pianoRoll;
	private SongPanel songPanel;
	private ToolPanel toolPanel;
	private StatusPanel statusPanel;
	
	private MainFrame frame;
	
	public MainPanel(MainFrame frame) {
		this.frame = frame;
		initialize();
	}
	
	private void initialize() {
		pianoRoll = new MainRollView();
		songPanel = new SongPanel();
		toolPanel = new ToolPanel();
		statusPanel = new StatusPanel();
		middlePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, songPanel, pianoRoll);
		
		initializeLayout();
	}
	
	private void initializeLayout() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		add(toolPanel, BorderLayout.NORTH);
		add(middlePane, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
		
	}
	
	public PianoRollView getPianoRollView() {
		return pianoRoll.getPianoRollView();
	}

}
