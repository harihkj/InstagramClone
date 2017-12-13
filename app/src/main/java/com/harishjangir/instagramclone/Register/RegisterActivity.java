package com.harishjangir.instagramclone.Register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harishjangir.instagramclone.Helper.FirebaseRules;

import com.harishjangir.instagramclone.R;

/**
 * Created by Harish Jangir on 01-11-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "RegisterActivity";
    Context context = RegisterActivity.this;

    EditText email,password,name;
    TextView loading;
    Button btRegister;
    ProgressBar progressBar;

    String stremail,strname,strpassword;
    String append;

    FirebaseRules firebaseRules;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseRules = new FirebaseRules(RegisterActivity.this);

        Fields();
        init();
        setupFirebaseAuth();



    }

    private void Fields(){

        email = (EditText) findViewById(R.id.input_email);
        name = (EditText) findViewById(R.id.input_name);
        password = (EditText) findViewById(R.id.input_password);

        btRegister = (Button) findViewById(R.id.bt_Login);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        loading = (TextView) findViewById(R.id.tv_login_text);


    }

    private void init(){
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stremail = email.getText().toString();
                strname = name.getText().toString();
                strpassword = password.getText().toString();

                if (checkInput(stremail,strname,strpassword)){
                    progressBar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);

                    firebaseRules.registerUserEmail(stremail,strname,strpassword);
                }


            }
        });
    }

    private boolean isStringnull(String str){
        if (str.equals("")){
            return  true;
        }
        else
            return false;
    }

    private boolean checkInput(String stremail, String strname, String strpassword) {

        if(stremail.equals("")||strname.equals("")|| strpassword.equals("")){
            Toast.makeText(context,"Check Fields",Toast.LENGTH_SHORT);
            return false;
        }
        else
            return  true;


    }


    private void setupFirebaseAuth(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference  = firebaseDatabase.getReference();

        mAuth =FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Log.d(TAG, "onAuthStateChanged: Sign_in"+user.getUid());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(firebaseRules.checkExistUser(strname,dataSnapshot)){
                                append = databaseReference.getKey();
                            }
                            strname =strname + append;
                            firebaseRules.AddnewUser(stremail,strname,"","","");
                            Toast.makeText(context, "Signup Successfull.\n Sending Verification Email.", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                    Log.d(TAG, "onAuthStateChanged: sign_out");


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


}
