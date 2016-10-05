package definitions;

public class Packet {
	Address ip_src;
	Address ip_dest;
	Port port_src;
	Port port_dest;
	Protocol protocol;
	Url url;
	Mail_Host mail_from;
	Mail_Host mail_to;
	Body body;
	Body orig_body;
	Address origin;
	
	public static boolean match(Field f1, Field f2, Operator op) {
		return true;
	}
	
	public static boolean match(Field f1, Constants c, Operator op) {
		return true;
	}
	
	/*
	public Field getField(FieldType fieldType) {
		return null;
	}
	
	public void setField(FieldType fieldType, Field field) {
		
	}
	
	public void setField(FieldType fieldType, Constants c) {
		
	}
	*/
	
	public Address getSourceAddress() {
		return null;
	}
	
	public Address getDestinationAddress() {
		return null;
	}
	
	public Port getSourcePort() {
		return null;
	}
	
	public Port getDestinationPort() {
		return null;
	}
	
	public Protocol getProtocol() {
		return null;
	}
	
	public Url getUrl() {
		return null;
	}
	
	public Mail_Host getMailSource() {
		return null;
	}
	
	public Mail_Host getMailDestination() {
		return null;
	}
	
	public Body getBody() {
		return null;
	}
	
	public Body getOrigBody() {
		return null;
	}
	
	public Address getOrigin() {
		return null;
	}
	
	public void setSourceAddress(Address address) {
		return;
	}
	
	public void setDestinationAddress(Address adress) {
		return ;
	}
	
	public void setSourcePort(Port port) {
		return ;
	}
	
	public void setDestinationPort(Port port) {
		return ;
	}
	
	public void setProtocol(Protocol protocol) {
		return ;
	}
	
	public void setProtocol(Constants c) {
		return ;
	}
	
	public void setUrl(Url url) {
		return ;
	}
	
	public void setMailSource(Mail_Host mail_host) {
		return ;
	}
	
	public void setMailDestination(Mail_Host mail_host) {
		return ;
	}
	
	public void setBody(Body body) {
		return ;
	}
	
	public void setOrigBody(Body orig_body) {
		return ;
	}
	
	public void setOrigin(Address origin) {
		return;
	}
}
