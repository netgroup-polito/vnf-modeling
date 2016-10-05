package it.polito.parser;

public class SendCondition extends Condition{
	String source;
	String destination;
	String packet;
	String time;
	
	public SendCondition(String source,	String destination,	String packet,	String time, boolean validity) {
		this.type = ConditionType.send;
		this.source = source;
		this.destination = destination;
		this.packet = packet;
		this.time = time;
		this.validity = validity;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("SourceNode: " +this.source);
		System.out.println("DestNode: " +this.destination);
		System.out.println("Packet: " +this.packet);
		System.out.println("Time: " +this.time);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((SendCondition)c1).getPacket() ) &&
					this.source.equalsIgnoreCase( ((SendCondition)c1).getSource() ) &&
					this.destination.equalsIgnoreCase( ((SendCondition)c1).getDestination() ) &&
					this.time.equalsIgnoreCase( ((SendCondition)c1).getTime() ) &&
					this.validity == ((SendCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	
}
