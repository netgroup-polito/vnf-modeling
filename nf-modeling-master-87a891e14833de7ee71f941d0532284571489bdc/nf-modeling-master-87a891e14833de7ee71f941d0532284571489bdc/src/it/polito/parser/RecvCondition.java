package it.polito.parser;

public class RecvCondition extends Condition{
	String sourceNode;
	String destNode;
	String time;
	String packet;
	
	public RecvCondition(String sourceNode, String destNode, String time, String packet, boolean validity) {
		this.type = ConditionType.recv;
		this.sourceNode = sourceNode;
		this.destNode = destNode;
		this.time = time;
		this.packet = packet;
		this.validity = validity;
	}

	public String getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(String sourceNode) {
		this.sourceNode = sourceNode;
	}

	public String getDestNode() {
		return destNode;
	}

	public void setDestNode(String destNode) {
		this.destNode = destNode;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("SourceNode: " +this.sourceNode);
		System.out.println("DestNode: " +this.destNode);
		System.out.println("Time: " +this.time);
		System.out.println("Packet: " +this.packet);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((RecvCondition)c1).getPacket() ) &&
					this.sourceNode.equalsIgnoreCase( ((RecvCondition)c1).getSourceNode() ) &&
					this.destNode.equalsIgnoreCase( ((RecvCondition)c1).getDestNode() ) &&
					this.time.equalsIgnoreCase( ((RecvCondition)c1).getTime() ) &&
					this.validity == ((RecvCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	

}
