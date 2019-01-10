package com.speedChina.SRTMTerrianDownloader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	JTextField fild1;
	JTextField fild2;
	JTextField fild3;
	JTextField fild4;
	JTextField fild5;
	
	public MainFrame() {
		this.setTitle("SRTMTerrianDownloader");
		this.getContentPane().setLayout(null);
		this.setSize(800, 400);

		JButton print = new JButton("DOWNLOAD");
		print.setBounds(150, 300, 150, 50);
		print.setActionCommand("DOWNLOAD");
		print.addActionListener(this);
		JButton bu_quit = new JButton("QUIT");
		bu_quit.setBounds(450, 300, 100, 50);
		bu_quit.setActionCommand("QUIT");
		bu_quit.addActionListener(this);

		JLabel lable1 = new JLabel("请输入下载目录:");
		lable1.setBounds(50, 30, 100, 50);
		fild1 = new JTextField();
		fild1.setBounds(210, 40, 550, 30);
		fild1.setText("/data/DownLoad/SRTMTerrian");

		JLabel lable2 = new JLabel("请输入目标网络地址:");
		lable2.setBounds(50, 80, 150, 50);
		fild2 = new JTextField();
		fild2.setBounds(210, 90, 550, 30);
		fild2.setText("https://dds.cr.usgs.gov/srtm/version2_1/");

		JLabel lable3 = new JLabel("请输入目标目录:");
		lable3.setBounds(50, 130, 100, 50);
		fild3 = new JTextField();
		fild3.setBounds(210, 140, 550, 30);
		fild3.setText("SRTM1,SRTM3");
		
		JLabel lable4 = new JLabel("请输入文件格式:");
		lable4.setBounds(50, 180, 100, 50);
		fild4 = new JTextField();
		fild4.setBounds(210, 190, 550, 30);
		fild4.setText(".hgt.zip");
		
		JLabel lable5 = new JLabel("请输入断流时间:");
		lable5.setBounds(50, 230, 100, 50);
		fild5 = new JTextField();
		fild5.setBounds(210, 240, 550, 30);
		fild5.setText("120");

		this.getContentPane().add(lable1);
		this.getContentPane().add(fild1);
		this.getContentPane().add(lable2);
		this.getContentPane().add(fild2);
		this.getContentPane().add(lable3);
		this.getContentPane().add(fild3);
		this.getContentPane().add(lable4);
		this.getContentPane().add(fild4);
		this.getContentPane().add(lable5);
		this.getContentPane().add(fild5);
		this.getContentPane().add(bu_quit);
		this.getContentPane().add(print);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "DOWNLOAD":
			Downloader.downloadBase = fild1.getText();
			Downloader.targetNet = fild2.getText();
			Downloader.targets = fild3.getText().split(",");
			Downloader.fileType = fild4.getText();
			Downloader.timeOut = Integer.parseInt(fild5.getText());
			new DownloadFrame();
			Downloader.start();
			this.dispose();
			break;
		case "QUIT":
			this.dispose();
			break;
		}
	}

}
