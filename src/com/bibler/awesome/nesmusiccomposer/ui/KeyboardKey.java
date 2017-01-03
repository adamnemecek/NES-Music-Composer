package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class KeyboardKey {
	
	private Rectangle keyboardRect;
	
	private int keyValue;
	private String keyName;
	private Color keyColor;
	private int keyType;
	private boolean clicked;
	
	public KeyboardKey(int x, int y, int width, int height, int keyValue, String keyName, int keyType) {
		keyboardRect = new Rectangle(x, y, width, height);
		this.keyValue = keyValue;
		this.keyName = keyName;
		this.keyType = keyType;
		if(keyType == Keyboard.BLACK) {
			keyColor = Color.BLACK;
		} else {
			keyColor = Color.WHITE;
		}
	}
	
	public int getKeyType() {
		return keyType;
	}
	
	public boolean clickedMe(int x, int y) {
		clicked = keyboardRect.contains(x, y);
		return clicked;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public int getKeyValue() {
		return keyValue;
	}
	
	public String getKeyName() {
		return keyName;
	}
	
	public void drawNormal(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(keyboardRect.x, keyboardRect.y, keyboardRect.width, keyboardRect.height);
		g.setColor(clicked ? Color.MAGENTA : keyColor);
		g.fillRect(keyboardRect.x + 1, keyboardRect.y + 1, keyboardRect.width - 1, keyboardRect.height - 1);
		if(keyType == Keyboard.WHITE) {
			g.setColor(Color.DARK_GRAY);
			g.drawString(keyName, (int) (keyboardRect.x + (keyboardRect.width * .85f)), (int) (keyboardRect.y + (keyboardRect.height * .70f)));
		}
	}

	public int getKeyHeight() {
		return keyboardRect.height;
	}
	

}
