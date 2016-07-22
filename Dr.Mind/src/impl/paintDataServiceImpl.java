package impl;

import java.util.ArrayList;
import java.util.List;

import android.R.drawable;
import android.R.integer;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.sax.RootElement;
import data.paintDao;
import po.paintInfoPO;
import service.paintDataService;
import vo.BinaryTree;
import vo.Node;
import vo.paintInfoVo;

public class paintDataServiceImpl implements paintDataService{
	

	@SuppressWarnings("null")
	public paintInfoPO getData(String paintName,Context context) {
		// TODO Auto-generated method stub
		paintInfoPO po =new paintInfoPO();
        paintDao dao = new paintDao(context);
        String result=dao.execQuery(paintName);
        String[] list=result.split("#");
        ArrayList<Integer> rootID = new ArrayList<Integer>();
        ArrayList<Node> root = new ArrayList<Node>();

        //新建出所有的结点和根结点
        for(int i=0;i<list.length;i++){
        	/*id/paintName/root/parent/leftchild/rightchild/textvalue/level# */
        	String[] list1=list[i].split("/");
        	if(rootID==null){
        		rootID.add(Integer.parseInt(list1[2]));
        		Node root1 = new Node();
        		root.add(root1);
        	
        	}
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
        				node.setId(Integer.parseInt(list1[2]));
        			    }
        				
        		}
        		
        		else{
        				//根结点不在根结点链表中
        				Node root2 = new Node();
        				root.add(root2);
        				rootID.add(Integer.parseInt(list1[2]));
        				
        			    if(Integer.parseInt(list1[0])==Integer.parseInt(list1[2])){
          			      //是根结点
          			       root2.setId(Integer.parseInt(list1[0]));
          			       root2.setRoot(root2);
          			    }
        			    else{
        				Node node = new Node();
        				node.setRoot(root2);
        				node.setId(Integer.parseInt(list1[2]));
        			}
        		}
        	}
        }
        
        
        //链接结点，填充内容
    	/*id/paintName/root/parent/leftchild/rightchild/textvalue/level# */
        for(int i=0;i<list.length;i++){
        	String[] list1=list[i].split("/");
        	//查找根结点
        	int m=0;
        	for( m=0;m<rootID.size();m++){
        		if(rootID.get(m)==Integer.parseInt(list1[2])) break;
        	}
        	Node tempRoot=root.get(m);
        	Node tempNode =PreorderID(Integer.parseInt(list1[2]),tempRoot);
        	Node tempParent=PreorderID(Integer.parseInt(list1[3]),tempRoot);
        	Node templeft=PreorderID(Integer.parseInt(list1[4]),tempRoot);
        	Node tempright=PreorderID(Integer.parseInt(list1[5]),tempRoot);
        	
        	tempNode.setParent(tempParent);
        	tempNode.setLeftChild(templeft);
        	tempNode.setRightChild(tempright);
        	tempNode.setTextValue(list1[6]);
        	tempNode.setLevel(Integer.parseInt(list1[7]));
        }
        
    	BinaryTree bTreeRoot = new BinaryTree();
        bTreeRoot.setRoot(root);
		po.setbTreeRoot(bTreeRoot);
		
		return po;
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
	
	public boolean saveData(String paintName, paintInfoPO paintpo,Context context) {
		// TODO Auto-generated method stub
//		paintInfoPO po = new paintInfoPO();
//		po.setbTreeRoot(paintvo.getbTreeRoot());
		paintpo.setPaintName(paintName);
		 paintDao dao =new paintDao(context);		 
		if(dao.isExistPaint(paintName)){
			deleteData(paintName, context);//已存在画图，先删除再保存即实现更新
		}
		for(int index=0;index<paintpo.getbTreeRoot().getRoot().size();index++){
		   Node temproot=paintpo.getbTreeRoot().getRoot().get(index);
		   PreOrderSave(temproot, dao, paintName);
		}
		
		return true;
	}

	public boolean deleteData(String paintName, Context context) {
		// TODO Auto-generated method stub
		paintDao dao =new paintDao(context);
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
			System.out.println(root.getLevel()+"+"+root.getId()+"jjjjjjjjjjjjjjjjjjjjjj");
			dao.insert(root.getId(), paintname,parentID,leftID, 
					rightID, root.getTextValue(),root.getLevel());
			PreOrderSave(root.getLeftChild(),dao,paintname);
			PreOrderSave(root.getRightChild(),dao,paintname);
		}
	}

}
