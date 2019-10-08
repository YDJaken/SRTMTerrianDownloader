package com.dy.SRTMTerrianDownloader;

public class Downloader {
	public static String[] targets = null;
	public static String downloadBase;
	public static String targetNet;
	public static String fileType;
	public static SRTMThread[] threads;
	public static int timeOut;
	
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
