package com.dy.ImageFileProcessor;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dy.Util.FileUtil;
import com.dy.Util.HttpRequestUtil;
import com.dy.Util.URLRoutingUtil;
import com.dy.Util.Sup.Detect401;

public class SRTMThread extends Thread {

	private boolean stopFlag = false;
	private boolean stillRuning = false;
	private boolean error = false;
	private int index = 0;
	private File[] target;

	public boolean stillRun() {
		return this.stillRuning;
	}

	public void setFlag(boolean input) {
		this.stopFlag = input;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTarget(File[] target) {
		this.target = target;
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