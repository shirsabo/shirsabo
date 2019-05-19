package components;
import biuoop.DrawSurface;
/**
 * The interface Animation.
 * @author Shir sabo
 */
public interface Animation {
    /**
     *  Do one frame.
     * @param d GUI DrawSurface
     */
    void doOneFrame(DrawSurface d);
    /**
     *  Return if the loop should stop.
     * @return if should stop
     */
    boolean shouldStop();
}
