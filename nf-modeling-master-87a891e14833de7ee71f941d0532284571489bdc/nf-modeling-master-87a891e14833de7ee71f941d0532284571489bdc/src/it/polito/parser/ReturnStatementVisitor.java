package it.polito.parser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.QualifiedName;

import it.polito.modelib.RoutingResult.ForwardDirection;
import it.polito.modelib.RoutingResult.Result;

public class ReturnStatementVisitor extends ASTVisitor {
	
	private Result action;
	private ForwardDirection direction;
	private String packetName;
	private CompilationUnit compilationUnit;
	
	public ReturnStatementVisitor(CompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
		this.action = Result.UNKNOW;
	}
	
	public ReturnStatementVisitor() {
		this.action = Result.UNKNOW;
	}
	
	public Result getAction() {
		return action;
	}
	
	public String getPacketName() {
		return packetName;
	}
	
	public ForwardDirection getForwardDirection() { 
		return direction; 
	}
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
		if(!node.getType().toString().equals(Constants.ROUTING_RESULT_CLASS))
		{
			System.err.println("Error in the Return statement at line " + compilationUnit.getLineNumber(node.getStartPosition()));
			return false;
		}
		@SuppressWarnings("unchecked")
		List<Expression> arg = node.arguments();
		if(arg.size() != 3)
		{
			System.err.println("Wrong number of arguments provided to the "+Constants.ROUTING_RESULT_CLASS+" class");
			return false;
		}
		String packetName = arg.get(0).toString();
		Expression action = arg.get(1);
		Expression direction = arg.get(2);
		ReturnResult r1 = new ReturnResult();
		ReturnDirection r2 = new ReturnDirection();
		action.accept(r1);
		direction.accept(r2);
		this.packetName = packetName;
		this.action = r1.getResult();
		this.direction = r2.getForwardDirection();
		return true;
	}
	
	private class ReturnResult extends ASTVisitor {
		
		private Result result;
		
		@Override
		public boolean visit(QualifiedName node) {
			//System.out.println("ACTION->" + node.getQualifier()+"."+node.getName());
			switch(node.getName().toString())
			{
				case "FORWARD":
					this.result = Result.FORWARD;
					break;
				case "DROP":
					this.result = Result.DROP;
					break;
				default:
					System.err.println("Unrecognized action");
					this.result = Result.UNKNOW;
			}
			return true;
		}
		
		public Result getResult() { return this.result; }
		
	}
	
	private class ReturnDirection extends ASTVisitor {
		
		private ForwardDirection direction = ForwardDirection.UNSPECIFIED;
		
		@Override
		public boolean visit(QualifiedName node) {
			//System.out.println("DIRECTION->" + node.getQualifier()+"."+node.getName());
			switch(node.getName().toString())
			{
				case "UPSTREAM":
					this.direction = ForwardDirection.UPSTREAM;
					break;
				case "SAME_INTERFACE":
					this.direction = ForwardDirection.SAME_INTERFACE;
					break;
				case "UNSPECIFIED":
					this.direction = ForwardDirection.UNSPECIFIED;
					break;
				default:
					System.err.println("Unrecognized direction");
			}
			return true;
		}
		
		public ForwardDirection getForwardDirection() { return this.direction; }
	}

}
