package android.os;

public class Handler {

    public final boolean post(Runnable r) {
        r.run();
        return true;
    }
}
