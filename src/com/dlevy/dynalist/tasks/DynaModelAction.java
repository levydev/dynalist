package com.dlevy.dynalist.tasks;

import com.dlevy.dynalist.model.DynaModel;


public class DynaModelAction {
	
	public static enum ActionType {

		fastConnection250Items,
		slowConnection250Items,
		fastConnection25Items,
		slowConnection25Items
	}
	
	private ActionType type;

	private DynaModel dynaModel;
 
	public DynaModelAction(ActionType type) {
		super();
		this.type = type;
	}
	
 
	 
	public DynaModel getDynaModel() {
		return dynaModel;
	}



	public void setDynaModel(DynaModel dynaModel) {
		this.dynaModel = dynaModel;
	}



	public ActionType getType() {
		return type;
	}
 	
	
}
