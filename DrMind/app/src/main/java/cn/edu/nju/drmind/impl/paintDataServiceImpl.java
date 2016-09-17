package cn.edu.nju.drmind.impl;

import java.util.ArrayList;

import cn.edu.nju.drmind.data.paintDao;
import cn.edu.nju.drmind.po.paintInfoPO;
import cn.edu.nju.drmind.service.paintDataService;
import cn.edu.nju.drmind.vo.BinaryTree;
import cn.edu.nju.drmind.vo.Node;


public class paintDataServiceImpl implements paintDataService {
	
	
//	 public paintDataServiceImpl(Context context) {
//	 paintDao dao = new dao
//	}
	

	@SuppressWarnings("null")
	public paintInfoPO getData(String paintName, paintDao dao) {
		// TODO Auto-generated method stub
		paintInfoPO po =new paintInfoPO();
     //   paintDao dao = new paintDao(context);
        String result=dao.execQuery(paintName);
        String[] list=result.split("#");
        ArrayList<Integer> rootID = new ArrayList<Integer>();
        ArrayList<Node> root = new ArrayList<Node>();//所有根结点的链表
        ArrayList<Node> nodeList = new ArrayList<Node>();//所有节点的链表

        //新建出所有的结点和根结点
        for(int i=0;i<list.length;i++){
        	/*id/paintName/root/parent/leftchild/rightchild/textvalue/level/x/y# */
        	String[] list1=list[i].split("/");
        	//根结点链表为空
        	if(rootID==null){
        		rootID.add(Integer.parseInt(list1[2]));
        		Node root1 = new Node();
        		root1.setId(Integer.parseInt(list1[2]));
        		root1.setRoot(root1);
        		nodeList.add(root1);
        		System.out.println("rootid = "+root1.getId());
        		root.add(root1);
        	
        	}
        	//根结点链表不为空时
        	else{
        		boolean rootIsIn=false;
                int m=0;       		
        		for(m=0;m<rootID.size();m++){
        			if(Integer.parseInt(list1[2])==rootID.get(m)){
        				//如果根结点存在于根结点链表中
        				rootIsIn=true;
        				break;
        			}
        		}
        		if(rootIsIn){
        			    if(Integer.parseInt(list1[0])==Integer.parseInt(list1[2])){
        			      //是根结点
        			       root.get(m).setId(Integer.parseInt(list1[0]));
        			       root.get(m).setRoot(root.get(m));
        			    }
        			    else{//不是根结点就新建出来
        				Node node = new Node();
        				node.setRoot(root.get(m));
        				node.setId(Integer.parseInt(list1[0]));
        				nodeList.add(node);
        				System.out.println("node "+node.getId()+"rootid = "+node.getRoot().getId());
        			    }
        				
        		}
        		
        		else{
        				//根结点不在根结点链表中
        				Node root2 = new Node();

        				root.add(root2);
        				rootID.add(Integer.parseInt(list1[2]));
        				nodeList.add(root2);
        				
        			    if(Integer.parseInt(list1[0])==Integer.parseInt(list1[2])){
          			      //是根结点
          			       root2.setId(Integer.parseInt(list1[0]));
          			       root2.setRoot(root2);
          			 	System.out.println("rootid = "+root2.getId());
          			    }
        			    else{
        				Node node = new Node();
        				node.setRoot(root2);
        				node.setId(Integer.parseInt(list1[0]));
        				nodeList.add(node);
        				System.out.println("node "+node.getId()+"rootid = "+root2.getId());
        			}
        		}
        	}
        }
        
        
        //链接结点，填充内容
    	/*id/paintName/root/parent/leftchild/rightchild/textvalue/level/x/y# */
        System.out.println("rootID "+rootID);
        for(int i=0;i<list.length;i++){
        	String[] list1=list[i].split("/");
        	//查找根结点
        	
//        	int m=0;
//        	for( m=0;m<rootID.size();m++){
//        		if(rootID.get(m)==Integer.parseInt(list1[2])) break;
//        	}
//        	Node tempRoot=root.get(m);
//        	Node tempNode =PreorderID(Integer.parseInt(list1[2]),tempRoot);
//        	Node tempParent=PreorderID(Integer.parseInt(list1[3]),tempRoot);
//        	Node templeft=PreorderID(Integer.parseInt(list1[4]),tempRoot);
//        	Node tempright=PreorderID(Integer.parseInt(list1[5]),tempRoot);
        	
     
        	Node tempNode =searchNode(nodeList, Integer.parseInt(list1[0]));
        	Node tempParent=searchNode(nodeList, Integer.parseInt(list1[3]));
        	Node templeft=searchNode(nodeList, Integer.parseInt(list1[4]));
        	Node tempright=searchNode(nodeList, Integer.parseInt(list1[5]));
        	
        	
        	
        	tempNode.setParent(tempParent);
        	tempNode.setLeftChild(templeft);
        	tempNode.setRightChild(tempright);
        	tempNode.setTextValue(list1[6]);
        	tempNode.setLevel(Integer.parseInt(list1[7]));
        	tempNode.setX(Integer.parseInt(list1[8]));
        	tempNode.setY(Integer.parseInt(list1[9]));
        	
        }
        
    	BinaryTree bTreeRoot = new BinaryTree();
        bTreeRoot.setRoot(root);
		po.setbTreeRoot(bTreeRoot);
		
		for(int i=0;i<root.size();i++){
		System.out.println("root链表里是"+root.get(i).getTextValue());
		System.out.println("root链表里是"+root.get(i).getId());
		}
		return po;
	}

	public Node searchNode(ArrayList<Node> nodeList,int id){
		for(int i=0;i<nodeList.size();i++){
			if(nodeList.get(i).getId()==id){
				return nodeList.get(i);
			}
		}
		return null;
	}
	
	//遍历ID获取结点
	public Node PreorderID(int id,Node root){
		if(id==-1) return null;
		if(root==null){
			
		}
		else{
			if(root.getId()==id) return root;
			PreorderID(id, root.getLeftChild());
			PreorderID(id, root.getRightChild());
		}
			
	    //找不到返回null
		return null;
	}
	
	public boolean saveData(String paintName, paintInfoPO paintpo,paintDao dao) {
		// TODO Auto-generated method stub
		paintpo.setPaintName(paintName);		 
		if(dao.isExistPaint(paintName)){
			deleteData(paintName,dao);//已存在画图，先删除再保存即实现更新
		}
		for(int index=0;index<paintpo.getbTreeRoot().getRoot().size();index++){
		   Node temproot=paintpo.getbTreeRoot().getRoot().get(index);
		   System.out.println(temproot.getTextValue());
		   PreOrderSave(temproot, dao, paintName);
		}
		
		return true;
	}

	public boolean deleteData(String paintName, paintDao dao) {
		// TODO Auto-generated method stub
		//paintDao dao =new paintDao(context);
		dao.delete(paintName);
		return true;
	}
	
	//遍历保存
	public void PreOrderSave(Node root,paintDao dao,String paintname){ 
		if(root!=null){
			int parentID;
			if(root.getParent()==null){
				parentID=0;
			}else{
				parentID=root.getParent().getId();
			}
			int leftID;
			if(root.getLeftChild()==null){
				leftID=0;
			}else{
				leftID=root.getLeftChild().getId();
			}
			int rightID;
			if(root.getRightChild()==null){
				rightID=0;
			}else{
				rightID=root.getRightChild().getId();
			}
			
			dao.insert(root.getId(),paintname,root.getRoot().getId(),parentID,leftID, 
					rightID, root.getTextValue(),root.getLevel(),root.getX(),root.getY());
			PreOrderSave(root.getLeftChild(),dao,paintname);
			PreOrderSave(root.getRightChild(),dao,paintname);
		}
	}

	public ArrayList<String> getAllPaintName(paintDao dao) {
		// TODO Auto-generated method stub
       
		return   dao.getAllPaintName();
	}



}
