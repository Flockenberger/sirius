
package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.WindowSizeEvent;

/**
 * <h1>WindowSizeListener</h1><br>
 * Handles {@link WindowSizeEvent}s.
 * 
 * @author Florian Wagner
 *
 */
public interface WindowSizeListener extends EventListener
{
	/**
	 * Called whenever the window was resized.
	 * 
	 * @param e the {@link WindowSizeEvent}
	 */
	public void onSizeChanged(WindowSizeEvent e);

}
