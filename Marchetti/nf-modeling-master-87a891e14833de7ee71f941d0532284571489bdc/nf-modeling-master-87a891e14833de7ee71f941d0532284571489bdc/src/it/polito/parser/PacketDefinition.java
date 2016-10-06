package it.polito.parser;

import java.util.ArrayList;

public class PacketDefinition {
	ArrayList<Condition> packetCondition = new ArrayList<Condition>();

	public ArrayList<Condition> getPacketCondition() {
		return packetCondition;
	}

	public void setPacketCondition(ArrayList<Condition> packetCondition) {
		this.packetCondition = packetCondition;
	}
	
	public void addPacketCondition(Condition condition) {
		this.packetCondition.add(condition);
	}
}
