package it.polito.parser;

public abstract class Condition {
	ConditionType type;
	boolean validity;
	
	public ConditionType getType() {
		return type;
	}
	public void setType(ConditionType type) {
		this.type = type;
	}
	public boolean getValidity() {
		return validity;
	}
	public void setValidity(boolean validity) {
		this.validity = validity;
	}
	
	public abstract void stampa();
	public abstract boolean sameCondition(Condition c1) ;
	
	
}
