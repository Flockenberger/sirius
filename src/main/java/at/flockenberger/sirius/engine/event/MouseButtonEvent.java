package at.flockenberger.sirius.engine.event;

import at.flockenberger.sirius.engine.input.InputState;
import at.flockenberger.sirius.engine.input.MouseButton;

/**
 * <h1>MouseButtonEvent</h1><br>
 * This event is called when a mouse button was used.
 * 
 * @author Florian Wagner
 *
 */
public class MouseButtonEvent extends InputEvent
{

	private static final long serialVersionUID = -4389178441397265075L;
	private MouseButton button;
	private double x;
	private double y;

	/**
	 * Creates a new {@link MouseButtonEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param button    the button that was used
	 * @param state     the state of that button
	 */
	public MouseButtonEvent(Object src, long timeStamp, MouseButton button, InputState state, double x, double y)
	{
		super(src, timeStamp, state);
		this.button = button;
		this.x = x;
		this.y = y;

	}

	/**
	 * @return the {@link MouseButton} that was used
	 */
	public MouseButton getButton()
	{
		return this.button;
	}

	/**
	 * @return the x position of the mouse
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * @return the y position of the mouse
	 */
	public double getY()
	{
		return y;
	}

}
