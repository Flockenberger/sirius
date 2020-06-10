package at.flockenberger.sirius.engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>Window</h1><br>
 * The Window class represents a window on screen. <br>
 * 
 * @author Florian Wagner
 *
 */
public class Window implements IFreeable
{

	private int width;
	private int height;
	private long id;
	private String title;
	private boolean showing = false;
	private boolean windowedFullscreen = false;
	private boolean initialized = false;
	private Icon icon;
	private boolean vsync = false;

	static int[] _width = new int[1];
	static int[] _height = new int[1];
	static int[] _size = new int[2];

	public static int getActiveWidth()
	{
		long _id = GLFW.glfwGetCurrentContext();
		GLFW.glfwGetWindowSize(_id, _width, _height);
		return _width[0];
	}

	public static int getActiveHeight()
	{
		long _id = GLFW.glfwGetCurrentContext();
		GLFW.glfwGetWindowSize(_id, _width, _height);
		return _height[0];
	}

	public static int[] getActiveSize()
	{
		long _id = GLFW.glfwGetCurrentContext();
		GLFW.glfwGetWindowSize(_id, _width, _height);
		_size[0] = _width[0];
		_size[1] = _height[0];
		return _size;
	}

	public static int[] getActiveFrameBufferSize()
	{
		long _id = GLFW.glfwGetCurrentContext();
		GLFW.glfwGetFramebufferSize(_id, _width, _height);
		_size[0] = _width[0];
		_size[1] = _height[0];
		return _size;
	}

	public static void setActiveWindowIcon(Icon icon)
	{
		GLFW.glfwSetWindowIcon(GLFW.glfwGetCurrentContext(), icon.getIcon());
	}

	public static double getTime()
	{
		return (GLFW.glfwGetTime() * 1000) / GLFW.glfwGetTimerFrequency();

	}

	public static long getActiveHandle()
	{
		long _id = GLFW.glfwGetCurrentContext();

		return _id;
	}

	public Window(boolean windowedFullscreen)
	{
		this(windowedFullscreen, "Sirius");
	}

	public Window(boolean windowedFullscreen, String title)
	{
		this(windowedFullscreen, title, null);
	}

	public Window(boolean windowedFullscreen, String title, Icon icon)
	{
		this.windowedFullscreen = windowedFullscreen;
		this.title = title;
		this.icon = icon;

	}

	/**
	 * Creates a new {@link Window}.
	 */
	public Window()
	{
		this(200, 200);
	}

	/**
	 * Creates a new {@link Window}.
	 * 
	 * @param width  the width of the window
	 * @param height the height of the window
	 */
	public Window(int width, int height)
	{
		this(width, height, "Sirius");
	}

	/**
	 * Creates a new {@link Window}.
	 * 
	 * @param width  the width of the window
	 * @param height the height of the window
	 * @param title  the title of the window
	 */
	public Window(int width, int height, String title)
	{
		this(width, height, title, null);
	}

	/**
	 * Creates a new {@link Window}.
	 * 
	 * @param width  the width of the window
	 * @param height the height of the window
	 * @param title  the title of the window
	 * @param icon   the icon of the window
	 */
	public Window(int width, int height, String title, Icon icon)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		this.icon = icon;

	}

	private void initGLFW()
	{
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
		{
			SLogger.getSystemLogger().except(new IllegalStateException("Unable to initialize GLFW"));
			return;
		}
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default

		GLFW.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
		GLFW.glfwWindowHint(GLFW_SAMPLES, 8);

		long monitor = NULL;

		if (windowedFullscreen)
		{
			monitor = GLFW.glfwGetPrimaryMonitor();
			GLFWVidMode mode = GLFW.glfwGetVideoMode(monitor);
			glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
			glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
			glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
			glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
			this.width = mode.width();
			this.height = mode.height();

		}
		id = GLFW.glfwCreateWindow(this.width, this.height, title, monitor, NULL);

		if (id == NULL)
			GLFW.glfwTerminate();

		GLFW.glfwMakeContextCurrent(id);

		enableVSync(true);

		GL.createCapabilities();

		if (windowedFullscreen)
		{
			glfwSetKeyCallback(id, (windowHnd, key, scancode, action, mods) ->
				{
					if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					{
						glfwSetWindowShouldClose(windowHnd, true);
					}
				});
		}

		Keyboard.assign(this);
		Mouse.assign(this);

		glViewport(0, 0, this.width, this.height);

		if (icon == null)
		{
			GLFW.glfwSetWindowIcon(id, GLFWImage.malloc(0));
			return;
		}

		GLFW.glfwSetWindowIcon(id, icon.getIcon());

	}

	/**
	 * Sets the window icon to the given {@link Icon} <code>icon</code> <br>
	 * <b>NOTE: It method may only be called <b> after </b> the
	 * {@link Window#show()} method has been called!</b>
	 * 
	 * @param icon the icon to set the window to
	 */
	public void setIcon(Icon icon)
	{
		this.icon = icon;
		if (isShowing())
		{
			if (icon == null)
			{
				GLFW.glfwSetWindowIcon(id, GLFWImage.malloc(0));
				return;
			}

			GLFW.glfwSetWindowIcon(id, icon.getIcon());
		}
	}

	/**
	 * En- or Disables VSync for this window.<br>
	 * 
	 * @param vsync if true VSync will be enabled otherwise disabled
	 */
	public void enableVSync(boolean vsync)
	{
		glfwSwapInterval((vsync == true) ? 1 : 0);
		this.vsync = vsync;
	}

	/**
	 * Shows the window.<br>
	 * Internally this method initializes GLFW and other important window handling
	 * functionality as well as the {@link Keyboard} and {@link Mouse} classes to
	 * function properly.
	 * 
	 * If the window is already showing nothing happens.
	 */
	public void show()
	{
		if (!initialized)
		{
			initGLFW();
			setIcon(this.icon);
			initialized = true;
		}

		if (!isShowing())
		{
			glfwShowWindow(id);
			showing = true;
		}
	}

	/**
	 * @return true if the window is showing otherwise false
	 */
	public boolean isShowing()
	{ return this.showing; }

	/**
	 * Iconifies (minimizes) the specified window if it was previously restored. If
	 * the window is already iconified, this function does nothing.
	 */
	public void minimize()
	{
		GLFW.glfwIconifyWindow(id);
	}

	/**
	 * Maximizes the specified window if it was previously not maximized. If the
	 * window is already maximized, this function does nothing.
	 */
	public void maximize()
	{
		GLFW.glfwMaximizeWindow(id);
	}

	/**
	 * Restores the specified window if it was previously iconified (minimized) or
	 * maximized. If the window is already restored, this function does nothing.
	 */
	public void restore()
	{
		GLFW.glfwRestoreWindow(id);
	}

	/**
	 * Resizes the window to the given <code>width</code> and <code>height</code>
	 * 
	 * @param width  the new width of the window
	 * @param height the new height of the window
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		GLFW.glfwSetWindowSize(id, this.width, this.height);
		glViewport(0, 0, this.width, this.height);
	}

	/**
	 * Checks if the window should be closed.<br>
	 * 
	 * @return true if it should be closed (i.e by pressing the close button of the
	 *         window) otherwise false
	 */
	public boolean askClose()
	{
		return GLFW.glfwWindowShouldClose(id);
	}

	public void setFullscreen()
	{
		this.windowedFullscreen = true;
	}

	/**
	 * Updates the window<br>
	 * Swaps the front and back buffers of the specified window when rendering with
	 * OpenGL or OpenGL ES. If the swap interval is greater than zero, the GPUdriver
	 * waits the specified number of screen updates before swapping the buffers.
	 * <br>
	 * Processes all pending events.
	 */
	public void update()
	{
		glfwPollEvents();
		glfwSwapBuffers(id); // swap the color buffers

	}

	/**
	 * Destroys the specified window and its context. On calling this function, no
	 * further callbacks will be called for that window.
	 */
	@Override
	public void free()
	{
		glfwFreeCallbacks(id);
		glfwDestroyWindow(id);

		// Terminate GLFW and free the error callback
		glfwTerminate();

	}

	/**
	 * @return the id (handle) of this window
	 */
	public long getID()
	{ return id; }

	public boolean isVSyncEnabled()
	{ return this.vsync; }

	public String getTitle()
	{ return title; }

}
