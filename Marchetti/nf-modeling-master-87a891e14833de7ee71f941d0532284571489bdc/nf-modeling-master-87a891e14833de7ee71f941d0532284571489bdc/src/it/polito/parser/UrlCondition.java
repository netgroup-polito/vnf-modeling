package it.polito.parser;

public class UrlCondition extends Condition{
	String packet;
	String url;
	
	public UrlCondition(String packet, String url, boolean validity) {
		this.type = ConditionType.packet_url;
		this.packet = packet;
		this.url = url;
		this.validity = validity;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Packet: " +this.packet);
		System.out.println("Url: " +this.url);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.packet.equalsIgnoreCase( ((UrlCondition)c1).getPacket() ) &&
					this.url.equalsIgnoreCase( ((UrlCondition)c1).getUrl() ) &&
					this.validity == ((UrlCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
	
}
