package com.dy.ImageFileProcessor;

import java.io.File;
import java.io.IOException;

public class SRTMThread extends Thread {

	private boolean stopFlag = false;
	private boolean stillRuning = false;
	private boolean error = false;
	private int index = 0;

	public boolean stillRun() {
		return this.stillRuning;
	}

	public void setFlag(boolean input) {
		this.stopFlag = input;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	public void processImage() throws IOException {
	}

	@Override
	public void run() {
		super.run();
		if (stopFlag == true)
			return;
		stillRuning = true;
		File target = Downloader.requestFile();
		try {
			processImage();
		} catch (IOException e) {
			e.printStackTrace();
			DownloadFrame.changeStatus(index, "程序错误，请检查报错信息!" + e.getMessage().toString());
			error = true;
		}
		if (error == false)
			DownloadFrame.changeStatus(index, "重命名完成");
		stillRuning = false;
	}
}