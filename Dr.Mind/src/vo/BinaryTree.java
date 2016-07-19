package vo;

import java.util.ArrayList;

import android.sax.RootElement;

public class BinaryTree {

	ArrayList<Node> rootList;//根结点链表
	
	public BinaryTree(){
		rootList=new ArrayList<Node>();
		Node root =new Node();
		rootList.add(root);
	}
			
	public ArrayList<Node> getRoot(){
		return rootList;
	}

	public void setRoot(ArrayList<Node> root) {
		this.rootList = root;
	}
	
}
