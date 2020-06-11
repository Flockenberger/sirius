package at.flockenberger.sirius.engine.input;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class Gamepad extends InputDevice
{	
	/**
	 * the current state of the gamepad
	 */
	private GLFWGamepadState currentState;

	/**
	 * the id of the gamepad
	 */
	private int id;

	/**
	 * cache for the 16 gamepads available
	 */
	private static Map<Gamepads, Gamepad> cache;
	static
	{
		cache = new HashMap<Gamepads, Gamepad>(16);
	}

	/**
	 * Checks whether the given {@link Gamepad} is present (plugged in / detected)
	 * 
	 * @param id the id of the gamepad to check
	 * @return true if present otherwise false
	 */
	public static boolean isGamepadPresent(Gamepads id)
	{
		return GLFW.glfwJoystickIsGamepad(id.getID());
	}

	/**
	 * Retrieves the first {@link Gamepad} which is present.<br>
	 * 
	 * @return the first Gamepad which is present
	 */
	public static Gamepad getFirstPresent()
	{
		for (Gamepads g : Gamepads.values())
			if (isGamepadPresent(g))
			{ return get(g); }
		return null;
	}

	/**
	 * Retrieves the given Gamepad for this id.<br>
	 * 
	 * @param id the id of the Gamepad
	 * @return a new {@link Gamepad} instance or null
	 */
	public static Gamepad get(Gamepads id)
	{
		SUtils.checkNull(id, "Gamepads");

		if (isGamepadPresent(id))
		{
			if (cache.containsKey(id))
			{
				return cache.get(id);
			} else
			{
				Gamepad gp = new Gamepad(id.getID());
				cache.put(id, gp);
				return gp;
			}
		}

		else
			SLogger.getSystemLogger()
					.error("The given Gamepad with id: " + id.name() + " is not plugged in or not a Gamepad!");
		return null;
	}

	/**
	 * private constructor.<br>
	 * intializes the gamepad
	 * 
	 * @param id the id of this gamepad
	 */
	private Gamepad(int id)
	{
		super(InputDevice.Device.GAMEPAD);
		this.id = id;
		this.currentState = GLFWGamepadState.create();
	}

	/**
	 * @return the name of this gamepad
	 */
	public String getGamepadName()
	{ return GLFW.glfwGetGamepadName(this.id); }

	/**
	 * @return a {@link Vector2f} with the left joystick positions in the range of
	 *         -1.0 and 1.0
	 */
	public Vector2f getLeftJoystick()
	{
		Vector2f vec = new Vector2f(0, 0);
		getLeftJoystick(vec);
		return vec;
	}

	/**
	 * @return a {@link Vector2f} with the right joystick positions in the range of
	 *         -1.0 and 1.0
	 */
	public Vector2f getRightJoystick()
	{
		Vector2f vec = new Vector2f(0, 0);
		getRightJoystick(vec);
		return vec;
	}

	/**
	 * Retrieves the left joystick position and stores the resulting vector into the
	 * destination given with <code> dst </code>.<br>
	 * The <code> dst </code> {@link Vector2f} must not be null! <br>
	 * The vector range is between -1.0 and 1.0
	 * 
	 * @param dst the destination to store the values into
	 */
	public void getLeftJoystick(Vector2f dst)
	{
		SUtils.checkNull(dst, "Vector2f");
		dst.x = getAxisValue(GamepadAxis.LEFT_X);
		dst.y = getAxisValue(GamepadAxis.LEFT_Y);
	}

	/**
	 * Retrieves the right joystick position and stores the resulting vector into
	 * the destination given with <code> dst </code>.<br>
	 * The <code> dst </code> {@link Vector2f} must not be null!<br>
	 * The vector range is between -1.0 and 1.0
	 * 
	 * @param dst the destination to store the values into
	 */
	public void getRightJoystick(Vector2f dst)
	{
		SUtils.checkNull(dst, "Vector2f");
		dst.x = getAxisValue(GamepadAxis.RIGHT_X);
		dst.y = getAxisValue(GamepadAxis.RIGHT_Y);
	}

	/**
	 * Returns the current state of the given {@link GamepadAxis} between -1.0 and
	 * 1.0 (inclusive).
	 * 
	 * @param axis the axis to get the value from
	 * @return a float value representing the axis movement -1.0 to 1.0
	 */
	public float getAxisValue(GamepadAxis axis)
	{
		getGamepadState();
		return currentState.axes(axis.getID());
	}

	/**
	 * Checks if the given {@link GamepadButton} is pressed
	 * 
	 * @param button the button to check
	 * @return true if pressed otherwise false
	 */
	public boolean isButtonPressed(GamepadButton button)
	{
		return onPressed(getButtonState(button));
	}

	/**
	 * Checks if the given {@link GamepadButton} is being released
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public boolean isButtonReleased(GamepadButton button)
	{
		InputState newState = getButtonState(button);
		return onReleased(newState);
	}

	/**
	 * Checks if the given {@link GamepadButton} is being clicked once
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public boolean isButtonClicked(GamepadButton button)
	{
		InputState newState = getButtonState(button);
		return onClicked(newState);
	}

	/**
	 * Returns the button state of the given button <code> button </code>.
	 * 
	 * @param button the {@link GamepadButton} to get the state from
	 * @return the {@link InputState} of this button
	 */
	public InputState getButtonState(GamepadButton button)
	{
		getGamepadState();
		return InputState.getFromInt(currentState.buttons(button.getID()));
	}

	/**
	 * Checks the game state of the this gamepad
	 * 
	 * @return
	 */
	private boolean getGamepadState()
	{ return GLFW.glfwGetGamepadState(id, currentState); }

	private InputState oldState = InputState.RELEASED;

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
