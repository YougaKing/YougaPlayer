package youga.com.player.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;

import java.lang.ref.WeakReference;

import youga.com.player.IMediaPlayer;

/**
 * author: YougaKingWu@gmail.com
 * created on: 2018/10/22 15:34
 * description:
 */
public class TextureRenderView extends TextureView implements IRenderView {

    private final SurfaceCallback mSurfaceCallback;

    public TextureRenderView(Context context) {
        this(context, null);
    }

    public TextureRenderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextureRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceCallback = new SurfaceCallback(this);
        setSurfaceTextureListener(mSurfaceCallback);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void addRenderCallback(@NonNull IRenderCallback callback) {

    }

    @Override
    public void removeRenderCallback(@NonNull IRenderCallback callback) {

    }

    public ISurfaceHolder getSurfaceHolder() {
        return new InternalSurfaceHolder(this, mSurfaceCallback.mSurfaceTexture);
    }

    private static final class SurfaceCallback implements SurfaceTextureListener {

        private final WeakReference<TextureRenderView> mWeakRenderView;
        public SurfaceTexture mSurfaceTexture;

        public SurfaceCallback(@NonNull TextureRenderView renderView) {
            mWeakRenderView = new WeakReference<>(renderView);
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mSurfaceTexture = surface;
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            mSurfaceTexture = surface;
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            mSurfaceTexture = surface;
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    private static final class InternalSurfaceHolder implements IRenderView.ISurfaceHolder {

        private TextureRenderView mTextureView;
        private SurfaceTexture mSurfaceTexture;

        public InternalSurfaceHolder(@NonNull TextureRenderView textureView,
                                     @Nullable SurfaceTexture surfaceTexture) {
            mTextureView = textureView;
            mSurfaceTexture = surfaceTexture;
        }

        @Override
        public void bindToMediaPlayer(IMediaPlayer mp) {

        }

        @NonNull
        @Override
        public IRenderView getRenderView() {
            return null;
        }

        @Nullable
        @Override
        public SurfaceHolder getSurfaceHolder() {
            return null;
        }

        @Nullable
        @Override
        public Surface openSurface() {
            return null;
        }

        @Nullable
        @Override
        public SurfaceTexture getSurfaceTexture() {
            return null;
        }
    }
}
