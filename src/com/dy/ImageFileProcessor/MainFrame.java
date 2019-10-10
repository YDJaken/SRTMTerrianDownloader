package com.dy.ImageFileProcessor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.dy.Util.FileUtil;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	JTextField fild1;
	JTextField fild2;
	JTextField fild3;
	JTextField fild4;

	public MainFrame() {
		this.setTitle("ImageFileProcessor");
		this.getContentPane().setLayout(null);
		this.setSize(800, 400);

		JButton print = new JButton("开始");
		print.setBounds(150, 300, 150, 50);
		print.setActionCommand("DOWNLOAD");
		print.addActionListener(this);
		JButton bu_quit = new JButton("退出");
		bu_quit.setBounds(450, 300, 100, 50);
		bu_quit.setActionCommand("QUIT");
		bu_quit.addActionListener(this);

		JLabel lable1 = new JLabel("请输入文件目录:");
		lable1.setBounds(50, 30, 100, 50);
		fild1 = new JTextField();
		fild1.setBounds(210, 40, 550, 30);
		fild1.setText("/data/Image/");

		JLabel lable2 = new JLabel("请输入线程个数:");
		lable2.setBounds(50, 80, 200, 50);
		fild2 = new JTextField();
		fild2.setBounds(210, 90, 550, 30);
		fild2.setText(Runtime.getRuntime().availableProcessors() * 2 + "");

		JLabel lable3 = new JLabel("请输入最大允许高度差:");
		lable3.setBounds(50, 130, 200, 50);
		fild3 = new JTextField();
		fild3.setBounds(210, 140, 550, 30);
		fild3.setText("40");

		fild4 = new JTextField();
		fild4.setBounds(50, 190, 550, 50);
		fild4.setFont(new Font("黑体", Font.PLAIN, 35));
		fild4.setForeground(Color.RED);
		fild4.setBorder(null);

		this.getContentPane().add(lable1);
		this.getContentPane().add(fild1);
		this.getContentPane().add(lable2);
		this.getContentPane().add(fild2);
		this.getContentPane().add(lable3);
		this.getContentPane().add(fild3);
		this.getContentPane().add(fild4);
		this.getContentPane().add(bu_quit);
		this.getContentPane().add(print);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public int canProcess(String fileBase, Map<String, Object> config) {
		int ret = 0;
		File[] subFolders = FileUtil.loadsubFilesAsFile(new File(fileBase));
		if (subFolders != null) {
			int imgCount = 0;
			LinkedList<LinkedList<File>> filesList = new LinkedList<LinkedList<File>>();
			for (int i = 0; i < subFolders.length; i++) {
				File target = subFolders[i];
				if (target.isDirectory()) {
					File[] imgs = FileUtil.loadsubFilesAsFile(target);
					if (imgs != null) {
						if (imgCount == 0) {
							imgCount = imgs.length;
						} else {
							if (imgCount != imgs.length) {
								return -1;
							}
							LinkedList<File> fs = new LinkedList<File>();
							for (int j = 0; j < imgs.length; j++) {
								fs.add(imgs[j]);
							}
							filesList.add(fs);
						}
					}
				} else {
					config.put("Config", MainFrame.loadConfig(target, config));
				}
			}
			config.put("FileList", filesList);
		} else {
			return -2;
		}
		return ret;
	}

	public static LinkedList<String[]> loadConfig(File configFile, Map<String, Object> configration) {
		String config = FileUtil.readString(configFile);
		String[] sub = config.split("\r\n");
		LinkedList<String[]> configs = new LinkedList<String[]>();
		double sum = 0.0;
		int count = 0;
		for (int i = 0; i < sub.length; i++) {
			String[] tmp = sub[i].split("\t");
			try {
				double a = Double.parseDouble(tmp[3]);
				sum += a;
				count++;
			} catch (NumberFormatException e) {
				continue;
			}
			configs.add(tmp);
		}
		Double avg = sum / count;
		configration.put("HeightAvg", avg);
		return configs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "DOWNLOAD":
			String fileBase = fild1.getText();
			int threadNumber = Integer.parseInt(fild2.getText());
			HashMap<String, Object> config = new HashMap<String, Object>();
			int errorCode = canProcess(fileBase, config);
			if (errorCode != 0) {
				String reason;
				switch (errorCode) {
				case -2:
					reason = "目录有误。";
					break;
				case -1:
					reason = "文件数目不匹配";
					break;
				default:
					reason = "未知";
					break;
				}
				fild4.setText("出现错误： " + reason);
				return;
			}
			fild4.setText("");
			Downloader.config = (LinkedList<String[]>) config.get("Config");
			Downloader.targets = (LinkedList<LinkedList<File>>) config.get("FileList");
			Downloader.HeightAvg = (Double) config.get("HeightAvg");
			Downloader.fileBase = fileBase;
			Downloader.threadNumber = threadNumber;
			Downloader.maxHeightDiffer = Integer.parseInt(fild3.getText());
			new DownloadFrame();
			Downloader.start();
			config.clear();
			this.dispose();
			break;
		case "QUIT":
			this.dispose();
			break;
		}
	}

}
