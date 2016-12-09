package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;

public class Note {
	
	private Point topLeft;
	private int length;
	Color color;
	
	public Note() {
		topLeft = new Point(0, 0);
	}
	
	public void setPos(int x, int y) {
		if(topLeft == null) {
			topLeft = new Point(x, y);
		} else {
			topLeft.x = x;
			topLeft.y = y;
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setNoteValues(int notePos, int noteValue) {
		topLeft.x = notePos;
		topLeft.y = noteValue;
	}
	
	public boolean posFallsWithin(int posX) {
		return posX >= topLeft.x && posX <= topLeft.x + length;
	}
	
	public void  setLength(int length) {
		this.length = NoteTable.getNoteLength(length);
	}
	
	public int getNoteLength() {
		return length;
	}
	
	public int getNoteX() {
		return topLeft.x;
	}
	
	public void paint(Graphics g, Point dims, int[] noteLaneNumbers, int currentMarkerX) {
		if(topLeft == null || topLeft.y >= 94) {
			return;
		}
		g.setColor(color);
		final int x = (dims.x * topLeft.x);
		final int y = 1512 - dims.y * (topLeft.y + 10);
		if(currentMarkerX > x && currentMarkerX < (x + (dims.x * length))) {
			g.setColor(Color.MAGENTA);
		}
		Graphics2D g2d = (Graphics2D) g;
		final RoundRectangle2D rect = new RoundRectangle2D.Float(x + 1, y, (dims.x * length) - 2, dims.y, 10, 10);
		g2d.fill(rect);
	}
	
	@Override
	public String toString() {
		return "Pos: " + topLeft.x + ", " + topLeft.y;
	}
	
	

}
