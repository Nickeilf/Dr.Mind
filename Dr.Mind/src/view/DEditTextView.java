package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import vo.Node;

public class DEditTextView extends EditText {
	private Node node;

	public DEditTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public DEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DEditTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

}
