package com.bibler.awesome.nesmusiccomposer.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class EditMenu extends JMenu {
	
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	
	private MainFrame mainFrame;
	
	public EditMenu(MainFrame frame, String name) {
		super(name);
		this.mainFrame = frame;
		initialize();
	}
	
	private void initialize() {
		EditMenuActionListener listener = new EditMenuActionListener();
		undoItem = new JMenuItem("Undo");
		undoItem.setActionCommand("UNDO");
		undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		undoItem.addActionListener(listener);
		
		redoItem = new JMenuItem("Redo");
		redoItem.setActionCommand("REDO");
		redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		redoItem.addActionListener(listener);
		
		add(undoItem);
		add(redoItem);
		
	}

	
	private class EditMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			
			switch(command) {
			case "UNDO":
				mainFrame.undo();
				break;
				
			case "REDO":
				mainFrame.redo();
				break;
			}
			
		}
		
	}
}
