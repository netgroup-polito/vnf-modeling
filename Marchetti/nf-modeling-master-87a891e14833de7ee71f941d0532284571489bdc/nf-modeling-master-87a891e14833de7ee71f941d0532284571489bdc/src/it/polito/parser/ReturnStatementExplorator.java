package it.polito.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ReturnStatement;

public class ReturnStatementExplorator extends ASTVisitor {
	
	private boolean foundReturn = false;
	
	public boolean hasReturnStatement() {
		return foundReturn;
	}
	
	@Override
	public boolean visit(ReturnStatement node) {
		this.foundReturn = true;
		return true;
	}

}
