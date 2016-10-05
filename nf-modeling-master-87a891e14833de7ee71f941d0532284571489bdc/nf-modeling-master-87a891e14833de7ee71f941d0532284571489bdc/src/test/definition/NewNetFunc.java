package test.definition;

import definitions.Action;
import definitions.Address;
import definitions.Constants;
import definitions.EndHost;
import definitions.FieldType;
import definitions.ForwardDirection;
import definitions.HostTable;
import definitions.Operator;
import definitions.Packet;
import definitions.*;

public class NewNetFunc extends EndHost{


	@Override
	public Packet defineSendingPacket() {
		Packet packet = new Packet();
		packet.setSourceAddress(this.address);
		//packet.setOrigin(this.address);
		packet.setBody(packet.getOrigBody());
		packet.setProtocol(Constants.POP3_REQUEST_PROTOCOL);
		//packet.setDestinationAddress((Address) (this.hostPropertyList.get("ip_mailServer")));
		return packet;
	}

	@Override
	public void defineState() {
		this.state.hostTableList.add(HostTable.createTable("Blacklist",1, FieldType.ORIGIN));
		this.state.hostTableList.add(HostTable.createTable("Blacklist2",1, FieldType.SRC_PORT));
		this.hostPropertyList.add("ip_mailServer");
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		if(Packet.match(packet.getProtocol(), Constants.HTTP_RESPONSE_PROTOCOL, Operator.EQUAL) &&
				!this.state.hostTableList.get("Blacklist").contains(packet.getOrigin()) &&
				!this.state.hostTableList.get("Blacklist2").contains(packet.getSourcePort())
				) {
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		if(Packet.match(packet.getProtocol(), Constants.HTTP_RESPONSE_PROTOCOL, Operator.EQUAL) &&
				!this.state.hostTableList.get("Blacklist").contains(packet.getOrigin()) &&
				this.state.hostTableList.get("Blacklist2").contains(packet.getSourcePort())
				) {
			return new RoutingResult(packet,Action.FORWARD,ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet,Action.DROP,null);
	}

}
