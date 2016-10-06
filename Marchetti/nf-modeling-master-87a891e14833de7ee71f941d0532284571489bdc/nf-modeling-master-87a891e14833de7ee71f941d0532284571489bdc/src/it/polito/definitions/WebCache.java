package it.polito.definitions;

import it.polito.definitions.Expression.Operator;
import it.polito.definitions.Packet.PacketField;

public class WebCache extends ForwardingHost{

	@Override
	public Result onReceivedPacket(Packet packet) {
		//Cache cache = new Cache();
		//this.state.add(cache);
		
		if(packet.matches(new Expression(Constants.HTTP_REQUEST_PROTOCOL,packet.getField(PacketField.PROTOCOL),Operator.EQUAL)) &&
				//this.state.select("cache").contains((Url)(packet.getField(PacketField.URL)))
				this.state.cache.contains((Url)(packet.getField(PacketField.URL))) &&
				this.state.internalNodes.contains(packet.getField(PacketField.IP_SRC))) 
		{
			Packet p1 = Packet.newPacketInstance();
			p1.setField(PacketField.PROTOCOL, Constants.HTTP_RESPONSE_PROTOCOL);
			p1.setField(PacketField.URL, packet.getField(PacketField.URL));
			p1.setField(PacketField.IP_SRC, packet.getField(PacketField.IP_DST));
			p1.setField(PacketField.IP_DST, packet.getField(PacketField.IP_SRC));
		}
		return null;
	}

	

}

/*
public class Cache implements StateElement {

	@override
	public boolean contains(List<Field> list) {
		
	}
}

public class InternalNodes implements StateElement {

	@override
	public boolean contains(List<Field> list) {
		for(int i=0;i<listRows.size();i++) {
			if(
	}
}

public interface StateElement {
	List<List<Field>> listRows;

	public boolean contains(List<Field> list);
}
*/
