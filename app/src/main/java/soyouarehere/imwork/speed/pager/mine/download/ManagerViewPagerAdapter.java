package soyouarehere.imwork.speed.pager.mine.download;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;

/**
 * Created by li.xiaodong on 2018/7/26.
 */

public class ManagerViewPagerAdapter extends FragmentPagerAdapter {

    private  List<BaseFragment> fragments;
    private  List<String> stringListTitle;

    public ManagerViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> stringListTitle) {
        super(fm);
        this.fragments =fragments;
        this.stringListTitle = stringListTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringListTitle.get(position);
    }
}
