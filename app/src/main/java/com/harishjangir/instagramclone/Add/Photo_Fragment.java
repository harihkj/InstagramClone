package com.harishjangir.instagramclone.Add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.harishjangir.instagramclone.R;


public class Photo_Fragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE=5;

    Button btCamera;
    Context context;
    private static final String TAG = "Photo_Fragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo,container,false);

        btCamera = (Button) view.findViewById(R.id.bt_Photo);

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((AddActivity)getActivity()).getCurrentTabNumber()==1){
                    if(((AddActivity)getActivity()).checkPermission(Permissions.CAMERA_PERMISSIONS[0])){

                        Log.d(TAG, "onClick: Camera is started");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,CAMERA_REQUEST_CODE);

                    }
                }
                else
                {
                    Intent intent = new Intent(getActivity(),AddActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }



            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== CAMERA_REQUEST_CODE){
            Log.d(TAG, "onActivityResult: Image Capture  Success");
        }
        
    }
}
