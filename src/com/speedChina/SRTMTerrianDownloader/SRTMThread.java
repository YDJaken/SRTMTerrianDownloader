package com.speedChina.SRTMTerrianDownloader;

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
	private String target;

	public boolean stillRun() {
		return this.stillRuning;
	}

	public void setFlag(boolean input) {
		this.stopFlag = input;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	public void getDocument(String url, String src) throws IOException {
		if (url.lastIndexOf("/") != url.length() - 1 && url.lastIndexOf(".html") == -1) {
			url += "/";
		}
		Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(40000).get();
		Elements allElements = doc.getElementsByTag("a");
		l1: while (!allElements.isEmpty()) {
			if (stopFlag == true)
				return;
			Element e = allElements.remove(0);
			String href = e.attr("href");
			if (href.indexOf('?') != -1 || href.indexOf("..") != -1)
				continue;
			String html = e.html();
			if (html.equals("Parent Directory"))
				continue;
			String tmp = new String(html.trim());
			if (html.lastIndexOf(Downloader.fileType) != -1) {
				File target = new File(src + File.separatorChar + tmp);
				if (!target.exists()) {
					File p = target.getParentFile();
					if (!p.exists())
						p.mkdirs();
					target.createNewFile();
				}
				Detect401 status = HttpRequestUtil.postDownTerrain(url + tmp, target, Downloader.timeOut * 1000);
				if (stopFlag == true)
					return;
				if (status.getCode() == 200) {
					if (FileUtil.isGzip(target)) {
						if (!FileUtil.unGzipFile(target, new File(src + File.separatorChar + tmp + "丨"))) {
							DownloadFrame.changeStatus(index, "Gzip文件解码失败:" + url + tmp);
							error = true;
							target.delete();
							break l1;
						}
					}
					DownloadFrame.changeStatus(index, "下载文件完成:" + url + tmp);
				} else if (status.getCode() == 429) {
					if (status.getMessage() == null) {
						break l1;
					} else {
						try {
							Thread.sleep(Integer.parseInt(status.getMessage()) * 1000);
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				} else if (status.getMessage().equals("time out")) {
					while (HttpRequestUtil.postDownTerrain(url + tmp, target, Downloader.timeOut * 1000).getMessage()
							.equals("time out")) {

					}
				} else {
					DownloadFrame.changeStatus(index, "下载文件失败,访问代码为:" + status.getCode() + "路径为:" + url + tmp);
					error = true;
					target.delete();
					break l1;
				}
			} else {
				if (stopFlag == true)
					return;
				if (!URLRoutingUtil.isCommonFile(html))
					this.getDocument(url + tmp, src + File.separatorChar + tmp);
			}
		}
	}

	@Override
	public void run() {
		super.run();
		stillRuning = true;
		try {
			this.getDocument(Downloader.targetNet + target, Downloader.downloadBase + File.separatorChar + target);
		} catch (IOException e) {
			e.printStackTrace();
			DownloadFrame.changeStatus(index, "程序错误，请检查报错信息!" + e.getMessage().toString());
		}
		if (error == false)
			DownloadFrame.changeStatus(index, "下载完成");
		stillRuning = false;
	}
}