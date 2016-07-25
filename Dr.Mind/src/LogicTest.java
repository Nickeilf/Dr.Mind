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
        Node D=pService.InsertNode(A);
        Node E=pService.InsertNode(D);
        Node F=pService.InsertNode(B);
        pService.MoveNode(B,A,D);
        System.out.println(B.getLevel()+"lalal");
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
