package it.polito.parser;

public class NodeAddressCondition extends Condition {
	String node;
	String address;

	public NodeAddressCondition(String node, String address, boolean validity) {
		this.type = ConditionType.node_has_addr;
		this.node = node;
		this.address = address;
		this.validity = validity;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Node: " +this.node);
		System.out.println("Address: " +this.address);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.node.equalsIgnoreCase( ((NodeAddressCondition)c1).getNode() ) &&
					this.address.equalsIgnoreCase( ((NodeAddressCondition)c1).getAddress() ) &&
					this.validity == ((NodeAddressCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}

}
