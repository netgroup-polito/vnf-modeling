package it.polito.definitions;

public class Packet {
	
	public enum PacketField {
		IP_SRC,
		IP_DST,
		PORT_SRC,
		PORT_DST,
		PROTOCOL,
		URL,
		MAIL_FROM
	};
	
	public static Packet newPacketInstance() { return new PacketImpl(); }
	
	public  Field getField(PacketField field) {
		return null;
		
	}
	
	public  void setField(PacketField field, Integer value) {
		
	}
	
	public  void setField(PacketField field, Field value) {
		
	}
	
	public  boolean matches(Expression expression) {
		return false;
		
	}
	
	public  boolean satisfies(Expression exp) {
		return false;
		
	}
	
}
