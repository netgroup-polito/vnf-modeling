package test.definition;

import definitions.Action;
import definitions.Constants;
import definitions.FieldType;
import definitions.ForwardDirection;
import definitions.ForwardingHost;
import definitions.HostTable;
import definitions.Operator;
import definitions.Packet;
import definitions.RoutingResult;
import definitions.Url;

public class WebCache extends ForwardingHost{

	@Override
	public void defineState() {
		this.state.hostTableList.add(HostTable.createTable("ResorceCache",1, FieldType.URL));
		//this.state.internalNodes.set();
		this.state.internalNodes.setWithoutPrivateAddresses();
		this.state.receivedPackets.set();
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) { 
		if(Packet.match(packet.getProtocol(), Constants.HTTP_REQUEST_PROTOCOL, Operator.EQUAL) &&
				this.state.hostTableList.get("ResorceCache").contains(packet.getUrl()) &&
				this.state.internalNodes.contains(packet.getSourceAddress())
				) {
			Packet p1 = new Packet();
			p1.setSourceAddress(packet.getDestinationAddress());
			p1.setDestinationAddress(packet.getSourceAddress());
			p1.setUrl(packet.getUrl());
			p1.setProtocol(Constants.HTTP_RESPONSE_PROTOCOL);
//			p1.setBody(null);
//			p1.setDestinationPort(null);
//			p1.setMailDestination(null);
//			p1.setMailSource(null);
//			p1.setOrigBody(null);
//			p1.setOrigin(packet.getDestinationAddress());
//			p1.setOrigin(this.address);
//			p1.setProtocol(packet.getProtocol());
//			p1.setSourcePort(null);
//			p1.setUrl(null);
//			p1.setUrl((Url)this.hostPropertyList.get("rr"));
			return new RoutingResult(p1,Action.FORWARD,ForwardDirection.SAME_INTERFACE);
		}
		if(Packet.match(packet.getProtocol(), Constants.HTTP_REQUEST_PROTOCOL, Operator.EQUAL) &&
				this.state.internalNodes.contains(packet.getSourceAddress()) &&
				!this.state.hostTableList.get("ResorceCache").contains(packet.getUrl()) &&
				!this.state.internalNodes.contains(packet.getDestinationAddress())
				) {
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		Packet p2 = new Packet();
		if(Packet.match(packet.getProtocol(), Constants.HTTP_RESPONSE_PROTOCOL, Operator.EQUAL) &&
				!this.state.internalNodes.contains(packet.getSourceAddress()) &&
				this.state.receivedPackets.contains(p2) &&
				Packet.match(p2.getProtocol(), Constants.HTTP_REQUEST_PROTOCOL, Operator.EQUAL) &&
				this.state.internalNodes.contains(p2.getSourceAddress()) &&
				Packet.match(p2.getUrl(), packet.getUrl(), Operator.EQUAL)
				) {
			this.state.hostTableList.get("ResorceCache").store(p2.getUrl());
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
	}

}
