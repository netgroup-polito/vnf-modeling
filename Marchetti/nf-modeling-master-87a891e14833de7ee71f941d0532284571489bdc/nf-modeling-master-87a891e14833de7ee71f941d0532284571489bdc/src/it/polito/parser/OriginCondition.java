package it.polito.parser;

public class OriginCondition extends Condition{
	String packet;
	String origin;
	
	public OriginCondition(String packet, String origin, boolean validity) {
		this.type = ConditionType.packet_origin;
		this.packet = packet;
		this.origin = origin;
		this.validity = validity;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Origin: " +this.origin);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((OriginCondition)c1).getPacket() ) &&
					this.origin.equalsIgnoreCase( ((OriginCondition)c1).getOrigin() ) &&
					this.validity == ((OriginCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	

}
