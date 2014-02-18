package com.dlevy.dynalist.tasks;

import com.dlevy.dynalist.model.DynaModel;

 

public class DynaModelResult {
	DynaModelAction action;
	 

	DynaModel[] results;
	 	

	public DynaModelResult(DynaModelAction action) {
		super();
		this.action = action;
	}

	public DynaModel[] getResults() {
		return results;
	}

	public void setResults(DynaModel[] results) {
		this.results = results;
	}

	public DynaModelAction getAction() {
		return action;
	}

 
	

}
