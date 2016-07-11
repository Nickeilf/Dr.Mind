package bl;

import android.R.id;
import service.paintService;
import vo.Node;
import vo.paintInfoVo;

public class paintblImpl implements paintService{

	//创建画布
	public paintInfoVo createPaint(){
		System.out.println("create");
		return new paintInfoVo();
	}
	
	//新建结点
	public Node InsertNode(Node node){
		Node inNode=new Node();
		if(node.getLeftChild()==null){
			node.setLeftChild(inNode);
		}//如果左子女为空，则插入在结点的左子女
		else{
			node=node.getLeftChild();
			for(;;){
              
               if(node.getRightChild()==null){
            	   node.setRightChild(inNode);
            	   break;
               }
               node=node.getRightChild();
			}//如果左子女存在，将其插入在左子女的右兄弟的最后一个
			
		}
		
		return inNode;
	}
	
	//输入结点文字
	public Node InputText(Node node,String text){
		node.setTextValue(text);
		node.setTextLength(text.length());
		return node;
	}
	
	//计算当前结点的子结点个数
	public int countNode(Node node){
		int num=0;
		System.out.println("count");
		if(node.getLeftChild()==null){
			System.out.println(num+"mmm");
			return 0;
		}
		else{
			node=node.getLeftChild();
			for(;;){
				num++;
				System.out.println(num);
				if(node.getRightChild()==null)
					break;
				node=node.getRightChild();
			}
			System.out.println(num);
			return num;
		}
		
	}
	
}

