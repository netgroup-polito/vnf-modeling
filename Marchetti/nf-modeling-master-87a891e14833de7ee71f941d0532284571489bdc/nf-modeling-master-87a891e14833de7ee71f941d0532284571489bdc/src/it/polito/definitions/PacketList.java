package it.polito.definitions;

public class PacketList {

	public enum Mode {
		SENT,
		REVEIVED
	}
	
	public boolean contains(Packet packet, Mode mode) {
		return true;
	}
}
