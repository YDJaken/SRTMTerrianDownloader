package com.dy.ImageFileProcessor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DownloadFrame extends JFrame implements ActionListener {

	static JTextArea textArea;
	static JTextField fild1;

	public DownloadFrame() {
		this.setTitle("ImageFileProcessor");
		this.getContentPane().setLayout(null);
		this.setSize(800, 350);

		JButton bu_quit = new JButton("退出");
		bu_quit.setBounds(325, 250, 150, 50);
		bu_quit.setActionCommand("QUIT");
		bu_quit.addActionListener(this);

		fild1 = new JTextField();
		fild1.setFont(new Font("宋体", Font.BOLD, 20));
		fild1.setBounds(50, 0, 700, 30);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("宋体", Font.BOLD, 20));
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(400, 200));
		textArea.setBounds(50, 50, 700, 200);
		textArea.setLineWrap(true);
		scroll.setBounds(50, 50, 700, 200);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < Downloader.threadNumber; i++) {
			build.append("\n").append("进程" + (i + 1)).append("的当前状态为:").append("正在获取数据情况").append("\n");
		}

		textArea.setText(build.toString());

		this.getContentPane().add(scroll);
		this.getContentPane().add(bu_quit);
		this.getContentPane().add(fild1);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public synchronized static void changeStatus(int index, String update) {
		String[] current = textArea.getText().split("\n");
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < current.length; i++) {
			if ((index + 1) * 2 == (i + 1)) {
				build.append("进程" + (index + 1)).append("的当前状态为:").append(update).append("\n");
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

	public synchronized static void updateStatus(int complete, int total) {
		double ratio = (complete * 1.0) /(total * 1.0);
		fild1.setText("已完成:" + complete + ", 总任务:" + total + ", 剩余:" + (total - complete) + ", 完成率:" + ratio);
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
