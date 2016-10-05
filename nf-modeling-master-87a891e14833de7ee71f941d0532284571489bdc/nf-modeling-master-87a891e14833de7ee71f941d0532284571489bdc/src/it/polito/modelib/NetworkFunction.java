package it.polito.modelib;

import java.util.List;
import java.util.Map;

public abstract class NetworkFunction {	
	
	/*
	 * The NF routing table.
	 */
	protected RoutingTable rTable;
	
	/*
	 * The NF state.
	 */
	protected State state;
	
	/*
	 * The NF configuration (list option)
	 */
	protected final List<String> configList;
	
	/*
	 * The NF configuration (map option)
	 */
	protected final Map<String, String> configMap;
	
	/*
	 * Constructor
	 */
	public NetworkFunction(RoutingTable rTable, List<String> configList, Map<String, String> configMap) {
		this.rTable = rTable;
		this.configList = configList;
		this.configMap = configMap;
	}
		
	/*
	 * The main function representing the NF processing.
	 */
	public abstract RoutingResult onReceivedPacket(Packet packet);

}
