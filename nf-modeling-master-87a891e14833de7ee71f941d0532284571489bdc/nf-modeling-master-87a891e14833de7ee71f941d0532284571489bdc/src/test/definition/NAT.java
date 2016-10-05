package test.definition;

import definitions.Action;
import definitions.Address;
import definitions.Constants;
import definitions.ForwardDirection;
import definitions.ForwardingHost;
import definitions.Operator;
import definitions.Packet;
import definitions.RoutingResult;

public class NAT extends ForwardingHost{

	@Override
	public void defineState() {
		this.state.internalNodes.setWithPrivateAddresses();;
		this.state.receivedPackets.set();
		this.state.sentPackets.set();
		this.hostPropertyList.add("public_ip");
		
	}

//	@Override
//	public RoutingResult onReceivedPacket(Packet packet) {
//		if(this.state.internalNodes.contains(packet.getSourceAddress()) &&
//				!this.state.internalNodes.contains(packet.getDestinationAddress())
//				) {
//			packet.setSourceAddress((Address) (this.hostPropertyList.get("public_ip")));
//			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
//		}
//		Packet p2 = new Packet();
//		Packet p1 = new Packet();
//		if(!this.state.internalNodes.contains(packet.getSourceAddress()) &&
//				Packet.match(packet.getDestinationAddress(), (Address) (this.hostPropertyList.get("public_ip")), Operator.EQUAL) &&
//				Packet.match(p2.getDestinationAddress(), packet.getSourceAddress(), Operator.EQUAL) &&
//				Packet.match(p2.getSourceAddress(), (Address) (this.hostPropertyList.get("public_ip")), Operator.EQUAL) &&
//				Packet.match(p2.getSourcePort(), packet.getDestinationPort(), Operator.EQUAL) &&
//				Packet.match(p2.getDestinationPort(), packet.getSourcePort(), Operator.EQUAL) &&
//				this.state.sentPackets.contains(p2) &&
//				this.state.internalNodes.contains(p1.getSourceAddress()) &&
//				Packet.match(p1.getDestinationAddress(), packet.getSourceAddress(), Operator.EQUAL) &&
//				Packet.match(p1.getSourcePort(), packet.getDestinationPort(), Operator.EQUAL) &&
//				this.state.receivedPackets.contains(p1)
//				) {
//			Packet p3 = new Packet();
//			p3.setDestinationAddress(p1.getSourceAddress());
//			p3.setDestinationPort(p1.getSourcePort());
//			p3.setSourceAddress(packet.getSourceAddress());
//			p3.setSourcePort(packet.getSourcePort());
//			p3.setUrl(packet.getUrl());
//			p3.setBody(packet.getBody());
//			return new RoutingResult(p3,Action.FORWARD,ForwardDirection.UPSTREAM);
//		}
//		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
//	}
	
	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		if(this.state.internalNodes.contains(packet.getSourceAddress()) &&
				!this.state.internalNodes.contains(packet.getDestinationAddress())
				) {
			Packet p1 = new Packet();
			p1.setOrigin(packet.getOrigin());
			p1.setDestinationAddress(packet.getDestinationAddress());
			p1.setOrigBody(packet.getOrigBody());
			p1.setBody(packet.getBody());
			p1.setProtocol(packet.getProtocol());
			p1.setMailSource(packet.getMailSource());
			p1.setUrl(packet.getUrl());
			p1.setSourceAddress((Address) (this.hostPropertyList.get("public_ip")));
			return new RoutingResult(p1,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		Packet p2 = new Packet();
		Packet p1 = new Packet();
		if(!this.state.internalNodes.contains(packet.getSourceAddress()) &&
				Packet.match(packet.getDestinationAddress(), (Address) (this.hostPropertyList.get("public_ip")), Operator.EQUAL) &&
//				Packet.match(p2.getDestinationAddress(), packet.getSourceAddress(), Operator.EQUAL) &&
//				Packet.match(p2.getSourceAddress(), (Address) (this.hostPropertyList.get("public_ip")), Operator.EQUAL) &&
//				this.state.internalNodes.contains(p1.getSourceAddress()) &&
				Packet.match(p1.getDestinationAddress(), packet.getSourceAddress(), Operator.EQUAL) &&
				this.state.receivedPackets.contains(p1) &&
				Packet.match(packet.getSourceAddress(), p1.getDestinationAddress(), Operator.EQUAL) &&
				this.state.internalNodes.contains(p1.getSourceAddress()) 
				
				) {
			Packet p3 = new Packet();
//			p3.setDestinationAddress(p1.getSourceAddress());
//			p3.setDestinationPort(p1.getSourcePort());
//			p3.setSourceAddress(packet.getSourceAddress());
//			p3.setSourcePort(packet.getSourcePort());
//			p3.setUrl(packet.getUrl());
//			p3.setBody(packet.getBody());
			p3.setOrigin(packet.getOrigin());
			p3.setSourceAddress(packet.getSourceAddress());
			p3.setOrigBody(packet.getOrigBody());
			p3.setBody(packet.getBody());
			p3.setProtocol(packet.getProtocol());
			p3.setMailSource(packet.getMailSource());
			p3.setUrl(packet.getUrl());
			p3.setDestinationAddress(p1.getSourceAddress());
			return new RoutingResult(p3,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
	}

}
