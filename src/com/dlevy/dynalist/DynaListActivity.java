package com.dlevy.dynalist;

 
import com.mportal.android.ui.mpfragments.SimpleFragmentActivity;

import android.support.v4.app.Fragment;

 public class DynaListActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DynaListFragment();
    }
}
