package it.polito.definitions;

public abstract class Node {
	public Address address;
	public State state;
	
	public abstract Result onReceivedPacket(Packet packet);
}
