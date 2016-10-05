package it.polito.parser;

public class BodyCondition extends Condition{
	String packet;
	String body;
	
	public BodyCondition(String packet, String body, boolean validity) {
		this.type = ConditionType.packet_body;
		this.packet = packet;
		this.body = body;
		this.validity = validity;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public void stampa() {
		// TODO Auto-generated method stub
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Body: " +this.body);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((BodyCondition)c1).getPacket() ) &&
					this.body.equalsIgnoreCase( ((BodyCondition)c1).getBody() ) &&
					this.validity == ((BodyCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	

}
