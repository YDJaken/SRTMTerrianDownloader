package com.dy.ObjFileProperty;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.dy.Util.OBJUtil;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	JTextField fild1;
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


		fild4 = new JTextField();
		fild4.setBounds(50, 190, 550, 50);
		fild4.setFont(new Font("黑体", Font.PLAIN, 20));
		fild4.setForeground(Color.RED);
		fild4.setBorder(null);

		this.getContentPane().add(lable1);
		this.getContentPane().add(fild1);
		this.getContentPane().add(fild4);
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
			String fileBase = fild1.getText();
			fild4.setText(fileBase + ":" + OBJUtil.computeArea(fileBase));
			
			break;
		case "QUIT":
			this.dispose();
			break;
		}
	}

}
