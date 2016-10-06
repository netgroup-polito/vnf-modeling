package it.polito.parser;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

public class ThenStatementVisitor extends ASTVisitor{

	private NFdefinition nfd;
	ArrayList<String> condition = new ArrayList<String>();
	int level=0;

	public ThenStatementVisitor(NFdefinition nfd) {
		// TODO Auto-generated constructor stub
		this.nfd = nfd;
	}

	public NFdefinition getNfd() {
		return nfd;
	}

	public void setNfd(NFdefinition nfd) {
		this.nfd = nfd;
	}

	public ArrayList<String> getCondition() {
		return condition;
	}

	public void setCondition(ArrayList<String> condition) {
		this.condition = condition;
	}
	
	@Override
	public boolean visit(SimpleName node) { //it builds the then condition 
		level++;
		if(nfd.getVariableList().get(node.toString())!=null && level==4 && this.condition.get(1).equalsIgnoreCase("hostTableList")) { //in case a variable is used instead of a string
			this.condition.add(nfd.getVariableList().get(node.toString()));
			return true;
		}
		this.condition.add(node.toString());
		return super.visit(node);
	}
	
	@Override
	public boolean visit(StringLiteral node) { //it builds the then condition
		level++;
		if(level==4 || level==6) {
			this.condition.add(node.toString());
		}
		return super.visit(node);
	}
	
}
