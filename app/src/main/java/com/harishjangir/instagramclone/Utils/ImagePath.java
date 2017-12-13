package com.harishjangir.instagramclone.Utils;


import android.os.Environment;

public class ImagePath {


    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String Camera = ROOT_DIR+ "/DCIM/Camera";
    public String Pictures = ROOT_DIR + "/Pictures";


}

