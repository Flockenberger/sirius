package at.flockenberger.sirius.engine.event;

import at.flockenberger.sirius.engine.input.InputState;

/**
 * <h1>InputEvent</h1><br>
 * The {@link InputEvent} class is the base event class for {@link KeyEvent} and
 * {@link MouseButtonEvent}.
 * 
 * @author Florian Wagner
 *
 */
public class InputEvent extends EventBase
{

	private static final long serialVersionUID = -7163801615519802576L;

	/**
	 * Creates a new InputEvent.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param state     the input state
	 */
	public InputEvent(Object src, long timeStamp, InputState state)
	{
		super(src, timeStamp);
		this.state = state;
	}

	private InputState state;

	/**
	 * @return the input state
	 */
	public InputState getState()
	{
		return state;
	}

}
