package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;

public class Keyboard extends JPanel implements Runnable, Notifier, Notifiable {
	
	public static final int BLACK = 0x00;
	public static final int WHITE = 0x01;
	
	private KeyboardKey[] keys = new KeyboardKey[108];
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	private int panelWidth = 220;
	private int panelHeight;
	
	public Keyboard() {
		super();
		fillUpWhiteKeysArray();
		fillUpBlackKeysArray();
		calculateDimensions();
		initialize();
		Thread t = new Thread(this);
		t.start();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		addMouseListener(new KeyboardMouseListener());
	}
	
	private void calculateDimensions() {
		panelHeight = 0;
		for(KeyboardKey key : keys) {
			if(key.getKeyType() == WHITE) {
				panelHeight += key.getKeyHeight();
			}
		}
	}
	
	public int getPanelWidth() {
		return panelWidth;
	}
	
	public int getPanelHeight() {
		return panelHeight;
	}
	
	public void registerObjectToNotify(Notifiable objectToNotify) {
		objectsToNotify.add(objectToNotify);
	}
	
	private void fillUpWhiteKeysArray() {
		final int[] keyHeights = new int[] {
			21, 28, 28, 21, 21, 28, 21
		};
		
		final String[] keyBaseNames = new String[] {
			"B", "A", "G", "F", "E", "D", "C"	
		};
		final int[] steps = new int[] {
			2, 2, 2, 1, 2, 2, 1
		};
		int y = 0;
		int keyHeight = 0;
		int keyValue = 107;
		String keyName = "";
		for(int i = 0; i < 63; i++) {
			keyHeight = keyHeights[i % 7];
			keyName = keyBaseNames[i % 7] + (9 - (i / 7));
			keys[keyValue] = new KeyboardKey(0, y, panelWidth, keyHeight, keyValue, keyName, WHITE);
			y += keyHeight;
			keyValue -= steps[i % 7];
		}
	}
	
	private void fillUpBlackKeysArray() {
		final int[] keyYStartPositions = new int[] {
			14, 42, 70, 112, 140
		};
		final String[] keyBaseNames = new String[] {
				"As", "Gs", "Fs", "Ds", "Cs"
		};
		final int[] steps = new int[] {
			2, 2, 3, 2, 3	
		};
		int y = 0;
		int keyValue = 106;
		String keyName = "";
		for(int i = 0; i < 45; i++) {
			keyName = keyBaseNames[i % 5] + (9 - (i / 5));
			y = 168 * (i / 5) + keyYStartPositions[i % 5];
			keys[keyValue] = new KeyboardKey(0, y, 128, 14, keyValue, keyName, BLACK);
			keyValue -= steps[i % 5];
		}
		
	}
	
	private void checkForKeyClick(int x, int y) {
		KeyboardKey clickedKey = null;
		for(KeyboardKey key : keys) {
			key.setClicked(false);
			if(key.getKeyType() == BLACK && clickedKey == null) {
				if(key.clickedMe(x, y)) {
					clickedKey = key;
				}
			}
		}
		if(clickedKey == null) {
			for(KeyboardKey key : keys) {
				if(key.getKeyType() == WHITE) {
					if(key.clickedMe(x, y)) {
						clickedKey = key;
						break;
					}
				}
			}
		}
		if(clickedKey != null) {
			notify("KEY_CLICKED:" + clickedKey.getKeyValue());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawWhiteKeys(g);
		drawBlackKeys(g);
	}
	
	private void drawWhiteKeys(Graphics g) {
		for(KeyboardKey key : keys) {
			if(key.getKeyType() == WHITE) {
				key.drawNormal(g);
				key.setClicked(false);
			}
		}
	}
	
	private void drawBlackKeys(Graphics g) {
		for(KeyboardKey key : keys) {
			if(key.getKeyType() == BLACK) {
				key.drawNormal(g);
				key.setClicked(false);
			}
		}
	}
	
	private class KeyboardMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			checkForKeyClick(arg0.getX(), arg0.getY());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			repaint();
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {}
		}
	}

	@Override
	public void notify(String messageToSend) {
		for(Notifiable objectToNotify : objectsToNotify) {
			objectToNotify.takeNotice(messageToSend,  this);
		}
	}

	@Override
	public void takeNotice(String message, Object Notifier) {
		if(message.contains("PAINT_NOTE")) {
			int keyValue = Integer.parseInt(message.split(":")[1]);
			keys[keyValue].setClicked(true);
		}
	}

}
