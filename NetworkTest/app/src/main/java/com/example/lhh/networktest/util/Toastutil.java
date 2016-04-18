package com.example.lhh.networktest.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LHH on 2016/4/18.
 */
public class Toastutil {
    public static void ShowToast(Context context, CharSequence text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
