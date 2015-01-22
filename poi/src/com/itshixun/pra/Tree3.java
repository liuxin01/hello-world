package com.itshixun.pra;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Tree3 {
	JTree tree;	
	JFrame frame;
	JPanel Jpanel; 	
	JScrollPane sp;
	Stack<Integer> intstack = new Stack<Integer>();
	Stack<String>  stringstack = new Stack<String>();	
	
	public Tree3(String[] name,int[] indentation,String[] combineString){
		name[0] = "元素清单";
		indentation[0] = -2;
		int t = 0;
		for(int m = 1;m < name.length;m++){
			if(indentation[m] == -1){
				name[m] = combineString[t];
				t++;
			}
		}		
		/*
		for(int i = 0;i < name.length;i++)
			System.out.println(indentation[i]+" "+name[i]);
		*/
		
		HashMap<String,List> map = new HashMap<String, List>();
		List<String> childlist;
		
		for(int i = 0;i < name.length;i++){
			if(intstack.empty()){
				intstack.push(indentation[i]);
				stringstack.push(name[i]);
				map.put(name[i], new ArrayList<String>());
				continue;
			}
			
			if(indentation[i] - intstack.peek() == 1){
				intstack.push(indentation[i]);
				stringstack.push(name[i]);
				map.put(name[i],new ArrayList<String>());
			
			}else{
				while(indentation[i] - intstack.peek() != 1){
					String childname = stringstack.pop();
					intstack.pop();
					
					String fathername = stringstack.peek();
					
					childlist = map.get(fathername);
					childlist.add(childname);
								
				}
				intstack.push(indentation[i]);
				stringstack.push(name[i]);
				map.put(name[i], new ArrayList<String>());				
			}	
			
			
		}
		while(stringstack.empty() != true){
			String childname = stringstack.pop();
			String fathername;
			if(stringstack.empty() != true){
				fathername = stringstack.peek();
				childlist = map.get(fathername);
				childlist.add(childname);				
			}else{
				break;
			}				
		}		
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			List mylist = map.get(key);
			//System.out.println(key);
		/*
			for(int i = 0;i < mylist.size();i++){
				System.out.println(mylist.get(i));
			}
					
			System.out.println();
		*/
		}
	
		createTree(map);		
		frame = new JFrame("Test");
		Jpanel = new JPanel(new BorderLayout());	
		
		sp = new JScrollPane(tree);				
		Jpanel.add(sp);
		frame.add(Jpanel);	
		
		frame.setSize(400,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createTree(HashMap map2){
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("元素清单");
		createchild(top.toString(),top,map2);
		tree = new JTree(top);
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
