package at.flockenberger.sirius.game;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.graphic.WindowIcon;

/**
 * <h1>GameBaes</h1><br>
 * Base class for the main game loop.
 * 
 * @author Florian Wagner
 *
 */
public abstract class GameBase extends Window implements IGame
{
	// Whether our game loop is running
	protected boolean running = false;

	/** time at last frame */
	private long lastFrame;
	private int fpsTick;
	/** frames per second */
	private int fps;
	/** last fps time */
	private long lastFPS;
	/** the delta time in milliseconds */
	private int delta;

	public GameBase(boolean windowedFullscreen, String title, WindowIcon icon)
	{
		super(windowedFullscreen, title, icon);
		// TODO Auto-generated constructor stub
	}

	public GameBase(boolean windowedFullscreen, String title)
	{
		super(windowedFullscreen, title);
		// TODO Auto-generated constructor stub
	}

	public GameBase(boolean windowedFullscreen)
	{
		super(windowedFullscreen);
		// TODO Auto-generated constructor stub
	}

	public GameBase(int width, int height, String title, WindowIcon icon)
	{
		super(width, height, title, icon);
		// TODO Auto-generated constructor stub
	}

	public GameBase(int width, int height, String title)
	{
		super(width, height, title);
		// TODO Auto-generated constructor stub
	}

	public GameBase(int width, int height)
	{
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public int getDeltaTime()
	{
		return delta;
	}

	public int getFPS()
	{
		return fps;
	}

	@Override
	public void start()
	{
		show();
		init();
		running = true;
	
		delta = tick();
		lastFPS = (long) Window.getTime();
		resize();
		
		while (running && !askClose())
		{
			delta = tick();
			render();
			update();
			
			updateFPS();

		}
	}

	@Override
	public void stop()
	{
		running = false;
		free();
	}
	
	public abstract void render();
	public abstract void init();
	public abstract void resize();
	
	private int tick()
	{
		long time = (long) Window.getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public void updateFPS()
	{
		if (Window.getTime() - lastFPS > 1000)
		{
			fps = fpsTick;
			fpsTick = 0;
			lastFPS += 1000;
		}
		fpsTick++;
	}

}
