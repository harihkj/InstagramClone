package com.harishjangir.instagramclone.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harishjangir.instagramclone.Helper.BottomNavigationIconHelper;
import com.harishjangir.instagramclone.R;
import com.harishjangir.instagramclone.Register.LoginActivity;
import com.harishjangir.instagramclone.SectionPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "HomeActivity";
    Context context = HomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setupFirebaseAuth();
        setupNavigationIcon();
        setUpViewPager();





    }


    private void CheckUser(FirebaseUser user){
        if(user==null){
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    private void setupFirebaseAuth(){

        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                CheckUser(user);
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: sign_in"+user.getUid());

                }
                else
                {
                    Log.d(TAG, "onAuthStateChanged: sign_out");
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListner != null)
            mAuth.removeAuthStateListener(mAuthListner);
    }

    private void setupNavigationIcon(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.Bottom_navigation_icon);

        BottomNavigationIconHelper.setupBottomNavigatio(bottomNavigationViewEx);
        BottomNavigationIconHelper.enableNavigationClick(HomeActivity.this,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);



    }


    public void setUpViewPager(){
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new CameraFragment());
        sectionPagerAdapter.addFragment(new HomeFragment());
        sectionPagerAdapter.addFragment(new MessagesFragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue));
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_messages);



    }
}
