package com.bibler.awesome.nesmusiccomposer.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongLoader {
	
	public static void loadSong(File f) {
		String[] lines = parseFile(f);
		processLines(lines);
	}

	private static String[] parseFile(File f) {
		String token = "";
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(f).useDelimiter("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> tempList = new ArrayList<String>();
		while(fileScanner.hasNext()) {
			token = fileScanner.next();
			if( (token.isEmpty() || token.trim().equals("") || token.trim().equals("\n")) ) {
				continue;
			}
			tempList.add(token);
		}
		fileScanner.close();
		String[] tempArray = tempList.toArray(new String[0]);
		return tempArray;
	}
	
	private static void processLines(String[] lines) {
		String s = "";
		for(int i = 0; i < lines.length; i++) {
			s = lines[i];
			if(s.contains("header")) {
				i += processHeader(lines, i + 1);
			}
		}
	}
	
	private static int processHeader(String[] lines, int startLine) {
		int lineCount = 1;
		String temp = lines[startLine];
		int startIndex = temp.indexOf('$') + 1;
		final int numStreams = Integer.parseInt(temp.substring(startIndex, startIndex + 2));
		System.out.println("Num streams: " + numStreams);
		for(int i = 0; i < numStreams; i++) {
			temp = lines[lineCount++];
			if(temp.contains("MUSIC_SQ1")) {
				
			}
		}
		return lineCount;
	}

}
