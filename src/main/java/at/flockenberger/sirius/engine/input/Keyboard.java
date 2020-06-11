package at.flockenberger.sirius.engine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.event.KeyEvent;
import at.flockenberger.sirius.engine.event.listener.KeyListener;

/**
 * <h1>Keyboard</h1><br>
 * The {@link Keyboard} class represents a physical keyboard. <br>
 * With this class you can check for key inputs using the methods
 * {@link #isKeyPressed(int)}, {@link #isKeyReleased(int)} and
 * {@link #isKeyRepeat(int)}.
 * 
 * <br>
 * It is recommended to use the static functionality of the {@link Keyboard}
 * class. <br>
 * <b> NOTE: It is important to only call the static keyboard methods
 * <b>after</b> the {@link Window#show()} method has been called! </b>
 * 
 * @author Florian Wagner
 * @see InputDevice
 */
public class Keyboard extends InputDevice
{

	/**
	 * the window id associated with this keyboard
	 */
	private long id;

	/**
	 * cache for windows and keyboards
	 */
	private static Map<Long, Keyboard> windowCache;

	static
	{
		windowCache = new HashMap<Long, Keyboard>();
	}

	/**
	 * Retrieves a {@link Keyboard} for the current active {@link Window}
	 * 
	 * @return a new, or cached, {@link Keyboard} for the current active
	 *         {@link Window}
	 */
	public static Keyboard get()
	{
		long activeHandle = Window.getActiveHandle();
		if (windowCache.containsKey(activeHandle))
			return windowCache.get(activeHandle);
		else
		{
			Keyboard m = new Keyboard(activeHandle);
			windowCache.put(Window.getActiveHandle(), m);
			return m;
		}

	}

	private Keyboard(long _id)
	{
		super(InputDevice.Device.KEYBOARD);

		this.id = _id;
		keyListener = new ArrayList<>();

		GLFW.glfwSetKeyCallback(id, (w, k, s, a, m) ->
			{
				for (KeyListener l : keyListener)
				{
					l.onKey(new KeyEvent(this, System.nanoTime(), KeyCode.getFromInt(k), InputState.getFromInt(a)));
				}
			});

	}

	private List<KeyListener> keyListener;

	/**
	 * Adds a {@link KeyListener} to this keyboard.
	 * 
	 * @param kl the {@link KeyListener} to add
	 */
	public void addKeyListener(KeyListener kl)
	{
		keyListener.add(kl);
	}

	/**
	 * Removes a {@link KeyListener} from this keyboard.
	 * 
	 * @param kl the {@link KeyListener} to remove
	 */
	/**
	 * @param kl
	 */
	public void removeKeyListener(KeyListener kl)
	{
		keyListener.remove(kl);
	}

	/**
	 * @return true if the shift key (left OR right) is pressed.
	 */
	public boolean isShiftDown()
	{
		return (getKeyState(KeyCode.LEFT_SHIFT) == InputState.PRESSED) ? true
				: (((getKeyState(KeyCode.RIGHT_SHIFT) == InputState.PRESSED) ? true : false));
	}

	/**
	 * @return true if the control key (left OR right) is pressed.
	 */
	public boolean isControlDown()
	{
		return (getKeyState(KeyCode.LEFT_CONTROL) == InputState.PRESSED) ? true
				: (((getKeyState(KeyCode.RIGHT_CONTROL) == InputState.PRESSED) ? true : false));
	}

	/**
	 * @return true if the alt key (left OR right) is pressed.
	 */
	public boolean isAltDown()
	{
		return (getKeyState(KeyCode.LEFT_ALT) == InputState.PRESSED) ? true
				: (((getKeyState(KeyCode.RIGHT_ALT) == InputState.PRESSED) ? true : false));
	}

	/**
	 * Returns true if the given key <code> key </code> is pressed.
	 * 
	 * @param key the KeyCode to check.
	 * @return true if the key is currently pressed otherwise false.
	 */
	public boolean isKeyPressed(KeyCode key)
	{
		return onPressed(getKeyState(key));
	}

	/**
	 * Returns true if the given key <code> key </code> is typed.
	 * 
	 * @param key the KeyCode to check.
	 * @return true if the key is currently typed otherwise false.
	 */
	public boolean isKeyTyped(KeyCode key)
	{
		return onClicked(getKeyState(key));
	}

	/**
	 * Returns true if the given key <code> key </code> is released.
	 * 
	 * @param key the KeyCode to check.
	 * @return true if the key is currently released otherwise false.
	 */
	public boolean isKeyReleased(KeyCode key)
	{
		return onReleased(getKeyState(key));
	}

	/**
	 * Returns the key state of the given key <code> key </code>
	 * 
	 * @param key the key to check
	 * @return the key state
	 */
	public InputState getKeyState(KeyCode key)
	{
		return InputState.getFromInt(GLFW.glfwGetKey(this.id, key.getID()));
	}

	/**
	 * Returns the name of the given key.
	 * 
	 * @param key the key to get the name for
	 * @return the name of the key
	 */
	public String getKeyName(KeyCode key)
	{
		return key.getName();
	}
}