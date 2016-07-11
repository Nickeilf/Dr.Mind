package vo;

import android.graphics.Bitmap;

public class Node {

	Node leftChild=null;//左子女，二叉树
	Node rightChild=null;//右兄弟，二叉树
	Node parent=null;//父结点,多叉树结构中的父结点
	String textValue=null;//结点文字
	int textLength=0;//结点文字长度
	TextType font;//结点文字类型
	Bitmap bmp;//结点图片
	
	public Node(){
		leftChild=null;
		rightChild=null;
		textValue=null;
		parent=null;
		textLength=0;
	    font=null;
	    bmp=null;
	}

	
public Node getParent() {
		return parent;
	}


	public void setParent(Node parent) {
		this.parent = parent;
	}


public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}

//设置结点文本
public void setTextValue(String text){
	this.textValue=text;
}

public void setLeftChild(Node lchild){
	this.leftChild=lchild;
}

public TextType getFont() {
	return font;
}

public void setFont(TextType font) {
	this.font = font;
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