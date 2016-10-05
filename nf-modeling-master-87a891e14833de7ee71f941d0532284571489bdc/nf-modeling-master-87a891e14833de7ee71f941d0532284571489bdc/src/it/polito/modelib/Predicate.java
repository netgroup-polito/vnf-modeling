package it.polito.modelib;

import it.polito.modelib.Packet.PacketField;

public class Predicate {
	
	private PacketField field;
	private Integer value;
	
	public Predicate(PacketField field, Integer value) {
		this.field = field;
		this.value = value;
	}

	public PacketField getField() {
		return field;
	}

	public Integer getValue() {
		return value;
	}

}
