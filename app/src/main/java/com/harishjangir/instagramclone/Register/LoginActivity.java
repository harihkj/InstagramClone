package com.harishjangir.instagramclone.Register;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.harishjangir.instagramclone.Helper.FirebaseRules;
import com.harishjangir.instagramclone.Home.HomeActivity;
import com.harishjangir.instagramclone.R;



public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "LoginActivity";
    Context context = LoginActivity.this;

    EditText email,password;
    TextView loading;
    Button btRegister;
    ProgressBar progressBar;

    String stremail,strpassword;


    TextView Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);

        btRegister = (Button) findViewById(R.id.bt_Login);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        loading = (TextView) findViewById(R.id.tv_login_text);

        progressBar.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);

loginMethod();
setupFirebaseAuth();
        Register = (TextView) findViewById(R.id.tv_signup);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }


    private void loginMethod(){

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stremail = email.getText().toString();
                strpassword = password.getText().toString();

                if(stremail.equals("")|| strpassword.equals("")){
                    Toast.makeText(context, "Enter Email and Password", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);


                    mAuth.signInWithEmailAndPassword(stremail,strpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (!task.isSuccessful()){

                                Toast.makeText(context, "Wrong Email and Password", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                loading.setVisibility(View.GONE);
                            }
                            else
                            {
                                try{

                                    if(user.isEmailVerified()){
                                        Toast.makeText(context, "Authentication Successful", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        loading.setVisibility(View.GONE);
                                        startActivity(new Intent(context, HomeActivity.class));
                                    }else
                                    {
                                        Toast.makeText(context, "Email not verified\n Check Your Inbox.", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        loading.setVisibility(View.GONE);
                                        mAuth.signOut();
                                    }

                                }
                                catch (NullPointerException e){

                                }

                            }


                        }
                    });



                }


            }
        });
//        if (mAuth.getCurrentUser()!=null){
//            startActivity(new Intent(context,HomeActivity.class));
//        }



    }


    private void setupFirebaseAuth(){

        mAuth = FirebaseAuth.getInstance();
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


    public void resetpass(View view) {
        startActivity(new Intent(context,ResetActivity.class));
    }
}
