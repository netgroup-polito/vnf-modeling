package it.polito.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import it.polito.modelib.RoutingResult.Result;
import it.polito.parser.IfElseBranch.Branch;

public class StatementVisitor extends ASTVisitor {
	
	private List<IfElseBranch> conditions;
	private List<IfElseBranch> previousIfElseBranch;
	private List<ForLoop> loops;
	private List<MyExpression> predicatesOnSentPacket;
	private boolean foundReturn;
	private boolean isDeadCode;
	private int nestingLevel;
	private int skippedActions;
	private CompilationUnit compilationUnit;

	public StatementVisitor(CompilationUnit compilationUnit, List<IfElseBranch> conditions, boolean isDeadCode, List<IfElseBranch> previousIfElseBranch, List<ForLoop> loops, int nestingLevel, List<MyExpression> predicatesOnSentPacket, int skippedActions) {
		this.conditions = conditions;
		this.foundReturn = false;
		this.isDeadCode = isDeadCode;
		this.loops = loops;
		this.previousIfElseBranch = previousIfElseBranch;
		this.nestingLevel = nestingLevel;
		this.predicatesOnSentPacket = predicatesOnSentPacket;
		this.compilationUnit = compilationUnit;
		this.skippedActions = skippedActions;
	}
	
	public boolean isDeadCode() {
		return isDeadCode;
	}
	
	public boolean isFoundReturn() {
		return foundReturn;
	}

	public List<IfElseBranch> getConditions() {
		return conditions;
	}
	
	public List<IfElseBranch> getPreviousIfElseBranch() {
		return previousIfElseBranch;
	}
	
	public List<ForLoop> getLoops() { 
		return loops; 
	}
	
	public List<MyExpression> getPredicatesOnSentPacket() {
		return predicatesOnSentPacket;
	}
	
	public int getSkippedActions() {
		return skippedActions;
	}
	
	@Override
	public boolean visit(IfStatement node) {
		System.out.println("ok1");
		System.out.println(node.toString());
		//System.out.println("\tNode " + node.getExpression() + " received " + previousIfElseBranch.size() + " prev. if");
		//System.out.println("\tNode " + node.getExpression() + node.getNodeType() + " has parent " + node.getParent() + node.getNodeType());
		TrapExploratorVisitor trapFinder = new TrapExploratorVisitor();
		node.accept(trapFinder);
		
		ReturnStatementExplorator visitor = new ReturnStatementExplorator();
		node.getThenStatement().accept(visitor);
		ReturnStatementExplorator visitor2 = new ReturnStatementExplorator();
		if(node.getElseStatement() != null)
			node.getElseStatement().accept(visitor2);
		if(!visitor.hasReturnStatement() && !visitor2.hasReturnStatement())	// No return stmt inside this IF-THEN-ELSE (non c e nessuna azione in questo if quindi vado avanti col prossimo)
			return true;
		
		List<IfElseBranch> c = new ArrayList<IfElseBranch>();
		StatementVisitor s;
		IfElseBranch b = new IfElseBranch();
		b.setStatement(node);
		b.setBranch(Branch.IF);
		b.setNestingLevel(nestingLevel);
		if(visitor.hasReturnStatement())	/* Visit IF branch */
		{
			conditions.add(b);
			c.addAll(conditions);
			s = new StatementVisitor(compilationUnit, c, false, previousIfElseBranch, loops, nestingLevel+1, predicatesOnSentPacket, skippedActions);
			node.getThenStatement().accept(s);
			conditions.remove(b);
		}

		if(node.getElseStatement() == null || !visitor2.hasReturnStatement())	/* Skip ELSE branch */
		{
			previousIfElseBranch.add(b);
			removePreviousIf(nestingLevel+1);
			return false;
		}
		
		removePreviousIf(nestingLevel+1);
		
		b = new IfElseBranch();
		b.setStatement(node);
		b.setBranch(Branch.ELSE);
		b.setNestingLevel(nestingLevel);
		conditions.add(b);
		c = new ArrayList<IfElseBranch>();
		c.addAll(conditions);
		s = new StatementVisitor(compilationUnit, c, false, previousIfElseBranch, loops, nestingLevel+1, predicatesOnSentPacket, skippedActions);
		node.getElseStatement().accept(s);	/* Visit ELSE branch */
		
		conditions.remove(b);
		
		removePreviousIf(nestingLevel+1);
		
		previousIfElseBranch.add(b);
		
		if(trapFinder.isATrap())
			this.isDeadCode = true;
		
		return false;
	}
	
	@Override
	public boolean visit(ReturnStatement node) {
		System.out.println("ok2");
		System.out.println(node.toString());
		this.foundReturn = true;
		if(this.isDeadCode)
		{
			System.err.println("\t[ERROR] Found RETURN but it is dead code! " + node);
			return false;
		}
		ReturnStatementVisitor r = new ReturnStatementVisitor(compilationUnit);
		node.accept(r);
		if(r.getAction() == Result.DROP)
		{
			skippedActions++;
			return false;
		}
		System.out.println("\t*****Found action " + r.getAction() + " ["+r.getForwardDirection()+"] (with "+conditions.size()+" conditions leading to it) at line " + compilationUnit.getLineNumber(node.getStartPosition()) + ". Reached nesting level " + nestingLevel);
		
		int j = 1;
		for(IfElseBranch ieb : previousIfElseBranch)
			System.out.println("\t\tPrevious IF-THEN-ELSE #" + j++ + " " + ieb.getStatement().getExpression() + " (IF has RETURN="+ieb.ifBranchContainsReturn()+") (ELSE has RETURN="+ieb.elseBranchContainsReturn()+")");
		
		j = 1;
		for(IfElseBranch ieb : conditions)
			System.out.println("\t\tCondition #" + j++ + " ("+ieb.getBranch()+"): " + ieb.getStatement().getExpression());
		
		for(ForLoop fLoop : loops)
			System.out.println("\tEncountered " + fLoop.getStatement().getExpression() + " with nesting level " + fLoop.getNestingLevel());
		
		if(r.getAction() == Result.FORWARD)
		{
			System.out.println("\n\t\tRules for the outgoing packet:");
			for(MyExpression expr : predicatesOnSentPacket)
				System.out.println("\t\t\tpacket." + expr.getField() + "\t= " + expr.getValue());
			System.out.println("\t\tThe remaining fields are unchanged");
		}
		
		System.out.println();
		return false;
	}
	
	private void removePreviousIf(int nestingLevel)
	{
		List<IfElseBranch> toRemove = new ArrayList<IfElseBranch>();
		for(IfElseBranch ifElse : previousIfElseBranch)
			if(ifElse.getNestingLevel() == nestingLevel)
				toRemove.add(ifElse);
		previousIfElseBranch.removeAll(toRemove);
		return;
	}
	
	@Override
	public boolean visit(WhileStatement node) {
		System.out.println("ok3");
		System.out.println(node.toString());
		System.out.println("\tFound while -> " + node.getExpression());
		return true;
	}
	
	@Override
	public boolean visit(EnhancedForStatement node) {
		System.out.println("ok4");
		System.out.println(node.toString());
		System.out.println("\tFound FOR-EACH. Variable declaration -> " + node.getParameter() + ". Iterate over -> " + node.getExpression());
		ForLoop l = new ForLoop();
		l.setNestingLevel(nestingLevel);
		l.setStatement(node);
		loops.add(l);
		List<IfElseBranch> c = new ArrayList<IfElseBranch>();
		c.addAll(conditions);
		StatementVisitor s = new StatementVisitor(compilationUnit, c, false, previousIfElseBranch, loops, nestingLevel+1, predicatesOnSentPacket, skippedActions);
		node.getBody().accept(s);
		return false;
	}
	
	@Override
	public boolean visit(ExpressionStatement node) {
		System.out.println("ok5");
		System.out.println(node.toString());
		//System.out.println("\tExpressionStatement " + node.getExpression());
		ExpressionVisitor v = new ExpressionVisitor();
		node.accept(v);
		predicatesOnSentPacket.addAll(v.getPredicates());
		return true;
	}
	
	@Override
	public boolean visit(LineComment node) {
		System.out.println("ok6");
		System.out.println(node.toString());
		System.out.println("\tFound comment -> " + node);
		return true;
	}
	
	@Override
	public boolean visit(BlockComment node) {
		System.out.println("ok7");
		System.out.println(node.toString());
		System.out.println("\tFound comment block -> " + node);
		return true;
	}
	
//	@Override
//	public boolean visit(Assignment node) {
//		// TODO Auto-generated method stub
//		System.out.println("ok8");
//		System.out.println(node.toString());
//		/*
//		if(!node.getRightHandSide().toString().equalsIgnoreCase("null")) {
//			//System.out.println(node.getLeftHandSide().toString());
//			//System.out.println(node.getRightHandSide().toString());
//			if(node.getRightHandSide().toString().equalsIgnoreCase("new Packet()")) {
//				this.keywords.add(node.getLeftHandSide().toString());
//				this.keywords.add(node.getRightHandSide().toString());
//				level++;
//			}
//		}
//		*/
//		return super.visit(node);
//	}
//	
//	@Override
//	public boolean visit(VariableDeclarationStatement node) {
//		// TODO Auto-generated method stub
//		System.out.println("ok9");
//		System.out.println(node.toString());
//		/*
//		VariableDeclarationFragment vdf = (VariableDeclarationFragment) node.fragments().get(0);
//		//System.out.println(vdf.getName());
//		//System.out.println(vdf.getInitializer());
//		if(vdf.getInitializer()!=null) {
//			//nfd.setVariable(vdf.getName().toString(), vdf.getInitializer().toString());
//			//System.out.println(vdf.getName().toString());
//			//System.out.println(vdf.getInitializer().toString());
//			if(vdf.getInitializer().toString().equalsIgnoreCase("new Packet()")) {
//				this.keywords.add(vdf.getName().toString());
//				this.keywords.add(vdf.getInitializer().toString());
//				level++;
//			}
//		}
//		
//		//else
//			//nfd.setVariable(vdf.getName().toString(), null);
//		*/
//		return super.visit(node);
//	}
	
}
