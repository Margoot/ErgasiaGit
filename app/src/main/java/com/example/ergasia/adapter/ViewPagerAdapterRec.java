package com.example.ergasia.adapter;

/**
 * Created by Margot on 09/05/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ergasia.Activity.CandidateFragmentRec;
import com.example.ergasia.Activity.MessageFragmentRec;
import com.example.ergasia.Activity.ProfilFragmentRec;

public class ViewPagerAdapterRec extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdapterRec(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                ProfilFragmentRec tab1 = new ProfilFragmentRec();
                return tab1;
            case 1:
                CandidateFragmentRec tab2 = new CandidateFragmentRec();
                return tab2;
            case 2:
                MessageFragmentRec tab3 = new MessageFragmentRec();
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

