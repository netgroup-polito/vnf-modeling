package it.polito.parser;

public class InternalNodeCondition extends Condition{
	String node;
	
	
	public InternalNodeCondition(String node, boolean validity) {
		this.type = ConditionType.internal_node;
		this.node = node;
		this.validity = validity;
	}


	public String getNode() {
		return node;
	}


	public void setNode(String node) {
		this.node = node;
	}


	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("Node: " +this.node);
		System.out.println("Validity: "+this.validity);
	}


	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.node.equalsIgnoreCase( ((InternalNodeCondition)c1).getNode() ) &&
					this.validity == ((InternalNodeCondition)c1).getValidity()  
					) {
				return true;
			}
		}
		return false;
	}
	
}
