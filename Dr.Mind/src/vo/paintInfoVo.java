package vo;

import java.io.Serializable;


public class paintInfoVo implements Serializable{

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


