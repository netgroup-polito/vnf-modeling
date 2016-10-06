package it.polito.parser;

public class SourceCondition extends Condition {
	String packet;
	String source;
	
	public SourceCondition(String packet, String source, boolean validity) {
		this.type = ConditionType.packet_source;
		this.packet = packet;
		this.source = source;
		this.validity = validity;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Source: " +this.source);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((SourceCondition)c1).getPacket() ) &&
					this.source.equalsIgnoreCase( ((SourceCondition)c1).getSource() ) &&
					this.validity == ((SourceCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	
}
