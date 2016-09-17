package cn.edu.nju.drmind.vo;

import java.util.ArrayList;

public class BinaryTree {

	ArrayList<Node> rootList;//根结点链表
	
	public BinaryTree(){
		rootList=new ArrayList<Node>();
		Node root =new Node();
		root.setRoot(root);
		rootList.add(root);
	}
			
	public ArrayList<Node> getRoot(){
		return rootList;
	}

	public void setRoot(ArrayList<Node> root) {
		this.rootList = root;
	}
	
}
