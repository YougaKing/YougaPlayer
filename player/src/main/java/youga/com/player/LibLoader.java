package youga.com.player;

/**
 * author: YougaKingWu@gmail.com
 * created on: 2018/10/22 16:30
 * description:
 */
public interface LibLoader {

    void loadLibrary(String libName) throws UnsatisfiedLinkError, SecurityException;
}
