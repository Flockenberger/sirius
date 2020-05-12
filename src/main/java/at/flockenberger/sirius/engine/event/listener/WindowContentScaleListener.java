package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.WindowContentScaleEvent;

/**
 * <h1>WindowContentScaleListener</h1> <br>
 * Handles {@link WindowContentScaleEvent}s.
 * 
 * @author Florian Wagner
 *
 */
public interface WindowContentScaleListener extends EventListener
{

	/**
	 * Called when the contents of a window is scaled.
	 * 
	 * @param e the {@link WindowContentScaleEvent}
	 */
	public void onContentScaleChanged(WindowContentScaleEvent e);

}
