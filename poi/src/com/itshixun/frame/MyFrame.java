package com.itshixun.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.itshixun.com.Test;
import com.itshixun.write.ExcelWrite;

public class MyFrame {
	public static JFrame frame;
	JPanel mainJPanel;
	public static JPanel JPaneltree; 
	public static JPanel JPaneltable;
	public static JPanel JPanelList;	
	public static JList Jlist;
	
	JPanel JPanelexp;
	JButton JButtonexp;
	JPanel Jpanelprogress;
	JProgressBar JprogressBar;
	
	public static JScrollPane JSJlist;	
	
	public static JTree Jtree;
	public static JScrollPane JSJtree;
	public static JTable Jtable;
	public static String[] property = {"Property","Value"};
	public static String[][] values = new String[15][2]; 
	public static JScrollPane JSJtable;
	
	JPanel Jpanelleft;
	JPanel Jpanelright;
	
	JPanel JBottomPanel;
	JTabbedPane JTabbedPane1; //------1.Element
	
	public static JPanel JpanelListdown;
	public static JPanel Jpaneltreedown;
	
	public static JPanel Jpanelchoose;
	public static JTextField JTextchoose;
	public static JButton JButtonchoose;

	public static JPanel Jpanelcombox;
	public static JComboBox combox;

	public MyFrame(){
		frame = new JFrame();		
		MyMenu myMenu = new MyMenu();
		JMenuBar myJMenuBar = myMenu.myMenu();
		frame.setJMenuBar(myJMenuBar);
		
		mainJPanel = new JPanel(new BorderLayout());		
		JPaneltree = new JPanel(new BorderLayout());
		JPaneltable = new JPanel(new BorderLayout());
		
		Jpanelleft = new JPanel(new BorderLayout());
		Jpanelright = new JPanel(new BorderLayout());
		JSplitPane JSP2 = new JSplitPane(1,false,Jpanelleft,Jpanelright);
		JSP2.setDividerLocation(300);
		JSP2.setDividerSize(10);
		
		JPanelList = new JPanel(new BorderLayout());
		
		String[] name = {"aaa","bbbb"};
		Jlist = new JList(name);
		
		Jpanelchoose = new JPanel(new FlowLayout());
		JTextchoose = new JTextField(20);
		JButtonchoose = new JButton("Find");
		
		JTextchoose.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				String s = JTextchoose.getText().trim();
				System.out.println(s);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				String s = JTextchoose.getText().trim();
				System.out.println(s);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				String s = JTextchoose.getText().trim();
				System.out.println(s);
			}
		});
		
		JButtonchoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton Jbutton = (JButton)e.getSource();
				if(Jbutton == JButtonchoose){
					String str = JTextchoose.getText();
					System.out.println(str);				
				}
			}
		});
		
		Jpanelchoose.add(JTextchoose);
		Jpanelchoose.add(JButtonchoose);
		JPanelList.add(Jpanelchoose,BorderLayout.NORTH);		
		JpanelListdown = new JPanel(new BorderLayout());
		
		JpanelListdown.add(Jlist,BorderLayout.CENTER);
		JPanelList.add(JpanelListdown,BorderLayout.CENTER);
		JSplitPane JSP3 = new JSplitPane(0,false,JPanelList,JPaneltable);
		JSP3.setDividerLocation(250);
		JSP3.setDividerSize(10);
		Jpanelleft.add(JSP3);
		
		JBottomPanel = new JPanel(new BorderLayout());
		Jpanelcombox = new JPanel(new BorderLayout());
		combox = new JComboBox();
		combox.addItem(null);
		combox.addItem("First");
		combox.addItem("Second");
		
		combox.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == combox)
					System.out.println(combox.getSelectedItem());
			}
		});
		Jpanelcombox.add(combox,BorderLayout.CENTER);
		Jpaneltreedown = new JPanel(new BorderLayout());
				
		JPaneltree.add(Jpanelcombox,BorderLayout.NORTH);
		
		JTabbedPane1 = new JTabbedPane();
		JTabbedPane1.add("Element", JPaneltree);
		JTabbedPane1.add("Pre",new JLabel("Pre"));
		JTabbedPane1.add("Cal",new JLabel("Cal"));
		JBottomPanel.add(JTabbedPane1,BorderLayout.CENTER);
		//JBottomPanel.add(JTabbedPane2,BorderLayout.CENTER);
		//JBottomPanel.add(JTabbedPane3,BorderLayout.CENTER);
		
		JPanelexp = new JPanel(new BorderLayout());
		Jpanelprogress = new JPanel();
		JButtonexp = new JButton("µ¼³ö");
		
		JButtonexp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ExcelWrite(Test.name,Test.indentation,Test.combineString,Test.headerString);
			}
		});
		
		JprogressBar = new JProgressBar();
		JprogressBar.setPreferredSize(new Dimension(300,30));
		Jpanelprogress.add(JprogressBar);
		Jpanelprogress.add(JButtonexp);
		JPanelexp.add(Jpanelprogress,BorderLayout.SOUTH);
		
		JSplitPane JSP1 = new JSplitPane(0,false,JBottomPanel,JPanelexp);
		JSP1.setDividerLocation(300);
		JSP1.setDividerSize(5);	
		Jpanelright.add(JSP1);
		
		Jtree = new JTree();
		JSJtree = new JScrollPane(Jtree);
		Jpaneltreedown.add(JSJtree,BorderLayout.CENTER);
		JPaneltree.add(Jpaneltreedown);
		
		Jtable = new JTable(values,property);
		JSJtable = new JScrollPane(Jtable);
		JPaneltable.add(JSJtable);
		mainJPanel.add(JSP2);	
		frame.add(mainJPanel);		
				
		frame.setVisible(true);
		frame.setBounds(100, 100, 40, 40);
	    frame.setSize(1024,500);  
	  	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}		
	public static void main(String[] args) {
		new MyFrame();
	}
}
