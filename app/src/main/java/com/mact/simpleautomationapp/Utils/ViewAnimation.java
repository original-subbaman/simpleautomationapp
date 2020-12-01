package com.mact.simpleautomationapp.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class ViewAnimation {
    public static boolean rotateFAB(final View v, boolean isRotate){
        v.animate().setDuration(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(isRotate ? 360f : 0f);

        return isRotate;
    }
}
