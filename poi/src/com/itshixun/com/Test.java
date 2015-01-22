package com.itshixun.com;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
*/
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itshixun.entity.MyElement;
import com.itshixun.frame.MyFrame;
import com.itshixun.pra.Tree2;

public class Test {
	String fileToread;
	List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
	public static int rowcount;
	public static int columncount;
	int sheetmergerCount;
	public static String[] headerString = new String[100];// --记录首行标题
	int countIndexCombineCell = 0; // --下标
	int[] indexCombineCell = new int[100]; // --合并单元格所在行
	public static String[] combineString;// --合并单元格的内容
	String[][] elementString = null;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	public static MyElement[] myElement = null;  	
	public static int[] indentation;        //值下标从1开始     合并单元格为-1 
	public static String[] name;
	String[] nameusebychoose;
	List<String> myList;
	Map<String,List<String>> myMap = new HashMap<String,List<String>>();
	
	public Test(String filename) {
		fileToread = filename.replaceAll("\\\\", "//");

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
					fileToread));
			sheet = workbook.getSheetAt(0);
			rowcount = sheet.getLastRowNum();
			getCombineCell(sheet);
			combineString = new String[sheetmergerCount];
			int i = sheet.getLastRowNum();// i : 单元格行数
		
			row = sheet.getRow(0);
			for (int m = 0; m < row.getLastCellNum(); m++) {
				cell = row.getCell(m);

				headerString[m] = cell.getStringCellValue();
			}
			elementString = new String[rowcount][];
			for (int m = 0; m < i; m++) {
				row = sheet.getRow(m);
				int j = row.getLastCellNum(); // j : 当前行有多少列
				columncount = row.getLastCellNum();
				elementString[m] = new String[columncount];
				for (int n = 0; n < j; n++) {
					cell = row.getCell(n);					
					//System.out.println(cell.getCellStyle().getIndention());

					if (isCombineCell(cell, sheet)) {
						indexCombineCell[countIndexCombineCell] = m;
						combineString[countIndexCombineCell] = cell
								.getStringCellValue();
						countIndexCombineCell++;
						elementString[m][n] = cell.getStringCellValue();
						break;
					} else {
						elementString[m][n] = cell.getStringCellValue();
					}
				}// end inner for
			}// end outer for
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myElement = new MyElement[rowcount];
		for (int m = 1; m < elementString.length; m++) {
			myElement[m] = new MyElement();
			for (int n = 0; n < elementString[m].length; n++) {

				int flag = 0;
				for (int t = 0; t < sheetmergerCount; t++) {
					if (indexCombineCell[t] == m) {
						flag = 1;
						break;
					}
				}
				if (flag == 1) {
					break;
				} else {
					if (headerString[n].equals("前缀"))
						myElement[m].setPrefix(elementString[m][n]);
					if (headerString[n].equals("元素名称"))
						myElement[m].setName(elementString[m][n]);
					if (headerString[n].equals("标准标签（中文）"))
						myElement[m].setZnName(elementString[m][n]);
					if (headerString[n].equals("标准标签（英文）"))
						myElement[m].setEnName(elementString[m][n]);
					if (headerString[n].equals("数据类型"))
						myElement[m].setItemType(elementString[m][n]);
					if (headerString[n].equals("替换组"))
						myElement[m].setSubsitutionGroup(elementString[m][n]);
					if (headerString[n].equals("时期属性"))
						myElement[m].setPeriodType(elementString[m][n]);
					if (headerString[n].equals("借贷属性"))
						myElement[m].setBalance(elementString[m][n]);
					if (headerString[n].equals("是否虚元素"))
						myElement[m].setGhostElement(elementString[m][n]);
					if (headerString[n].equals("置空属性"))
						myElement[m].setNullable(elementString[m][n]);
					if (headerString[n].equals("参考"))
						myElement[m].setReference(elementString[m][n]);
					if (headerString[n].equals("展示标签（中文）"))
						myElement[m].setShowznLabel(elementString[m][n]);
					if (headerString[n].equals("展示标签（英文）"))
						myElement[m].setShowenLabel(elementString[m][n]);
					if (headerString[n].equals("首选标签"))
						myElement[m].setFirstLabel(elementString[m][n]);
					myElement[m].setId();
				}		
			}			
		}
		name = new String[rowcount];
		for(int m = 1;m < rowcount;m++){
			name[m] = myElement[m].getName();
		}

		indentation = new int[rowcount];
		for(int m = 1;m < rowcount;m++){
			row = sheet.getRow(m);
			cell = row.getCell(1);
			if(elementString[m][1] == null)
				indentation[m] = -1;
			else
				indentation[m] = cell.getCellStyle().getIndention();				
		}		
	
		nameusebychoose = new String[rowcount];
		for(int i = 0;i < rowcount;i++)
			nameusebychoose[i] = name[i];
		conversionTotree();
	
		conversionTolist();
		
		
	}
	
	public void conversionTolist(){
		Vector vec = new Vector();
		for(int i = 0;i < name.length;i++){
			if(name[i] == null)
				continue;
			vec.add(name[i]);
		}
		
		/*for(int i = 0;i < vec.size();i++)
			System.out.println(vec.get(i));
		*/
		MyFrame.Jlist = new JList(vec);	
		MyFrame.Jlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				jListvalueChanged(e);}
		});		
		
		MyFrame.Jpanelchoose.removeAll();
		MyFrame.Jpanelchoose = new JPanel(new FlowLayout());
		MyFrame.JTextchoose = new JTextField(20);
		MyFrame.JButtonchoose = new JButton("Find");
		
		MyFrame.JButtonchoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton Jbutton = (JButton)e.getSource();
				if(MyFrame.JButtonchoose == Jbutton){
					String str = MyFrame.JTextchoose.getText();
					System.out.println(str);
					
					Vector vec = new Vector();
					for(int i = 0;i < name.length;i++){
						if(nameusebychoose[i] == null)
							continue;
						
						if(nameusebychoose[i].contains(str))
							vec.add(nameusebychoose[i]);						
					}
					
					MyFrame.Jlist = new JList(vec);
					MyFrame.Jlist.addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							jListvalueChanged(e);}
					});		
					MyFrame.JSJlist = new JScrollPane(MyFrame.Jlist);
					MyFrame.JpanelListdown.removeAll();
					MyFrame.JpanelListdown.add(MyFrame.JSJlist);
					MyFrame.JPanelList.add(MyFrame.JpanelListdown);
					MyFrame.JPanelList.validate();								
				}
			}
		});
		MyFrame.Jpanelchoose.add(MyFrame.JTextchoose);
		MyFrame.Jpanelchoose.add(MyFrame.JButtonchoose);
		MyFrame.Jpanelchoose.validate();
		MyFrame.JSJlist = new JScrollPane(MyFrame.Jlist);
		MyFrame.JpanelListdown.removeAll();
		MyFrame.JpanelListdown.add(MyFrame.JSJlist);
		MyFrame.JpanelListdown.validate();
		MyFrame.JPanelList.removeAll();
		MyFrame.JPanelList.add(MyFrame.Jpanelchoose,BorderLayout.NORTH);
		MyFrame.JPanelList.add(MyFrame.JpanelListdown,BorderLayout.CENTER);
		MyFrame.JPanelList.validate();		

	}	
	private void jListvalueChanged(ListSelectionEvent e){
		Object obj = ((JList)e.getSource()).getSelectedValue();
		final String str;		
		if(e.getValueIsAdjusting()){
			str = (String)obj;
			MyElement element = new MyElement();
			for(int i = 1;i <rowcount;i++){
				if(myElement[i].getName() == null)
					continue;
				if(myElement[i].getName() == str){
					element = myElement[i];		
					break;
				}
			}		
	
			MyFrame.values[0][0] = "id";
			MyFrame.values[0][1] = element.getId();
			for(int i = 0;i < columncount;i++){
				MyFrame.values[i+1][0] = headerString[i];	
			}
			
			for(int i = 0;i < columncount;i++){
				if(MyFrame.values[i+1][0].equals("前缀")){
					MyFrame.values[i+1][1] = element.getPrefix();
				}
				if(MyFrame.values[i+1][0].equals("元素名称")){
					MyFrame.values[i+1][1] = element.getName();
				}
				if(MyFrame.values[i+1][0].equals("标准标签（中文）")){
					MyFrame.values[i+1][1] = element.getZnName();
				}
				if(MyFrame.values[i+1][0].equals("标准标签（英文）")){
					MyFrame.values[i+1][1] = element.getEnName();
				}
				if(MyFrame.values[i+1][0].equals("数据类型")){
					MyFrame.values[i+1][1] = element.getItemType();
				}
				if(MyFrame.values[i+1][0].equals("替换组")){
					MyFrame.values[i+1][1] = element.getSubsitutionGroup();
				}
				if(MyFrame.values[i+1][0].equals("时期属性")){
					MyFrame.values[i+1][1] = element.getPeriodType();
				}
				if(MyFrame.values[i+1][0].equals("借贷属性")){
					MyFrame.values[i+1][1] = element.getBalance();
				}
				if(MyFrame.values[i+1][0].equals("是否虚元素")){
					MyFrame.values[i+1][1] = element.getGhostElement();
				}
				if(MyFrame.values[i+1][0].equals("置空属性")){
					MyFrame.values[i+1][1] = element.getNullable();
				}
				if(MyFrame.values[i+1][0].equals("参考")){
					MyFrame.values[i+1][1] = element.getReference();
				}
				if(MyFrame.values[i+1][0].equals("展示标签（中文）")){
					MyFrame.values[i+1][1] = element.getShowznLabel();
				}
				if(MyFrame.values[i+1][0].equals("展示标签（英文）")){
					MyFrame.values[i+1][1] = element.getShowenLabel();
				}
				if(MyFrame.values[i+1][0].equals("首选标签")){
					MyFrame.values[i+1][1] = element.getFirstLabel();
				}					
			}				
			TableModel dataModel = new AbstractTableModel() {

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					// TODO Auto-generated method stub
					return MyFrame.values[rowIndex][columnIndex];
				}

				@Override
				public int getRowCount() {
					// TODO Auto-generated method stub
					return MyFrame.values.length;
				}

				@Override
				public int getColumnCount() {

					// TODO Auto-generated method stub
					return MyFrame.property.length;
				}

				public String getColumnName(int column) {
					return MyFrame.property[column];
				}
				/*
				public Class getColumnClass(int c) {
					return getValueAt(0, c).getClass();
				}*/

				public boolean isCellEditable(int row, int col) {
					return true;
				}

				public void setValueAt(Object avalue, int row, int column) {
					int index = 0;
					System.out.println("Change value to:" + avalue);
					MyFrame.values[row][column] = (String)avalue;
					System.out.println(str+"   "+MyFrame.values[row][column]);
					for(int i = 1;i < myElement.length;i++){
						if(myElement[i].getName() == null) continue;
						if(myElement[i].getName().equals(str)){
							System.out.println("名字执行");
							index = i;
							for(int j = 1;j < MyFrame.values.length;j++){
								if(MyFrame.values[j][0].equals("前缀") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setPrefix(MyFrame.values[row][column]);
									System.out.println("1执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("元素名称") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setName(MyFrame.values[row][column]);
									System.out.println("2执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("标准标签（中文）") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setZnName(MyFrame.values[row][column]);
									System.out.println("3执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("标准标签（英文）") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setEnName(MyFrame.values[row][column]);
									System.out.println("4执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("数据类型") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setItemType(MyFrame.values[row][column]);
									System.out.println("5执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("替换组") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setSubsitutionGroup(MyFrame.values[row][column]);
									System.out.println("6执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("时期属性") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setPeriodType(MyFrame.values[row][column]);
									System.out.println("7执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("借贷属性") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setBalance(MyFrame.values[row][column]);
									System.out.println("8执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("是否虚元素") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setGhostElement(MyFrame.values[row][column]);
									System.out.println("9执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("置空属性") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setNullable(MyFrame.values[row][column]);
									
									System.out.println("10执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("参考") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setReference(MyFrame.values[row][column]);
									System.out.println("11执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("展示标签（中文）") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setShowznLabel(MyFrame.values[row][column]);
									System.out.println("12执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("展示标签（英文）") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setShowenLabel(MyFrame.values[row][column]);
									System.out.println("13执行---");
									break;
								}
								if(MyFrame.values[j][0].equals("首选标签") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setFirstLabel(MyFrame.values[row][column]);
									System.out.println("14执行---");
									break;
								}								
							}
						
						}					
					}					
						
					System.out.println(myElement[index].getName()+" "+myElement[index].getNullable());					
				}

			};					
			MyFrame.Jtable = new JTable(dataModel);
			MyFrame.JSJtable = new JScrollPane(MyFrame.Jtable);
			MyFrame.JPaneltable.removeAll();
			MyFrame.JPaneltable.add(MyFrame.JSJtable);
			MyFrame.JPaneltable.validate();			
		}
	}
	
	public void conversionTotree(){		
		MyFrame.combox = new JComboBox();
		MyFrame.combox.addItem(null);
		for(int i = 0;i < combineString.length;i++){
			MyFrame.combox.addItem(combineString[i]);
		}		
		MyFrame.combox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == MyFrame.combox){
					if(MyFrame.combox.getSelectedItem() == null){
						System.out.println("null");
						MyFrame.Jpaneltreedown.removeAll();
						MyFrame.Jpaneltreedown.add(new JLabel("选择为空"));
						MyFrame.Jpaneltreedown.validate();
					}else{
						String str = ((JComboBox)e.getSource()).getSelectedItem().toString();
						System.out.println(str);	
						new Tree2(name,indentation,combineString,str);
					}					
				}
			}
		});		
		MyFrame.Jpanelcombox.removeAll();
		MyFrame.Jpanelcombox.add(MyFrame.combox);
		MyFrame.Jpanelcombox.validate();

	}

	// 获得合并单元格的数目 并放于list中
	public void getCombineCell(XSSFSheet sheet) {
		sheetmergerCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetmergerCount; i++) {
			CellRangeAddress ca = (CellRangeAddress) sheet.getMergedRegion(i);
			list.add(ca);
		}
	}

	// 判断当前单元格是否是合并单元格
	public Boolean isCombineCell(XSSFCell cell, XSSFSheet sheet) {
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : list) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getColumnIndex() <= lastC
					&& cell.getColumnIndex() >= firstC) {
				if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR) {
					return true;
				}
			}
		}
		return false;
	}

}
