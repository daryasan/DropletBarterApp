package com.example.dropletbarterapp.validators;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public Boolean checkNullsAndGetToast(String message, Context context, String... checked) {
        for (String s : checked) {
            if (s == null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void getToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public Boolean checkNullsAndGetToast(String message, Context context, String checked) {
        if (checked == null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
