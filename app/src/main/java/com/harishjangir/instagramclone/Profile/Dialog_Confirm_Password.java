package com.harishjangir.instagramclone.Profile;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harishjangir.instagramclone.R;

public class Dialog_Confirm_Password extends DialogFragment {

    public interface OnDialog_Confirm_Password{
        public void onDialog_Confirm_Password(String password);
    }

    OnDialog_Confirm_Password mOnDialog_confirm_password;

    EditText etPassword;
    Button btConfirm,btCancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_confirm_password,container,false);

        etPassword = (EditText) view.findViewById(R.id.password_confirm);
        btCancel = (Button) view.findViewById(R.id.dialog_cancel);
        btConfirm = (Button) view.findViewById(R.id.dialog_confirm);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password =etPassword.getText().toString();

                if(!password.equals("")){
                    mOnDialog_confirm_password.onDialog_Confirm_Password(password);
                    getDialog().dismiss();
                }
                else
                {
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnDialog_confirm_password = (OnDialog_Confirm_Password)getTargetFragment();
        }catch (ClassCastException e){

        }



    }
}
