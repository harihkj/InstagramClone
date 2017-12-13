package com.harishjangir.instagramclone.Add;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.harishjangir.instagramclone.Helper.BottomNavigationIconHelper;
import com.harishjangir.instagramclone.R;
import com.harishjangir.instagramclone.SectionPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";
    private static final int VERIFY_PERMISSION_REQUST = 5;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        if(checkPermissionList(Permissions.PERMISSIONS)){

        }
        else
        if(verifyPermissionList(Permissions.PERMISSIONS)){

        }
        setupViewpager();

    }


    private void setupViewpager(){

        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new Gallery_Fragment());
        sectionPagerAdapter.addFragment(new Photo_Fragment());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Gallery");
        tabLayout.getTabAt(1).setText("Photo");


    }


    private boolean checkPermissionList(String[] permissions) {

        for (int i = 0;i<permissions.length;i++){

            String check = permissions[i];
            if(!checkPermission(check)){
                return false;
            }
        }

        return true;
    }

    public boolean checkPermission(String check) {

        int permissionRequest = ActivityCompat.checkSelfPermission(AddActivity.this,check);
        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: Permissions not granted");
            return false;
        }
        else
        {
            Log.d(TAG, "checkPermission: Permissions granted");
            return true;
        }


    }

    private boolean verifyPermissionList(String[] permissions) {

        ActivityCompat.requestPermissions(AddActivity.this,permissions,VERIFY_PERMISSION_REQUST);


        return true;
    }


    public int getCurrentTabNumber() {
        return viewPager.getCurrentItem();
    }
}
