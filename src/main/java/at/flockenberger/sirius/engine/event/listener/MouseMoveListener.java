package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.MouseMoveEvent;

/**
 * <h1>MouseMoveListener</h1> <br>
 * Handles {@link MouseMoveEvent}s.
 * 
 * @author Florian Wagner
 *
 */
public interface MouseMoveListener extends EventListener
{
	/**
	 * Called whenever the mouse is moved
	 * 
	 * @param e the {@link MouseMoveEvent}
	 */
	public void onMouseMoved(MouseMoveEvent e);

}
