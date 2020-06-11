package at.flockenberger.sirius.engine.input;

/**
 * <h1>InputDevice</h1><br>
 * The {@link InputDevice} class is the base class for all input devices such as
 * the {@link Keyboard}, {@link Gamepad} or {@link Mouse} class. <br>
 * 
 * @author Florian Wagner
 * @see Keyboard
 * @see Mouse
 * @see Gamepad
 */
public class InputDevice
{
	/**
	 * <h1>Device</h1><br>
	 * The {@link Device} enum tells this input device what kind of device it is
	 * 
	 * @author Florian Wagner
	 *
	 */
	public enum Device
	{
		/**
		 * mouse device
		 */
		MOUSE,

		/**
		 * keyboard device
		 */
		KEYBOARD,

		/**
		 * gamepad device
		 */
		GAMEPAD;
	}

	/**
	 * the type of this device
	 */
	private Device type;

	/**
	 * the old input state
	 */
	private InputState oldState = InputState.RELEASED;

	/**
	 * Creates a new {@link InputDevice} and intializes it.
	 * 
	 * @param type the type of device created
	 */
	public InputDevice(Device type)
	{
		this.type = type;
	}

	/**
	 * @return the type of device
	 */
	public Device getDeviceType()
	{ return this.type; }

	/**
	 * Checks whether the the given state <code>newState</code> indicates that a
	 * one-time click has been issued. <br>
	 * It is important to use this to get the desired result check otherwise
	 * repeated clicks or a press can be detected.
	 * 
	 * @param newState the state to check
	 * @return true if it was just a click otherwise false
	 */
	protected boolean onClicked(InputState newState)
	{

		boolean retVal = false;
		if (newState.equals(InputState.PRESSED) && oldState.equals(InputState.RELEASED))
			retVal = true;
		oldState = newState;
		return retVal;
	}

	/**
	 * Checks if the given state equals a pressed input action. <br>
	 * This is for example holding a key or mouse button.
	 * 
	 * @param newState the state to check
	 * @return true if the state equals a pressed state otherwise false
	 */
	protected boolean onPressed(InputState newState)
	{
		return newState.equals(InputState.PRESSED);
	}

	/**
	 * /** Checks whether the the given state <code>newState</code> indicates that a
	 * button or key has been released. <br>
	 * It is important to use this check otherwise repeated clicks or a press can be
	 * detected.
	 * 
	 * @param newState the state to check
	 * @return true if it was a release action otherwise false
	 * 
	 */
	protected boolean onReleased(InputState newState)
	{
		boolean retVal = false;
		if (newState.equals(InputState.RELEASED) && oldState.equals(InputState.PRESSED))
			retVal = true;
		oldState = newState;
		return retVal;
	}
}
