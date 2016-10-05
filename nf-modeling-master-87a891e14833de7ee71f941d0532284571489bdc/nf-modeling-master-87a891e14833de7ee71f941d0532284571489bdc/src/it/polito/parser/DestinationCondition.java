package it.polito.parser;

public class DestinationCondition extends Condition{
	String packet;
	String destination;


	public DestinationCondition(String packet, String destination, boolean validity) {
		this.type = ConditionType.packet_destination;
		this.packet = packet;
		this.destination = destination;
		this.validity = validity;
	}


	public String getPacket() {
		return packet;
	}


	public void setPacket(String packet) {
		this.packet = packet;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Destination: " +this.destination);
		System.out.println("Validity: "+this.validity);
	}


	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((DestinationCondition)c1).getPacket() ) &&
					this.destination.equalsIgnoreCase( ((DestinationCondition)c1).getDestination() ) &&
					this.validity == ((DestinationCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	

}
