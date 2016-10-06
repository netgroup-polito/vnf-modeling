package it.polito.NFmodels;

import java.util.List;

import it.polito.modelib.NetworkFunction;
import it.polito.modelib.Packet;
import it.polito.modelib.Packet.PacketField;
import it.polito.modelib.RoutingResult;
import it.polito.modelib.RoutingResult.ForwardDirection;
import it.polito.modelib.RoutingResult.Result;
import it.polito.modelib.RoutingTable;

public class DNSFilter extends NetworkFunction {

	/*
	 * configList: a list of blocked URLs
	 */
	public DNSFilter(RoutingTable rTable, List<String> configList) {
		super(rTable, configList, null);
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet) {
		for(String url : configList)
		{
			if(packet.getField(PacketField.URL) == Integer.parseInt(url))
				return new RoutingResult(null, Result.DROP, null);
		}
		return new RoutingResult(packet, Result.FORWARD, ForwardDirection.UPSTREAM);
	}

}
