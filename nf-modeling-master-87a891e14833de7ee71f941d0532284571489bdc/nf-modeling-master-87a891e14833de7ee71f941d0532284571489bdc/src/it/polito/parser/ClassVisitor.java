package it.polito.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class ClassVisitor extends ASTVisitor {
	
	private boolean isFunctionProcessed;
	private CompilationUnit result;
	NFdefinition nfd;
	
	public ClassVisitor(CompilationUnit result, NFdefinition nfd) {
		this.result = result;
		this.isFunctionProcessed = false;
		this.nfd = nfd;
	}

	public boolean isFunctionProcessed() {
		return isFunctionProcessed;
	}
	
	@Override
	public boolean visit(FieldDeclaration node) { //insert into the nfd the variables declared into the definition of the nf
		VariableDeclarationFragment vdf = (VariableDeclarationFragment) node.fragments().get(0);
		if(vdf.getInitializer()!=null)
			nfd.setVariable(vdf.getName().toString(), vdf.getInitializer().toString());
		else
			nfd.setVariable(vdf.getName().toString(), null);
		return true;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) { //insert the name and the type of nf
		System.out.println("Class name -> " + node.getName());
		nfd.setName(node.getName().toString());
		System.out.println("Type of node -> "+node.getSuperclassType());
		if(node.getSuperclassType().toString().equalsIgnoreCase("EndHost") || node.getSuperclassType().toString().equalsIgnoreCase("definitions.EndHost")) {
			nfd.setEndHost(true);
		}
		return true;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) { //inspects the 3 methods
		String methodName = node.getName().toString();
		if(methodName.equalsIgnoreCase(Constants.STATE_DEFINITION_METHOD)) {
			System.out.println("Found method -> " + node.getName());
			@SuppressWarnings("unchecked")
			List<Statement> stmts = (List<Statement>)node.getBody().statements();
			for(Statement s : stmts)
			{
				DefineStateVisitor dsv = new DefineStateVisitor(this.nfd); //inspect the state definition's statement and create the keyword
				s.accept(dsv);
				if(dsv.getKeywords().size()==1 && dsv.getKeywords().get(0).equalsIgnoreCase("this.state.internalNodes.set()") ) {
					nfd.setInternalNodesPresence(true);
				}
				if(dsv.getKeywords().size()==1 && dsv.getKeywords().get(0).equalsIgnoreCase("this.state.internalNodes.setWithoutPrivateAddresses()") ) {
					nfd.setInternalNodesPresence(true);
					nfd.setPrivateAddresses(true);
				}
				if(dsv.getKeywords().size()==1 && dsv.getKeywords().get(0).equalsIgnoreCase("this.state.internalNodes.setWithPrivateAddresses()") ) {
					nfd.setInternalNodesPresence(true);
					nfd.setPrivateAddresses(false);
				}
				if(dsv.getKeywords().size()==3 && dsv.getKeywords().get(0).equalsIgnoreCase("hostPropertyList") ) {
					nfd.addProperty(dsv.getKeywords().get(2).substring(1, dsv.getKeywords().get(2).length()-1)); //it adds the property deleting the quotation marks
				} 
				if(dsv.getKeywords().size()>=7 && dsv.getKeywords().get(0).equalsIgnoreCase("hostTableList")) {
					nfd.addHostTable(dsv.getKeywords().get(4).substring(1, dsv.getKeywords().get(4).length()-1), dsv.getKeywords().subList(6, dsv.getKeywords().size())); //it adds the hostTable without the quotation marks and all its fields
					nfd.addHostTableStatic(dsv.getKeywords().get(4).substring(1, dsv.getKeywords().get(4).length()-1), true); //it initializes the hostTable as static
				} 
			}
			return true;
		}
		if(methodName.equalsIgnoreCase(Constants.PACKET_DEFINITION_METHOD)) {
			System.out.println("Found method -> " + node.getName());
			@SuppressWarnings("unchecked")
			List<Statement> stmts = (List<Statement>)node.getBody().statements();
			ArrayList<String> packetName=new ArrayList<String>();
			ArrayList<ArrayList<String>> operations = new ArrayList<ArrayList<String>>();
			for(Statement s : stmts)
			{
				PacketDefinitionVisitor pdv = new PacketDefinitionVisitor(this.nfd);
				s.accept(pdv); //inspect the packet_definition's statement
				if(pdv.getKeywords().size()==2 && pdv.getKeywords().get(1).equals("new Packet()"))
					packetName.add(pdv.getKeywords().get(0));
				if(packetName.size()>1) {
					System.out.println("Error: define only one packet");
				}
				if(pdv.getKeywords().size()==3 && 
						pdv.getKeywords().get(0).equalsIgnoreCase(packetName.get(0)) && 
						pdv.getKeywords().get(2).equalsIgnoreCase("this.address")) {
					ArrayList<String> t = new ArrayList<String>();
					t.add(pdv.getKeywords().get(1)); //insert field packetName this address (like setSourceAddress-p-this-address)
					t.add(pdv.getKeywords().get(0));
					t.add("this");
					t.add("address");
					operations.add(t);
				}
				if(pdv.getKeywords().size()==4 && 
						pdv.getKeywords().get(0).equalsIgnoreCase(packetName.get(0)) && 
						pdv.getKeywords().get(2).equalsIgnoreCase(packetName.get(0))) {
					ArrayList<String> t = new ArrayList<String>();
					t.add(pdv.getKeywords().get(1)); //insert field packetName packetName field (like setSourceAddress-p-p-getSourceAddress)
					t.add(pdv.getKeywords().get(0));
					t.add(pdv.getKeywords().get(2));
					t.add(pdv.getKeywords().get(3));
					operations.add(t);
				}
				if(pdv.getKeywords().size()==4 && 
						pdv.getKeywords().get(0).equalsIgnoreCase(packetName.get(0)) && 
						pdv.getKeywords().get(2).equalsIgnoreCase("Constants")) {
					ArrayList<String> t = new ArrayList<String>();
					t.add(pdv.getKeywords().get(1)); //insert field packetName Constants element (like setProtocol-p-Constants-HTTP_REQUEST_PROTOCOL)
					t.add(pdv.getKeywords().get(0));
					t.add(pdv.getKeywords().get(2));
					t.add(pdv.getKeywords().get(3));
					operations.add(t);
				}
				if(pdv.getKeywords().size()==5 && 
						pdv.getKeywords().get(0).equalsIgnoreCase(packetName.get(0)) && 
						pdv.getKeywords().get(2).equalsIgnoreCase("hostPropertyList") &&
						pdv.getKeywords().get(3).equalsIgnoreCase("get")) {
					ArrayList<String> t = new ArrayList<String>();
					t.add(pdv.getKeywords().get(1)); //insert field packetName hostPropertyList propertyName
					t.add(pdv.getKeywords().get(0));
					t.add(pdv.getKeywords().get(2));
					t.add(pdv.getKeywords().get(4));
					operations.add(t);
				}
				
			}
			PacketDefinition pd = new PacketDefinition();
			if(packetName.size()==0)
				return true; //at least one packet must be definined
			pd.addPacketCondition(new SendCondition(nfd.getName(),"n_0",packetName.get(0),"t_2",true)); //std for packetDefinition
			for(ArrayList<String> operation : operations) { //create the packet definition condition
				switch(operation.get(0)) {
					case "setSourceAddress": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							pd.addPacketCondition(new NodeAddressCondition(nfd.getName(),packetName.get(0)+".getSourceAddress",true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new NodeAddressCondition(nfd.getName(),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
					case "setOrigin": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							pd.addPacketCondition(new OriginCondition(packetName.get(0),nfd.getName(),true)); 
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new OriginCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true)); 
							break;
						}
						break;
					case "setBody":
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							pd.addPacketCondition(new BodyCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							
							break;
						}
						break;
					case "setProtocol": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							pd.addPacketCondition(new ProtocolCondition(packetName.get(0),"Constants."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							
							break;
						}
						break;
					case "setDestinationAddress": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new DestinationCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
					case "setDestinationPort": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							pd.addPacketCondition(new DestPortCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new DestPortCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
					case "setMailDestination": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							pd.addPacketCondition(new EmailToCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new EmailToCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
					case "setMailSource": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							pd.addPacketCondition(new EmailFromCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new EmailFromCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
					case "setOrigBody": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							//pd.addPacketCondition(new OriginBodyCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							
							break;
						}
						break;
					case "setSourcePort": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							pd.addPacketCondition(new SourcePortCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new SourcePortCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
					case "setUrl": 
						if(operation.get(2).equalsIgnoreCase("this")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase("Constants")) {
							
							break;
						}
						if(operation.get(2).equalsIgnoreCase(packetName.get(0))) {
							pd.addPacketCondition(new UrlCondition(packetName.get(0),operation.get(2)+"."+operation.get(3),true));
							break;
						}
						if(operation.get(2).equalsIgnoreCase("hostPropertyList")) {
							pd.addPacketCondition(new UrlCondition(packetName.get(0),"hostPropertyList."+operation.get(3),true));
							break;
						}
						break;
				}
			}
			PacketDefinition pd1 = new PacketDefinition();
			pd1.addPacketCondition(new RecvCondition("n_0",nfd.getName(),"t_2",packetName.get(0),true)); //std for packetDefinition
			pd1.addPacketCondition(new NodeAddressCondition(nfd.getName(),packetName.get(0)+".getDestinationAddress",true)); //std for packetDefinition
			nfd.addPacketDefinition(pd1); //add the conditions of the packetDefinition to nfd
			nfd.addPacketDefinition(pd);
			return true;
		}
		if(methodName.equalsIgnoreCase(Constants.MAIN_NF_METHOD)) {
			String packetName=null;
			isFunctionProcessed = true;
			System.out.println("Found method -> " + node.getName());
			@SuppressWarnings("unchecked")
			List<SingleVariableDeclaration> l = node.parameters();
			if(l.size() != 1)
			{
				System.err.println("[ERROR] The input parameter of the "+ Constants.MAIN_NF_METHOD + "() method must be named "+Constants.PACKET_NAME+"!");
				return false;
			}
			//System.out.println("\tFound parameter -> " + l.get(0).getName());
			packetName = l.get(0).getName().toString();
			node.accept(new MethodVisitor()); //inutile
			System.out.println("\tParsing model...");
			int counter = 0;
			StatementVisitor myStmtVisitor = null;
			@SuppressWarnings("unchecked")
			ArrayList<IfBlockConditions> ibcList = new ArrayList<IfBlockConditions>();
			List<Statement> stmts = (List<Statement>)node.getBody().statements();
			for(Statement s : stmts)
			{
				ReceivedPacketVisitor rpv = new ReceivedPacketVisitor(ibcList,this.nfd);
				s.accept(rpv); //inspect the receivedPacket statement 
				counter++;
			}
			for(IfBlockConditions ibc : ibcList) {
				CreateImplication ci = new CreateImplication(ibc,this.nfd); //create the implication from the ibc
			}
			if(myStmtVisitor != null)
				System.out.println("\tSkipped "+myStmtVisitor.getSkippedActions()+" DROP action(s).\n");
			return true;
		}
		System.out.println("\tParsing done!\n");
		return true;
	}
}
