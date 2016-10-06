package it.polito.parser;

public class DestPortCondition extends Condition{
	String packet;
	String port;
	
	public DestPortCondition(String packet, String port, boolean validity) {
		this.type = ConditionType.packet_port_destination;
		this.validity = validity;
		this.packet = packet;
		this.port = port;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Port: " +this.port);
		System.out.println("Validity: "+this.validity);
		
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((DestPortCondition)c1).getPacket() ) &&
					this.port.equalsIgnoreCase( ((DestPortCondition)c1).getPort() ) &&
					this.validity == ((DestPortCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	

}
