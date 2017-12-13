package com.harishjangir.instagramclone.Add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.harishjangir.instagramclone.R;
import com.harishjangir.instagramclone.Utils.FileSearch;
import com.harishjangir.instagramclone.Utils.ImagePath;

import java.util.ArrayList;


public class Gallery_Fragment extends Fragment {

    private static final String TAG = "Gallery_Fragment";

    Spinner spinner;
    ProgressBar progressBar;
    ImageView galleryView,close_Gallery;
    GridView gridView;
    TextView tvNext;


    ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        galleryView = (ImageView) view.findViewById(R.id.gallery_imageView);
        gridView = (GridView) view.findViewById(R.id.gridView);
        close_Gallery = (ImageView) view.findViewById(R.id.gallery_close);
        tvNext = (TextView) view.findViewById(R.id.tvNext);

        directories = new ArrayList<>();
        close_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Gallery is Closed");
                getActivity().finish();
            }
        });


        setFolder();
        return view;
    }

    private void setFolder(){

        ImagePath imagePath = new ImagePath();

        if(FileSearch.getFolderPath(imagePath.Pictures)!= null){
            directories = FileSearch.getFolderPath(imagePath.Pictures);
        }

        directories.add(imagePath.Camera);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,directories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


}
