package test.definition;

import definitions.Action;
import definitions.FieldType;
import definitions.ForwardDirection;
import definitions.ForwardingHost;
import definitions.HostTable;
import definitions.Packet;
import definitions.RoutingResult;

public class Firewall extends ForwardingHost{

	@Override
	public void defineState() {
		this.state.hostTableList.add(HostTable.createTable("Acl",2,FieldType.IP_SRC,FieldType.IP_DEST));
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		if(!this.state.hostTableList.get("Acl").contains(packet.getDestinationAddress(),packet.getSourceAddress()) &&
				!this.state.hostTableList.get("Acl").contains(packet.getSourceAddress(),packet.getDestinationAddress())
				) {
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,ForwardDirection.UPSTREAM);
	}

}
