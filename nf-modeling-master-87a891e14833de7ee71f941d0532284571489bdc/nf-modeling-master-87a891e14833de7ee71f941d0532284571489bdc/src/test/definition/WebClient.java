package test.definition;

import definitions.Address;
import definitions.Constants;
import definitions.EndHost;
import definitions.Packet;
import definitions.RoutingResult;

public class WebClient extends EndHost{

	@Override
	public Packet defineSendingPacket() {
		Packet packet = new Packet();
		packet.setSourceAddress(this.address);
		packet.setOrigin(this.address);
		packet.setBody(packet.getOrigBody());
		packet.setProtocol(Constants.HTTP_REQUEST_PROTOCOL);
		packet.setDestinationAddress((Address)(this.hostPropertyList.get("ipServer")));
		return packet;
	}

	@Override
	public void defineState() {
		this.hostPropertyList.add("ipServer");
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		// TODO Auto-generated method stub
		return null;
	}

}
