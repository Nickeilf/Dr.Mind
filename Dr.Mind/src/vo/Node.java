package vo;

import android.graphics.Bitmap;

public class Node {

	Node leftChild=null;//左子女
	Node rightChild=null;//右兄弟
	Node parent=null;//父结点，多叉树
	String textValue=null;//文本
	int textLength=0;//文本长度
	TextType font;//字体类型
	Bitmap bmp;//图片
	int level;//结点层数
	
	public Node(){
		leftChild=null;
		rightChild=null;
		textValue=null;
		parent=null;
		textLength=0;
	    font=null;
	    bmp=null;
	    level=0;
	}

	
public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
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