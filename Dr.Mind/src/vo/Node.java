package vo;

public class Node {

	Node leftChild=null;
	Node rightChild=null;
	String textValue=null;
	int textLength=0;
	
	public Node(){
		leftChild=null;
		rightChild=null;
		textValue=null;
		textLength=0;
	}

//设置结点文本
public void setTextValue(String text){
	this.textValue=text;
}

public void setLeftChild(Node lchild){
	this.leftChild=lchild;
}

public void setRightChild(Node rchild){
	this.rightChild=rchild;
}

public void setTextLength(int len){
	this.textLength=len;
}

public String getTextValue(){
    return textValue;
}

public Node getLeftChild(){
	return leftChild;
}

public Node getRightChild(){
	return rightChild;
}

public int getTextLength(){
	return textLength;
}
}