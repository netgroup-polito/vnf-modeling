package it.polito.NFmodels;

import java.util.List;

import it.polito.modelib.Constants;
import it.polito.modelib.Expression;
import it.polito.modelib.NetworkFunction;
import it.polito.modelib.Packet;
import it.polito.modelib.Packet.PacketField;
import it.polito.modelib.RoutingResult;
import it.polito.modelib.RoutingResult.ForwardDirection;
import it.polito.modelib.RoutingResult.Result;
import it.polito.modelib.State.Type;
import it.polito.modelib.RoutingTable;
import it.polito.modelib.Expression.Operator;

public class WebCache extends NetworkFunction{

	/*
	 * No configuration is used.
	 */
	public WebCache(RoutingTable rTable, List<String> configList) {
		super(rTable, configList, null);
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		Packet p1 = Packet.newPacketInstance()
				.matches(new Expression(PacketField.URL, packet.getField(PacketField.URL), Operator.EQUAL));
		
		if(packet.getField(PacketField.PROTOCOL) == Constants.HTTP_REQUEST_PROTOCOL &&
			state.matchPacket(p1, Type.RECEIVED))
		{
			/* Reply with cached content */
			packet.setField(PacketField.IP_SRC, packet.getField(PacketField.IP_DST));
			packet.setField(PacketField.IP_DST, packet.getField(PacketField.IP_SRC));
			packet.setField(PacketField.PROTOCOL, Constants.HTTP_RESPONSE_PROTOCOL);
			return new RoutingResult(packet, Result.FORWARD, ForwardDirection.SAME_INTERFACE);
		}
		
		if(packet.getField(PacketField.PROTOCOL) == Constants.HTTP_REQUEST_PROTOCOL &&
		   !state.matchPacket(p1, Type.RECEIVED))
		{
			/* No content in cache -> forward the packet upstream */
			return new RoutingResult(packet, Result.FORWARD, ForwardDirection.UPSTREAM);
		}
		
		if(packet.getField(PacketField.PROTOCOL) == Constants.HTTP_RESPONSE_PROTOCOL)
		{
			/* Store the content in cache */
			state.setState(Packet.newPacketInstance()
								.matches(new Expression(PacketField.URL, packet.getField(PacketField.URL), Operator.EQUAL)), 
						   Type.RECEIVED);
			
			/* Propagate the packet upstream */
			return new RoutingResult(packet, Result.FORWARD, ForwardDirection.UPSTREAM);
		}
		return new RoutingResult(packet, Result.DROP, null);
	}

}
