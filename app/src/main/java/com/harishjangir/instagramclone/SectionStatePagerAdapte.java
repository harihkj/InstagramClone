package com.harishjangir.instagramclone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harishjangir.instagramclone.Profile.EditProfileFragment;
import com.harishjangir.instagramclone.Profile.SignoutFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harish Jangir on 10/7/2017.
 */

public class SectionStatePagerAdapte extends FragmentStatePagerAdapter {

    private  final List<Fragment> list  = new ArrayList<>();

    private final HashMap<Fragment,Integer>  mFragment = new HashMap<>();

    private final HashMap<String,Integer>  mFragmentNumber = new HashMap<>();

    private final HashMap<Integer,String >  mFragmentName = new HashMap<>();



    public SectionStatePagerAdapte(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public void addFragment(Fragment fragment,String fragmentName){
        list.add(fragment);
        mFragment.put(fragment,list.size()-1);
        mFragmentNumber.put(fragmentName,list.size()-1);
        mFragmentName.put(list.size()-1,fragmentName);
    }


    public Integer getFragmentNumber(String frarmentName){
        if (mFragmentNumber.containsKey(frarmentName)){
            return mFragmentNumber.get(frarmentName);
        }
        else
        {
            return null;
        }
    }

    public Integer getFragmentNumber(Fragment frarment){
        if (mFragmentNumber.containsKey(frarment)){
            return mFragmentNumber.get(frarment);
        }
        else
        {
            return null;
        }
    }

    public String getFragmentName(Integer frarmentNumber){
        if (mFragmentName.containsKey(frarmentNumber)){
            return mFragmentName.get(frarmentNumber);
        }
        else
        {
            return null;
        }
    }



}
