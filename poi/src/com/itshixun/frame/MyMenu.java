package com.itshixun.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.itshixun.com.Test;

public class MyMenu {
	String openFileName = null;

	public JMenuBar myMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem first = new JMenuItem("opne");
		JMenuItem second = new JMenuItem("exit");
		JMenuItem third = new JMenuItem("save");
		menuFile.add(first);
		menuFile.add(second);
		menuBar.add(menuFile);

		third.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		second.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MyFrame.frame.dispose();
			}
		});

		first.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					System.out.println("文件夹:" + file.getAbsolutePath());
				} else if (file.isFile()) {
					System.out.println("文件:" + file.getAbsolutePath());
				}				
				openFileName = file.getAbsolutePath();			
				new Test(openFileName);
			}
		});
		return menuBar;
	}

}
