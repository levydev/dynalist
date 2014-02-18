package com.dlevy.dynalist.tasks;
 
import android.os.AsyncTask;

import com.dlevy.dynalist.model.DynaModel;
import com.dlevy.dynalist.model.DynaModel.LabelledIcon;
 
public class DynaModelTask extends AsyncTask<DynaModelAction, Void, DynaModelResult>  {

	public interface  UIContext {     
		void onDynaModelsLoaded( DynaModel[] models);	 
	}
	
 

 	private UIContext uiContext;
	private int itemsPerRequest;
 
   	public DynaModelTask() {

	}

	public DynaModelTask(UIContext uiContext ) {
		this();
		this.uiContext = uiContext; 
	}


	@Override
	protected DynaModelResult doInBackground(DynaModelAction... actions) {

		DynaModelAction action = (DynaModelAction)actions[0];
		DynaModelResult result = new DynaModelResult(action);
		switch ( action.getType() ) {
			case fastConnection25Items:	{
				itemsPerRequest = 25;
				sleep(200);
				result.setResults(mockResults());
				break;
			}
			case slowConnection25Items: {
				itemsPerRequest = 25;
				sleep(2000);
				result.setResults(mockResults());
				break;
			}
			case fastConnection250Items:	{
				itemsPerRequest = 250;
				sleep(2000);
				result.setResults(mockResults());
				break;
			}
			case slowConnection250Items: {
				itemsPerRequest = 250;
				sleep(5000);
				result.setResults(mockResults());
				break;
			}
			
			default: {
				
			}
		}
 
		return result;
	}


	protected void onPostExecute(DynaModelResult  result ) {

		if (uiContext == null ) return;

		uiContext.onDynaModelsLoaded( result.getResults());	


	}

 
	private DynaModel[] mockResults ( ) {
		
		LabelledIcon[] labelledIcons =  LabelledIcon.values();
 
		DynaModel[]  models = new DynaModel[itemsPerRequest];
		int iconInt = 0;
		for ( int i = 0 ; i < itemsPerRequest ; i++ ) {
			models[i] = new DynaModel(labelledIcons[iconInt++]);
			if ( iconInt > 16 ) iconInt = 0;

		}
	 
		return models;

	}
 
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  
 
}
