package it.polito.parser;

import java.util.ArrayList;

public class RightFormImplication {
	ArrayList<Condition> beforeImplication = new ArrayList<Condition>();
	ArrayList<Condition> afterImplication = new ArrayList<Condition>();
	int orCondition = -1;

	public RightFormImplication() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Condition> getBeforeImplication() {
		return beforeImplication;
	}

	public void setBeforeImplication(ArrayList<Condition> beforeImplication) {
		this.beforeImplication = beforeImplication;
	}
	
	public void addBeforeImplicationCondition(Condition beforeImplication) {
		this.beforeImplication.add(beforeImplication);
	}

	public ArrayList<Condition> getAfterImplication() {
		return afterImplication;
	}

	public void setAfterImplication(ArrayList<Condition> afterImplication) {
		this.afterImplication = afterImplication;
	}
	
	public void addAfterImplicationCondition(Condition afterImplication) {
		this.afterImplication.add(afterImplication);
	}

	public int getOrCondition() {
		return orCondition;
	}

	public void setOrCondition(int orCondition) {
		this.orCondition = orCondition;
	}
	
	

}
