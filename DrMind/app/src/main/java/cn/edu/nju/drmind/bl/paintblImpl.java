package cn.edu.nju.drmind.bl;

import android.graphics.Bitmap;

import java.util.ArrayList;

import cn.edu.nju.drmind.data.paintDao;
import cn.edu.nju.drmind.impl.paintDataServiceImpl;
import cn.edu.nju.drmind.po.paintInfoPO;
import cn.edu.nju.drmind.service.paintDataService;
import cn.edu.nju.drmind.service.paintService;
import cn.edu.nju.drmind.vo.Node;
import cn.edu.nju.drmind.vo.TextType;
import cn.edu.nju.drmind.vo.paintInfoVo;

public class paintblImpl implements paintService {

	public int ID = 1;
	paintDataService pds = new paintDataServiceImpl();

	// 新建画板
	public paintInfoVo createPaint() {
		System.out.println("create");
		return new paintInfoVo();
	}

	// 插入子结点
	public Node InsertNode(Node node) {
		ID++;
		Node inNode = new Node();
		inNode.setId(ID);// 添加一个唯一的结点编号用于数据保存
		inNode.setParent(node);
		inNode.setRoot(node.getRoot());// 设置根结点
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

		if (inNode.getParent() == null) {
			inNode.setLevel(0);
		} else {
			inNode.setLevel(inNode.getParent().getLevel() + 1);
		}
		System.out.print("innode=" + inNode.getLevel());
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
			// Node tempNode=node.getLeftChild();
			int level = node.getLevel();
			Node pa = node.getParent();
			PreorderLevel(node.getLeftChild());
			node.getParent().setLeftChild(node.getLeftChild());
			node.getLeftChild().setParent(node.getParent());
			node.getLeftChild().setLevel(node.getLevel());
			Node tempNode = node.getRightChild();
			node = node.getLeftChild();

			for (;;) {
				if (node.getRightChild() == null) {
					node.setRightChild(tempNode);
					break;
				} else {
					node = node.getRightChild();

					node.setLevel(level);
					node.setParent(pa);

				}
			}

		} else {
			int level = node.getLevel();
			Node tNode = node.getParent().getLeftChild();

			for (;;) {
				if (tNode.getRightChild() == node)
					break;
				tNode = tNode.getRightChild();
			}
			tNode.setRightChild(node.getLeftChild());

			Node parent = node.getParent();
			Node rNode = node.getRightChild();
			node = node.getLeftChild();
			PreorderLevel(node);
			for (;;) {
				if (node.getRightChild() == null)
					break;
				else {
					node.setParent(parent);// 改变子结点的父结点
					// 修改结点层级
					if (node.getParent() == null) {
						node.setLevel(0);
					} else {
						node.setLevel(level);
					}
					node = node.getRightChild();

				}

			}
			node.setParent(parent);
			node.setLevel(level);
			node.setRightChild(rNode);
			// if (rNode.getParent() == null) {
			// rNode.setLevel(0);
			// } else {
			// rNode.setLevel(rNode.getParent().getLevel() + 1);
			// }
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

	// 遍历子结点，修改level
	public void PreorderLevel(Node node) {
		if (node == null) {
			// System.out.println("here"+n);

		} else {
			PreorderLevel(node.getLeftChild());
			node.setLevel(node.getLevel() - 1);

			PreorderLevel(node.getRightChild());
			// System.out.println("lala"+n);
		}

	}

	public Boolean SavePaint(String paintName, paintInfoVo paintvo, paintDao dao) {
		// TODO Auto-generated method stub
		// paintDao dao = new paintDao(context);
		paintInfoPO po = new paintInfoPO();
		po.setbTreeRoot(paintvo.getbTreeRoot());
		pds.saveData(paintName, po, dao);
		return true;
	}

	public paintInfoVo OpenPaint(String paintName, paintDao dao,int maxid) {
		// TODO Auto-generated method stub
		paintInfoVo vo = new paintInfoVo();
		vo.setbTreeRoot(pds.getData(paintName, dao).getbTreeRoot());
        ID=maxid;
		return vo;
	}

	// 获取所有子孙结点

	public ArrayList<Node> getAllChild(Node parent) {
		// TODO Auto-generated method stub
		ArrayList<Node> child = new ArrayList<Node>();
		Node node = parent.getLeftChild();
		PreChild(node, child);
		return child;
	}

	public void PreChild(Node node, ArrayList<Node> child) {
		if (node != null) {
			child.add(node);
			PreChild(node.getLeftChild(), child);
			PreChild(node.getRightChild(), child);
		}
	}

	// 移动结点
	public Boolean MoveNode(Node node, Node newpa, Node lastBro) {
		// TODO Auto-generated method stub
		Node oldpa = node.getParent();
		if (oldpa != null) {
			if (oldpa.getLeftChild() == node) {
				oldpa.setLeftChild(node.getRightChild());
			} else {
				oldpa = oldpa.getLeftChild();
				while (oldpa.getRightChild() != node) {
					oldpa = oldpa.getRightChild();
				}
				oldpa.setRightChild(node.getRightChild());

			}
		}

		if (lastBro == null) {
			node.setParent(newpa);
			node.setRightChild(newpa.getLeftChild());
			newpa.setLeftChild(node);
		} else {
			node.setParent(lastBro.getParent());
			Node nextBro = lastBro.getRightChild();
			lastBro.setRightChild(node);
			node.setRightChild(nextBro);
		}
		node.setRoot(newpa.getRoot());
		
		changelevel(node);
	
		return true;
	}

	public void changelevel(Node node) {
		if (node != null) {
			node.setLevel(node.getParent().getLevel() + 1);
			node.setRoot(node.getParent().getRoot());
			changelevel(node.getLeftChild());
			changelevel(node.getRightChild());

		}

	}

	// 删除根结点
	public Boolean DeleteRoot(Node root, paintInfoVo vo) {
		// TODO Auto-generated method stub
		for (int i = 0; i < vo.getbTreeRoot().getRoot().size(); i++) {
			if (root == vo.getbTreeRoot().getRoot().get(i)) {
				vo.getbTreeRoot().getRoot().remove(i);
				return true;
			}
		}
		System.out.println(" no this root in the list !");
		return false;
	}

	// 新建根结点
	public Node NewRoot(paintInfoVo vo) {
		// TODO Auto-generated method stub
		ID++;
		Node newRoot = new Node();
		newRoot.setId(ID);
		newRoot.setRoot(newRoot);
		vo.getbTreeRoot().getRoot().add(newRoot);
		return newRoot;
	}

	// 获取所有子结点

	public ArrayList<Node> getAllSon(Node parent) {
		// TODO Auto-generated method stub
		ArrayList<Node> child = new ArrayList<Node>();
		Node node = parent.getLeftChild();
		while (node != null) {
			child.add(node);
			node = node.getRightChild();
		}
		return child;
	}

	public ArrayList<String> getAllPaintName(paintDao dao) {
		// TODO Auto-generated method stub

		return pds.getAllPaintName(dao);
	}
	
	public Node getAncestor(Node node){
	   return node.getRoot();
	}

}
