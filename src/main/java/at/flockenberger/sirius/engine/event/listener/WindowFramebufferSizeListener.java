package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.WindowFrameBufferSizeEvent;

/**
 * <h1>WindowFramebufferSizeListener</h1><br>
 * Handles {@link WindowFrameBufferSizeEvent}s.
 * 
 * @author Florian Wagner
 *
 */
public interface WindowFramebufferSizeListener extends EventListener
{
	/**
	 * Called whenever the framebuffer was resized
	 * 
	 * @param e the {@link WindowFrameBufferSizeEvent}
	 */
	public void onFrambufferSize(WindowFrameBufferSizeEvent e);

}
