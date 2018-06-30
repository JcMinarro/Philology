package android.support.v7.app;

public class AppCompatDelegate {

    private static boolean shouldBeUsed = false;

    public static boolean isCompatVectorFromResourcesEnabled() {
        return shouldBeUsed;
    }

    public static void shouldBeUsed(boolean shouldBeUsed) {
        AppCompatDelegate.shouldBeUsed = shouldBeUsed;
    }
}
