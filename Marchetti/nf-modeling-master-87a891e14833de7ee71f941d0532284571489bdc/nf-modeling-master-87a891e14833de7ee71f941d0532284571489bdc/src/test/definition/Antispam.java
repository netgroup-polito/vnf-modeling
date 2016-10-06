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

public class Antispam extends ForwardingHost{

	@Override
	public void defineState() {
		this.state.hostTableList.add(HostTable.createTable("Blacklist",1, FieldType.MAIL_FROM));
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		if(Packet.match(packet.getProtocol(), Constants.POP3_REQUEST_PROTOCOL, Operator.EQUAL)) {
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		if(Packet.match(packet.getProtocol(), Constants.POP3_RESPONSE_PROTOCOL, Operator.EQUAL) &&
				!this.state.hostTableList.get("Blacklist").contains(packet.getMailSource())
				) {
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
	}

}
