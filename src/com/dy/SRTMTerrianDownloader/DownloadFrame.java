package com.dy.SRTMTerrianDownloader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class DownloadFrame extends JFrame implements ActionListener {

	static JTextArea textArea;

	public DownloadFrame() {
		this.setTitle("SRTMTerrianDownloader");
		this.getContentPane().setLayout(null);
		this.setSize(800, 350);

		JButton bu_quit = new JButton("QUIT");
		bu_quit.setBounds(325, 250, 150, 50);
		bu_quit.setActionCommand("QUIT");
		bu_quit.addActionListener(this);

		textArea = new JTextArea();
		textArea.setFont(new Font("宋体", Font.BOLD, 20));
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(400, 200));
		textArea.setBounds(50, 50, 700, 200);
		textArea.setLineWrap(true);
		scroll.setBounds(50, 50, 700, 200);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < Downloader.targets.length; i++) {
			build.append("\n").append("目标目录" + (i + 1)).append("的当前状态为:").append("正在获取数据情况").append("\n");
		}

		textArea.setText(build.toString());

		this.getContentPane().add(scroll);
		this.getContentPane().add(bu_quit);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public synchronized static void changeStatus(int index, String update) {
		String[] current = textArea.getText().split("\n");
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < current.length; i++) {
			if ((index + 1) * 2 == (i + 1)) {
				build.append("目标目录" + (index + 1)).append("的当前状态为:").append(update).append("\n");
			} else {
				if (current[i].equals("")) {
					build.append("\n");
				} else {
					build.append(current[i]).append("\n");
				}
			}
		}
		textArea.setText(build.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		switch (e.getActionCommand()) {
		case "QUIT":
			Downloader.end();
			this.dispose();
			break;
		}
	}

}
