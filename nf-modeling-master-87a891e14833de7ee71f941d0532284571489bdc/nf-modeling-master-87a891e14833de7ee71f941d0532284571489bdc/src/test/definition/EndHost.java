package test.definition;

import definitions.*;

public class EndHost extends definitions.EndHost{

	public EndHost() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Packet defineSendingPacket() {
		Packet p = new Packet();
		p.setSourceAddress(this.address);
		p.setOrigin(this.address);
		p.setBody(p.getOrigBody());
		return p;
	}

	@Override
	public void defineState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		// TODO Auto-generated method stub
		return null;
	}

}
