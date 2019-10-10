package com.dy.ImageFileProcessor;

import java.io.File;
import java.io.IOException;

import com.dy.ImageUtil.ImageUtill;

public class SRTMThread extends Thread {

	private boolean stopFlag = false;
	private boolean error = false;
	private int index = 0;

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

	public void processImage(FilePackage target) throws IOException {
		File img = target.getFile();
		String[] config = target.getConfig();
		File parent = img.getParentFile();
		File write = new File(parent.getAbsolutePath() + File.separatorChar + parent.getName() + "-" + img.getName());

		ImageUtill.setExifGPSTag(img, write, Double.parseDouble(config[2]), Double.parseDouble(config[1]),
				Double.parseDouble(config[3]));
	}

	@Override
	public void run() {
		super.run();
		if (stopFlag == true)
			return;
		while (stopFlag == false) {
			FilePackage target = Downloader.requestFile();
			if (target == null) {
				break;
			}
			double differ = Double.parseDouble(target.getConfig()[3]);
			differ = Math.abs(differ - Downloader.HeightAvg);
			if (differ > Downloader.maxHeightDiffer) {
				continue;
			}

			try {
				processImage(target);
			} catch (IOException e) {
				e.printStackTrace();
				DownloadFrame.changeStatus(index, "程序错误，请检查报错信息!" + e.getMessage().toString());
				error = true;
			}
			if (error == false)
				DownloadFrame.changeStatus(index, target.getFile().getAbsolutePath() + "处理完成。");
		}
		
		DownloadFrame.changeStatus(index, "处理完成。");
	}
}