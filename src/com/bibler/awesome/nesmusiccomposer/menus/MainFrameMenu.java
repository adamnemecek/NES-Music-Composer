package com.bibler.awesome.nesmusiccomposer.menus;

import javax.swing.JMenuBar;

import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class MainFrameMenu extends JMenuBar {

	
	private static final long serialVersionUID = -5696426661185813460L;
	
	private FileMenu fileMenu;
	private EditMenu editMenu;
	
	public MainFrameMenu(MainFrame frame) {
		super();
		fileMenu = new FileMenu(frame);
		editMenu = new EditMenu(frame);
		add(fileMenu);
		add(editMenu);
	}

}
