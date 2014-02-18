package com.dlevy.dynalist.model;

import com.dlevy.dynalist.R;
 
public class DynaModel {
   
    private LabelledIcon labbeledIcon;
 
    public DynaModel( LabelledIcon labbeledIcon ) {
 
    	this.labbeledIcon = labbeledIcon;
 
    }
 
 
 
	public LabelledIcon getLabbeledIcon() {
		return labbeledIcon;
	}


	public void setLabbeledIcon(LabelledIcon labbeledIcon) {
		this.labbeledIcon = labbeledIcon;
	}





	public static enum LabelledIcon{
		LANDMARKS ( R.string.label_landmarks, R.drawable.bridgemodern),
		FAMILY ( R.string.label_family, R.drawable.communitycentre),
		FRIENDS ( R.string.label_friends, R.drawable.friends),
		DINING ( R.string.label_dining, R.drawable.fastfood),
		BARS ( R.string.label_bars, R.drawable.bar),
		ENTERTAINMENT ( R.string.label_entertainment, R.drawable.music_rock),
		SHOPPING ( R.string.label_shopping, R.drawable.supermarket),
		RESTROOMS ( R.string.label_restrooms, R.drawable.toilets),
		SPORTS( R.string.label_sports, R.drawable.basketball2),
		RECREATION ( R.string.label_recreation, R.drawable.park_urban),
		LEISURE ( R.string.label_leisure, R.drawable.beach),
		HIKING_TRAIL ( R.string.label_hiking, R.drawable.hiking_tourism),
		CYCLING_TRAIL ( R.string.label_cycling, R.drawable.cycling),
		RAIL_PASSAGE ( R.string.label_rail, R.drawable.steamtrain),
		PETS ( R.string.label_pets, R.drawable.dog_leash),
		WILDLIFE ( R.string.label_wildlife, R.drawable.zoo),
		OTHER ( R.string.label_other, R.drawable.info);
  
		private final int nameKey;
		private final int drawableKey;
		
		LabelledIcon(int nameKey, int drawableKey) {
	        this.nameKey = nameKey;
	        this.drawableKey = drawableKey;
	    }

		
		public int getNameKey() {
			return nameKey;
		}


		public int getDrawableKey() {
			return drawableKey;
		}
		
		
	}

}
