package it.polito.modelib;

public abstract class Packet {
	
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
	
	public abstract Integer getField(PacketField field);
	
	public abstract void setField(PacketField field, Integer value);
	
	public abstract Packet matches(Expression expression);
	
	public abstract boolean satisfies(Expression exp);
	
}
