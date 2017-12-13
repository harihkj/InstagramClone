package com.harishjangir.instagramclone.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.harishjangir.instagramclone.Model.GetInfo;
import com.harishjangir.instagramclone.Model.UserSettings;
import com.harishjangir.instagramclone.Model.User_Account_Settings;
import com.harishjangir.instagramclone.R;


/**
 * Created by Harish Jangir on 01-11-2017.
 */

public class FirebaseRules {

    private static final String TAG = "FirebaseRules";

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthListner;
    Context context;
    String userID;

    public FirebaseRules(Context mcontext){
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference  = firebaseDatabase.getReference();
        context =mcontext;


        if(mAuth.getCurrentUser()!=null)
            userID = mAuth.getCurrentUser().getUid();
    }



    public void registerUserEmail(String email,String username,String password){
    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()){
                Toast.makeText(context,"Email and password",Toast.LENGTH_LONG).show();
            }
            else
            {

                sendVerificationEmail();
                userID = mAuth.getCurrentUser().getUid();
//                Toast.makeText(context,"Succesful",Toast.LENGTH_LONG).show();
            }

        }
    });
}


    public boolean checkExistUser(String username, DataSnapshot dataSnapshot){

    GetInfo getInfo = new GetInfo();

    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

        getInfo.setUsername(dataSnapshot1.getValue(GetInfo.class).getUsername());

//        if (getInfo.getUsername().equals(username)){
//            username.replace(" ",".");
//        }
//        else
//            username.replace("."," ");
   }
    return false;
}



    public void AddnewUser(String email,String username,String descripition,String website,String profile_photo){

            GetInfo getInfo = new GetInfo(email, userID,username,1);
            databaseReference.child(context.getString(R.string.user_db)).child(userID).setValue(getInfo);

        User_Account_Settings user_account_settings = new User_Account_Settings(
                descripition,
                username,
                profile_photo,
                username,
                website,
                0,
                0,
                0

        );
        databaseReference.child(context.getString(R.string.user_account)).child(userID).setValue(user_account_settings);
    }



    public void sendVerificationEmail(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                    }
                    else
                    {
                        Toast.makeText(context, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void resetUserpassword(String email){

        mAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Check Your Inbox.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public UserSettings userSettings(DataSnapshot dataSnapshot){

        GetInfo getInfo = new GetInfo();
        User_Account_Settings user_account_settings = new User_Account_Settings();

        for (DataSnapshot ds:dataSnapshot.getChildren()){

         if(ds.getKey().equals(context.getString(R.string.user_account))){

             try{
                 user_account_settings.setDisplay_name(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getDisplay_name()
                 );
                 user_account_settings.setUsername(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getUsername()
                 );
                 user_account_settings.setDescripition(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getDescripition()
                 );
                 user_account_settings.setProfile_photo(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getProfile_photo()
                 );
                 user_account_settings.setWebsite(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getWebsite()
                 );user_account_settings.setFollowers(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getFollowers()
                 );
                 user_account_settings.setFollowing(
                         ds.child(userID)
                                 .getValue(User_Account_Settings.class)
                                 .getFollowing()
                 );
             }catch (NullPointerException e){
                 Log.d(TAG, "userSettings: "+e.getMessage());
             }

         }
         if(ds.getKey().equals(context.getString(R.string.user_db))){

             getInfo.setEmail(
                     ds.child(userID)
                     .getValue(GetInfo.class)
                     .getEmail()
             );
             getInfo.setPhone_number(
                     ds.child(userID)
                             .getValue(GetInfo.class)
                             .getPhone_number()
             );
             getInfo.setUser_id(
                     ds.child(userID)
                             .getValue(GetInfo.class)
                             .getUser_id()
             );
             getInfo.setUsername(
                     ds.child(userID)
                             .getValue(GetInfo.class)
                             .getUsername()
             );



         }


        }

        return new UserSettings(getInfo,user_account_settings);
    }


    public void updateUsername(String username){


        databaseReference.child(context.getString(R.string.user_db))
                .child(userID)
                .child(context.getString(R.string.field_username))
                .setValue(username);

        databaseReference.child(context.getString(R.string.user_account))
                .child(userID)
                .child(context.getString(R.string.field_username))
                .setValue(username);


    }

    public void updateWebsite(String website){
        databaseReference.child(context.getString(R.string.user_account))
                .child(userID)
                .child(context.getString(R.string.field_website))
                .setValue(website);

    }

    public void updatePhone_number(long phone){
        databaseReference.child(context.getString(R.string.user_db))
                .child(userID)
                .child(context.getString(R.string.field_mobile))
                .setValue(phone);

    }
    public void updateBio(String bio){
        databaseReference.child(context.getString(R.string.user_account))
                .child(userID)
                .child(context.getString(R.string.field_desc))
                .setValue(bio);

    }
    public void updateName(String name){
        databaseReference.child(context.getString(R.string.user_account))
                .child(userID)
                .child(context.getString(R.string.field_name))
                .setValue(name);

    }
    public void updateEmail(String email){
        databaseReference.child(context.getString(R.string.user_db))
                .child(userID)
                .child(context.getString(R.string.field_email))
                .setValue(email);

    }



}


