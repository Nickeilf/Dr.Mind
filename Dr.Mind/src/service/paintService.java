package service;

import vo.Node;
import vo.paintInfoVo;

public interface paintService {
    
	 //paintInfoVo getPaintInfo(Node root);
	 
	 paintInfoVo createPaint();//新建画布
	 
	 Node InsertNode(Node node);//添加子结点
	 
	 Node InputText(Node node,String text);//输入结点文字
	 
	 int countNode(Node node);//统计当前结点下子结点个数
	 	 	 
}
