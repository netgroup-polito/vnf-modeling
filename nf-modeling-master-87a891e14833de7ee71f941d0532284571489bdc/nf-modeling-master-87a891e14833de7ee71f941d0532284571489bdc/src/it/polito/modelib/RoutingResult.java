package it.polito.modelib;

public class RoutingResult {
	
	/*
	 * The NF processing result can be
	 * - FORWARD 	-> the packet is propagated upstream
	 * - DROP 		-> the packet is discarded
	 */
	public enum Result { FORWARD, DROP, UNKNOW };
	
	public enum ForwardDirection { SAME_INTERFACE, UPSTREAM, UNSPECIFIED };
	
	protected Packet packet;
	protected Result result;
	protected ForwardDirection forwardDirection;
	
	public RoutingResult(Packet packet, Result result, ForwardDirection forwardDirection) {
		this.packet = packet;
		this.result = result;
		this.forwardDirection = forwardDirection;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ForwardDirection getForwardDirection() {
		return forwardDirection;
	}

	public void setForwardDirection(ForwardDirection forwardDirection) {
		this.forwardDirection = forwardDirection;
	}

}
