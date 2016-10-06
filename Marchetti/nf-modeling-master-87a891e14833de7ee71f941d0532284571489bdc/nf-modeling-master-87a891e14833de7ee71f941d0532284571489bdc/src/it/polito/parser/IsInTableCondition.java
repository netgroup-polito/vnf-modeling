package it.polito.parser;

import java.util.ArrayList;

public class IsInTableCondition extends Condition{
	String tableName;
	ArrayList<String> field;

	public IsInTableCondition(String tableName,	ArrayList<String> field, boolean validity) {
		this.type = ConditionType.is_in_table;
		this.tableName = tableName;
		this.field = field;
		this.validity = validity;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<String> getField() {
		return field;
	}

	public void setField(ArrayList<String> field) {
		this.field = field;
	}

	@Override
	public void stampa() {
		System.out.println(this.type);
		System.out.println("TableName: " +this.tableName);
		System.out.println("Fields: " +this.field);
		System.out.println("Validity: "+this.validity);
	}

	@Override
	public boolean sameCondition(Condition c1) {
		if(c1.getType()==this.getType()) {
			if(this.tableName.equalsIgnoreCase( ((IsInTableCondition)c1).getTableName() ) &&
					this.validity == ((IsInTableCondition)c1).getValidity()  
					) {
				if(this.field.size()!= ((IsInTableCondition)c1).getField().size()) {
					return false;
				}
				for(int i = 0; i<this.field.size();i++) {
					if(!this.field.get(i).equalsIgnoreCase(((IsInTableCondition)c1).getField().get(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	
}
