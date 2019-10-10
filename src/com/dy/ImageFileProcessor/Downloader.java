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
	private static String[] currentConfig = null;
	private static LinkedList<File> pendingFiles = new LinkedList<File>();

	public static void start() {
		threads = new SRTMThread[threadNumber];
		for (int i = 0; i < threadNumber; i++) {
			SRTMThread t = new SRTMThread();
			t.setIndex(i);
			t.start();
			threads[i] = t;
		}
	}

	public static void loadFiles() {
		for (int i = 0; i < targets.size(); i++) {
			LinkedList<File> target = targets.get(i);
			if (target.size() > 0) {
				pendingFiles.add(target.pop());
			}
		}
	}

	public static synchronized FilePackage requestFile() {
		if (currentConfig == null) {
			if (config.size() == 0 && pendingFiles.size() == 0) {
				return null;
			}
			if (config.size() != 0) {
				currentConfig = config.pop();
				loadFiles();
			}
		}
		if (pendingFiles.size() == 0) {
			if (config.size() != 0) {
				currentConfig = config.pop();
				loadFiles();
			}
		}

		if (pendingFiles.size() == 0) {
			return null;
		} else {
			return new FilePackage(pendingFiles.pop(), currentConfig);
		}

	}

	public static void end() {
		for (int i = 0; i < threads.length; i++) {
			threads[i].setFlag(true);
		}
	}
}
