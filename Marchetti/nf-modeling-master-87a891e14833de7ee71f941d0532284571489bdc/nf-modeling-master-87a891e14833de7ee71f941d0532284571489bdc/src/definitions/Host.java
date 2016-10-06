package definitions;


public abstract class Host {
	public State state;
	public Address address;
	public HostPropertyList hostPropertyList;
	
	public abstract void defineState();

	public abstract RoutingResult onReceivedPacket(Packet packet);
}
