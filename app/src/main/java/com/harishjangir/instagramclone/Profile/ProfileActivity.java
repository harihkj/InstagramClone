package com.harishjangir.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.harishjangir.instagramclone.Helper.BottomNavigationIconHelper;
import com.harishjangir.instagramclone.Helper.GridViewImageHelper;
import com.harishjangir.instagramclone.Helper.UniversalImageHelper;
import com.harishjangir.instagramclone.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    Context context=ProfileActivity.this;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = (ImageView) findViewById(R.id.ivProfileImage);


//        setupNavigationIcon();
//        setupToolbar();
//        gridImage();
//        initImageLoader();
//        setProfileImage();

        getProfileFragment();
    }


    private void getProfileFragment(){

        Profile_Fragment profile_fragment = new Profile_Fragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,profile_fragment);
        transaction.addToBackStack("Profile_Fragment");
        transaction.commit();


    }

    public void setSupportActionBar(Toolbar toolbar) {
    }




    /*


    private void initImageLoader(){
        UniversalImageHelper universalImageHelper = new UniversalImageHelper(context);
        ImageLoader.getInstance().init(universalImageHelper.getConfig());
    }



    private void setProfileImage(){
        String imgUrl= "www.pngmart.com/files/4/Android-PNG-Image.png";
        UniversalImageHelper.setImage(imgUrl,profileImage,null,"http://");
    }


    private void gridImage(){

        ArrayList<String> imgUrls= new ArrayList<>();
        imgUrls.add("https://upload.wikimedia.org/wikipedia/commons/6/66/Android_robot.png");
        imgUrls.add("http://fifthingenium.com/wp-content/uploads/2014/08/android.png");
        imgUrls.add("https://cdn.vox-cdn.com/thumbor/CfRbYk4cgIZ-aCsCiMAfpCQewoA=/4x0:963x639/1200x800/filters:focal(4x0:963x639)/cdn.vox-cdn.com/uploads/chorus_image/image/46978008/android.0.0.jpg");
        imgUrls.add("https://hips.htvapps.com/htv-prod-media.s3.amazonaws.com/images/android-png-1499805306.png");
        imgUrls.add("http://icons.veryicon.com/ico/System/Circle/android.ico");
        imgUrls.add("http://pngimg.com/uploads/android_logo/android_logo_PNG35.png");
        imgUrls.add("http://www.pngmart.com/files/4/Android-PNG-Photo.png");
        imgUrls.add("https://upload.wikimedia.org/wikipedia/commons/4/43/Android_Robot_POV-Ray.png");
        imgUrls.add("http://files.softicons.com/download/object-icons/large-android-icons-by-aha-soft/png/512/Android.png");
        imgUrls.add("https://images.vexels.com/media/users/3/139556/isolated/preview/1718a076e29822051df8bcf8b5ce1124-android-logo-by-vexels.png");
        setupImageGridView(imgUrls);

    }

    private void setupImageGridView(ArrayList<String> imgUrls) {

        GridView gridview = (GridView) findViewById(R.id.gridView);
        GridViewImageHelper helper = new GridViewImageHelper(context,R.layout.layout_grid_image_view,"",imgUrls);
        gridview.setAdapter(helper);


    }


    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);

        ImageView profileMenu = (ImageView) findViewById(R.id.profilesetting) ;

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AccountSettingActivity.class));

            }
        });
    }



    private void setupNavigationIcon(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.Bottom_navigation_icon);

        BottomNavigationIconHelper.setupBottomNavigatio(bottomNavigationViewEx);
        BottomNavigationIconHelper.enableNavigationClick(context,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);



    }*/
}
