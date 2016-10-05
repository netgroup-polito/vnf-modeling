package it.polito.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class ExpressionVisitor extends ASTVisitor {
	
	private List<MyExpression> predicates;
	
	public ExpressionVisitor() {
		predicates = new ArrayList<MyExpression>();
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		if(node.getName().toString().equals(Constants.SET_FIELD_METHOD_NAME))
		{
			if(node.arguments().size() != 2)
			{
				System.err.println("[ERROR] Wrong number of arguments passed to the "+Constants.SET_FIELD_METHOD_NAME+" method!");
				return false;
			}
			if(!node.getExpression().toString().equals(Constants.PACKET_NAME))
			{
				System.err.println("[ERROR] The "+Constants.SET_FIELD_METHOD_NAME+"() method must be called on the "+Constants.PACKET_NAME+" variable!");
				return false;
			}
			Expression field = (Expression) node.arguments().get(0);
			Expression value = (Expression) node.arguments().get(1);
			predicates.add(new MyExpression(field.toString(), value.toString()));
		}
		return false;
	}
	
	public List<MyExpression> getPredicates() { return predicates; }
	
}
