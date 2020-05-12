
package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.MouseButtonEvent;

/**
 * <h1>MouseButtonListener</h1><br>
 * This Listener handles {@link MouseButtonEvent}.
 * 
 * @author Florian Wagner
 *
 */
public interface MouseButtonListener extends EventListener
{
	/**
	 * Called when ever a mouse button is used
	 * 
	 * @param e the {@link MouseButtonEvent}
	 */
	public void onMouseButton(MouseButtonEvent e);

}
