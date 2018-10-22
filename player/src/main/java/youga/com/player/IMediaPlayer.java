package youga.com.player;

import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * author: YougaKingWu@gmail.com
 * created on: 2018/10/22 15:26
 * description:
 */
public interface IMediaPlayer {

    void setDisplay(SurfaceHolder sh);

    void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    void prepareAsync() throws IllegalStateException;

    void release();

    void reset();

    void setOnPreparedListener(OnPreparedListener listener);

    interface OnPreparedListener {
        void onPrepared(IMediaPlayer mp);
    }
}
