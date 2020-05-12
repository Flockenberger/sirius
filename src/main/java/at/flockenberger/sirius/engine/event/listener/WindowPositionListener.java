
package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.WindowPositionEvent;

/**
 * <h1>WindowPositionListner</h1><br>
 * Handles {@link WindowPositionEvent}s.
 * 
 * @author Florian Wagner
 *
 */
public interface WindowPositionListener extends EventListener
{
	/**
	 * Called whenever the window position was changed
	 * 
	 * @param e the {@link WindowPositionEvent}
	 */
	public void onPositionChanged(WindowPositionEvent e);

}
