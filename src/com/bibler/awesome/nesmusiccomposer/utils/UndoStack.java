package com.bibler.awesome.nesmusiccomposer.utils;

import java.util.Stack;

import com.bibler.awesome.nesmusiccomposer.commands.Command;

public class UndoStack {
	
	private static Stack<Command> undo = new Stack<Command>();
	private static Stack<Command> redo = new Stack<Command>();
	
	public static void executeAndStore(Command c) {
		undo.push(c);
		c.execute();
	}
	
	public static void undo() {
		Command c = undo.pop();
		redo.push(c);
		c.undo();
	}
	
	public static void redo() {
		Command c = redo.pop();
		undo.push(c);
		c.redo();
	}

}
