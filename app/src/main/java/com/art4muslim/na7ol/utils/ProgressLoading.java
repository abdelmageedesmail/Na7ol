package com.art4muslim.na7ol.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class ProgressLoading {

    private KProgressHUD show;
    Context context;

    public ProgressLoading(Context context) {
        this.context = context;
    }

    public void showLoading() {

        show = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public void cancelLoading() {
        show.dismiss();
    }
}
