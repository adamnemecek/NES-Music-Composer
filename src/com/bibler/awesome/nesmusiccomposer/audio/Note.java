package com.bibler.awesome.nesmusiccomposer.audio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import com.bibler.awesome.nesmusiccomposer.audio.NoteTable;

public class Note {
	
	private Point noteValues;
	private int length;
	Color color;
	private MusicStream parentStream;
	
	public Note() {
		noteValues = new Point(0, 0);
	}
	
	public void setPos(int x, int y) {
		if(noteValues == null) {
			noteValues = new Point(x, y);
		} else {
			noteValues.x = x;
			noteValues.y = y;
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setNoteValues(int notePos, int noteValue) {
		noteValues.x = notePos;
		noteValues.y = noteValue;
	}
	
	public void setParentStream(MusicStream parentStream) {
		this.parentStream = parentStream;
	}
	
	public void removeThyself() {
		parentStream.removeNote(this);
	}
	
	public boolean posFallsWithin(int posX) {
		return posX >= noteValues.x && posX < noteValues.x + length;
	}
	
	public void  setLength(int length) {
		this.length = NoteTable.getNoteLength(length);
	}
	
	public int getNoteLength() {
		return length;
	}
	
	public int getNoteX() {
		return noteValues.x;
	}
	
	public boolean paint(Graphics g, Point dims, int[] noteLaneNumbers, int currentMarkerX, int scrollX) {
		boolean playing = false;
		if(noteValues == null || noteValues.y >= 108) {
			return playing;
		}
		g.setColor(color);
		final int x = (dims.x * noteValues.x) + scrollX;
		final int y = 1512 - dims.y * (noteValues.y + 1);
		if(currentMarkerX > x && currentMarkerX < (x + (dims.x * length))) {
			g.setColor(Color.MAGENTA);
			playing = true;
		}
		Graphics2D g2d = (Graphics2D) g;
		final RoundRectangle2D rect = new RoundRectangle2D.Float(x + 1, y, (dims.x * length) - 2, dims.y, 10, 10);
		g2d.fill(rect);
		return playing;
	}
	
	@Override
	public String toString() {
		return "Pos: " + noteValues.x + ", " + noteValues.y;
	}

	public int getNotePeriod() {
		return NoteTable.getNote(noteValues.y);
	}

	public Point getNoteProperties() {
		return new Point(noteValues.x, noteValues.y);
	}

	public boolean matchesValues(Point inputPos) {
		return noteValues.x == inputPos.x && noteValues.y == inputPos.y;
	}

	public int getNoteEndPos() {
		return noteValues.x + length;
	}

	public boolean checkForClick(int x, int y) {
		if(y == noteValues.y) {
			if(x >= noteValues.x && x <= noteValues.x + length) {
				return true;
			}
		}
		return false;
	}

	public void updateNoteValues(int xUpdate, int yUpdate) {
		noteValues.x += xUpdate;
		noteValues.y += yUpdate;
	}
	
	public void updateNoteLength(int lengthIncrement) {
		length += lengthIncrement;
		parentStream.updateTotalLength();
	}
	
}
