package com.itshixun.pra;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.itshixun.com.Test;
import com.itshixun.entity.MyElement;
import com.itshixun.frame.MyFrame;

public class Tree2 {	
	String childvalue;
	String fathervalue;
	HashMap<Integer,String> map1; 
	HashMap<String,List>    map2;	
	DefaultTreeModel model;  
	TreePath movePath;  
	
	public Tree2(String[] name,int[] indentation,String[] combineString,String str){		
		name[0] = "Ԫ���嵥";
		indentation[0] = -2;
		int t = 0;
		for(int m = 1;m < name.length;m++){
			if(indentation[m] == -1){
				name[m] = combineString[t];
				t++;
			}
		}			
		map1 = new HashMap<Integer, String>();
		map2 = new HashMap<String, List>();
		List<String> childlist;

		for(int i = 0;i < name.length;i++){
			if(map1.get(indentation[i]-1) == null){
				map1.put(indentation[i], name[i]);
				childlist = new ArrayList<String>();
				map2.put(name[i], childlist);
				
			}else{
				String fathername = map1.get(indentation[i]-1);
				//System.out.println(fathername);
				childlist = map2.get(fathername);
				childlist.add(name[i]);
				map1.put(indentation[i], name[i]);
				map2.put(name[i], new ArrayList());
			}			
		}		
		Iterator iter = map2.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			List mylist = map2.get(key);
			//System.out.println(key);
			/*
			for(int i = 0;i < mylist.size();i++){
				System.out.println(mylist.get(i));
			}		
			System.out.println();
			*/
		}		
		createTree(map2,name,indentation,str);		
	}
	
	public void createTree(final HashMap map2,final String[] name,final int[] indentation,String str){			
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(str);
		createchild(top.toString(),top,map2);
		MyFrame.Jtree = new JTree(top);			
		
		model = (DefaultTreeModel)MyFrame.Jtree.getModel();
		MyFrame.Jtree.setEditable(true);
		
		MouseListener ml = new MouseAdapter(){  
	        //�������ʱ���ñ��϶��Ľڵ�  
			public void mousePressed(MouseEvent e){  
	            //�����ҪΨһȷ��ĳ���ڵ㣬����ͨ��TreePath����ȡ��  
	            TreePath tp = MyFrame.Jtree.getPathForLocation(e.getX(), e.getY());  
	            if (tp != null){  
	                movePath = tp;  
	            }  
	        }  
	        //����ɿ�ʱ�����Ҫ�ϵ��ĸ����ڵ�  
	        public void mouseReleased(MouseEvent e){  
	                
	        	//��������ɿ�ʱ��TreePath����ȡTreePath  
	            TreePath tp = MyFrame.Jtree.getPathForLocation(e.getX(), e.getY());  
	            if (tp != null && movePath != null){  
	                //��ֹ���ӽڵ��϶�    isDescendant(TreePath aTreePath)  ��� aTreePath Ϊ�� TreePath �ĺ�����򷵻� true��
	                if (movePath.isDescendant(tp) && movePath != tp){  	                        
	                    return;  
	                    
	                }  
	                //�Ȳ������ӽڵ��ƶ���������갴�¡��ɿ��Ĳ���ͬһ���ڵ�     
	                else if (movePath != tp){  
	                    fathervalue = tp.getLastPathComponent().toString();
	                	System.out.println("parent node:"+fathervalue);  
	                	System.out.println("child node:"+childvalue);
	                	int count;
	                	
	                	for(int j = 0;j < name.length;j++){
	                		if(name[j].equals(fathervalue)){
	                			count = indentation[j];
	                			for(int i = 0;i < name.length;i++){
	                				if(name[i].equals(childvalue)){
	                					for(int m = i;m > j+1;m--){
	                						name[m] = name[m-1];
	                						indentation[m] = indentation[m-1]; 			                						
	                					}
	                					name[j+1] = childvalue;
	                					indentation[j+1] = count+1;                					
	                					
	                					break;
	                				}	                				
	                			}
	                			break;
	                		}	                		
	                	}   
	                	fathervalue =  childvalue;
	                	for(int j = 0;j < name.length;j++){
	                		if(name[j].equals(fathervalue)){
	                			count = indentation[j];
	                			List mylist = (List)map2.get(fathervalue);
	                			for(int n = 0;n < mylist.size();n++){
	                				childvalue = (String)mylist.get(n);
	                				for(int i = 0;i < name.length;i++){
	                					if(name[i].equals(childvalue)){
	                						for(int m = i;m > j+1;m--){
	                							name[m] = name[m-1];
	                							indentation[m] = indentation[m-1];                							
	                						}
	                						name[j+1] = childvalue;
	                						indentation[j+1] = count+1;
	                						
	                					}
	                					
	                				}
	                				
	                			}
	                		}
	                		
	                	}
	      
	                	//add���������Ƚ�ԭ�ڵ��ԭ���ڵ�ɾ��������ӵ��¸��ڵ��� 	                
	                    ((DefaultMutableTreeNode)tp.getLastPathComponent()).add(  
	                            (DefaultMutableTreeNode)movePath.getLastPathComponent());  
	                    movePath = null;  
	                    MyFrame.Jtree.updateUI(); 	                    	
	                }  	                
	            }  	   
	            /* 
	            for(int i = 0;i < name.length;i++){
	            	System.out.println(indentation[i]+" "+name[i]+" ");
	            	
	            }
	            */
	        }  	        
	    };  
	    
		MyFrame.Jtree.addMouseListener(ml);  
	 
		MyFrame.Jtree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode nodevalue = (DefaultMutableTreeNode)MyFrame.Jtree.getLastSelectedPathComponent();
				childvalue = nodevalue.toString();
				//System.out.println("childvalue:"+childvalue);
				
				jTreevalueChanged(e);
				
			}
		});		
		MyFrame.JSJtree = new JScrollPane(MyFrame.Jtree);
		MyFrame.Jpaneltreedown.removeAll();
		MyFrame.Jpaneltreedown.add(MyFrame.JSJtree,BorderLayout.CENTER);	
		MyFrame.Jpaneltreedown.validate();

	}
	
	private void jTreevalueChanged(TreeSelectionEvent e){
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)MyFrame.Jtree.getLastSelectedPathComponent();
		final String str; 
		if(!selectedNode.isRoot()){
			str = selectedNode.toString();
			MyElement element = new MyElement();
			for(int i = 1;i <Test.rowcount;i++){
				if(Test.myElement[i].getName() == null)
					continue;
				if(Test.myElement[i].getName() == str)
					element = Test.myElement[i];		
			}					
	
			MyFrame.values[0][0] = "id";
			MyFrame.values[0][1] = element.getId();
			for(int i = 0;i < Test.columncount;i++){
				MyFrame.values[i+1][0] = Test.headerString[i];	
			}
			
			for(int i = 0;i < Test.columncount;i++){
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
					for(int i = 1;i < Test.myElement.length;i++){
						if(Test.myElement[i].getName() == null) continue;
						if(Test.myElement[i].getName().equals(str)){
							System.out.println("����ִ��");
							index = i;
							for(int j = 1;j < MyFrame.values.length;j++){
								if(MyFrame.values[j][0].equals("ǰ׺") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setPrefix(MyFrame.values[row][column]);
									System.out.println("1ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("Ԫ������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setName(MyFrame.values[row][column]);
									System.out.println("2ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��׼��ǩ�����ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setZnName(MyFrame.values[row][column]);
									System.out.println("3ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��׼��ǩ��Ӣ�ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setEnName(MyFrame.values[row][column]);
									System.out.println("4ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setItemType(MyFrame.values[row][column]);
									System.out.println("5ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�滻��") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setSubsitutionGroup(MyFrame.values[row][column]);
									System.out.println("6ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("ʱ������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setPeriodType(MyFrame.values[row][column]);
									System.out.println("7ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�������") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setBalance(MyFrame.values[row][column]);
									System.out.println("8ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�Ƿ���Ԫ��") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setGhostElement(MyFrame.values[row][column]);
									System.out.println("9ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�ÿ�����") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setNullable(MyFrame.values[row][column]);
									
									System.out.println("10ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("�ο�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setReference(MyFrame.values[row][column]);
									System.out.println("11ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("չʾ��ǩ�����ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setShowznLabel(MyFrame.values[row][column]);
									System.out.println("12ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("չʾ��ǩ��Ӣ�ģ�") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setShowenLabel(MyFrame.values[row][column]);
									System.out.println("13ִ��---");
									break;
								}
								if(MyFrame.values[j][0].equals("��ѡ��ǩ") && MyFrame.values[j][0].equals(MyFrame.values[row][0])){
									Test.myElement[i].setFirstLabel(MyFrame.values[row][column]);
									System.out.println("14ִ��---");
									break;
								}								
							}
						}							
					}		
					System.out.println(Test.myElement[index].getName()+" "+Test.myElement[index].getNullable());					
				}
			};				
			
			MyFrame.Jtable = new JTable(dataModel);
			MyFrame.JSJtable = new JScrollPane(MyFrame.Jtable);
			MyFrame.JPaneltable.removeAll();
			MyFrame.JPaneltable.add(MyFrame.JSJtable);
			MyFrame.JPaneltable.validate();			
		}		
	}
	
	public void createchild(String parentname,DefaultMutableTreeNode node,HashMap map2){
		if(node == null){
			node = new DefaultMutableTreeNode(parentname);
		}		
		Iterator iter = map2.entrySet().iterator();	
		String key = parentname;
		List mylist = (List) map2.get(key);
		
		for(int i = 0;i < mylist.size();i++){
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(mylist.get(i));
			node.add(child);
			createchild((String)mylist.get(i),child,map2);			
		}		
	}

}
