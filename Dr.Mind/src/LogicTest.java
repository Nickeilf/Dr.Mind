import bl.paintblImpl;
import junit.framework.TestCase;
import service.paintService;
import vo.Node;
import vo.paintInfoVo;

public class LogicTest extends TestCase {

    
	public static void testBL(){
		paintService pService = new paintblImpl();
		paintInfoVo vo=pService.createPaint();
		Node node=vo.getbTreeRoot().getRoot();
		pService.InsertNode(node);
		pService.InsertNode(node);
		pService.InputText(node, "lalal");
		System.out.println(node.getTextValue());
		System.out.println(node.getTextLength());
		System.out.println(pService.countNode(node));
	    
	}
	
	public static void main(String[] args){
		testBL();
	}
	
}
