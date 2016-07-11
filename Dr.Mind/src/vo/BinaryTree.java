package vo;

import android.sax.RootElement;

public class BinaryTree {

	Node root;//根结点
	
	public BinaryTree(){
		root=new Node();
	}
	

	private void insert(Node node){
			root.leftChild=node;
	}
	
	
	public Node getRoot(){
		return root;
	}
	
	public void setRoot(){
		this.root=new Node();
	}
	
}
