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
	public static String[] headerString = new String[100];// --��¼���б���
	int countIndexCombineCell = 0; // --�±�
	int[] indexCombineCell = new int[100]; // --�ϲ���Ԫ��������
	public static String[] combineString;// --�ϲ���Ԫ�������
	String[][] elementString = null;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	public static MyElement[] myElement = null;  	
	public static int[] indentation;        //ֵ�±��1��ʼ     �ϲ���Ԫ��Ϊ-1 
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
			int i = sheet.getLastRowNum();// i : ��Ԫ������
		
			row = sheet.getRow(0);
			for (int m = 0; m < row.getLastCellNum(); m++) {
				cell = row.getCell(m);

				headerString[m] = cell.getStringCellValue();
			}
			elementString = new String[rowcount][];
			for (int m = 0; m < i; m++) {
				row = sheet.getRow(m);
				int j = row.getLastCellNum(); // j : ��ǰ���ж�����
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
					if (headerString[n].equals("ǰ׺"))
						myElement[m].setPrefix(elementString[m][n]);
					if (headerString[n].equals("Ԫ������"))
						myElement[m].setName(elementString[m][n]);
					if (headerString[n].equals("��׼��ǩ�����ģ�"))
						myElement[m].setZnName(elementString[m][n]);
					if (headerString[n].equals("��׼��ǩ��Ӣ�ģ�"))
						myElement[m].setEnName(elementString[m][n]);
					if (headerString[n].equals("��������"))
						myElement[m].setItemType(elementString[m][n]);
					if (headerString[n].equals("�滻��"))
						myElement[m].setSubsitutionGroup(elementString[m][n]);
					if (headerString[n].equals("ʱ������"))
						myElement[m].setPeriodType(elementString[m][n]);
					if (headerString[n].equals("�������"))
						myElement[m].setBalance(elementString[m][n]);
					if (headerString[n].equals("�Ƿ���Ԫ��"))
						myElement[m].setGhostElement(elementString[m][n]);
					if (headerString[n].equals("�ÿ�����"))
						myElement[m].setNullable(elementString[m][n]);
					if (headerString[n].equals("�ο�"))
						myElement[m].setReference(elementString[m][n]);
					if (headerString[n].equals("չʾ��ǩ�����ģ�"))
						myElement[m].setShowznLabel(elementString[m][n]);
					if (headerString[n].equals("չʾ��ǩ��Ӣ�ģ�"))
						myElement[m].setShowenLabel(elementString[m][n]);
					if (headerString[n].equals("��ѡ��ǩ"))
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
				if(MyFrame.values[i+1][0].equals("ǰ׺")){
					MyFrame.values[i+1][1] = element.getPrefix();
				}
				if(MyFrame.values[i+1][0].equals("Ԫ������")){
					MyFrame.values[i+1][1] = element.getName();
				}
				if(MyFrame.values[i+1][0].equals("��׼��ǩ�����ģ�")){
					MyFrame.values[i+1][1] = element.getZnName();
				}
				if(MyFrame.values[i+1][0].equals("��׼��ǩ��Ӣ�ģ�")){
					MyFrame.values[i+1][1] = element.getEnName();
				}
				if(MyFrame.values[i+1][0].equals("��������")){
					MyFrame.values[i+1][1] = element.getItemType();
				}
				if(MyFrame.values[i+1][0].equals("�滻��")){
					MyFrame.values[i+1][1] = element.getSubsitutionGroup();
				}
				if(MyFrame.values[i+1][0].equals("ʱ������")){
					MyFrame.values[i+1][1] = element.getPeriodType();
				}
				if(MyFrame.values[i+1][0].equals("�������")){
					MyFrame.values[i+1][1] = element.getBalance();
				}
				if(MyFrame.values[i+1][0].equals("�Ƿ���Ԫ��")){
					MyFrame.values[i+1][1] = element.getGhostElement();
				}
				if(MyFrame.values[i+1][0].equals("�ÿ�����")){
					MyFrame.values[i+1][1] = element.getNullable();
				}
				if(MyFrame.values[i+1][0].equals("�ο�")){
					MyFrame.values[i+1][1] = element.getReference();
				}
				if(MyFrame.values[i+1][0].equals("չʾ��ǩ�����ģ�")){
					MyFrame.values[i+1][1] = element.getShowznLabel();
				}
				if(MyFrame.values[i+1][0].equals("չʾ��ǩ��Ӣ�ģ�")){
					MyFrame.values[i+1][1] = element.getShowenLabel();
				}
				if(MyFrame.values[i+1][0].equals("��ѡ��ǩ")){
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
							System.out.println("����ִ��");
							index = i;
							for(int j = 1;j < MyFrame.values.length;j++){
								if(MyFrame.values[j][0].equals("ǰ׺") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setPrefix(MyFrame.values[row][column]);
									System.out.println("1ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("Ԫ������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setName(MyFrame.values[row][column]);
									System.out.println("2ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��׼��ǩ�����ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setZnName(MyFrame.values[row][column]);
									System.out.println("3ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��׼��ǩ��Ӣ�ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setEnName(MyFrame.values[row][column]);
									System.out.println("4ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setItemType(MyFrame.values[row][column]);
									System.out.println("5ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�滻��") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setSubsitutionGroup(MyFrame.values[row][column]);
									System.out.println("6ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("ʱ������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setPeriodType(MyFrame.values[row][column]);
									System.out.println("7ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setBalance(MyFrame.values[row][column]);
									System.out.println("8ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�Ƿ���Ԫ��") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setGhostElement(MyFrame.values[row][column]);
									System.out.println("9ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�ÿ�����") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setNullable(MyFrame.values[row][column]);
									
									System.out.println("10ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�ο�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setReference(MyFrame.values[row][column]);
									System.out.println("11ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("չʾ��ǩ�����ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setShowznLabel(MyFrame.values[row][column]);
									System.out.println("12ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("չʾ��ǩ��Ӣ�ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setShowenLabel(MyFrame.values[row][column]);
									System.out.println("13ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��ѡ��ǩ") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									myElement[i].setFirstLabel(MyFrame.values[row][column]);
									System.out.println("14ִ��---");
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
						MyFrame.Jpaneltreedown.add(new JLabel("ѡ��Ϊ��"));
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

	// ��úϲ���Ԫ�����Ŀ ������list��
	public void getCombineCell(XSSFSheet sheet) {
		sheetmergerCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetmergerCount; i++) {
			CellRangeAddress ca = (CellRangeAddress) sheet.getMergedRegion(i);
			list.add(ca);
		}
	}

	// �жϵ�ǰ��Ԫ���Ƿ��Ǻϲ���Ԫ��
	public Boolean isCombineCell(XSSFCell cell, XSSFSheet sheet) {
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : list) {
			// ��úϲ���Ԫ�����ʼ��, ������, ��ʼ��, ������
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
