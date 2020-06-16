package at.flockenberger.sirius.game.application;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.audio.AudioManager;
import at.flockenberger.sirius.engine.camera.Camera;
import at.flockenberger.sirius.engine.camera.GUICamera;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.render.gl.FBO;
import at.flockenberger.sirius.utillity.logging.SLogger;

public abstract class GameBase implements IFreeable
{
	//hack so that the audio manager is initialized first
	static
	{
		Sirius.audioManager = new AudioManager();
	}

	/**
	 * target FPS
	 */
	public static int TARGET_FPS = 60;

	/**
	 * target UPS
	 */
	public static int TARGET_UPS = 30;

	/**
	 * default camera for GUI rendering
	 */
	protected final Camera DEFAULT_CAM = new GUICamera();

	/**
	 * Shows if the game is running.
	 */
	protected boolean running;

	private SLogger logger = SLogger.getSystemLogger();

	/**
	 * the game's frame buffer object
	 */
	protected FBO fbo;

	/**
	 * Default constructor for the game.
	 */
	public GameBase(int width, int height, String title)
	{
		// instantiate a new Sirius
		new Sirius(width, height, title, this);

		// load resources
		loadGameResources();

	}

	/**
	 * This should be called to initialize and start the game.
	 */
	public void start()
	{

		Sirius.window.show();
		// fbo = new FBO();

		logger.debug(Sirius.window.getTitle() + " has been started!");
		init();
		gameLoop();
		free();
	}

	/**
	 * Releases resources that where used by the game.
	 */
	@Override
	public void free()
	{
		// fbo.free();
		Sirius.free();
		logger.debug("Freeing " + Sirius.window.getTitle() + "'s resources.");

	}

	/**
	 * Initializes the game.<br>
	 * This method also calls {@link Window#show()} first which is needed to
	 * properly initialise all window handling.
	 */
	public void init()
	{
		logger.debug("Initializing " + Sirius.window.getTitle() + "...");
		Sirius.init();
		initGame(Sirius.layerStack);
		running = true;
		logger.debug("Initialization done!");

		try (MemoryStack frame = MemoryStack.stackPush())
		{
			int[] w = new int[1];
			int[] h = new int[1];
			GLFW.glfwGetFramebufferSize(getWindow().getID(), w, h);
			fbo = new FBO(w[0], h[0]);

		}

		GLFW.glfwSetFramebufferSizeCallback(Sirius.window.getID(), new GLFWFramebufferSizeCallback()
		{
			@Override
			public void invoke(long window, int width, int height)
			{
				if (width > 0 && height > 0 && (fbo.getWidth() != width || fbo.getHeight() != height))
				{
					fbo.setWidth(width);
					fbo.setHeight(height);
					fbo.setResetFramebuffer(true);
				}
			}
		});
		fbo.createFramebufferObject();
	}

	/**
	 * @return the window of this game
	 */
	public Window getWindow()
	{ return Sirius.window; }

	/**
	 * Initializes the game and its layers.
	 */
	public abstract void initGame(LayerStack gameLayers);

	/**
	 * The game loop. <br>
	 */
	public abstract void gameLoop();

	/**
	 * Called as the very first method to load all game-resouces.
	 */
	public abstract void loadGameResources();

	/**
	 * Sets the target FPS for this game
	 * 
	 * @param fps the fps count to target
	 */
	public void setTargetFPS(int fps)
	{ TARGET_FPS = fps; }

	/**
	 * Sets the game to windowed fullscreen-mode.<br>
	 * Note: This method needs to be called before the {@link #start()} method to
	 * work
	 */
	public void setFullscreen()
	{
		Sirius.window.setFullscreen();
	}

	/**
	 * Sets the game's icon.<br>
	 * 
	 * @param icon the icon to set
	 */
	public void setGameIcon(Icon icon)
	{
		Sirius.icon = icon;
		Sirius.window.setIcon(icon);
	}

	/**
	 * @return the default camera used for gui rendering
	 */
	public Camera getGUICamera()
	{ return DEFAULT_CAM; }

	/**
	 * Handles input.
	 */
	public void input()
	{
		Sirius.layerStack.onInput();
	}

	/**
	 * Updates the game (fixed timestep).
	 */
	public void update()
	{
		fbo.update();
		Sirius.layerStack.onUpdate();
	}

	public void applyPostProcessing(PostProcessor pp)
	{
		Sirius.layerStack.onPostProcess(pp);
	}

	/**
	 * Updates the game (variable timestep).
	 *
	 * @param delta Time difference in seconds
	 */
	public void update(float delta)
	{
		Sirius.layerStack.onUpdate(delta);
	}

	/**
	 * Renders the game (no interpolation).
	 */
	public void render()
	{
		Sirius.layerStack.onRender(Sirius.renderer);
	}

	/**
	 * Renders the game (with interpolation).
	 *
	 * @param alpha Alpha value, needed for interpolation
	 */
	public void render(float alpha)
	{
		Sirius.layerStack.onRender(Sirius.renderer, alpha);
	}

	/**
	 * Synchronizes the game at specified frames per second.
	 *
	 * @param fps Frames per second
	 */
	public void sync(int fps)
	{
		double lastLoopTime = Sirius.timer.getLastLoopTime();
		double now = Sirius.timer.getTime();
		float targetTime = 1f / fps;

		while (now - lastLoopTime < targetTime)
		{
			Thread.yield();

			/*
			 * This is optional if you want your game to stop consuming too much CPU but you
			 * will loose some accuracy because Thread.sleep(1) could sleep longer than 1
			 * millisecond
			 */
			try
			{
				Thread.sleep(1);
			} catch (InterruptedException ex)
			{
				SLogger.getSystemLogger().except(ex);
			}

			now = Sirius.timer.getTime();
		}
	}

	/**
	 * Determines if the OpenGL context supports version 3.2.
	 *
	 * @return true, if OpenGL context supports version 3.2, else false
	 */
	public static boolean isDefaultContext()
	{ return GL.getCapabilities().OpenGL32; }

	/**
	 * @return the game's frame buffer
	 */
	public FBO getFBO()
	{ return fbo; }
}
