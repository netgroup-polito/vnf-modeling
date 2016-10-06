package test.definition;

import definitions.Address;
import definitions.Constants;
import definitions.EndHost;
import definitions.Packet;
import definitions.Port;
import definitions.RoutingResult;

public class MailClient extends EndHost{

	@Override
	public void defineState() {
		this.hostPropertyList.add("ip_mailServer");
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		return null;
	}

	@Override
	public Packet defineSendingPacket() {
		Packet packet = new Packet();
		packet.setSourceAddress(this.address);
		packet.setOrigin(this.address);
		packet.setBody(packet.getOrigBody());
		packet.setProtocol(Constants.POP3_REQUEST_PROTOCOL);
		packet.setDestinationAddress((Address) (this.hostPropertyList.get("ip_mailServer")));
		return packet;
	}

}
