import bl.paintblImpl;
import junit.framework.TestCase;
import service.paintService;
import vo.Node;
import vo.paintInfoVo;

public class LogicTest extends TestCase {

    
	public static void testBL(){
		paintService pService = new paintblImpl();
		paintInfoVo vo=pService.createPaint();
		Node node=vo.getbTreeRoot().getRoot().get(0);
		Node A=pService.InsertNode(node);
		Node B=pService.InsertNode(node);
		Node C=pService.InsertNode(node);
		//Node D=pService.InsertNode(node);
		System.out.println(pService.countNode(node));
		pService.InsertNode(node.getLeftChild());
		node.getLeftChild().getRightChild().setTextValue("mmmm");
		pService.InsertNode(node.getLeftChild().getLeftChild());
		//pService.InsertNode(node.getLeftChild().getRightChild());
		//pService.InsertNode(node.getLeftChild().getRightChild());
		node.getRightChild();
		//B.getLeftChild().setTextValue("llll");

	//	System.out.println(node.getTextValue());
	//	System.out.println(node.getTextLength());
	//	System.out.println(pService.countNode(node));
		//pService.DeleteAllChild(node.getLeftChild());
//		System.out.println(pService.countNode(node));
		System.out.println(node.getLeftChild().getTextValue());
		//pService.DeleteAndMerge(B);
//		node=node.getLeftChild();
//		for(;;){
//			if(node.getRightChild()==null)break;
//			System.out.println(node.getTextValue());
//			node=node.getRightChild();
//		}
	    
		System.out.println("num="+pService.numNode(node));
		
	}
	
	public static void main(String[] args){
		testBL();
	}
	
}
