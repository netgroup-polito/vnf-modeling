package it.polito.parser;

public class ProtocolCondition extends Condition{
	String packet;
	String protocol;
	
	public ProtocolCondition(String packet,String protocol, boolean validity) {
		this.type = ConditionType.packet_protocol;
		this.packet = packet;
		this.protocol = protocol;
		this.validity = validity;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Protocol: " +this.protocol);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((ProtocolCondition)c1).getPacket() ) &&
					this.protocol.equalsIgnoreCase( ((ProtocolCondition)c1).getProtocol() ) &&
					this.validity == ((ProtocolCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	

}
