package it.polito.parser;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

public class PacketDefinitionVisitor extends ASTVisitor{
	private NFdefinition nfd;
	int level = 0;
	ArrayList<String> keywords = new ArrayList<String>();

	public PacketDefinitionVisitor(NFdefinition nfd) {
		this.nfd = nfd;
	}

	@Override
	public boolean visit(Assignment node) { //it inserts into keywords the packet creation
		if(!node.getRightHandSide().toString().equalsIgnoreCase("null")) {
			if(node.getRightHandSide().toString().equalsIgnoreCase("new Packet()")) {
				this.keywords.add(node.getLeftHandSide().toString());
				this.keywords.add(node.getRightHandSide().toString());
				level++;
			}
		}
		return super.visit(node);
	}

	

	
	@Override
	public boolean visit(FieldAccess node) { //it creates keyword for setting a field
		if(level==2 && node.toString().equalsIgnoreCase("this.address")) {
			this.keywords.add(node.toString());
			level++;
		}
		return super.visit(node);
	}
	

	
	@Override
	public boolean visit(MethodInvocation node) { //it creates keyword for setting a field
		if(level==2 && node.getExpression().toString().equalsIgnoreCase(this.keywords.get(0))) {
			if(node.getName().toString().equalsIgnoreCase("getSourceAddress")||
					node.getName().toString().equalsIgnoreCase("getOrigin")||
					node.getName().toString().equalsIgnoreCase("getBody")||
					node.getName().toString().equalsIgnoreCase("getProtocol")||
					node.getName().toString().equalsIgnoreCase("getDestinationAddress")||
					node.getName().toString().equalsIgnoreCase("getDestinationPort")||
					node.getName().toString().equalsIgnoreCase("getMailDestination")||
					node.getName().toString().equalsIgnoreCase("getMailSource")||
					node.getName().toString().equalsIgnoreCase("getOrigBody")||
					node.getName().toString().equalsIgnoreCase("getSourcePort")||
					node.getName().toString().equalsIgnoreCase("getUrl")
					) {
				this.keywords.add(node.getExpression().toString()); //it inserts packet name and field
				this.keywords.add(node.getName().toString());
				level++;
			}
			
		}
		if(level==2 && node.getName().toString().equalsIgnoreCase("get") && (node.getExpression().toString().equalsIgnoreCase("this.hostPropertyList")||node.getExpression().toString().equalsIgnoreCase("hostPropertyList"))) {
			this.keywords.add("hostPropertyList"); //it inserts hostPropertyList get and property name in this order
			this.keywords.add(node.getName().toString());
			this.keywords.add(node.arguments().get(0).toString());
		}
		return super.visit(node);
	}
	

	
	@Override
	public boolean visit(SimpleName node) { //it creates keywords for setting a field
		if(level==0) {
			this.keywords.add(node.toString());
			level++;
		}
		if(level==1) {
			if(node.toString().equalsIgnoreCase("setSourceAddress")||
					node.toString().equalsIgnoreCase("setOrigin")||
					node.toString().equalsIgnoreCase("setBody")||
					node.toString().equalsIgnoreCase("setProtocol")||
					node.toString().equalsIgnoreCase("setDestinationAddress")||
					node.toString().equalsIgnoreCase("setDestinationPort")||
					node.toString().equalsIgnoreCase("setMailDestination")||
					node.toString().equalsIgnoreCase("setMailSource")||
					node.toString().equalsIgnoreCase("setOrigBody")||
					node.toString().equalsIgnoreCase("setSourcePort")||
					node.toString().equalsIgnoreCase("setUrl")
					) {
				this.keywords.add(node.toString());
				level++;
			}
		}
		if(level==2 && node.toString().equalsIgnoreCase("Constants")) {
			this.getKeywords().add(node.toString());
			level++;
			return true;
		}
		if(level==3 && this.keywords.get(2).equals("Constants")) {
			this.keywords.add(node.toString());
			level++;
		}
		return super.visit(node);
	}
	


	@Override
	public boolean visit(VariableDeclarationStatement node) { //it inserts into keywords the packet creation
		VariableDeclarationFragment vdf = (VariableDeclarationFragment) node.fragments().get(0);
		if(vdf.getInitializer()!=null) {
			if(vdf.getInitializer().toString().equalsIgnoreCase("new Packet()")) {
				this.keywords.add(vdf.getName().toString());
				this.keywords.add(vdf.getInitializer().toString());
				level++;
			}
		}
		return super.visit(node);
	}



	public ArrayList<String> getKeywords() {
		return keywords;
	}

	
}
