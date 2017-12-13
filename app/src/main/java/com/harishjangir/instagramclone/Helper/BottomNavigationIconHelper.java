package com.harishjangir.instagramclone.Helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.harishjangir.instagramclone.Add.AddActivity;
import com.harishjangir.instagramclone.Home.HomeActivity;
import com.harishjangir.instagramclone.Profile.ProfileActivity;
import com.harishjangir.instagramclone.R;
import com.harishjangir.instagramclone.Search.SearchActivity;
import com.harishjangir.instagramclone.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Harish Jangir on 9/22/2017.
 */

public class BottomNavigationIconHelper {

    public static void setupBottomNavigatio(BottomNavigationViewEx bottomNavigationViewEx)
    {
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);

    }

    public static void enableNavigationClick(final Context context, BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        context.startActivity(new Intent(context, HomeActivity.class));
                        break;
                    case R.id.Search:
                        context.startActivity(new Intent(context, SearchActivity.class));
                        break;
                    case R.id.Add:
                        context.startActivity(new Intent(context, AddActivity.class));
                        break;
                    case R.id.Share:
                        context.startActivity(new Intent(context, ShareActivity.class));
                        break;
                    case R.id.Profile:
                        context.startActivity(new Intent(context, ProfileActivity.class));
                        break;



                }




                return false;
            }
        });
    }
}
