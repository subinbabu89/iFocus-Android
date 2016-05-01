package com.example.dwayne.ifocus;

/**
 * Created by Dwayne on 3/15/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dwayne.ifocus.budget.BudgetFragment;
import com.example.dwayne.ifocus.health.HealthFragment;
import com.example.dwayne.ifocus.study.StudyFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BudgetFragment tab1 = new BudgetFragment();
                return tab1;
            case 1:
                StudyFragment tab2 = new StudyFragment();
                return tab2;
            case 2:
                HealthFragment tab3 = new HealthFragment();
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