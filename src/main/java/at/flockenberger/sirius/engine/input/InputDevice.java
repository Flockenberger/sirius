package at.flockenberger.sirius.engine.input;

/**
 * <h1>InputDevice</h1><br>
 * The {@link InputDevice} class is the base class for all input devices such as
 * the {@link Keyboard} or {@link Mouse} class.
 * 
 * @author Florian Wagner
 * @see Keyboard
 * @see Mouse
 * @see Gamepad
 */
public class InputDevice
{
	public enum Device
	{
		MOUSE, KEYBOARD, GAMEPAD;
	}

	private Device type;

	public InputDevice(Device type)
	{
		this.type = type;
	}

	/**
	 * @return the type of device
	 */
	public Device getDeviceType()
	{ return this.type; }
}
