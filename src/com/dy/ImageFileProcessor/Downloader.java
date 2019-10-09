package com.dy.ImageFileProcessor;

import java.io.File;

public class Downloader {
	public static File[][] targets = null;
	public static String[][] config = null;
	public static String fileBase;
	public static SRTMThread[] threads;
	public static int maxHeightDiffer;
	public static int threadNumber;
	
	public static void start() {
		threads = new SRTMThread[targets.length];
		for (int i = 0; i < targets.length; i++) {
			SRTMThread t = new SRTMThread();
			t.setIndex(i);
			t.setTarget(targets[i]);
			t.start();
			threads[i] = t;
		}
	}
	
	public static void end() {
		for (int i = 0; i < targets.length; i++) {
			threads[i].setFlag(true);
		}
	}
}
