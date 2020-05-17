package at.flockenberger.sirius.game.application;

import org.lwjgl.opengl.GL;

import at.flockenberger.sirius.engine.Camera;
import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.utillity.logging.SLogger;

public abstract class AbstractGame implements IFreeable
{

	public static int TARGET_FPS = 75;
	public static int TARGET_UPS = 30;
	protected final Camera DEFAULT_CAM = new Camera();
	/**
	 * Shows if the game is running.
	 */
	protected boolean running;

	private SLogger logger = SLogger.getSystemLogger();


	// protected FBO fbo;

	/**
	 * Default constructor for the game.
	 */
	public AbstractGame(int width, int height, String title)
	{
		new Sirius(width, height, title);
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

		logger.debug("Game has been started!");
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
		logger.debug("Freeing game resources.");
		
	}

	/**
	 * Initializes the game.<br>
	 * This method also calls {@link Window#show()} first which is needed to
	 * properly initialise all window handling.
	 */
	public void init()
	{
		logger.debug("Initializing game...");
		Sirius.init();
		initGame(Sirius.layerStack);
		running = true;
		logger.debug("Initialization done!");
	}

	/**
	 * @return the window of this game
	 */
	public Window getWindow()
	{
		return Sirius.window;
	}

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
	
	
	public void setTargetFPS(int fps){
		TARGET_FPS = fps;
	}
	public void drawFBO()
	{

	}

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
	{
		return GL.getCapabilities().OpenGL32;
	}

}
