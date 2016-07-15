package bl;

import android.R.id;
import android.graphics.Bitmap;
import service.paintService;
import vo.Node;
import vo.TextType;
import vo.paintInfoVo;

public class paintblImpl implements paintService {

	// 新建画板
	public paintInfoVo createPaint() {
		System.out.println("create");
		return new paintInfoVo();
	}

	// 插入子结点
	public Node InsertNode(Node node) {
		Node inNode = new Node();
		inNode.setParent(node);
		if (node.getLeftChild() == null) {
			node.setLeftChild(inNode);
		} //
		else {
			node = node.getLeftChild();
			for (;;) {

				if (node.getRightChild() == null) {
					node.setRightChild(inNode);
					break;
				}
				node = node.getRightChild();
			}

		}
		
		if(inNode.getParent()==null){
			inNode.setLevel(0);
		}
		else{
			inNode.setLevel(inNode.getParent().getLevel()+1);
		}
		return inNode;// 返回子结点
	}

	//
	public Node InputText(Node node, String text, TextType font) {
		node.setTextValue(text);
		node.setTextLength(text.length());
		node.setFont(font);
		return node;
	}

	// 统计儿子结点个数，不用于排版
	public int countNode(Node node) {
		int num = 0;
		// System.out.println("count");
		if (node.getLeftChild() == null) {
			return 0;
		} else {
			node = node.getLeftChild();
			for (;;) {
				num++;
				if (node.getRightChild() == null)
					break;
				node = node.getRightChild();
			}
			// System.out.println(num);
			return num;
		}

	}

	//
	public Node InputImage(Node node, Bitmap bmp) {
		// TODO Auto-generated method stub
		node.setBmp(bmp);
		return node;
	}

	public Boolean DeleteAllChild(Node node) {
		// TODO Auto-generated method stub
		node.setLeftChild(null);//

		if (node.getParent() == null) {
			System.out.println("error in delete root node!");
			return false;
		}
		if (node.getParent().getLeftChild() == node) {
			node.getParent().setLeftChild(node.getRightChild());
		} else {
			Node tNode = node.getParent().getLeftChild();
			for (;;) {
				if (tNode.getRightChild() == node)
					break;
				tNode = tNode.getRightChild();
			}
			tNode.setRightChild(node.getRightChild());
		}
		return true;
	}

	public Boolean DeleteAndMerge(Node node) {
		// TODO Auto-generated method stub
		if (node.getLeftChild() == null) {
			DeleteAllChild(node);
			return true;
		}

		if (node.getParent().getLeftChild() == node) {
			node.getParent().setLeftChild(node.getLeftChild());
		} else {
			Node tNode = node.getParent().getLeftChild();

			for (;;) {
				if (tNode.getRightChild() == node)
					break;
				tNode = tNode.getRightChild();
			}
			tNode.setRightChild(node.getLeftChild());
		}
		Node parent = node.getParent();
		Node rNode = node.getRightChild();
		node = node.getLeftChild();
		for (;;) {
			if (node.getRightChild() == null)
				break;
			else {
				node.setParent(parent);// 改变子结点的父结点
				//修改结点层级
				if(node.getParent()==null){
					node.setLevel(0);
				}
				else{
					node.setLevel(node.getParent().getLevel()+1);
				}
				node = node.getRightChild();
			}
			
			
		}
		node.setLeftChild(rNode);
		rNode.setParent(node);
		if(rNode.getParent()==null){
			rNode.setLevel(0);
		}
		else{
			rNode.setLevel(rNode.getParent().getLevel()+1);
		}

		return true;

	}

	// 计算用于界面排版的子结点个数，多叉树，调用此方法
	public int numNode(Node node) {
		int num = 0;
		// TODO Auto-generated method stub
		if (node.getLeftChild() == null)
			return 1;
		else {
			num = Preorder(node.getLeftChild(), 0);
			return num;
		}

	}

	// 前序遍历结点
	public int Preorder(Node node, int n) {

		if (node.getLeftChild() == null) {
			n++;
			// System.out.println("here"+n);
		} else {
			n = Preorder(node.getLeftChild(), n);
			// System.out.println("lala"+n);
		}

		if (node.getRightChild() != null) {
			n = Preorder(node.getRightChild(), n);
		}

		// System.out.println("n=" + n);
		return n;

	}

}
