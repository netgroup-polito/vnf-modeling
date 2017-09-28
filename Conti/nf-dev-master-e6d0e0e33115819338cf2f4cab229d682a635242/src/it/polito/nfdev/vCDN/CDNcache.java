package it.polito.nfdev.vCDN;

import java.util.List;
import java.net.URL;
import it.polito.nfdev.lib.Interface;
import it.polito.nfdev.lib.NetworkFunction;
import it.polito.nfdev.lib.Packet;
import it.polito.nfdev.lib.Packet.PacketField;
import it.polito.nfdev.lib.RoutingResult;
import it.polito.nfdev.lib.RoutingResult.Action;
import it.polito.nfdev.lib.TableEntry;
import it.polito.nfdev.verification.Verifier;
import it.polito.nfdev.webcache.*;
import it.polito.nfdev.verification.Table;

public class CDNcache extends NetworkFunction {

	public String webServer_Ip;
	private String client_Ip;
	private final Integer TIMEOUT;
	private Interface internalFace;
	private Interface externalFace;
	
	@Table( fields = {"URL", "CONTENT", "EXTERNAL_IP"} )
	private CacheTable cdnCacheTable;
	
	public CDNcache(List<Interface> interfaces,String webServer_Ip, Integer timeout) {
		super(interfaces);
		assert interfaces.size() == 2;
		assert timeout > 0;
		assert webServer_Ip != null && !webServer_Ip.isEmpty();
		internalFace = null;
		externalFace = null;
		for(Interface i : interfaces)
		{
			if(i.getAttributes().contains(Interface.INTERNAL_ATTR))
				internalFace = i;
			if(i.getAttributes().contains(Interface.EXTERNAL_ATTR))
				externalFace = i;
		}
		assert internalFace != null;
		assert externalFace != null;
		assert internalFace.getId() != externalFace.getId();
		
		this.webServer_Ip=webServer_Ip;
		this.TIMEOUT = timeout;
		
		this.cdnCacheTable = new CacheTable(3,1);  // URL, CONTENT, WebSite_IP
		this.cdnCacheTable.setDataDriven();
		
	}
	
	@Override
	public RoutingResult onReceivedPacket(Packet packet, Interface iface) {		
		Packet p = null;
		try {
			p = packet.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return new RoutingResult(Action.DROP, null, null);
		}
		
		if(iface.isInternal())
		{
			if(packet.equalsField(PacketField.APPLICATION_PROTOCOL,Packet.HTTP_REQUEST)){
				TableEntry entry = cdnCacheTable.matchEntry(packet.getField(PacketField.L7DATA), Verifier.ANY_VALUE, Verifier.ANY_VALUE);				
				
				if(entry != null&&entry.getValue(1)!=null)   
				{
					p.setField(PacketField.IP_DST, packet.getField(PacketField.IP_SRC));
					p.setField(PacketField.IP_SRC, packet.getField(PacketField.IP_DST));
					p.setField(PacketField.PORT_DST, packet.getField(PacketField.PORT_SRC));
					p.setField(PacketField.PORT_SRC, packet.getField(PacketField.PORT_DST));
					p.setField(PacketField.APPLICATION_PROTOCOL, Packet.HTTP_RESPONSE);
					p.setField(PacketField.L7DATA, (String)entry.getValue(0));   
					return new RoutingResult(Action.FORWARD, p, internalInterface); 
				
				}
				else if(entry != null && ((String)entry.getValue(2)).equals(webServer_Ip))
				{
					client_Ip = packet.getField(PacketField.IP_SRC);
					
					p.setField(PacketField.IP_DST, webServer_Ip);
					p.setField(PacketField.IP_SRC, packet.getField(PacketField.IP_DST));
				//	p.setField(PacketField.PORT_DST, packet.getField(PacketField.PORT_SRC));
				//	p.setField(PacketField.PORT_SRC, packet.getField(PacketField.PORT_DST));
					p.setField(PacketField.APPLICATION_PROTOCOL, Packet.HTTP_REQUEST);
				//	p.setField(PacketField.L7DATA, (String)entry.getValue(0));   
					return new RoutingResult(Action.FORWARD, p, externalInterface); 
				}
				else
					return new RoutingResult(Action.DROP, null, null);
			}
			return new RoutingResult(Action.FORWARD, packet, externalInterface);  // if the packet is from internal network and not http_request, FORWARD outside the network directly 
			
		}
		else  
		{		
			if(packet.equalsField(PacketField.APPLICATION_PROTOCOL,Packet.HTTP_RESPONSE)){
				CacheTableEntry cacheEntry = (CacheTableEntry)cdnCacheTable.matchEntry(packet.getField(PacketField.L7DATA), Verifier.ANY_VALUE, Verifier.ANY_VALUE);
				try {
					Content contt = new Content(new URL(packet.getField(PacketField.L7DATA)));
					cacheEntry.setValue(1, contt);
					p.setField(PacketField.IP_SRC, packet.getField(PacketField.IP_DST));
					p.setField(PacketField.IP_DST, client_Ip); 
					return new RoutingResult(Action.FORWARD, p, internalInterface); 
					
				} catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			return new RoutingResult(Action.FORWARD, packet, internalInterface);
			
		}
		
	}
	
	
	/*public boolean addCdnCacheRule(String pagePath, String srcIp, String cacheIp){
		
		TableEntry entry = new TableEntry(3);
		entry.setValue(0, serverName.trim());
		entry.setValue(1, srcIp.trim());
		entry.setValue(2, cacheIp.trim());
			
		return cdnCacheTable.storeEntry(entry);
	}
	
	public boolean removeCDNRule(String serverName, String srcIp, String cacheIp){
		TableEntry entry  = cdnTable.matchEntry(serverName, srcIp, cacheIp);
		
		return cdnTable.removeEntry(entry);  
	}*/

	public void clearCdnCacheTable(){
		cdnCacheTable.clear();
	}

}
