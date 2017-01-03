package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.menus.MainFrameMenu;
import com.bibler.awesome.nesmusiccomposer.systems.SongManager;
import com.bibler.awesome.nesmusiccomposer.toolbars.ToolBar;
import com.bibler.awesome.nesmusiccomposer.utils.UndoStack;

public class MainFrame extends JFrame implements Notifiable {
	
	private static final long serialVersionUID = -4596167093653505485L;
	private MainPanel mainPanel;
	private MainFrameMenu menu;
	
	public MainFrame() {
		super();
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException  | InstantiationException | IllegalAccessException e) {}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu = new MainFrameMenu(this);
		setJMenuBar(menu);
		mainPanel = new MainPanel(this);
		add(mainPanel);
		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH );
		pack();
		setVisible(true);
		
	}
	
	public void advanceOneTick() {
		mainPanel.getPianoRollView().advanceOneTick();
	}
	
	public PianoRollView getPianoRollView() {
		return mainPanel.getPianoRollView();
	}
	
	public void undo() {
		UndoStack.undo();
	}
	
	public void redo() {
		UndoStack.redo();
	}
	
	public void resetSong() {
		getPianoRollView().resetSong();
	}
	
	public void playSong() {
		getPianoRollView().play();
	}

	public ToolBar getToolbar() {
		return mainPanel.getToolbar();
	}

	@Override
	public void takeNotice(String message, Object Notifier) {
		switch(message) {
		case "ADVANCE_TICK":
			if(Notifier instanceof SongManager) {
				advanceOneTick();
			}
			break;
		case "STOP":
		case "RESET":
			resetSong();
			break;
		case "PLAY":
			playSong();
		}
	}

	public MainRollView getMainRollView() {
		return mainPanel.getMainRollView();
	}

	public Keyboard getKeyboard() {
		return getMainRollView().getKeyboard();
	}

}
