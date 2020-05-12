package at.flockenberger.sirius.engine.event.listener;

import at.flockenberger.sirius.engine.event.EventListener;
import at.flockenberger.sirius.engine.event.KeyEvent;

/**
 * <h1>KeyListener</h1><br>
 * This KeyListener handles {@link KeyEvent}s.
 * 
 * @see KeyEvent
 * @author Florian Wagner
 *
 */
public interface KeyListener extends EventListener
{
	/**
	 * Called when a key was being used
	 * 
	 * @param e the {@link KeyEvent}
	 */
	public void onKey(KeyEvent e);

}
