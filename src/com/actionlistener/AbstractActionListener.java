package com.actionlistener;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalBorders.ButtonBorder;

import com.utils.StringUtils;

public abstract class AbstractActionListener implements ActionListener {
	protected JFrame frame = new JFrame("增量包生成工具");// 框架布局
	private Container con = new Container();
	private JLabel label1 = new JLabel("SVN更新信息文件");
	private JLabel label2 = new JLabel("项目根目录");
	private JLabel label3 = new JLabel("文件生成目录");
	protected JTextField text1 = new JTextField();// SVN更新信息文件的路局
	protected JTextField text2 = new JTextField();// 项目的路径
	protected JTextField text3 = new JTextField();// 生成文件的路径
	private JButton button1 = new JButton("...");// 选择
	private JButton button2 = new JButton("...");// 选择
	private JButton button3 = new JButton("...");// 选择
	private JFileChooser jfc = new JFileChooser();// 文件选择器
	private JButton button4 = new JButton("一键生成");//

	public AbstractActionListener() {
		jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setSize(430, 210);// 设定窗口大小
		frame.setResizable(false);
		label1.setBounds(9, 18, 200, 20);
		label1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		text1.setBounds(130, 18, 235, 22);
		button1.setBounds(364, 18, 50, 21);
		button1.setBackground(Color.WHITE);
		label2.setBounds(50, 55, 200, 20);
		label2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		text2.setBounds(130, 55, 235, 22);
		button2.setBounds(364, 55, 50, 21);
		button2.setBackground(Color.WHITE);
		label3.setBounds(36, 93, 200, 20);
		label3.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		text3.setBounds(130, 93, 235, 22);
		button3.setBounds(364, 93, 50, 21);
		button3.setBackground(Color.WHITE);
		button4.setBounds(335, 130, 80, 40);
		button4.setBackground(Color.WHITE);
		button4.setBorder(new ButtonBorder());
		button4.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		button1.addActionListener(this); // 添加事件处理
		button2.addActionListener(this); // 添加事件处理
		button3.addActionListener(this); // 添加事件处理
		button4.addActionListener(this); // 添加事件处理
		con.add(label1);
		con.add(text1);
		con.add(button1);
		con.add(label2);
		con.add(text2);
		con.add(button2);
		con.add(label3);
		con.add(text3);
		con.add(button3);
		con.add(button4);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
		frame.add(con);
	}

	/**
	 * 事件监听的方法
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
			jfc.setFileSelectionMode(0);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;
			} else {
				File f = jfc.getSelectedFile();// f为选择到的目录
				text1.setText(f.getAbsolutePath());
			}
		}
		// 绑定到选择文件，先择文件事件
		if (e.getSource().equals(button2)) {
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				text2.setText(f.getAbsolutePath());
			}
		}
		// 绑定到选择文件，先择文件事件
		if (e.getSource().equals(button3)) {
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				text3.setText(f.getAbsolutePath());
			}
		}
		if (e.getSource().equals(button4)) {
			if (StringUtils.isBlank(text1.getText())) {
				JOptionPane.showMessageDialog(null, "请选择SVN更新信息文件", "提示", 2);
			} else if (StringUtils.isBlank(text2.getText())) {
				JOptionPane.showMessageDialog(null, "请选择项目根目录", "提示", 2);
			} else if (StringUtils.isBlank(text3.getText())) {
				JOptionPane.showMessageDialog(null, "请选文件生成目录", "提示", 2);
			} else {
				onSubmit(text1.getText(), text2.getText(), text3.getText());
			}
		}
	}

	public abstract void onSubmit(String filePath, String projectPath, String createPath);
}
