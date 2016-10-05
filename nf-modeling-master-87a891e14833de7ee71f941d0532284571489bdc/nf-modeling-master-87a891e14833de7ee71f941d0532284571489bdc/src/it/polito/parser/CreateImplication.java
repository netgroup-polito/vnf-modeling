package it.polito.parser;

import java.util.ArrayList;

public class CreateImplication {
	Implication implication = new Implication();
	private IfBlockConditions ibc;
	private NFdefinition nfd;
	int node_counter=1; //every recv and send are related to a different node

	public CreateImplication(IfBlockConditions ibc, NFdefinition nfd) { //it creates the condition for if and then parts starting from the ibc
		this.ibc = ibc;
		this.nfd = nfd;
		for(ArrayList<String> c : ibc.getIfConditions()) {
			manageIfCondition(c); //create the condition
		}
		for(ArrayList<String> c : ibc.getThenConditions()) {
			manageThenCondition(c); //create the condition
		}
		nfd.addImplication(implication);
	}
	
	private void manageThenCondition(ArrayList<String> c) {
		if(c.size()>2) {
		ArrayList<String> t;
		switch(c.get(1)) {
			case "setSourceAddress": 
				if(c.get(2).equalsIgnoreCase("address") && c.size()==3) {
					implication.addThenCondition(new SourceCondition(c.get(0),nfd.getName(),true));
					break;
				}
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new SourceCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new SourceCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setOrigin": 
				if(c.get(2).equalsIgnoreCase("address") && c.size()==3) {
					implication.addThenCondition(new OriginCondition(c.get(0),nfd.getName(),true));
					break;
				}
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new OriginCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new OriginCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setBody":
				implication.addThenCondition(new BodyCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setProtocol": 
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new ProtocolCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new ProtocolCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setDestinationAddress": 
				if(c.get(2).equalsIgnoreCase("address") && c.size()==3) {
					implication.addThenCondition(new DestinationCondition(c.get(0),nfd.getName(),true));
					break;
				}
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new DestinationCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new DestinationCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setDestinationPort": 
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new DestPortCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new DestPortCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setMailDestination": 
				if(c.get(2).equalsIgnoreCase("address") && c.size()==3) {
					implication.addThenCondition(new EmailToCondition(c.get(0),nfd.getName(),true));
					break;
				}
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new EmailToCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new EmailToCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setMailSource": 
				if(c.get(2).equalsIgnoreCase("address") && c.size()==3) {
					implication.addThenCondition(new EmailFromCondition(c.get(0),nfd.getName(),true));
					break;
				}
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new EmailFromCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new EmailFromCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setOrigBody": 
				//implication.addThenCondition(new OriginBodyCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setSourcePort": 
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new SourcePortCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new SourcePortCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "setUrl": 
				if(c.size()==6 && c.get(3).equalsIgnoreCase("hostPropertyList")) {
					implication.addThenCondition(new UrlCondition(c.get(0),c.get(3)+"."+c.get(5),true));
					break;
				}
				implication.addThenCondition(new UrlCondition(c.get(0),c.get(2)+"."+c.get(3),true));
				break;
			case "hostTableList":
				t = new ArrayList<String>();
				for(int i=5;i<c.size();i=i+2) {
					t.add(c.get(i)+"."+c.get(i+1));
				}
				implication.addThenCondition(new StoreTableCondition(c.get(3),t,true));
				break;
			}
			if(c.get(0).equalsIgnoreCase("return")) {
				this.implication.addThenCondition(new ReturnCondition(c.get(1),c.get(2),true));
			}
		}
	}

	private void manageIfCondition(ArrayList<String> c) {
		switch(c.get(0)) {
		case "match":
			switch(c.get(2)) {
			case "getSourceAddress": 
				if(c.get(3).contains("Constants")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new SourceCondition(c.get(1),c.get(3),false));
						break;
					}
					else {
						this.implication.addIfCondition(new SourceCondition(c.get(1),c.get(3),true));
						break;
					}
				}
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new SourceCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new SourceCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(3).equalsIgnoreCase("address")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new SourceCondition(c.get(1),this.nfd.getName(),false));
						break;
					}
					else {
						this.implication.addIfCondition(new SourceCondition(c.get(1),this.nfd.getName(),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new SourceCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new SourceCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getOrigin": 
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new OriginCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new OriginCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(3).equalsIgnoreCase("address")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new OriginCondition(c.get(1),this.nfd.getName(),false));
						break;
					}
					else {
						this.implication.addIfCondition(new OriginCondition(c.get(1),this.nfd.getName(),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new OriginCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new OriginCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getBody":
				
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new BodyCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new BodyCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getProtocol": 
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new ProtocolCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new ProtocolCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(3).contains("Constants")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new ProtocolCondition(c.get(1),c.get(3),false));
						break;
					}
					else {
						this.implication.addIfCondition(new ProtocolCondition(c.get(1),c.get(3),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new ProtocolCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new ProtocolCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getDestinationAddress": 
				if(c.get(3).contains("Constants")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new DestinationCondition(c.get(1),c.get(3),false));
						break;
					}
					else {
						this.implication.addIfCondition(new DestinationCondition(c.get(1),c.get(3),true));
						break;
					}
				}
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new DestinationCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new DestinationCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(3).equalsIgnoreCase("address")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new DestinationCondition(c.get(1),this.nfd.getName(),false));
						break;
					}
					else {
						this.implication.addIfCondition(new DestinationCondition(c.get(1),this.nfd.getName(),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new DestinationCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new DestinationCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getDestinationPort": 
				if(c.get(3).contains("Constants")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new DestPortCondition(c.get(1),c.get(3),false));
						break;
					}
					else {
						this.implication.addIfCondition(new DestPortCondition(c.get(1),c.get(3),true));
						break;
					}
				}
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new DestPortCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new DestPortCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new DestPortCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new DestPortCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getMailDestination": 
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new EmailToCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new EmailToCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(3).equalsIgnoreCase("address")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new EmailToCondition(c.get(1),this.nfd.getName(),false));
						break;
					}
					else {
						this.implication.addIfCondition(new EmailToCondition(c.get(1),this.nfd.getName(),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new EmailToCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new EmailToCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getMailSource": 
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new EmailFromCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new EmailFromCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(3).equalsIgnoreCase("address")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new EmailFromCondition(c.get(1),this.nfd.getName(),false));
						break;
					}
					else {
						this.implication.addIfCondition(new EmailFromCondition(c.get(1),this.nfd.getName(),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new EmailFromCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new EmailFromCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getOrigBody": 
				
				break;
			case "getSourcePort": 
				if(c.get(3).contains("Constants")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new SourcePortCondition(c.get(1),c.get(3),false));
						break;
					}
					else {
						this.implication.addIfCondition(new SourcePortCondition(c.get(1),c.get(3),true));
						break;
					}
				}
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new SourcePortCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new SourcePortCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new SourcePortCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new SourcePortCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			case "getUrl": 
				if(c.size()>4 && c.get(4).equalsIgnoreCase("hostPropertyList")) {
					if(c.get(c.size()-1).equalsIgnoreCase("not")) {
						this.implication.addIfCondition(new UrlCondition(c.get(1),c.get(4)+"."+c.get(6),false));
						break;
					}
					else {
						this.implication.addIfCondition(new UrlCondition(c.get(1),c.get(4)+"."+c.get(6),true));
						break;
					}
				}
				if(c.get(c.size()-1).equalsIgnoreCase("not")) {
					this.implication.addIfCondition(new UrlCondition(c.get(1),c.get(3)+"."+c.get(4),false));
					break;
				}
				else {
					this.implication.addIfCondition(new UrlCondition(c.get(1),c.get(3)+"."+c.get(4),true));
					break;
				}
			}
			break;
		case "sentPackets": 
			if(c.get(c.size()-1).equalsIgnoreCase("not")) {
				this.implication.addIfCondition(new SendCondition(nfd.getName(),"n_"+node_counter,c.get(2),"t_0",false));
				node_counter++;
				break;
			}
			else {
				this.implication.addIfCondition(new SendCondition(nfd.getName(),"n_"+node_counter,c.get(2),"t_0",true));
				node_counter++;
				break;
			}
		case "hostTableList": 
			if(c.get(c.size()-1).equalsIgnoreCase("not")) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i=3;i+1<c.size();i=i+2) {
					t.add(c.get(i)+"."+c.get(i+1));
				}
				this.implication.addIfCondition(new IsInTableCondition(c.get(1),t,false));
				break;
			}
			else {
				ArrayList<String> t = new ArrayList<String>();
				for(int i=3;i+1<c.size();i=i+2) {
					t.add(c.get(i)+"."+c.get(i+1));
				}
				this.implication.addIfCondition(new IsInTableCondition(c.get(1),t,true));
				break;
			}
		case "internalNodes": 
			if(c.get(c.size()-1).equalsIgnoreCase("not")) {
				this.implication.addIfCondition(new InternalNodeCondition(c.get(2)+"."+c.get(3),false));
				break;
			}
			else {
				this.implication.addIfCondition(new InternalNodeCondition(c.get(2)+"."+c.get(3),true));
				break;
			}
		case "receivedPackets": 
			if(c.get(c.size()-1).equalsIgnoreCase("not")) {
				this.implication.addIfCondition(new RecvCondition("n_"+node_counter,nfd.getName(),"t_0",c.get(2),false));
				node_counter++;
				break;
			}
			else {
				this.implication.addIfCondition(new RecvCondition("n_"+node_counter,nfd.getName(),"t_0",c.get(2),true));
				node_counter++;
				break;
			}
		}

		
	}


}
