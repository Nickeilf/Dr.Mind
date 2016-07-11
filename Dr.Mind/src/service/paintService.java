package service;

import android.graphics.Bitmap;
import vo.Node;
import vo.TextType;
import vo.paintInfoVo;

public interface paintService {
    
	 //paintInfoVo getPaintInfo(Node root);
	 
	 paintInfoVo createPaint();//新建画布
	 
	 Node InsertNode(Node node);//添加子结点
	 
	 Node InputText(Node node,String text,TextType font);//输入结点文字
	 
	 int countNode(Node node);//统计当前结点下子结点个数
	 
	 Node InputImage(Node node,Bitmap bmp);//输入结点图片
	 
	 Boolean DeleteAllChild(Node node);//删除结点之删除自身及其所有子结点
	 
	 Boolean DeleteAndMerge(Node node);//删除结点且合并子结点
	 	 	 
}
