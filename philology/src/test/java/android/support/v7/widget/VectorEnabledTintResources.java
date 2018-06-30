package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

public class VectorEnabledTintResources extends Resources {

    private static boolean shouldBeUsed = false;

    public static boolean shouldBeUsed() {
        return shouldBeUsed;
    }

    public static void shouldBeUsed(boolean shouldBeUsed) {
        VectorEnabledTintResources.shouldBeUsed = shouldBeUsed;
    }

    public VectorEnabledTintResources(@NonNull final Context context, @NonNull final Resources res) {
        super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());
    }
}