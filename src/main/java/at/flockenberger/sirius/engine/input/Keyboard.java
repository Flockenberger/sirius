package at.flockenberger.sirius.engine.input;

import org.lwjgl.glfw.GLFW;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Keyboard</h1><br>
 * The {@link Keyboard} class represents a physical keyboard. <br>
 * With this class you can check for key inputs using the methods
 * {@link #isKeyPressed(int)}, {@link #isKeyReleased(int)} and
 * {@link #isKeyRepeat(int)}.
 * 
 * @author Florian Wagner
 *
 */
public class Keyboard extends InputDevice {

	private static Keyboard kb;

	public static Keyboard get() {
		SUtils.checkNull(kb, "Keyboard");
		return kb;
	}

	public static void assign(Window w) {
		kb = new Keyboard(w);
	}

	private long id;

	public Keyboard(Window w) {
		this.id = w.getID();
	}

	/**
	 * @return true if the shift key (left OR right) is pressed.
	 */
	public boolean isShiftDown() {
		return (getKeyState(KeyCode.LEFT_SHIFT) == InputState.PRESSED) ? true
				: (((getKeyState(KeyCode.RIGHT_SHIFT) == InputState.PRESSED) ? true : false));
	}

	/**
	 * @return true if the control key (left OR right) is pressed.
	 */
	public boolean isControlDown() {
		return (getKeyState(KeyCode.LEFT_CONTROL) == InputState.PRESSED) ? true
				: (((getKeyState(KeyCode.RIGHT_CONTROL) == InputState.PRESSED) ? true : false));
	}

	/**
	 * @return true if the alt key (left OR right) is pressed.
	 */
	public boolean isAltDown() {
		return (getKeyState(KeyCode.LEFT_ALT) == InputState.PRESSED) ? true
				: (((getKeyState(KeyCode.RIGHT_ALT) == InputState.PRESSED) ? true : false));
	}

	/**
	 * Returns true if the given key <code> key </code> is pressed.
	 * 
	 * @param key the KeyCode to check.
	 * @return true if the key is currently pressed otherwise false.
	 */
	public boolean isKeyPressed(KeyCode key) {
		return onPressed(getKeyState(key));
	}

	/**
	 * Returns true if the given key <code> key </code> is typed.
	 * 
	 * @param key the KeyCode to check.
	 * @return true if the key is currently typed otherwise false.
	 */
	public boolean isKeyTyped(KeyCode key) {
		return onClicked(getKeyState(key));
	}

	/**
	 * Returns true if the given key <code> key </code> is released.
	 * 
	 * @param key the KeyCode to check.
	 * @return true if the key is currently released otherwise false.
	 */
	public boolean isKeyReleased(KeyCode key) {
		return onReleased(getKeyState(key));
	}

	/**
	 * Returns the key state of the given key <code> key </code>
	 * 
	 * @param key the key to check
	 * @return the key state
	 */
	public InputState getKeyState(KeyCode key) {
		return InputState.getFromInt(GLFW.glfwGetKey(id, key.getID()));
	}

	/**
	 * Returns the name of the given key.
	 * 
	 * @param key the key to get the name for
	 * @return the name of the key
	 */
	public String getKeyName(KeyCode key) {
		return key.getName();
	}
}