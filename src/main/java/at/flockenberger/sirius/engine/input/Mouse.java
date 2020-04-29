package at.flockenberger.sirius.engine.input;

import org.lwjgl.glfw.GLFW;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.graphic.Cursor;
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
 * {@link #assign(Window)}.
 * 
 * @author Florian Wagner
 *
 */
public class Mouse extends InputDevice {
	private double[] x;
	private double[] y;
	private double oldX;
	private double oldY;
	private double dx;
	private double dy;

	private double deltaScrollX;
	private double deltaScrollY;
	private double scrollX;
	private double scrollY;

	private Cursor cursor = Cursor.getDefault();

	private static Mouse m;

	public static Mouse get() {
		SUtils.checkNull(m, "Mouse");
		return m;
	}

	public static void assign(Window w) {
		m = new Mouse(w);
	}

	private long id;

	private Mouse(Window w) {
		this.id = w.getID();
		x = new double[1];
		y = new double[1];

		setCursor(cursor);
	}

	/**
	 * @return true if the left mouse button is pressed
	 */
	public boolean isLeftButtonDown() {
		return onPressed(getMouseState(MouseButton.LEFT));
	}

	/**
	 * @return true if the left mouse button is clicked
	 */
	public boolean isLeftButtonClicked() {
		return onClicked(getMouseState(MouseButton.LEFT));
	}

	/**
	 * @return true if the left mouse button is released
	 */
	public boolean isLeftButtonReleased() {
		return onReleased(getMouseState(MouseButton.LEFT));
	}

	/**
	 * @return true if the right mouse button is pressed
	 */
	public boolean isRightButtonDown() {
		return onPressed(getMouseState(MouseButton.RIGHT));
	}

	/**
	 * @return true if the right mouse button is clicked
	 */
	public boolean isRightButtonClicked() {
		return onClicked(getMouseState(MouseButton.RIGHT));
	}

	/**
	 * @return true if the right mouse button is released
	 */
	public boolean isRightButtonReleased() {
		return onReleased(getMouseState(MouseButton.RIGHT));
	}

	/**
	 * @return true if the middle mouse button is pressed
	 */
	public boolean isMiddleButtonDown() {
		InputState newState = getMouseState(MouseButton.MIDDLE);
		return onPressed(newState);
	}

	/**
	 * @return true if the middle mouse button is clicked
	 */
	public boolean isMiddleButtonClicked() {
		return onClicked(getMouseState(MouseButton.MIDDLE));
	}

	/**
	 * @return true if the middle mouse button is released
	 */
	public boolean isMiddleButtonReleased() {
		return onReleased(getMouseState(MouseButton.MIDDLE));
	}

	/**
	 * Checks if the given {@link MouseButton} is pressed
	 * 
	 * @param button the button to check
	 * @return true if pressed otherwise false
	 */
	public boolean isButtonPressed(MouseButton button) {
		return onPressed(getMouseState(button));
	}

	/**
	 * Checks if the given {@link MouseButton} is being released
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public boolean isButtonReleased(MouseButton button) {
		InputState newState = getMouseState(button);
		return onReleased(newState);
	}

	/**
	 * Checks if the given {@link MouseButton} is being clicked once
	 * 
	 * @param button the button to check
	 * @return true if released otherwise false
	 */
	public boolean isButtonClicked(MouseButton button) {
		InputState newState = getMouseState(button);
		return onClicked(newState);
	}

	/**
	 * Checks the last state of the given {@link MouseButton}.
	 * 
	 * @param button the button to check the state for
	 * @return the {@link InputState} of this button
	 */
	public InputState getMouseState(MouseButton button) {
		return InputState.getFromInt(GLFW.glfwGetMouseButton(id, button.getID()));
	}

	/**
	 * @return the current x position of the mouse <rb> 0 is the bottom left corner
	 *         of the window.
	 */
	public double getX() {
		pollMousePosition();
		return x[0];
	}

	/**
	 * @return the current y position of the mouse <br>
	 *         0 is the bottom left corner of the window.
	 */
	public double getY() {
		pollMousePosition();
		return y[0];
	}

	/**
	 * @return the previous x value.
	 * 
	 */
	public double getOldX() {
		return oldX;
	}

	/**
	 * @return the previous y value.
	 */
	public double getOldY() {
		return oldX;
	}

	/**
	 * @return the delta value of the current and previous x mouse position
	 */
	public double getDX() {
		return dx;
	}

	/**
	 * @return the delta value of the current and previous y mouse position
	 */
	public double getDY() {
		return dy;
	}

	/**
	 * @return the delta value from the current and previous x scroll
	 */
	public double getDeltaScrollX() {
		return deltaScrollX;
	}

	/**
	 * @return the delta value from the current and previous y scroll
	 */
	public double getDeltaScrollY() {
		return deltaScrollY;
	}

	/**
	 * @return the current x scroll value
	 */
	public double getScrollX() {
		return scrollX;
	}

	/**
	 * @return the current y scroll value
	 */
	public double getScrollY() {
		return scrollY;
	}

	/**
	 * @return true if the mouse is hovering over the window otherwise false
	 */
	public boolean isMouseInWindow() {
		return (GLFW.glfwGetWindowAttrib(id, GLFW.GLFW_HOVERED) == 1) ? true : false;
	}

	/**
	 * Sets the current mouse cursor to the given cursor.
	 * 
	 * @param cursor the cursor to set
	 */
	public void setCursor(Cursor cursor) {
		if (cursor == null) {
			resetCursor();
			return;
		}
		GLFW.glfwSetCursor(id, cursor.getID());
	}

	/**
	 * Resets the current mouse cursor to the default cursor.
	 */
	private void resetCursor() {
		GLFW.glfwSetCursor(id, 0);
	}

	private void pollMousePosition() {
		GLFW.glfwGetCursorPos(id, x, y);
	}

}
