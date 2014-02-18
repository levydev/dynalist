package com.dlevy.dynalist;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlevy.dynalist.model.DynaModel;
import com.dlevy.dynalist.model.DynaModel.LabelledIcon;
import com.dlevy.dynalist.tasks.DynaModelAction;
import com.dlevy.dynalist.tasks.DynaModelAction.ActionType;
import com.dlevy.dynalist.tasks.DynaModelTask;
 
public class DynaListFragment extends ListFragment 
				implements ActionBar.OnNavigationListener , DynaModelTask.UIContext  , OnScrollListener  {
    
	
	private List<DynaModel> models;
 
	private DynaModelAdapter adapter;
	private boolean isLoading;
	private View loadingView;

	private ActionBar actionBar;

	private ActionType actionType;

	private boolean firstTimeIn;

	private Integer lastNavState;
	
	private final int AUTOLOAD_25_THRESHOLD = 4;
	private final int AUTOLOAD_250_THRESHOLD = 40;
 
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		models = new ArrayList<DynaModel>();
        setRetainInstance(true);


       
    }
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Context context = getActivity();
		adapter = new DynaModelAdapter(context, models);
		loadingView = LayoutInflater.from(context).inflate(R.layout.loading_view, null);
		getListView().addFooterView(loadingView, null, false);
		setListAdapter(adapter);
		getListView().setOnScrollListener(this);
	}
    

 


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        
        firstTimeIn = true;
        
    	View v = super.onCreateView(inflater, parent, savedInstanceState);
        
        setupActionBar();
 
        return v;
    }

 
 



	private void setupActionBar() {
    	// Set up the action bar to show a dropdown list.
	    actionBar = getActivity().getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
							getString(R.string.fast_connection_25),
							getString(R.string.slow_connection_25),
							getString(R.string.fast_connection_250),
							getString(R.string.slow_connection_250)}), this);
		
		if ( lastNavState == null ) {
			lastNavState = new Integer(2);
		}
		
		actionBar.setSelectedNavigationItem(lastNavState);
	
		 
	}


	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
  
    }
    
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
	
			lastNavState = position;
			
       		if ( position == 0) {
        		actionType = ActionType.fastConnection25Items;
        	}
        	else if (  position == 1) {
        		actionType = ActionType.slowConnection25Items;
        	}
        	else if ( position == 2) {
        		actionType = ActionType.fastConnection250Items;
        	}	
           	else if (position == 3) {
        		actionType = ActionType.slowConnection250Items;
        	}
       		
			if ( ! firstTimeIn ) {
				  models.clear();
					
				  setLoadingState();
				  loadNextItems();
		    }
			 firstTimeIn = false; 
       		 
        
	 	return true;
	}

 

    private static class DynaModelAdapter extends ArrayAdapter<DynaModel> {
        
    	private final LayoutInflater mInflater;
    	
     
    	
    	public DynaModelAdapter(Context context,  List<DynaModel> dynaModels) {
            super(context, -1 , dynaModels);
            mInflater = LayoutInflater.from(context);
            
      
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final DynaModel model = getItem(position);
            final ViewHolder holder;
            
            if (null == convertView) {
                convertView = mInflater.
                		inflate(R.layout.dyna_list_row, parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
            	 holder = (ViewHolder)convertView.getTag();
            }
    
            LabelledIcon labelledIcon  = model.getLabbeledIcon();
            if ( labelledIcon == null ) {
            	holder.imageText.setText("Loading...");
            	holder.icon.setImageBitmap(null);
            	holder.summary.setText(null);
            }
            else {
	            int bitmapKey = labelledIcon.getDrawableKey();
	            holder.icon.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), bitmapKey));
	            
	            int labelKey = labelledIcon.getNameKey();
	            holder.imageText.setText(getContext().getResources().getString(labelKey));
	            
	            holder.summary.setText(position + 1  + " of " + this.getCount());
            }
            
            return convertView;
        }
    }


    private void loadNextItems() {

        actionType = ActionType.fastConnection250Items;
       	if ( actionBar   != null) {
   
       		if ( actionBar.getSelectedNavigationIndex() == 0) {
        		actionType = ActionType.fastConnection25Items;
        	}
        	else if ( actionBar.getSelectedNavigationIndex() == 1) {
        		actionType = ActionType.slowConnection25Items;
        	}
        	else if ( actionBar.getSelectedNavigationIndex() == 3) {
        		actionType = ActionType.slowConnection250Items;
        	}	
       	}
    	
      	DynaModelAction action = new DynaModelAction(actionType);
    	DynaModelTask task = new DynaModelTask(this);
  		
    	task.execute(action);
		
	}

	@Override
	public void onDynaModelsLoaded(DynaModel[] dynaModels) {
 
		isLoading = false;
		getListView().removeFooterView(loadingView);
 
		for ( int i = 0 ; i < dynaModels.length ; i++ ) {
			models.add(dynaModels[i]);
		}
		
		 adapter.notifyDataSetChanged();
		 

		
	}
	
    static class ViewHolder {
        ImageView icon;
        TextView imageText;
        TextView summary;
        
        
        ViewHolder(View v  ) {
 
        icon  = (ImageView)v.findViewById(R.id.icon);
        imageText = (TextView)v.findViewById(R.id.imageText);
        summary = (TextView)v.findViewById(R.id.summary);
 	
        }
 
    }


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		  if ( isLoading   ) {
			  return;
		  }
		  int autoLoadThreshold = lastNavState < 2 ? AUTOLOAD_25_THRESHOLD : AUTOLOAD_250_THRESHOLD;
		  boolean loadMore = 
				  firstVisibleItem + visibleItemCount >= ( totalItemCount - autoLoadThreshold);
		  if ( loadMore ) { 
			  setLoadingState();
			  loadNextItems();

		  }
		
	}


	private void setLoadingState() {
		  isLoading = true;
		  if ( ! firstTimeIn ) {
			  getListView().addFooterView(loadingView, null, false);
		  }
		  adapter.notifyDataSetChanged();

	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
    
    
    
	
}

