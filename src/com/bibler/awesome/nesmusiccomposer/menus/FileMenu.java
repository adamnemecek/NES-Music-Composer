package com.bibler.awesome.nesmusiccomposer.menus;

import javax.swing.JMenu;

import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class FileMenu extends JMenu {
	
	private MainFrame frame;
	
	public FileMenu(MainFrame frame, String name) {
		super(name);
		this.frame = frame;
	}

}
