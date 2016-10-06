package test.definition;

import definitions.Action;
import definitions.Constants;
import definitions.EndHost;
import definitions.ForwardDirection;
import definitions.Operator;
import definitions.Packet;
import definitions.RoutingResult;

public class WebServer extends EndHost{

	@Override
	public Packet defineSendingPacket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defineState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		if(Packet.match(packet.getProtocol(), Constants.HTTP_REQUEST_PROTOCOL,Operator.EQUAL)) {
			Packet p1 = new Packet();
			p1.setProtocol(Constants.HTTP_RESPONSE_PROTOCOL);
			p1.setDestinationAddress(packet.getSourceAddress());
			p1.setUrl(packet.getUrl());
			p1.setSourceAddress(this.address);
			p1.setOrigin(this.address);
			p1.setBody(p1.getOrigBody());
			return new RoutingResult(p1,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
	}

}
