package youga.com.player;

import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * author: YougaKingWu@gmail.com
 * created on: 2018/10/22 16:01
 * description:
 */
public class YougaMediaPlayer implements IMediaPlayer {


    private static final LibLoader sLocalLibLoader = new LibLoader() {
        @Override
        public void loadLibrary(String libName) throws UnsatisfiedLinkError, SecurityException {
            try {
                System.loadLibrary(libName);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    };

    private static volatile boolean mIsLibLoaded = false;
    private EventHandler mEventHandler;
    private String mDataSource;

    public static void loadLibrariesOnce(LibLoader libLoader) {
        synchronized (YougaMediaPlayer.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null)
                    libLoader = sLocalLibLoader;

                libLoader.loadLibrary("youga-player");
                mIsLibLoaded = true;
            }
        }
    }

    private static volatile boolean mIsNativeInitialized = false;

    private static void initNativeOnce() {
        synchronized (YougaMediaPlayer.class) {
            if (!mIsNativeInitialized) {
                try {
                    native_init();
                    mIsNativeInitialized = true;
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public YougaMediaPlayer() {
        this(sLocalLibLoader);
    }

    public YougaMediaPlayer(LibLoader libLoader) {
        initPlayer(libLoader);
    }

    private void initPlayer(LibLoader libLoader) {
        loadLibrariesOnce(libLoader);
        initNativeOnce();

        Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            mEventHandler = new EventHandler(this, looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            mEventHandler = new EventHandler(this, looper);
        } else {
            mEventHandler = null;
        }

        /*
         * Native setup requires a weak reference to our object. It's easier to
         * create it here than in C++.
         */
        try {
            native_setup(new WeakReference<>(this));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static native void native_init();

    private native void native_setup(Object YougaMediaPlayer_this);

    private native void _setDataSource(String path, String[] keys, String[] values) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    @Override
    public void setDisplay(SurfaceHolder sh) {

    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mDataSource = path;
        _setDataSource(path, null, null);
    }

    @Override
    public void prepareAsync() throws IllegalStateException {

    }

    @Override
    public void release() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setOnPreparedListener(OnPreparedListener listener) {

    }

    private static class EventHandler extends Handler {
        private final WeakReference<YougaMediaPlayer> mWeakPlayer;

        public EventHandler(YougaMediaPlayer mp, Looper looper) {
            super(looper);
            mWeakPlayer = new WeakReference<YougaMediaPlayer>(mp);
        }

    }
}
