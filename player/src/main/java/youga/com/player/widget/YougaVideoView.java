package youga.com.player.widget;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;

import java.io.IOException;

import youga.com.player.IMediaController;
import youga.com.player.IMediaPlayer;
import youga.com.player.IMediaPlayer.OnPreparedListener;
import youga.com.player.YougaMediaPlayer;
import youga.com.player.widget.IRenderView.IRenderCallback;
import youga.com.player.widget.IRenderView.ISurfaceHolder;

/**
 * author: YougaKingWu@gmail.com
 * created on: 2018/10/22 15:17
 * description:
 */
public class YougaVideoView extends FrameLayout implements MediaController.MediaPlayerControl {

    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    private final Context mAppContext;
    private IRenderView mRenderView;
    private IMediaPlayer mMediaPlayer = null;
    private ISurfaceHolder mSurfaceHolder;
    private Uri mUri;

    private int mCurrentBufferPercentage;

    private long mPrepareStartTime;

    private IMediaController mMediaController;

    public YougaVideoView(@NonNull Context context) {
        this(context, null);
    }

    public YougaVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YougaVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAppContext = context.getApplicationContext();
        setRenderView();
    }

    private void setRenderView() {
        if (mRenderView != null) {
            if (mMediaPlayer != null)
                mMediaPlayer.setDisplay(null);

            View renderUIView = mRenderView.getView();
            mRenderView.removeRenderCallback(mIRenderCallback);
            mRenderView = null;
            removeView(renderUIView);
        }

        TextureRenderView renderView = new TextureRenderView(getContext());
        if (mMediaPlayer != null) {
            renderView.getSurfaceHolder().bindToMediaPlayer(mMediaPlayer);
        }

        mRenderView = renderView;

        View renderUIView = mRenderView.getView();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        renderUIView.setLayoutParams(lp);
        addView(renderUIView);

        mRenderView.addRenderCallback(mIRenderCallback);
    }

    public void setVideoURI(Uri uri) {
        mUri = uri;
        openVideo();
        requestLayout();
        invalidate();
    }

    private void openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            // not ready for playback just yet, will try again later
            return;
        }

        release(false);

        AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);


        try {
            mMediaPlayer = createPlayer();

            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mCurrentBufferPercentage = 0;
            mMediaPlayer.setDataSource(mUri.toString());
            bindSurfaceHolder(mMediaPlayer, mSurfaceHolder);

            mPrepareStartTime = System.currentTimeMillis();
            mMediaPlayer.prepareAsync();

            mCurrentState = STATE_PREPARING;
            attachMediaController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMediaController(IMediaController controller) {
        if (mMediaController != null) {
            mMediaController.hide();
        }
        mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (mMediaPlayer != null && mMediaController != null) {
            mMediaController.setMediaPlayer(this);
            View anchorView = this.getParent() instanceof View ? (View) this.getParent() : this;
            mMediaController.setAnchorView(anchorView);
        }
    }

    private IMediaPlayer createPlayer() {
        YougaMediaPlayer mediaPlayer = null;
        if (mUri != null) {
            mediaPlayer = new YougaMediaPlayer();
        }
        return mediaPlayer;
    }

    public void release(boolean clearTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            // REMOVED: mPendingSubtitleTracks.clear();
            mCurrentState = STATE_IDLE;
            if (clearTargetState) {
                mTargetState = STATE_IDLE;
            }
            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


    private OnPreparedListener mPreparedListener = new OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {

        }
    };

    private void bindSurfaceHolder(IMediaPlayer mp, IRenderView.ISurfaceHolder holder) {
        if (mp == null)
            return;

        if (holder == null) {
            mp.setDisplay(null);
            return;
        }

        holder.bindToMediaPlayer(mp);
    }

    private IRenderCallback mIRenderCallback = new IRenderCallback() {
        @Override
        public void onSurfaceCreated(@NonNull ISurfaceHolder holder, int width, int height) {
            mSurfaceHolder = holder;
            if (mMediaPlayer != null) {
                bindSurfaceHolder(mMediaPlayer, holder);
            } else {
                openVideo();
            }
        }

        @Override
        public void onSurfaceChanged(@NonNull ISurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void onSurfaceDestroyed(@NonNull ISurfaceHolder holder) {

        }
    };
}
