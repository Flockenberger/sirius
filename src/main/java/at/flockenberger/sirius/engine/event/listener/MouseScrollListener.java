package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.MouseScrollEvent;

/**
 * <h1>MouseScrollListener</h1><br>
 * Handles {@link MouseScrollEvent}s.F
 * 
 * @author Florian Wagner
 *
 */
public interface MouseScrollListener extends EventListener
{

	/**
	 * Called whenever the mouse is being scrolled
	 * 
	 * @param e the {@link MouseScrollEvent}
	 */
	public void onScroll(MouseScrollEvent e);

}
