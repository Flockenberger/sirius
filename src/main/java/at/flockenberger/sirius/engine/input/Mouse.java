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
import at.flockenberger.sirius.utillity.SUtils;

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
 * Therefore it is recommended to use the functionality of the {@link Mouse}
 * class. <br>
 * <b> NOTE: It is important to only call the mouse methods <b>after</b> the
 * {@link Window#show()} method has been called! </b>
 * 
 * @author Florian Wagner
 * @see InputDevice
 * @see MouseButton
 */
public class Mouse extends InputDevice
{
	/**
	 * container for x position polling
	 */
	private double[] x;

	/**
	 * container for y position polling
	 */
	private double[] y;

	/**
	 * old x position
	 */
	private double oldX;

	/**
	 * old y position
	 */
	private double oldY;

	/**
	 * delta x position
	 */
	private double dx;

	/**
	 * delta y position
	 */
	private double dy;

	/**
	 * delta scroll x value
	 */
	private double deltaScrollX;

	/**
	 * delta scroll y value
	 */
	private double deltaScrollY;

	/**
	 * current scroll x value
	 */
	private double scrollX;

	/**
	 * current scroll y value
	 */
	private double scrollY;

	private List<MouseScrollListener> scrollListener;
	private List<MouseButtonListener> buttonListener;
	private List<MouseMoveListener> moveListener;

	/**
	 * the id of the window associated with this mouse
	 */
	private long id;

	/**
	 * cache for windows and mice
	 */
	private static Map<Long, Mouse> windowCache;

	static
	{
		windowCache = new HashMap<Long, Mouse>();
	}

	/**
	 * Retrieves a {@link Mouse} for the current active {@link Window}
	 * 
	 * @return a new, or cached, {@link Mouse} for the current active {@link Window}
	 */
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

	private Mouse(long _id)
	{
		super(InputDevice.Device.MOUSE);
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
	public boolean isLeftButtonDown()
	{ return onPressed(getMouseState(MouseButton.LEFT)); }

	/**
	 * @return true if the left mouse button is clicked
	 */
	public boolean isLeftButtonClicked()
	{ return onClicked(getMouseState(MouseButton.LEFT)); }

	/**
	 * @return true if the left mouse button is released
	 */
	public boolean isLeftButtonReleased()
	{ return onReleased(getMouseState(MouseButton.LEFT)); }

	/**
	 * @return true if the right mouse button is pressed
	 */
	public boolean isRightButtonDown()
	{ return onPressed(getMouseState(MouseButton.RIGHT)); }

	/**
	 * @return true if the right mouse button is clicked
	 */
	public boolean isRightButtonClicked()
	{ return onClicked(getMouseState(MouseButton.RIGHT)); }

	/**
	 * @return true if the right mouse button is released
	 */
	public boolean isRightButtonReleased()
	{ return onReleased(getMouseState(MouseButton.RIGHT)); }

	/**
	 * @return true if the middle mouse button is pressed
	 */
	public boolean isMiddleButtonDown()
	{
		InputState newState = getMouseState(MouseButton.MIDDLE);
		return onPressed(newState);
	}

	/**
	 * @return true if the middle mouse button is clicked
	 */
	public boolean isMiddleButtonClicked()
	{ return onClicked(getMouseState(MouseButton.MIDDLE)); }

	/**
	 * @return true if the middle mouse button is released
	 */
	public boolean isMiddleButtonReleased()
	{ return onReleased(getMouseState(MouseButton.MIDDLE)); }

	/**
	 * Checks if the given {@link MouseButton} is pressed
	 * 
	 * @param button the button to check
	 * @return true if pressed otherwise false
	 */
	public boolean isButtonPressed(MouseButton button)
	{
		return onPressed(getMouseState(button));
	}

	/**
	 * Checks if the given {@link MouseButton} is being released
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public boolean isButtonReleased(MouseButton button)
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
	public boolean isButtonClicked(MouseButton button)
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
	public InputState getMouseState(MouseButton button)
	{
		return InputState.getFromInt(GLFW.glfwGetMouseButton(this.id, button.getID()));
	}

	/**
	 * @return the current x position of the mouse <rb> 0 is the bottom left corner
	 *         of the window.
	 */
	public double getX()
	{
		pollMousePosition();
		return x[0];
	}

	/**
	 * @return the current y position of the mouse <br>
	 *         0 is the top left corner of the window.
	 */
	public double getY()
	{
		pollMousePosition();
		return y[0];
	}

	/**
	 * @return the current x position of the mouse translated with the zero point
	 *         being in the center of the window.
	 */
	public double getSceneX()
	{
		float _activeW = Window.getActiveWidth();
		return SUtils.map(getX(), 0, _activeW, -_activeW / 2f, _activeW / 2f);
	}

	/**
	 * @return the current y position of the mouse translated with the zero point
	 *         being in the center of the window.
	 */
	public double getSceneY()
	{
		float _activeH = Window.getActiveHeight();
		return SUtils.map(getY(), 0, _activeH, -_activeH / 2f, _activeH / 2f);
	}

	/**
	 * @return the previous x value.
	 * 
	 */
	public double getOldX()
	{ return oldX; }

	/**
	 * @return the previous y value.
	 */
	public double getOldY()
	{ return oldX; }

	/**
	 * @return the delta value of the current and previous x mouse position
	 */
	public double getDX()
	{ return dx; }

	/**
	 * @return the delta value of the current and previous y mouse position
	 */
	public double getDY()
	{ return dy; }

	/**
	 * @return the delta value from the current and previous x scroll
	 */
	public double getDeltaScrollX()
	{ return deltaScrollX; }

	/**
	 * @return the delta value from the current and previous y scroll
	 */
	public double getDeltaScrollY()
	{ return deltaScrollY; }

	/**
	 * @return the current x scroll value
	 */
	public double getScrollX()
	{ return scrollX; }

	/**
	 * @return the current y scroll value
	 */
	public double getScrollY()
	{ return scrollY; }

	/**
	 * @return true if the mouse is hovering over the window otherwise false
	 */
	public boolean isMouseInWindow()
	{ return (GLFW.glfwGetWindowAttrib(this.id, GLFW.GLFW_HOVERED) == 1) ? true : false; }

	/**
	 * Sets the current mouse cursor to the given cursor.
	 * 
	 * @param cursor the cursor to set
	 */
	public void setCursor(Cursor cursor)
	{
		if (cursor == null)
		{
			resetCursor();
			return;
		}
		GLFW.glfwSetCursor(this.id, cursor.getID());
	}

	/**
	 * Resets the current mouse cursor to the default cursor.
	 */
	private void resetCursor()
	{
		GLFW.glfwSetCursor(this.id, 0);
	}

	private void pollMousePosition()
	{
		GLFW.glfwGetCursorPos(this.id, x, y);
	}

}
