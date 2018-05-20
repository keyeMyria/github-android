package com.sxq.github.utils;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.Arrays;
import java.util.List;

/**
 * Helper of animation
 */
public class AnimHelper {

    private static final Interpolator mInterpolator = new LinearInterpolator();

    @UiThread
    public static void startBeatsAnimation(@NonNull View view) {
        view.clearAnimation();
        if (view.getAnimation() != null) {
            view.getAnimation().cancel();
        }
        List<ObjectAnimator> animators = getBeats(view);
        for (ObjectAnimator anim : animators) {
            anim.setDuration(300).start();
            anim.setInterpolator(mInterpolator);
        }
    }

    @UiThread
    @NonNull
    private static List<ObjectAnimator> getBeats(@NonNull View view) {
        ObjectAnimator[] animator = new ObjectAnimator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
        };
        return Arrays.asList(animator);
    }
}
