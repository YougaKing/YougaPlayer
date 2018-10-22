package youga.com.sample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import youga.com.player.YougaMediaPlayer;
import youga.com.player.widget.YougaVideoView;


public class VideoActivity extends AppCompatActivity {


    private static final String TAG = "VideoActivity";
    private Uri mVideoUri;
    private AndroidMediaController mMediaController;
    private YougaVideoView mVideoView;

    public static Intent newIntent(Context context, String videoPath) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("videoPath", videoPath);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String videoPath = getIntent().getStringExtra("videoPath");
        mVideoUri = Uri.parse(videoPath);

        mMediaController = new AndroidMediaController(this, false);

        // init player
        YougaMediaPlayer.loadLibrariesOnce(null);

        mVideoView = findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);

        mVideoView.setVideoURI(mVideoUri);
        mVideoView.start();
    }
}
