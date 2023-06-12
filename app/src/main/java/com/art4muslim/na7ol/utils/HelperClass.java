package com.art4muslim.na7ol.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.art4muslim.na7ol.R;

public class HelperClass {
    //
    public static void replaceFragment(FragmentTransaction transaction, Fragment fragment) {
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static void replaceSmallFragment(FragmentTransaction transaction, Fragment fragment, int frame) {
        transaction.replace(frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static void replaceFragmentWithoutBack(FragmentTransaction transaction, Fragment fragment) {
        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }
}
