package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.WindowCloseEvent;

/**
 * <h1>WindowCloseListener</h1><br>
 * Handles {@link WindowCloseEvent}s.
 * 
 * @author Florian Wagner
 *
 */
public interface WindowCloseListener extends EventListener
{
	/**
	 * Called whenever the window is closed
	 * 
	 * @param e the {@link WindowCloseEvent}
	 */
	public void onWindowClose(WindowCloseEvent e);

}
