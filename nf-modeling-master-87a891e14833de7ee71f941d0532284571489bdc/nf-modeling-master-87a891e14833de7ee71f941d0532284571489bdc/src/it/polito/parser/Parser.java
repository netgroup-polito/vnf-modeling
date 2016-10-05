package it.polito.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser {
	
	private String fileName;
	
	public Parser(String fileName) {
		this.fileName = fileName;
	}
	
	private void parse() throws Exception {
		//System.out.println(this.fileName);
		BufferedReader reader = null;
		char[] source = null;
		try{
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			String classCode = "";
			while(true)
			{
				line = reader.readLine();
				if(line == null)
					break;
				classCode = classCode.concat(line + "\n");	// !!! We append \n in order for the getLineNumber() method to work properly !!!
			}
			source = classCode.toCharArray();
			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("[ERROR] Unable to load file!");
			System.exit(-2);
		}
		ASTParser parser = ASTParser.newParser(AST.JLS3);  // handles JDK 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setSource(source);
		NFdefinition nfd = new NFdefinition(); //it will contain all the information about the nf
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		ClassVisitor v = new ClassVisitor(compilationUnit,nfd);
		compilationUnit.accept(v); //start the parsing
		//System.out.println("\n Right form implication");
		createRightFormImplication(nfd); //it creates the rfi and writes the nfClass
		//stampa(nfd); //it shows all the extracted information about the nfd 
		if(v.isFunctionProcessed())
			System.out.println("!All Done!");
		else
			throw new ParserException("!Main NF method not available!");
	}
	
	private void createRightFormImplication(NFdefinition nfd) throws Exception {
		for(Implication impl : nfd.getImplication()) { //check the number of store condition
			int storeNumber =0;
			for(Condition c : impl.getThenConditions()) {
				if(c.getType()==ConditionType.store_in_table) {
					storeNumber++;
				}
			}
			if(storeNumber>1) {
				throw new Exception("Only one store instruction for one if-statement");
			}
		}
		for(Implication impl : nfd.getImplication()) { //it creates the basic form of the implication
			ArrayList<Condition> ic = impl.getIfConditions();
			ArrayList<Condition> tc = impl.getThenConditions();
			String returnPacket = ((ReturnCondition)(tc.get(tc.size()-1))).getPacket();
			String returnDirection = ((ReturnCondition)(tc.get(tc.size()-1))).getDirection();
			RightFormImplication rfi = new RightFormImplication();
			rfi.addBeforeImplicationCondition(new SendCondition(nfd.getName(),"n_0",returnPacket,"t_2",true)); //std for all the rfi
			if(returnDirection.equalsIgnoreCase("SAME_INTERFACE")) {
				rfi.addAfterImplicationCondition(new RecvCondition("n_0",nfd.getName(),"t_1","packet",true));
			}
			else {
				rfi.addAfterImplicationCondition(new RecvCondition("n_source",nfd.getName(),"t_1","packet",true));
			}
			for(Condition c : ic) { //add all the if block condition after the implication(they are all state condition and input packet condition)
				rfi.addAfterImplicationCondition(c);
			}
			for(Condition c : tc) {
				switch(c.getType()) {
				case internal_node:
					break;
				case is_in_table:
					break;
				case node_has_addr:
					rfi.addAfterImplicationCondition(c);
					break;
				case packet_body:
					rfi.addAfterImplicationCondition(c);
					break;
				case packet_destination:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_email_from:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_email_to:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_origin:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_port_destination:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_port_source:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_protocol:
					if(nfd.getImplication().size()>1 &&((ProtocolCondition)c).getPacket().equalsIgnoreCase(returnPacket) && (((ProtocolCondition)c).getProtocol().contains("Constants")||((ProtocolCondition)c).getProtocol().contains("hostPropertyList"))) {
						if((nfd.getImplication().size())>2 || nfd.getInternalNodesPresence()==false) {
							rfi.addAfterImplicationCondition(c);
						}
					}
					else {
						rfi.addAfterImplicationCondition(c);
					}
					break;
				case packet_source:
						rfi.addAfterImplicationCondition(c);
					break;
				case packet_url:
						rfi.addAfterImplicationCondition(c);
					break;
				case recv:
					//rfi.addAfterImplicationCondition(c);
					break;
				case return_type:
					break;
				case send:
					break;
				case store_in_table:
					rfi.addAfterImplicationCondition(c);
					break;
				default:
					break;
				}
			}
			/**/
			boolean newImpl = false;
			Condition t=null;
			for(Condition c : rfi.getAfterImplication()) {
				if(c.type==ConditionType.store_in_table) {
					newImpl = true;
					t = c;
					String name = ((StoreTableCondition)c).getTableName();
					nfd.getHostTableStatic().put(name.substring(1, name.length()-1), false); //if there is a store instruction on a table it is not static  
				}
			}
			if(newImpl==true) { //for the store create a new implication
				rfi.getAfterImplication().remove(t); //remove the store instruction from the previous implication
				RightFormImplication rfi2 = new RightFormImplication();
				rfi2.addBeforeImplicationCondition(t);
				int i=0;
				for(Condition c : rfi.getBeforeImplication()) {
					if(i!=0) { //jump the send condition
						rfi2.addAfterImplicationCondition(c);
					}
					i++;
				}
				for(Condition c : rfi.getAfterImplication()) {
					rfi2.addAfterImplicationCondition(c);
				}
				nfd.getRightFormImplication().add(rfi2); //add the implication for the store
			}
			if(newImpl==true) {
				continue;
			}
			/**/
			String[] parts;
			if(nfd.getImplication().size()>1) {
			for(Condition c : rfi.getAfterImplication()) { //check the condition to find all of them that regard only the output packet (also extracted from other conditions of other packets) and put them after the implication
				switch(c.type) {
				case internal_node:
					parts = ((InternalNodeCondition)c).getNode().split("[.]");
					if(parts.length>=2 && parts[0].equalsIgnoreCase(returnPacket)) {
						rfi.addBeforeImplicationCondition(c);
						break;
					}
					if(parts.length>=2) {
						if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
							for(Condition c1 : rfi.getAfterImplication()) {
								if(c1.type==ConditionType.packet_source) {
									if(((SourceCondition)c1).getPacket().equalsIgnoreCase(returnPacket) && ((SourceCondition)c1).getSource().equalsIgnoreCase(parts[0]+".getDestinationAddress")) {
										rfi.addBeforeImplicationCondition(new InternalNodeCondition(returnPacket+".getSourceAddress",c.getValidity()));
									}
								}
								if(c1.type==ConditionType.packet_destination) {
									if(((DestinationCondition)c1).getPacket().equalsIgnoreCase(returnPacket) && ((DestinationCondition)c1).getDestination().equalsIgnoreCase(parts[0]+".getDestinationAddress")) {
										rfi.addBeforeImplicationCondition(new InternalNodeCondition(returnPacket+".getDestinationAddress",c.getValidity()));
									}
								}
							}
						}
						if(parts[1].equalsIgnoreCase("getSourceAddress")) {
							for(Condition c1 : rfi.getAfterImplication()) {
								if(c1.type==ConditionType.packet_source) {
									if(((SourceCondition)c1).getPacket().equalsIgnoreCase(returnPacket) && ((SourceCondition)c1).getSource().equalsIgnoreCase(parts[0]+".getSourceAddress")) {
										rfi.addBeforeImplicationCondition(new InternalNodeCondition(returnPacket+".getSourceAddress",c.getValidity()));
									}
								}
								if(c1.type==ConditionType.packet_destination) {
									if(((DestinationCondition)c1).getPacket().equalsIgnoreCase(returnPacket) && ((DestinationCondition)c1).getDestination().equalsIgnoreCase(parts[0]+".getSourceAddress")) {
										rfi.addBeforeImplicationCondition(new InternalNodeCondition(returnPacket+".getDestinationAddress",c.getValidity()));
									}
								}
							}
						}
					}
					break;
				case packet_protocol:
					if(((ProtocolCondition)c).getPacket().equalsIgnoreCase(returnPacket) && ((ProtocolCondition)c).getProtocol().contains("Constants")) {
						if((nfd.getImplication().size())>2 || nfd.getInternalNodesPresence()==false) {
							rfi.addBeforeImplicationCondition(c);
						}
						break;
					}
					if(((ProtocolCondition)c).getProtocol().contains("Constants")) {
						for(Condition c1 : rfi.getAfterImplication()) {
							if(c1.type==ConditionType.packet_protocol) {
								if(((ProtocolCondition)c1).getPacket().equalsIgnoreCase(returnPacket) && ((ProtocolCondition)c1).getProtocol().equalsIgnoreCase(((ProtocolCondition)c).getPacket()+".getProtocol")) {
									if((nfd.getImplication().size())>2 || nfd.getInternalNodesPresence()==false) {
										rfi.addBeforeImplicationCondition(new ProtocolCondition(returnPacket,((ProtocolCondition)c).getProtocol(),c.getValidity()));
									}
								}
							}
						}
					}
					break;
				case is_in_table:
					break;
				case node_has_addr:
					break;
				case packet_body:
					break;
				}
			}
			}
			nfd.getRightFormImplication().add(rfi); //add the rfi
		}
		checkOrCondition(nfd); //check if there are two imlications that have a condition in OR
		if(nfd.getPacketDefinition().size()>0) { //create the implication for packetDefintion
			RightFormImplication rfi = new RightFormImplication();
			rfi.addBeforeImplicationCondition(nfd.getPacketDefinition().get(0).getPacketCondition().get(0)); //std(recv)
			rfi.addAfterImplicationCondition(nfd.getPacketDefinition().get(0).getPacketCondition().get(1)); //std(NodeHasAddress)
			nfd.getRightFormImplication().add(rfi);
			rfi = new RightFormImplication();
			rfi.addBeforeImplicationCondition(nfd.getPacketDefinition().get(1).getPacketCondition().get(0)); //std(send)
			for(int i=1;i<nfd.getPacketDefinition().get(1).getPacketCondition().size();i++) {
				rfi.addAfterImplicationCondition(nfd.getPacketDefinition().get(1).getPacketCondition().get(i));
			}
			nfd.getRightFormImplication().add(rfi);
		}
		CreateNFClass cnf = new CreateNFClass(nfd); //it creates the writer class of the nf
		int num_hostT_dyn = 0;
		for(Boolean bol : nfd.getHostTableStatic().values()) { //count the dynamic hostTable
			if(bol==false) {
				num_hostT_dyn++;
			}
		}

		for(RightFormImplication d : nfd.getRightFormImplication()) {
			Condition condProto=null;
			Condition condIntN=null;
			for(Condition c : d.getBeforeImplication()) { //check if there are packetProtocol or internal node condition before the implication
				if(c.getType()==ConditionType.packet_protocol) {
					condProto = c;
				}
				if(c.getType()==ConditionType.internal_node) {
					condIntN = c;
				}
			}
			if(condIntN!=null && condProto!=null && nfd.getImplication().size()-num_hostT_dyn<=2) { //if number of implications(excluding store implications) is less or equal then 2, and if there is a internal node condition, remove the protocol condition
				d.getBeforeImplication().remove(condProto);
				d.getAfterImplication().add(condProto);
			}
		}
		HashMap<String,String> protoHM = new HashMap<String, String>(); //it will contain all the protocol used
		ArrayList<String> sendHM = new ArrayList<String>(); //it will contain all the output packet name
		for(RightFormImplication d : nfd.getRightFormImplication()) {
			for(Condition c : d.getBeforeImplication()) {
				if(c.getType()==ConditionType.send) {
					sendHM.add(((SendCondition)c).getPacket());
				}
				if(c.getType()==ConditionType.packet_protocol && ((ProtocolCondition)c).getProtocol().contains("Constants")) {
					protoHM.put(((ProtocolCondition)c).getPacket(), ((ProtocolCondition)c).getProtocol());
				}
			}
			for(Condition c : d.getAfterImplication()) {
				if(c.getType()==ConditionType.send) {
					sendHM.add(((SendCondition)c).getPacket());
				}
				if(c.getType()==ConditionType.packet_protocol && ((ProtocolCondition)c).getProtocol().contains("Constants")) {
					protoHM.put(((ProtocolCondition)c).getPacket(), ((ProtocolCondition)c).getProtocol());
				}
			}
			for(String s : sendHM) {
				if(protoHM.keySet().contains(s)) {
					nfd.getProtocolHM().put(protoHM.get(s), s); //insert the protocol used for the output packet
				}
			}
		}
		//System.out.println(nfd.getProtocolHM());
		
		if(nfd.hasPrivateAddresses()) {
			changeCondition(nfd); //if the nf don't use private addresses change the address into node
		}
		//cnf.writeClass("..\\j-verigraph-master-f97f40b5cc458603778df79b806cb0c5d641352b\\service\\src\\mcnet\\netobjs\\NF"); //write the nf class
		cnf.writeClass(".."+File.separator+"j-verigraph-master-f97f40b5cc458603778df79b806cb0c5d641352b"+File.separator+"service"+File.separator+"src"+File.separator+"mcnet"+File.separator+"netobjs"+File.separator+"NF"); //write the nf class
	}

	private void changeCondition(NFdefinition nfd) {
		for(RightFormImplication rfi : nfd.getRightFormImplication()) {
			for(Condition c : rfi.getBeforeImplication()) {
				if(c.getType() == ConditionType.internal_node) {
					String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
					if(parts.length==2) {
						String packet = parts[0];
						String direction = parts[1];
						boolean find = false;
						for(Condition c2 : rfi.getBeforeImplication()) {
							if(c2.getType() == ConditionType.send && direction.equalsIgnoreCase("getDestinationAddress") && ((SendCondition)c2).getPacket().equalsIgnoreCase(packet)) {
								find = true;
								((InternalNodeCondition)c).setNode(((SendCondition)c2).getDestination());
								break;
							}
							if(c2.getType() == ConditionType.recv && direction.equalsIgnoreCase("getSourceAddress") && ((RecvCondition)c2).getPacket().equalsIgnoreCase(packet)) {
								find = true;
								((InternalNodeCondition)c).setNode(((RecvCondition)c2).getSourceNode());
								break;
							}
						}
					}
					
				}
			}
			for(Condition c : rfi.getAfterImplication()) {
				if(c.getType() == ConditionType.internal_node) {
					String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
					if(parts.length==2) {
						//System.out.println(parts[0]);
						String packet = parts[0];
						String direction = parts[1];
						boolean find = false;
						for(Condition c2 : rfi.getAfterImplication()) {
							if(c2.getType() == ConditionType.send && direction.equalsIgnoreCase("getDestinationAddress") && ((SendCondition)c2).getPacket().equalsIgnoreCase(packet)) {
								find = true;
								((InternalNodeCondition)c).setNode(((SendCondition)c2).getDestination());
								break;
							}
							if(c2.getType() == ConditionType.recv && direction.equalsIgnoreCase("getSourceAddress") && ((RecvCondition)c2).getPacket().equalsIgnoreCase(packet)) {
								find = true;
								((InternalNodeCondition)c).setNode(((RecvCondition)c2).getSourceNode());
								break;
							}
						}
						if(find==false) {
							for(Condition c2 : rfi.getBeforeImplication()) {
								if(c2.getType() == ConditionType.send && direction.equalsIgnoreCase("getDestinationAddress") && ((SendCondition)c2).getPacket().equalsIgnoreCase(packet)) {
									find = true;
									((InternalNodeCondition)c).setNode(((SendCondition)c2).getDestination());
									break;
								}
								if(c2.getType() == ConditionType.recv && direction.equalsIgnoreCase("getSourceAddress") && ((RecvCondition)c2).getPacket().equalsIgnoreCase(packet)) {
									find = true;
									((InternalNodeCondition)c).setNode(((RecvCondition)c2).getSourceNode());
									break;
								}
							}
						}
					}
					
				}
			}
		}
		
	}
	
	//eliminate one of the 2 conditions that have a condition thet can be in or, and add it to other implication with the number of condition within it is in or
	private void checkOrCondition(NFdefinition nfd) {  
		int d1=-1;
		int d2=-1;
		RightFormImplication deleteRFI = null;
		for(RightFormImplication rfi : nfd.getRightFormImplication()) {
			for(RightFormImplication rfi2 : nfd.getRightFormImplication()) {
				if(rfi!=rfi2) {
					if(sameBeforeCondition(rfi,rfi2)) { //check if the before conditions are equal
						d1 = similarAfterCondition(rfi,rfi2); //extract the number of the condition in or in the 2 implications
						d2 = similarAfterCondition(rfi2,rfi);
						if(d1!=-1 && d2!=-1) { //if there is one condition, eliminate one implication (store the pointer) and modify the other
							if(rfi.getAfterImplication().get(d1-1).getType() == rfi2.getAfterImplication().get(d2-1).getType()) {
								rfi.getAfterImplication().add(rfi2.getAfterImplication().get(d2-1));
								deleteRFI = rfi2;
								rfi.setOrCondition(d1-1);
							}
						}
					}
				}
			}
		}
		if(deleteRFI!=null) {
			nfd.getRightFormImplication().remove(deleteRFI); //eliminate the condition
		}
	}

	private int similarAfterCondition(RightFormImplication rfi, RightFormImplication rfi2) {
		int conditionNumber=0;
		int i = 0;
		ArrayList<Condition> d = new ArrayList<Condition>();
		if(rfi.getAfterImplication().size()!=rfi2.getAfterImplication().size()) {
			return -1;
		}
		for(Condition c : rfi.getAfterImplication()) {
			i++;
			boolean trovato = false;
			for(Condition c1 : rfi2.getAfterImplication()) {
				if(c.sameCondition(c1)) {
					trovato = true;
				}
			}
			if(trovato == false) {
				d.add(c);
				conditionNumber=i;
			}
		}
		if(d.size()==1) {
			return conditionNumber;
		}
		return -1;
	}

	private boolean sameBeforeCondition(RightFormImplication rfi, RightFormImplication rfi2) {
		boolean equal = true;
		for(Condition c : rfi.getBeforeImplication()) {
			boolean trovato = false;
			for(Condition c1 : rfi2.getBeforeImplication()) {
				if(c.sameCondition(c1)) {
					trovato = true;
				}
			}
			if(trovato == false) {
				equal = false;
			}
		}
		for(Condition c : rfi2.getBeforeImplication()) {
			boolean trovato = false;
			for(Condition c1 : rfi.getBeforeImplication()) {
				if(c.sameCondition(c1)) {
					trovato = true;
				}
			}
			//System.out.println("trovato:"+trovato);
			if(trovato == false) {
				equal = false;
			}
		}
		return equal;
	}

	private void stampa(NFdefinition nfd) { //print the nf features 
		System.out.println("\n");
		System.out.println("Nome classe: "+nfd.getName());
		System.out.println("End Host: "+nfd.isEndHost());
		System.out.println("Presente internal nodes: "+nfd.getInternalNodesPresence());
		System.out.println("Presenti indirizzi privati: "+nfd.hasPrivateAddresses());
		System.out.println("Property list: "+nfd.getPropertyList());
		System.out.println("Host table list: "+nfd.getHostTableList());
		System.out.println("Host table list static: "+nfd.getHostTableStatic());
//		System.out.println("Variable list: "+nfd.getVariableList());
		System.out.println("\n");
		
		for(int i = 0; i<nfd.getRightFormImplication().size();i++) {
			System.out.println("\n");
			
			System.out.println("Before implication condition");
			System.out.println("---------------------------------");
			for(int j=0; j<nfd.getRightFormImplication().get(i).getBeforeImplication().size();j++) {
				nfd.getRightFormImplication().get(i).getBeforeImplication().get(j).stampa();
				System.out.println("---------------------------------");
			}
			System.out.println("After implication condition");
			System.out.println("---------------------------------");
			for(int j=0; j<nfd.getRightFormImplication().get(i).getAfterImplication().size();j++) {
				nfd.getRightFormImplication().get(i).getAfterImplication().get(j).stampa();
				System.out.println("---------------------------------");
			}
			if(nfd.getRightFormImplication().get(i).getOrCondition()!=-1) {
				System.out.println("Last condition is in OR with condition :"+nfd.getRightFormImplication().get(i).getOrCondition());
			}
		}

	}

	public static void main(String[] args) throws IOException {
		if(args.length != 1)
		{
			System.err.println("Usage: java MyTest <NF_path>");
			System.exit(-1);
		}
		System.out.println("Reading VNF model...");
		
		Parser parser = new Parser(args[0]); //pass the name of the source file
		try {
			parser.parse(); //start the parsing
		} catch (ParserException e) {
			System.err.println("Parsing failed!");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Parsing failed!");
			e.printStackTrace();
		}
	}

}
