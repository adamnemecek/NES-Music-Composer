package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.bibler.awesome.nesmusiccomposer.toolbars.ToolBar;

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
		Color bg = new Color(192, 192, 192);
		pianoRoll = new MainRollView(bg);
		songPanel = new SongPanel(bg);
		toolPanel = new ToolPanel(bg);
		statusPanel = new StatusPanel(bg);
		middlePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, songPanel, pianoRoll);
		initializeLayout();
		toolPanel.getToolbar().registerObjectToNotify(frame);
		setBackground(bg);
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

	public ToolBar getToolbar() {
		return toolPanel.getToolbar();
	}

	public MainRollView getMainRollView() {
		return pianoRoll;
	}

	public VoiceSettingsPanel getStatusPanel() {
		return pianoRoll.getStatusPanel();
	}

}
