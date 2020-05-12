package at.flockenberger.sirius.engine.event;

import java.awt.event.KeyListener;

import at.flockenberger.sirius.engine.input.InputState;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;

/**
 * <h1>KeyEvent</h1><br>
 * Used by the {@link Keyboard} in conjunction with the {@link KeyListener}.
 * 
 * @author Florian Wagner
 *
 */
public class KeyEvent extends InputEvent
{

	private static final long serialVersionUID = 7165295966976472105L;
	private KeyCode key;

	/**
	 * Creates a new KeyEvent.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param key       the key
	 * @param state     the state of the key
	 */
	public KeyEvent(Object src, long timeStamp, KeyCode key, InputState state)
	{
		super(src, timeStamp, state);
		this.key = key;

	}

	/**
	 * @return the {@link KeyCode} of this event
	 */
	public KeyCode getKey()
	{
		return key;
	}

}
