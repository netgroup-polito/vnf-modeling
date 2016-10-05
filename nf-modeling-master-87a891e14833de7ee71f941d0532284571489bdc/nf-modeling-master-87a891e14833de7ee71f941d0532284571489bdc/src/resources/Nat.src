package it.polito.NFmodels;

import java.util.List;
import java.util.Map;

import it.polito.modelib.Constants;
import it.polito.modelib.Constraints;
import it.polito.modelib.Expression;
import it.polito.modelib.Expression.Operator;
import it.polito.modelib.NetworkFunction;
import it.polito.modelib.Packet;
import it.polito.modelib.RoutingResult;
import it.polito.modelib.RoutingTable;
import it.polito.modelib.Packet.PacketField;
import it.polito.modelib.RoutingResult.ForwardDirection;
import it.polito.modelib.RoutingResult.Result;
import it.polito.modelib.State.Type;

public class Nat extends NetworkFunction {

	public Nat(RoutingTable rTable, List<String> configList, Map<String, String> configMap) {
		/* configList 				is used to pass the list of internal nodes
		 * configMap["publicIp"] 	contains the IP address exposed by this nat 
		 */
		super(rTable, configList, configMap);
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) 
	{
		if(configList.contains(packet.getField(PacketField.IP_SRC).toString()) && !configList.contains(packet.getField(PacketField.IP_DST)))
		{
			/* Packet coming from the internal network going to the external network */
			
			/* Create a state information for this packet */
			state.setState(Packet.newPacketInstance()
							 .matches(new Expression(PacketField.IP_SRC, packet.getField(PacketField.IP_SRC), Operator.EQUAL))
							 .matches(new Expression(PacketField.IP_DST, packet.getField(PacketField.IP_DST), Operator.EQUAL)), 
						   Type.RECEIVED);
			
			/* IP masquerading */
			packet.setField(PacketField.IP_SRC, Integer.parseInt(configMap.get("publicIP")));
			packet.setField(PacketField.PORT_SRC, Constants.ANY_VALUE);
			
			/* Forward the packet upstream */
			state.setState(packet, Type.SENT);
			return new RoutingResult(packet, Result.FORWARD, ForwardDirection.UPSTREAM);
		}
		if(!configList.contains(packet.getField(PacketField.IP_SRC)) && 
				packet.satisfies(new Expression(PacketField.IP_DST, Integer.parseInt(configMap.get("publicIP")), Operator.EQUAL)))
		{
			/* Packet coming from the external network */
			
			Packet p1 = Packet.newPacketInstance()
					.matches(new Expression(PacketField.IP_DST, packet.getField(PacketField.IP_SRC), Operator.EQUAL))
					.matches(new Expression(PacketField.IP_SRC, Constants.ANY_VALUE, Operator.EQUAL))
					.matches(new Expression(PacketField.PORT_DST, packet.getField(PacketField.PORT_SRC), Operator.EQUAL))
					.matches(new Expression(PacketField.PORT_SRC, Constants.ANY_VALUE, Operator.EQUAL));
			Packet p2 = Packet.newPacketInstance()
					.matches(new Expression(PacketField.IP_DST, packet.getField(PacketField.IP_SRC), Operator.EQUAL))
					.matches(new Expression(PacketField.IP_SRC, Integer.parseInt(configMap.get("publicIP")), Operator.EQUAL))
					.matches(new Expression(PacketField.PORT_DST, packet.getField(PacketField.PORT_SRC), Operator.EQUAL))
					.matches(new Expression(PacketField.PORT_SRC, packet.getField(PacketField.PORT_DST), Operator.EQUAL));
			
			if(state.matchPacket(p1, Type.RECEIVED) &&
			   state.matchPacket(p2, Type.SENT) &&
			   Constraints.requireConstraint(new Expression(p2.getField(PacketField.PORT_SRC), packet.getField(PacketField.PORT_DST), Operator.EQUAL)))
			{
				packet.setField(PacketField.IP_DST, p1.getField(PacketField.IP_SRC));
				packet.setField(PacketField.PORT_DST, p1.getField(PacketField.PORT_SRC));
				return new RoutingResult(packet, Result.FORWARD, ForwardDirection.UPSTREAM);
			}
		}
		return new RoutingResult(null, Result.DROP, null);
	}

}
