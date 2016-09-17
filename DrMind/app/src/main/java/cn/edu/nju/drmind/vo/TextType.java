package cn.edu.nju.drmind.vo;

import android.graphics.Color;
import android.graphics.Typeface;

public class TextType {

	Typeface fontType;//字体
	int textsize;//大小
	int textColor;//颜色
	
	public TextType(){
		//fontType=Typeface.DEFAULT;
		textsize=12;
		textColor=Color.BLACK;
		fontType=null;
	}

	public Typeface getFontType() {
		return fontType;
	}

	public void setFontType(Typeface fontType) {
		this.fontType = fontType;
	}

	public int getTextsize() {
		return textsize;
	}

	public void setTextsize(int textsize) {
		this.textsize = textsize;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
	
	
	
}
