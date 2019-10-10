package com.dy.ImageFileProcessor;

import java.io.File;
import java.util.LinkedList;

public class Downloader {
	public static LinkedList<LinkedList<File>> targets = null;
	public static LinkedList<String[]> config = null;
	public static Double HeightAvg = null;
	public static String fileBase;
	public static SRTMThread[] threads;
	public static int maxHeightDiffer;
	public static int threadNumber;
	
	public static void start() {
		threads = new SRTMThread[threadNumber];
		for (int i = 0; i < threadNumber; i++) {
			SRTMThread t = new SRTMThread();
			t.setIndex(i);
			t.start();
			threads[i] = t;
		}
	}
	
	public static File requestFile() {
		return null;
		
	}
	
	public static void end() {
		for (int i = 0; i < threads.length; i++) {
			threads[i].setFlag(true);
		}
	}
}
