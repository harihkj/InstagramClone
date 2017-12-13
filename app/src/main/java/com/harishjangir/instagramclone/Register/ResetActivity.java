package com.harishjangir.instagramclone.Register;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.harishjangir.instagramclone.Helper.FirebaseRules;
import com.harishjangir.instagramclone.R;

public class ResetActivity extends AppCompatActivity {

    EditText etEmail;
    Button btreset;
    FirebaseAuth mAuth;
    FirebaseRules firebaseRules;

    Context context = ResetActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        etEmail = (EditText) findViewById(R.id.input_email);
        btreset = (Button) findViewById(R.id.btReset);

        mAuth = FirebaseAuth.getInstance();
        firebaseRules = new FirebaseRules(this);

        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (email.equals("")){
                    Toast.makeText(context, "Enter Register Email...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseRules.resetUserpassword(email);
                    mAuth.signOut();
                }
            }
        });
        



    }
}
