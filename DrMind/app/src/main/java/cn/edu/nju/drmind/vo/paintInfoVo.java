package cn.edu.nju.drmind.vo;

import java.io.Serializable;


public class paintInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BinaryTree bTreeRoot;
	
	public paintInfoVo(){
		bTreeRoot=new BinaryTree();
	}

	public BinaryTree getbTreeRoot() {
		return bTreeRoot;
	}

	public void setbTreeRoot(BinaryTree bTreeRoot) {
		this.bTreeRoot = bTreeRoot;
	}
	
	
}


