package com.itshixun.pra;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.itshixun.pra.Tree;

public class Tree {
	ArrayList list;
	int[] indexfather;
	JTree tree;	
	JFrame frame;
	JPanel Jpanel; 
	
	JScrollPane sp;
	
	public Tree(String[] name,int[] indentation,String[] combinestring) {
		int t = 0;
		for(int m = 1;m < name.length;m++){
			if(indentation[m] == -1){
				name[m] = combinestring[t];
				t++;
			}
		}
		indexfather = new int[name.length];		
		for(int i = 1;i < name.length;i++){
			if(indentation[i] == -1){
				indexfather[i] = 0;
			}else{
				for(int j = i-1;j >= 1;j--){
					if(indentation[i]-indentation[j] == 1){
						indexfather[i] = j;
						break;
					}
				}				
			}			
		}		
		
		list = new ArrayList();
		HashMap myhashmap  = new HashMap();
		myhashmap.put("name", "ÔªËØÇåµ¥");
	 	myhashmap.put("indentation", -2);		
	 	myhashmap.put("position", 0);
	 	myhashmap.put("fatherposition", -2);	
		list.add(myhashmap);
	
		for(int m = 1;m < name.length;m++){
		 	myhashmap  = new HashMap();
		 	myhashmap.put("name", name[m]);
		 	myhashmap.put("indentation", indentation[m]);		
		 	myhashmap.put("position", m);
		 	myhashmap.put("fatherposition", indexfather[m]);		 	
		 	list.add(myhashmap);
			
		}		
		for(int i = 0;i < list.size();i++){
			HashMap map = (HashMap) list.get(i);
			System.out.println(map.get("indentation")+" "+map.get("position")+" "+map.get("fatherposition")+" "+map.get("name"));

		}		
		
		createTree(list);		
		frame = new JFrame("Test");
		Jpanel = new JPanel(new BorderLayout());	
		
		sp = new JScrollPane(tree);				
		Jpanel.add(sp);
		frame.add(Jpanel);	
		
		frame.setSize(400,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createTree(ArrayList list) {
		HashMap map = null;
		DefaultMutableTreeNode top = null;
		for (int i = 0; i < list.size(); i++) {
			map = (HashMap) list.get(i);
			if (map.get("fatherposition").equals(-2)) {
				top = new DefaultMutableTreeNode(map.get("name"));
				ArrayList sublist = getsubtree(map);
				addChild(top,sublist);	
				tree = new JTree(top);	
			
			}	
		}		
	}
	
	public ArrayList getsubtree(HashMap map){
    	ArrayList l = new ArrayList();
    	int position = (Integer)map.get("position");
    	for(int i=0;i<list.size();i++){
    		HashMap map2 = (HashMap) list.get(i);
    		int fatherposition  = (Integer) map2.get("fatherposition");
    		if(fatherposition == position){    	 
    			l.add(map2);    	
    		}    	  
    	}
    	return l;
    } 
	
	  private void addChild(DefaultMutableTreeNode fatherNode,ArrayList list) {
		  HashMap map = null;
		  for (int i = 0; i < list.size(); i++) {
			  map = (HashMap) list.get(i);
			  DefaultMutableTreeNode child = new DefaultMutableTreeNode(map.get("name"));
			  ArrayList sublist = getsubtree(map);
			  fatherNode.add(child);
			  addChild(child,sublist);		  
		  }	  
	  }
}
