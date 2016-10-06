package it.polito.modelib;

import java.util.List;

public abstract class State {
	
	public enum Type { RECEIVED, SENT };
	
	public abstract void setState(Packet packet, Type type);
	
	public abstract Boolean matchPacket(Packet packet, Type type);
	
	public abstract Packet retrievePacket(List<Expression> expr);
	
}
