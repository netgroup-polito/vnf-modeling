package it.polito.nfdev.vCDN;

import java.util.ArrayList;
import java.util.List;

import it.polito.nfdev.lib.Interface;
import it.polito.nfdev.lib.NetworkFunction;
import it.polito.nfdev.lib.Packet;
import it.polito.nfdev.lib.RoutingResult;
import it.polito.nfdev.lib.Table;
import it.polito.nfdev.lib.TableEntry;
import it.polito.nfdev.lib.Packet.PacketField;
import it.polito.nfdev.lib.RoutingResult.Action;
import it.polito.nfdev.verification.Verifier;

public class GlobalDnsBalancer extends NetworkFunction {

	private Table balanTable;
	
	public GlobalDnsBalancer() {
		super(new ArrayList<Interface>());
		
		this.balanTable = new Table(3,0);	// Ip_Src_of_Requester, url, IP_of_CDNNode
		this.balanTable.setTypes(Table.TableTypes.Ip, Table.TableTypes.Generic, Table.TableTypes.Ip);
		
	}

	@Override
	public RoutingResult onReceivedPacket(Packet packet, Interface iface) {
		Packet packet_in = null;
		try {
			packet_in = packet.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return new RoutingResult(Action.DROP, null, null); 
		}
		
		TableEntry entry = balanTable.matchEntry(packet_in.getField(PacketField.IP_SRC), packet_in.getField(PacketField.L7DATA), Verifier.ANY_VALUE);
		if(entry != null)
		{
			packet_in.setField(PacketField.IP_SRC, packet.getField(PacketField.IP_DST));
			packet_in.setField(PacketField.PORT_SRC, packet.getField(PacketField.PORT_DST));
			packet_in.setField(PacketField.IP_DST, packet.getField(PacketField.IP_SRC));
			packet_in.setField(PacketField.PORT_DST, packet.getField(PacketField.PORT_SRC));
			packet_in.setField(PacketField.APPLICATION_PROTOCOL, Packet.DNS_RESPONSE);
			packet_in.setField(PacketField.L7DATA, (String) entry.getValue(2));
			
			return new RoutingResult(Action.FORWARD,packet_in,iface);
		
		}
		return new RoutingResult(Action.DROP,null,null);
		
	}

}
