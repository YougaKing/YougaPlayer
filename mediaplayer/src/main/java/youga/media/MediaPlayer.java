package youga.media;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2018/11/08 16:18
 * @description:
 */
public class MediaPlayer {

    static {
        System.loadLibrary("media_jni");
        native_init();
    }


    private static native final void native_init();
}
