package it.polito.parser;

public class ReturnCondition extends Condition{
	String packet;
	String direction;

	public ReturnCondition(String packet, String direction, boolean validity) {
		this.type = ConditionType.return_type;
		this.packet = packet;
		this.direction = direction;
		this.validity = validity;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Direction: " +this.direction);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((ReturnCondition)c1).getPacket() ) &&
					this.direction.equalsIgnoreCase( ((ReturnCondition)c1).getDirection() ) &&
					this.validity == ((ReturnCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	

}
