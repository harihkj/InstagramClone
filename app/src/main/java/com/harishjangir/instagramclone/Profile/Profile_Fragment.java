package com.harishjangir.instagramclone.Profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harishjangir.instagramclone.Helper.BottomNavigationIconHelper;
import com.harishjangir.instagramclone.Helper.FirebaseRules;
import com.harishjangir.instagramclone.Helper.UniversalImageHelper;
import com.harishjangir.instagramclone.Model.UserSettings;
import com.harishjangir.instagramclone.Model.User_Account_Settings;
import com.harishjangir.instagramclone.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;


public class Profile_Fragment extends Fragment {

    // Field
    TextView mPost,mFollowing,mFollowers,mDisplay,mWebsite,mUsername,mDescription,mEditprofile;
    ImageView mProfileImage,mProfilemenu;
    GridView gridView;
    Toolbar toolbar;
    Context mContext;
    BottomNavigationViewEx bottomNavigationViewEx;

    // Firebase Vars
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "Profile_Fragment";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRules firebaseRules;

    String userID;
    UserSettings mUserSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        firebaseRules= new FirebaseRules(getActivity());

        mPost = (TextView) view.findViewById(R.id.tvPosts);
        mFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        mFollowers = (TextView) view.findViewById(R.id.tvFollowers);
        mDisplay = (TextView) view.findViewById(R.id.tvDisplay_name);
        mWebsite = (TextView) view.findViewById(R.id.tvWebsite);
        mUsername = (TextView) view.findViewById(R.id.profileName);
        mDescription = (TextView) view.findViewById(R.id.tvDescription);
        mEditprofile = (TextView) view.findViewById(R.id.tvEditProfile);

        mProfilemenu = (ImageView) view.findViewById(R.id.profilesetting) ;
        mProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage) ;

        toolbar = (Toolbar) view.findViewById(R.id.profileToolbar);
        bottomNavigationViewEx = (BottomNavigationViewEx) view.findViewById(R.id.Bottom_navigation_icon);
        mContext =getActivity();

        mEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AccountSettingActivity.class);
                intent.putExtra(getString(R.string.calling_activity),getString(R.string.profile_activity));
                startActivity(intent);
            }
        });


        setupFirebaseAuth();
        setupNavigationIcon();
        setupToolbar();
//        gridImage();
        initImageLoader();
//        setProfileImage();
        return view;
    }


    private void getAlldata(UserSettings userSettings){
        User_Account_Settings user_account_settings = userSettings.getUser_account_settings();

        UniversalImageHelper.setImage(user_account_settings.getProfile_photo(),mProfileImage,null,"");

        mDisplay.setText(user_account_settings.getDisplay_name());
        mDescription.setText(user_account_settings.getDescripition());
        mWebsite.setText(user_account_settings.getWebsite());
        mPost.setText(String.valueOf(user_account_settings.getPosts()));
        mFollowers.setText(String.valueOf(user_account_settings.getFollowers()));
        mFollowing.setText(String.valueOf(user_account_settings.getFollowing()));
        mUsername.setText(user_account_settings.getUsername());

    }



    private void initImageLoader(){
        UniversalImageHelper universalImageHelper = new UniversalImageHelper(mContext);
        ImageLoader.getInstance().init(universalImageHelper.getConfig());
    }



//    private void setProfileImage(){
//        String imgUrl= "www.pngmart.com/files/4/Android-PNG-Image.png";
//
//    }
    private void setupToolbar(){

        ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);


        mProfilemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,AccountSettingActivity.class));

            }
        });
    }



    private void setupNavigationIcon(){

        BottomNavigationIconHelper.setupBottomNavigatio(bottomNavigationViewEx);
        BottomNavigationIconHelper.enableNavigationClick(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

    }


    private void setupFirebaseAuth(){

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        userID = mAuth.getCurrentUser().getUid();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: sign_in"+user.getUid());

                }
                else
                {
                    Log.d(TAG, "onAuthStateChanged: sign_out");
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    getAlldata(firebaseRules.userSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListner != null)
            mAuth.removeAuthStateListener(mAuthListner);
    }
}
