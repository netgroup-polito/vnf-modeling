package it.polito.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NFdefinition {
	
	String name;
	boolean isEndHost;
	boolean internalNodesPresence;
	ArrayList<String> propertyList = new ArrayList<String>();
	HashMap<String,List<String>> hostTableList = new HashMap<String,List<String>>();
	HashMap<String,Boolean> hostTableStatic = new HashMap<String, Boolean>(); //per ogni tabella inserisco true se e definita dall utente, false se e definita dinamicamente
	ArrayList<Implication> implication = new ArrayList<Implication>(); //derivano da onReceivedPacket
	ArrayList<PacketDefinition> packetDefinition = new ArrayList<PacketDefinition>(); //derivano da packetDefinition (solo endHost)
	HashMap<String,String> variableList = new HashMap<String, String>();
	ArrayList<RightFormImplication> rightFormImplication = new ArrayList<RightFormImplication>();
	HashMap<String,String> protocolHM = new HashMap<String, String>();
	boolean privateAddresses=false;
	
	public boolean hasPrivateAddresses() {
		return privateAddresses;
	}
	public void setPrivateAddresses(boolean privateAddresses) {
		this.privateAddresses = privateAddresses;
	}
	public HashMap<String, String> getProtocolHM() {
		return protocolHM;
	}
	public void setProtocolHM(HashMap<String, String> protocolHM) {
		this.protocolHM = protocolHM;
	}
	public String getName() {
		return this.name;
	}
	public HashMap<String, String> getVariableList() {
		return variableList;
	}
	public void setVariableList(HashMap<String, String> variableList) {
		this.variableList = variableList;
	}
	public void setVariable(String name, String value) {
		this.variableList.put(name, value);
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEndHost() {
		return isEndHost;
	}
	public void setEndHost(boolean isEndHost) {
		this.isEndHost = isEndHost;
	}
	public boolean getInternalNodesPresence() {
		return internalNodesPresence;
	}
	public void setInternalNodesPresence(boolean internalNodesPresence) {
		this.internalNodesPresence = internalNodesPresence;
	}
	public ArrayList<String> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(ArrayList<String> propertyList) {
		this.propertyList = propertyList;
	}
	public void addProperty(String property) {
		this.propertyList.add(property);
	}
	public HashMap<String, List<String>> getHostTableList() {
		return hostTableList;
	}
	public void setHostTableList(HashMap<String, List<String>> hostTableList) {
		this.hostTableList = hostTableList;
	}
	public void addHostTable(String tableName, List<String> field) {
		this.hostTableList.put(tableName, field);
	}
	public HashMap<String, Boolean> getHostTableStatic() {
		return hostTableStatic;
	}
	public void setHostTableStatic(HashMap<String, Boolean> hostTableStatic) {
		this.hostTableStatic = hostTableStatic;
	}
	public void addHostTableStatic(String tableName, boolean staticTable) {
		this.hostTableStatic.put(tableName, staticTable);
	}
	public ArrayList<Implication> getImplication() {
		return implication;
	}
	public void setImplicationList(ArrayList<Implication> implication) {
		this.implication = implication;
	}
	public void addImplication(Implication implication) {
		this.implication.add(implication);
	}
	public ArrayList<PacketDefinition> getPacketDefinition() {
		return packetDefinition;
	}
	public void setPacketDefinitionList(ArrayList<PacketDefinition> packetDefinition) {
		this.packetDefinition = packetDefinition;
	}
	public void addPacketDefinition(PacketDefinition packetDefinition) {
		this.packetDefinition.add(packetDefinition);
	}
	public ArrayList<RightFormImplication> getRightFormImplication() {
		return rightFormImplication;
	}
	public void setRightFormImplication(ArrayList<RightFormImplication> rightFormImplication) {
		this.rightFormImplication = rightFormImplication;
	}
	
}
