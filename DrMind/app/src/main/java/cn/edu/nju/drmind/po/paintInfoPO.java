package cn.edu.nju.drmind.po;


import cn.edu.nju.drmind.vo.BinaryTree;

public class paintInfoPO {

	private String paintName;//图表名称
	private BinaryTree bTreeRoot;//根结点链表
	
	public String getPaintName() {
		return paintName;
	}
	public void setPaintName(String paintName) {
		this.paintName = paintName;
	}
	public BinaryTree getbTreeRoot() {
		return bTreeRoot;
	}
	public void setbTreeRoot(BinaryTree bTreeRoot) {
		this.bTreeRoot = bTreeRoot;
	}
	
	
}
