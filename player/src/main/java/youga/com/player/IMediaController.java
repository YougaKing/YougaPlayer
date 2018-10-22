package youga.com.player;

import android.view.View;

import static android.widget.MediaController.MediaPlayerControl;

/**
 * author: YougaKingWu@gmail.com
 * created on: 2018/10/22 16:07
 * description:
 */
public interface IMediaController {

    void hide();

    void setAnchorView(View view);

    void setMediaPlayer(MediaPlayerControl player);
}
