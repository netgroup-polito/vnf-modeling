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

public class IDS extends ForwardingHost{

	@Override
	public void defineState() {
		this.state.hostTableList.add(HostTable.createTable("Blacklist",1, FieldType.BODY));
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		if(Packet.match(packet.getProtocol(), Constants.HTTP_RESPONSE_PROTOCOL, Operator.EQUAL) &&
				!this.state.hostTableList.get("Blacklist").contains(packet.getBody())) {
//			packet.setSourceAddress(this.address);
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		if(Packet.match(packet.getProtocol(), Constants.HTTP_REQUEST_PROTOCOL, Operator.EQUAL)) {
//			packet.setSourceAddress(this.address);
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
	}

}
