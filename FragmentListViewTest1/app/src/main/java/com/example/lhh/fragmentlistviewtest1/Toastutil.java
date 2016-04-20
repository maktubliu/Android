package com.example.lhh.fragmentlistviewtest1;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LHH on 2016/4/20.
 */
public class Toastutil {
    public static void ShowToast(Context context, CharSequence text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
