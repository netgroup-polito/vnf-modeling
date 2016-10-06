package it.polito.parser;

import java.util.ArrayList;

public class IfBlockConditions {
	ArrayList<ArrayList<String>> ifConditions = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> thenConditions = new ArrayList<ArrayList<String>>();
	ArrayList<String> elseConditions = new ArrayList<String>();

	public ArrayList<ArrayList<String>> getIfConditions() {
		return ifConditions;
	}

	public void setIfConditions(ArrayList<ArrayList<String>> ifConditions) {
		this.ifConditions = ifConditions;
	}

	public ArrayList<ArrayList<String>> getThenConditions() {
		return thenConditions;
	}

	public void setThenConditions(ArrayList<ArrayList<String>> thenConditions) {
		this.thenConditions = thenConditions;
	}

	public ArrayList<String> getElseConditions() {
		return elseConditions;
	}

	public void setElseConditions(ArrayList<String> elseConditions) {
		this.elseConditions = elseConditions;
	}

	public IfBlockConditions() {
		// TODO Auto-generated constructor stub
	}

}
