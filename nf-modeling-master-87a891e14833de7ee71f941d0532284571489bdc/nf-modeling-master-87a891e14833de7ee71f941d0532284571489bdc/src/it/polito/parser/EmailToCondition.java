package it.polito.parser;

public class EmailToCondition extends Condition{
	String packet;
	String destination;

	public EmailToCondition(String packet, String destination, boolean validity) {
		this.type = ConditionType.packet_email_to;
		this.validity = validity;
		this.packet = packet;
		this.destination = destination;
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
			if(this.packet.equalsIgnoreCase( ((EmailToCondition)c1).getPacket() ) &&
					this.destination.equalsIgnoreCase( ((EmailToCondition)c1).getDestination() ) &&
					this.validity == ((EmailToCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	

}
