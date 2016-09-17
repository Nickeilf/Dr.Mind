package cn.edu.nju.drmind.service;

import android.graphics.Bitmap;

import java.util.ArrayList;

import cn.edu.nju.drmind.data.paintDao;
import cn.edu.nju.drmind.vo.Node;
import cn.edu.nju.drmind.vo.TextType;
import cn.edu.nju.drmind.vo.paintInfoVo;


public interface paintService {
    
	 //paintInfoVo getPaintInfo(Node root);
	 
	 paintInfoVo createPaint();//新建画板
	 
	 //Node createRoot(int x,int y);//新建根结点
	 
	 Node InsertNode(Node node);//插入结点
	 
	 Node InputText(Node node, String text, TextType font);//输入结点文字
	 
	 int countNode(Node node);//计算当前结点下子结点个数，多叉树，不要调用该方法，调用新的
	 
	 int numNode(Node node);//计算用于界面排版的子结点个数，多叉树，调用此方法
	 
	 Node InputImage(Node node, Bitmap bmp);//输入结点图片
	 
	 Boolean DeleteAllChild(Node node);//删除所有子结点
	 
	 Boolean DeleteAndMerge(Node node);//删除归并
	 
	 Boolean SavePaint(String paintName, paintInfoVo paintvo, paintDao dao);//存储画图,dao需要在界面上new
	 
	 paintInfoVo OpenPaint(String paintName, paintDao dao, int maxid);//打开画图
	 
	 ArrayList<Node> getAllChild(Node parent);//获取所有子孙结点
	 
	 Boolean MoveNode(Node node, Node newpa, Node lastBro);//移动插入结点，传自己，一个新的父结点，和新的上一个兄弟，若无则null
	 
	 Boolean DeleteRoot(Node root, paintInfoVo vo);//删除根结点。传该根结点和当前vo
	 
	 Node NewRoot(paintInfoVo vo);//新建根结点,传一个当前的vo
	
	 ArrayList<Node> getAllSon(Node parent);//返回所有子结点
	 
	 ArrayList<String> getAllPaintName(paintDao dao);//获取所有数据库中图表的名字
	 
	 public Node getAncestor(Node node);//获得当前结点的根结点
}
