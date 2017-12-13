package com.harishjangir.instagramclone.Profile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.harishjangir.instagramclone.Helper.FirebaseRules;
import com.harishjangir.instagramclone.Helper.UniversalImageHelper;
import com.harishjangir.instagramclone.Model.UserSettings;
import com.harishjangir.instagramclone.Model.User_Account_Settings;
import com.harishjangir.instagramclone.R;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by Harish Jangir on 10/7/2017.
 */

public class EditProfileFragment extends Fragment implements Dialog_Confirm_Password.OnDialog_Confirm_Password {

    ///Firebase Variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "EditProfileFragment";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRules firebaseRules;

    String userID;
    UserSettings mUserSettings;

    EditText mUsername,mName,mBio,mWebsite,mEmail,mMobile;
    

    ImageView imageProfile,imageBack,imageSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_editprofile,container,false);

        firebaseRules = new FirebaseRules(getActivity());

        imageBack = (ImageView) view.findViewById(R.id.backArrow);
        imageProfile = (ImageView) view.findViewById(R.id.Profile_Photo);
        imageSave = (ImageView) view.findViewById(R.id.saveChanges);

        mUsername = (EditText) view.findViewById(R.id.ChangeUsername);
        mBio = (EditText) view.findViewById(R.id.ChangeBio);
        mWebsite = (EditText) view.findViewById(R.id.Changeweb);
        mEmail = (EditText) view.findViewById(R.id.ChangeEmail);
        mMobile = (EditText) view.findViewById(R.id.ChangeMobile);
        mName = (EditText) view.findViewById(R.id.ChangeName);


        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewProfile();
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
//        initImageLoader();
//        setProfileImage();


        setupFirebaseAuth();
        return view;
    }


    private void setProfileFields(UserSettings userSettings){

        mUserSettings = userSettings;

        User_Account_Settings user_account_settings = userSettings.getUser_account_settings();


        UniversalImageHelper.setImage(user_account_settings.getProfile_photo(),imageProfile,null,"");

        mName.setText(user_account_settings.getDisplay_name());
        mBio.setText(user_account_settings.getDescripition());
        mWebsite.setText(user_account_settings.getWebsite());
        mEmail.setText(userSettings.getGetInfo().getEmail());
        mMobile.setText(String.valueOf(userSettings.getGetInfo().getPhone_number()));
        mUsername.setText(user_account_settings.getUsername());



    }


    private void saveNewProfile(){
        String display_username,display_email,display_bio,display_name,display_web;
        long display_phone;

        display_email = mEmail.getText().toString();
        display_bio = mBio.getText().toString();
        display_name = mName.getText().toString();
        display_phone = Long.parseLong(mMobile.getText().toString());
        display_username = mUsername.getText().toString();
        display_web = mWebsite.getText().toString();



        if (!mUserSettings.getGetInfo().getUsername().equals(display_username)){
            chechusernameifExist(display_username);
        }
        if (!(mUserSettings.getGetInfo().getPhone_number()==(display_phone))){
            Log.d(TAG, "saveNewProfile: Phone number update");
            firebaseRules.updatePhone_number(display_phone);
        }
        if (!mUserSettings.getUser_account_settings().getDisplay_name().equals(display_name)){
            firebaseRules.updateName(display_name);
        }
        if (!mUserSettings.getUser_account_settings().getDescripition().equals(display_bio)){
            firebaseRules.updateBio(display_bio);
        }
        if (!mUserSettings.getUser_account_settings().getWebsite().equals(display_web)){
            firebaseRules.updateWebsite(display_web);
        }

        if (!mUserSettings.getGetInfo().getEmail().equals(display_email)){
            Dialog_Confirm_Password dialog_confirm_password = new Dialog_Confirm_Password();
            dialog_confirm_password.show(getFragmentManager(),"Dialog_Confirm_Password");
            dialog_confirm_password.setTargetFragment(EditProfileFragment.this,1);

            Log.d(TAG, "saveNewProfile: Dialog open");
        }
        

    }


//    Update user id or Email id
    @Override
    public void onDialog_Confirm_Password(String password) {

        AuthCredential authCredential = EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(),password);

        Log.d(TAG, "onDialog_Confirm_Password: ReAuthencate user");

        mAuth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                            if (task.isSuccessful()){

                                if(task.getResult().getProviders().size()==1){
                                    Log.d(TAG, "onComplete: Email Already Exist");
                                    Toast.makeText(getActivity(), "Email Already Exist", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    mAuth.getCurrentUser().updateEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.d(TAG, "onComplete: Email update");
                                                firebaseRules.updateEmail(mEmail.getText().toString());
                                                Toast.makeText(getActivity(), "Email update", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }



                        }
                    });


                }
                else
                {

                }





            }
        });

    }



//    Update Username
    private void chechusernameifExist(final String display_username) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(getString(R.string.user_db))
                .orderByChild(getString(R.string.field_username))
                .equalTo(display_username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.equals(display_username)){
                    Log.d(TAG, "onDataChange: Username Already Exist");
                    firebaseRules.updateUsername(display_username);
                    Toast.makeText(getActivity(), "Username Already Exist", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "onDataChange: Username Update");
                    firebaseRules.updateUsername(display_username);
                    Toast.makeText(getActivity(), "Username Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                setProfileFields(firebaseRules.userSettings(dataSnapshot));
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




//    private void initImageLoader(){
//        UniversalImageHelper universalImageHelper = new UniversalImageHelper(getActivity());
//        ImageLoader.getInstance().init(universalImageHelper.getConfig());
//    }
//UniversalImageHelper.setImage(user_account_settings.getProfile_photo(),imageProfile,null,"");
//    private void setProfileImage(){
//        String imgUrl= "www.pngmart.com/files/4/Android-PNG-Image.png";
//        UniversalImageHelper.setImage(imgUrl,imageProfile,null,"http://");
//    }



}
