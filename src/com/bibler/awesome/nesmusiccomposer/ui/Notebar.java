package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;

public class Notebar {
	
	Point topLeft;
	int length;
	
	public void setPos(int x, int y) {
		if(topLeft == null) {
			topLeft = new Point(x, y);
		} else {
			topLeft.x = x;
			topLeft.y = y;
		}
	}
	
	public void  setLength(int length) {
		this.length = NoteTable.getNoteLength(length);
	}
	
	public int getNoteX() {
		return topLeft.x;
	}
	
	public void paint(Graphics g, Point dims, int[] noteLaneNumbers) {
		if(topLeft.y >= 94) {
			return;
		}
		g.setColor(Color.RED);
		final int x = (dims.x * topLeft.x) + 120;
		final int y = 1512 - dims.y * (topLeft.y + 10);
		g.fillRect(x, y, dims.x * length, dims.y);
	}
	
	@Override
	public String toString() {
		return "Pos: " + topLeft.x + ", " + topLeft.y;
	}
	
	

}
