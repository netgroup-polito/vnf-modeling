package it.polito.parser;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

public class DefineStateVisitor extends ASTVisitor{
	int level = 0;
	ArrayList<String> keywords = new ArrayList<String>();
	private NFdefinition nfd;

	public DefineStateVisitor(NFdefinition nfd) {
		this.nfd = nfd;
	}
	
	
	
	@Override
	public boolean visit(ExpressionStatement node) {
		return true;
	}
	

	
	@Override
	public boolean visit(FieldAccess node) {
		return true;
	}
	

	
	@Override
	public boolean visit(MethodInvocation node) {
		if(level == 0 && node.toString().equalsIgnoreCase("this.state.internalNodes.set()")) {
			this.keywords.add(node.toString());
			level++;
		}
		if(level == 0 && node.toString().equalsIgnoreCase("this.state.internalNodes.setWithPrivateAddresses()")) {
			this.keywords.add(node.toString());
			level++;
		}
		if(level == 0 && node.toString().equalsIgnoreCase("this.state.internalNodes.setWithoutPrivateAddresses()")) {
			this.keywords.add(node.toString());
			level++;
		}
		if(level == 0 && node.toString().equalsIgnoreCase("this.state.receivedPackets.set()")) {
			this.keywords.add(node.toString());
			level++;
		}
		if(level == 0 && node.toString().equalsIgnoreCase("this.state.sentPackets.set()")) {
			this.keywords.add(node.toString());
			level++;
		}
		return true;
	}
	
	
	

	
	@Override
	public boolean visit(NumberLiteral node) { //insert the number of column of the hostTable
		if(level==5) {
			level++;
			keywords.add(node.toString());
		}
		return true;
	}
	

	
	@Override
	public boolean visit(QualifiedName node) { //insert the name of the column of the hostTable
		if(level>=6) {
			level++;
			keywords.add(node.toString());
		}
		return true;
	}
	

	
	@Override
	public boolean visit(SimpleName node) { //create the keywords for hostPropertyList and hostTabelList
		if(node.toString().equalsIgnoreCase("add") && level==1 && keywords.get(0).equalsIgnoreCase("hostTableList")) {
			level++;
			keywords.add(node.toString());
		}
		if(node.toString().equalsIgnoreCase("HostTable") && level==2 && keywords.get(1).equalsIgnoreCase("add")) {
			level++;
			keywords.add(node.toString());
		}
		if(node.toString().equalsIgnoreCase("createTable") && level==3 && keywords.get(2).equalsIgnoreCase("HostTable")) {
			level++;
			keywords.add(node.toString());
		}
		if(node.toString().equalsIgnoreCase("hostPropertyList") && level==0) {
			level++;
			keywords.add(node.toString());
		}
		if(node.toString().equalsIgnoreCase("hostTableList") && level==0) {
			level++;
			keywords.add(node.toString());
		}
		if(node.toString().equalsIgnoreCase("add") && level==1 && keywords.get(0).equalsIgnoreCase("hostPropertyList")) {
			level++;
			keywords.add(node.toString());
		}
		if(nfd.getVariableList().get(node.toString())!=null && level==4 && keywords.get(3).equalsIgnoreCase("createTable")) {
			level++;
			keywords.add(nfd.getVariableList().get(node.toString()));
		}
		if(nfd.getVariableList().get(node.toString())!=null && level==2 && keywords.get(1).equalsIgnoreCase("add")) { //inserisce il nome della hostTable o della property (se espresso tramite variabile)
			level++;
			keywords.add(nfd.getVariableList().get(node.toString()));
		}
		return true;
	}
	

	
	@Override
	public boolean visit(StringLiteral node) { //insert the name of the hostTable or property
		if(level==4 && keywords.get(3).equalsIgnoreCase("createTable")) {
			level++;
			keywords.add(node.toString());
		}
		if(level==2 && keywords.get(1).equalsIgnoreCase("add")) {
			level++;
			keywords.add(node.toString());
		}
		return true;
	}
	

	
	@Override
	public boolean visit(ThisExpression node) {
		return true;
	}



	public ArrayList<String> getKeywords() {
		return keywords;
	}
	
	
	
	@Override
	public boolean visit(Assignment node) { //insert the variable into the nfd
		if(node.getRightHandSide()!=null)
			nfd.setVariable(node.getLeftHandSide().toString(), node.getRightHandSide().toString());
		else
			nfd.setVariable(node.getLeftHandSide().toString(), null);
		return super.visit(node);
	}


	@Override
	public boolean visit(VariableDeclarationStatement node) { //insert the variable into the nfd
		VariableDeclarationFragment vdf = (VariableDeclarationFragment) node.fragments().get(0);
		if(vdf.getInitializer()!=null)
			nfd.setVariable(vdf.getName().toString(), vdf.getInitializer().toString());
		else
			nfd.setVariable(vdf.getName().toString(), null);
		return super.visit(node);
	}
	

}
