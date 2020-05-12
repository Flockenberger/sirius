package at.flockenberger.sirius.engine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.event.MouseButtonEvent;
import at.flockenberger.sirius.engine.event.MouseMoveEvent;
import at.flockenberger.sirius.engine.event.MouseScrollEvent;
import at.flockenberger.sirius.engine.event.listener.MouseButtonListener;
import at.flockenberger.sirius.engine.event.listener.MouseMoveListener;
import at.flockenberger.sirius.engine.event.listener.MouseScrollListener;
import at.flockenberger.sirius.engine.graphic.Cursor;

/**
 * <h1>Mouse</h1><br>
 * The Mouse class is able to handle Mouse inputs.<br>
 * The current mouse can be obtained from the active {@link Window} by using
 * {@link Window#getMouse()}.
 * 
 * <br>
 * While there can be multiple instances of {@link Mouse} alive, only one at a
 * time can be assigned to a {@link Window}. To assign a {@link Mouse} or
 * {@link Keyboard} or any {@link InputDevice} use the method
 * {@link #assign(Window)}. <br>
 * Therefore it is recommended to use the static functionality of the
 * {@link Mouse} class. <br>
 * <b> NOTE: It is important to only call the static mouse methods <b>after</b>
 * the {@link Window#show()} method has been called! </b>
 * 
 * @author Florian Wagner
 * @see InputDevice
 * @see MouseButton
 */
public class Mouse implements InputDevice
{
	private static double[] x;
	private static double[] y;
	private static double oldX;
	private static double oldY;

	private static double dx;
	private static double dy;

	private static double deltaScrollX;
	private static double deltaScrollY;
	private static double scrollX;
	private static double scrollY;

	private static Map<Long, Mouse> windowCache;
	private List<MouseScrollListener> scrollListener;
	private List<MouseButtonListener> buttonListener;
	private List<MouseMoveListener> moveListener;

	static
	{
		windowCache = new HashMap<Long, Mouse>();
	}

	public static Mouse get()
	{
		long activeHandle = Window.getActiveHandle();
		if (windowCache.containsKey(activeHandle))
			return windowCache.get(activeHandle);
		else
		{
			Mouse m = new Mouse(activeHandle);
			windowCache.put(Window.getActiveHandle(), m);
			return m;
		}

	}

	public static void assign(Window w)
	{
		windowCache.put(w.getID(), new Mouse(w.getID()));
	}

	private long id;

	private Mouse(long _id)
	{
		this.id = _id;
		x = new double[1];
		y = new double[1];

		scrollListener = new ArrayList<>();
		buttonListener = new ArrayList<>();
		moveListener = new ArrayList<>();

		GLFW.glfwSetScrollCallback(id, (w, x, y) ->
			{
				deltaScrollX = x;
				deltaScrollY = y;
				scrollX += deltaScrollX;
				scrollY += deltaScrollY;
				for (MouseScrollListener l : scrollListener)
					l.onScroll(new MouseScrollEvent(this, System.nanoTime(), scrollX, scrollY, x, y));
			});

		GLFW.glfwSetMouseButtonCallback(id, (w, b, a, m) ->
			{
				double[] x = new double[1];
				double[] y = new double[1];

				GLFW.glfwGetCursorPos(id, x, y);
				// y[0] = CardinalUtils.map(y[0], 0, Window.getActiveWindow().getHeight(),
				// Window.getActiveWindow().getHeight(), 0);

				for (MouseButtonListener bt : buttonListener)
					bt.onMouseButton(new MouseButtonEvent(this, System.nanoTime(), MouseButton.getFromInt(b),
							InputState.getFromInt(a), x[0], y[0]));
			});

		GLFW.glfwSetCursorPosCallback(id, (w, x, y) ->
			{
				dx = oldX - x;
				dy = oldY - y;

				for (MouseMoveListener m : moveListener)
					m.onMouseMoved(new MouseMoveEvent(this, System.nanoTime(), x, y));

				oldX = x;
				oldY = y;

			});

	}

	/**
	 * Adds a {@link MouseMoveListener} to this mouse.
	 * 
	 * @param mml the {@link MouseMoveListener} to add
	 */
	public void addMoveListener(MouseMoveListener mml)
	{
		this.moveListener.add(mml);
	}

	/**
	 * Removes a {@link MouseMoveListener} from this mouse
	 * 
	 * @param mml the {@link MouseMoveListener} to remove
	 */
	public void removeMoveListener(MouseMoveListener mml)
	{
		this.moveListener.remove(mml);
	}

	/**
	 * Adds a {@link MouseScrollListener} to this mouse
	 * 
	 * @param msl the {@link MouseScrollListener} to add
	 */
	public void addScrollListener(MouseScrollListener msl)
	{
		this.scrollListener.add(msl);
	}

	/**
	 * Removes a {@link MouseScrollListener} from this mouse
	 * 
	 * @param msl the {@link MouseScrollListener} to remove
	 */
	public void removeScrollListener(MouseScrollListener msl)
	{
		this.scrollListener.remove(msl);
	}

	/**
	 * Adds a {@link MouseButtonListener} to this mouse
	 * 
	 * @param mbl the {@link MouseButtonListener} to add
	 */
	public void addButtonListener(MouseButtonListener mbl)
	{
		this.buttonListener.add(mbl);
	}

	/**
	 * Removes a {@link MouseButtonListener} from this mouse
	 * 
	 * @param mbl the {@link MouseButtonListener} to remove
	 */
	public void removeButtonListener(MouseButtonListener mbl)
	{
		this.buttonListener.remove(mbl);
	}

	/**
	 * @return true if the left mouse button is pressed
	 */
	public static boolean isLeftButtonDown()
	{
		return onPressed(getMouseState(MouseButton.LEFT));
	}

	/**
	 * @return true if the left mouse button is clicked
	 */
	public static boolean isLeftButtonClicked()
	{
		return onClicked(getMouseState(MouseButton.LEFT));
	}

	/**
	 * @return true if the left mouse button is released
	 */
	public static boolean isLeftButtonReleased()
	{
		return onReleased(getMouseState(MouseButton.LEFT));
	}

	/**
	 * @return true if the right mouse button is pressed
	 */
	public static boolean isRightButtonDown()
	{
		return onPressed(getMouseState(MouseButton.RIGHT));
	}

	/**
	 * @return true if the right mouse button is clicked
	 */
	public static boolean isRightButtonClicked()
	{
		return onClicked(getMouseState(MouseButton.RIGHT));
	}

	/**
	 * @return true if the right mouse button is released
	 */
	public static boolean isRightButtonReleased()
	{
		return onReleased(getMouseState(MouseButton.RIGHT));
	}

	/**
	 * @return true if the middle mouse button is pressed
	 */
	public static boolean isMiddleButtonDown()
	{
		InputState newState = getMouseState(MouseButton.MIDDLE);
		return onPressed(newState);
	}

	/**
	 * @return true if the middle mouse button is clicked
	 */
	public static boolean isMiddleButtonClicked()
	{
		return onClicked(getMouseState(MouseButton.MIDDLE));
	}

	/**
	 * @return true if the middle mouse button is released
	 */
	public static boolean isMiddleButtonReleased()
	{
		return onReleased(getMouseState(MouseButton.MIDDLE));
	}

	/**
	 * Checks if the given {@link MouseButton} is pressed
	 * 
	 * @param button the button to check
	 * @return true if pressed otherwise false
	 */
	public static boolean isButtonPressed(MouseButton button)
	{
		return onPressed(getMouseState(button));
	}

	/**
	 * Checks if the given {@link MouseButton} is being released
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public static boolean isButtonReleased(MouseButton button)
	{
		InputState newState = getMouseState(button);
		return onReleased(newState);
	}

	/**
	 * Checks if the given {@link MouseButton} is being clicked once
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public static boolean isButtonClicked(MouseButton button)
	{
		InputState newState = getMouseState(button);
		return onClicked(newState);
	}

	/**
	 * Checks the last state of the given {@link MouseButton}.
	 * 
	 * @param button the button to check the state for
	 * @return the {@link InputState} of this button
	 */
	public static InputState getMouseState(MouseButton button)
	{
		return InputState.getFromInt(GLFW.glfwGetMouseButton(get().id, button.getID()));
	}

	/**
	 * @return the current x position of the mouse <rb> 0 is the bottom left corner
	 *         of the window.
	 */
	public static double getX()
	{
		pollMousePosition();
		return x[0];
	}

	/**
	 * @return the current y position of the mouse <br>
	 *         0 is the top left corner of the window.
	 */
	public static double getY()
	{
		pollMousePosition();
		return y[0];
	}

	/**
	 * @return the previous x value.
	 * 
	 */
	public static double getOldX()
	{
		return oldX;
	}

	/**
	 * @return the previous y value.
	 */
	public static double getOldY()
	{
		return oldX;
	}

	/**
	 * @return the delta value of the current and previous x mouse position
	 */
	public static double getDX()
	{
		return dx;
	}

	/**
	 * @return the delta value of the current and previous y mouse position
	 */
	public static double getDY()
	{
		return dy;
	}

	/**
	 * @return the delta value from the current and previous x scroll
	 */
	public static double getDeltaScrollX()
	{
		return deltaScrollX;
	}

	/**
	 * @return the delta value from the current and previous y scroll
	 */
	public static double getDeltaScrollY()
	{
		return deltaScrollY;
	}

	/**
	 * @return the current x scroll value
	 */
	public static double getScrollX()
	{
		return scrollX;
	}

	/**
	 * @return the current y scroll value
	 */
	public static double getScrollY()
	{
		return scrollY;
	}

	/**
	 * @return true if the mouse is hovering over the window otherwise false
	 */
	public static boolean isMouseInWindow()
	{
		return (GLFW.glfwGetWindowAttrib(get().id, GLFW.GLFW_HOVERED) == 1) ? true : false;
	}

	/**
	 * Sets the current mouse cursor to the given cursor.
	 * 
	 * @param cursor the cursor to set
	 */
	public static void setCursor(Cursor cursor)
	{
		if (cursor == null)
		{
			resetCursor();
			return;
		}
		GLFW.glfwSetCursor(get().id, cursor.getID());
	}

	/**
	 * Resets the current mouse cursor to the default cursor.
	 */
	private static void resetCursor()
	{
		GLFW.glfwSetCursor(get().id, 0);
	}

	private static void pollMousePosition()
	{
		GLFW.glfwGetCursorPos(get().id, x, y);
	}

	private static InputState oldState = InputState.RELEASED;

	/**
	 * Checks whether the the given state <code>newState</code> indicates that a
	 * one-time click has been issued. <br>
	 * It is important to use this to get the desired result check otherwise
	 * repeated clicks or a press can be detected.
	 * 
	 * @param newState the state to check
	 * @return true if it was just a click otherwise false
	 */
	protected static boolean onClicked(InputState newState)
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
	protected static boolean onPressed(InputState newState)
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
	protected static boolean onReleased(InputState newState)
	{
		boolean retVal = false;
		if (newState.equals(InputState.RELEASED) && oldState.equals(InputState.PRESSED))
			retVal = true;
		oldState = newState;
		return retVal;
	}

}
