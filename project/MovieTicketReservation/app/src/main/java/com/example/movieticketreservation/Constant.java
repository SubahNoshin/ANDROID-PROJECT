package com.example.movieticketreservation;

import android.app.Activity;
import android.widget.Toast;

public class Constant {
    private static Toast mToast;
    public static void showSafeToast(Activity activity,String message){
        try{
            if(mToast != null) mToast.cancel();
            mToast = Toast.makeText(activity,message,Toast.LENGTH_LONG);
            mToast.show();
        }catch (Exception ignored){}
    }
}
