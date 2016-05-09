package com.example.ergasia.adapter;

/**
 * Created by Margot on 09/05/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ergasia.Activity.MessageFragmentPost;
import com.example.ergasia.Activity.OfferFragmentPost;
import com.example.ergasia.Activity.ProfilFragmentPost;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                ProfilFragmentPost tab1 = new ProfilFragmentPost();
                return tab1;
            case 1:
                OfferFragmentPost tab2 = new OfferFragmentPost();
                return tab2;
            case 2:
                MessageFragmentPost tab3 = new MessageFragmentPost();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
