package com.harishjangir.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.harishjangir.instagramclone.Helper.BottomNavigationIconHelper;
import com.harishjangir.instagramclone.R;
import com.harishjangir.instagramclone.SectionStatePagerAdapte;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by Harish Jangir on 10/6/2017.
 */

public class AccountSettingActivity extends AppCompatActivity {

    Context context = AccountSettingActivity.this;
    ViewPager viewPager;
    RelativeLayout relativeLayout;
    SectionStatePagerAdapte sectionStatePagerAdapte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        viewPager = (ViewPager) findViewById(R.id.container);
        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);



        setupNavigationIcon();
        setupSettingList();

        ImageView imageView = (ImageView) findViewById(R.id.backArrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setupFragment();
        getExtraIntent();
    }

    private void getExtraIntent(){
        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.calling_activity))){
            setViewPager(sectionStatePagerAdapte.getFragmentNumber("Edit Profile"));
        }

    }


    private void setupNavigationIcon(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.Bottom_navigation_icon);

        BottomNavigationIconHelper.setupBottomNavigatio(bottomNavigationViewEx);
        BottomNavigationIconHelper.enableNavigationClick(context,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);



    }
    public void setupFragment(){

        sectionStatePagerAdapte = new SectionStatePagerAdapte(getSupportFragmentManager());
        sectionStatePagerAdapte.addFragment(new EditProfileFragment(),"Edit Profile");
        sectionStatePagerAdapte.addFragment(new SignoutFragment(),"Sign Out");
    }

    public void setViewPager(int fragmentNumber){
        relativeLayout.setVisibility(View.GONE);
        viewPager.setAdapter(sectionStatePagerAdapte);
        viewPager.setCurrentItem(fragmentNumber);
    }

    private void setupSettingList(){

        ListView listView = (ListView) findViewById(R.id.lvAccountSetings);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Edit Profile");
        arrayList.add("Sign Out");

        ArrayAdapter adapter = new ArrayAdapter(AccountSettingActivity.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setViewPager(i);
            }
        });



    }
}
