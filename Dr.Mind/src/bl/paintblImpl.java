package bl;

import android.R.id;
import android.graphics.Bitmap;
import service.paintService;
import vo.Node;
import vo.TextType;
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
		inNode.setParent(node);
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
	public Node InputText(Node node,String text,TextType font){
		node.setTextValue(text);
		node.setTextLength(text.length());
		node.setFont(font);
		return node;
	}
	
	//计算当前结点的子结点个数
	public int countNode(Node node){
		int num=0;
		System.out.println("count");
		if(node.getLeftChild()==null){
			return 0;
		}
		else{
			node=node.getLeftChild();
			for(;;){
				num++;
				if(node.getRightChild()==null)
					break;
				node=node.getRightChild();
			}
			System.out.println(num);
			return num;
		}
		
	}

	//输入结点图片
	public Node InputImage(Node node, Bitmap bmp) {
		// TODO Auto-generated method stub
		node.setBmp(bmp);
		return node;
	}

	//删除结点之删除自身及其所有子结点
	public Boolean DeleteAllChild(Node node) {
		// TODO Auto-generated method stub
		node.setLeftChild(null);//删除左子女
		//删除当前结点
		if(node.getParent()==null){
			System.out.println("error in delete root node!");
			return false;
		}
		if(node.getParent().getLeftChild()==node){
			node.getParent().setLeftChild(node.getRightChild());
		}
		else{
			Node tNode=node.getParent().getLeftChild();
			for(;;){
				if(tNode.getRightChild()==node) break;
				tNode=tNode.getRightChild();
			}
			tNode.setRightChild(node.getRightChild());
		}
		return true;
	}

	//删除结点且合并子结点
	public Boolean DeleteAndMerge(Node node) {
		// TODO Auto-generated method stub
		if(node.getLeftChild()==null){
			DeleteAllChild(node);
			return true;
		}
		
		if(node.getParent().getLeftChild()==node){
			node.getParent().setLeftChild(node.getLeftChild());
		}
		else{
			Node tNode=node.getParent().getLeftChild();
		
		for(;;){
			if(tNode.getRightChild()==node) break;
			tNode=tNode.getRightChild();
		}
		tNode.setRightChild(node.getLeftChild());
		}
			Node parent=node.getParent();
			Node rNode=node.getRightChild();
			node=node.getLeftChild();
			for(;;){
				if(node.getRightChild()==null) break;
				else {
					node.setParent(parent);//改变子结点的父母
					node=node.getRightChild();
				}
			}
			node.setLeftChild(rNode);
			rNode.setParent(node);
			return true;
		
	
	}
	
	
	
}

