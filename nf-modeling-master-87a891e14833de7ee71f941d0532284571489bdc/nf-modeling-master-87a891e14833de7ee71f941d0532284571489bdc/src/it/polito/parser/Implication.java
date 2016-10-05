package it.polito.parser;

import java.util.ArrayList;

public class Implication {
	ArrayList<Condition> ifConditions = new ArrayList<Condition>();
	ArrayList<Condition> thenConditions = new ArrayList<Condition>();
	Condition result;
	
	public ArrayList<Condition> getIfConditions() {
		return ifConditions;
	}
	public void setIfConditions(ArrayList<Condition> ifConditions) {
		this.ifConditions = ifConditions;
	}
	public void addIfCondition(Condition condition) {
		this.ifConditions.add(condition);
	}
	public ArrayList<Condition> getThenConditions() {
		return thenConditions;
	}
	public void setThenConditions(ArrayList<Condition> thenConditions) {
		this.thenConditions = thenConditions;
	}
	public void addThenCondition(Condition condition) {
		this.thenConditions.add(condition);
	}
	public Condition getResult() {
		return result;
	}
	public void setResult(Condition result) {
		this.result = result;
	}
}
