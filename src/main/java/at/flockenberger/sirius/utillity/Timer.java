package at.flockenberger.sirius.utillity;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import at.flockenberger.sirius.engine.IInitializable;

/**
 * <h1>Timer</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public class Timer implements IInitializable
{

	/**
	 * System time since last loop.
	 */
	private double lastLoopTime;
	/**
	 * Used for FPS and UPS calculation.
	 */
	private float timeCount;
	/**
	 * Frames per second.
	 */
	private int fps;
	/**
	 * Counter for the FPS calculation.
	 */
	private int fpsCount;
	/**
	 * Updates per second.
	 */
	private int ups;
	/**
	 * Counter for the UPS calculation.
	 */
	private int upsCount;

	private static Timer sysTimer;

	public static Timer getTimer()
	{
		if (sysTimer == null)
			sysTimer = new Timer();
		return sysTimer;
	}

	private Timer()
	{

	}

	@Override
	public void init()
	{
		lastLoopTime = getTime();
	}

	/**
	 * Returns the value of the GLFW timer. Unless the timer has been set using
	 * SetTime, the timer measures time elapsed since GLFW was initialized.
	 * 
	 * The resolution of the timer is system dependent, but is usually on the order
	 * of a few micro- or nanoseconds. It uses the highest-resolution monotonictime
	 * source on each supported platform.
	 * 
	 * This function may be called from any thread. Reading and writing of the
	 * internal timer offset is not atomic, so it needs to be externally
	 * synchronizedwith calls to SetTime.
	 * 
	 * @return the current value, in seconds, or zero if an error occurred
	 * 
	 */
	public double getTime()
	{
		return glfwGetTime();
	}

	/**
	 * Returns the time that have passed since the last loop.
	 *
	 * @return Delta time in seconds
	 */
	public float getDelta()
	{
		double time = getTime();
		float delta = (float) (time - lastLoopTime);
		lastLoopTime = time;
		timeCount += delta;
		return delta;
	}

	/**
	 * Updates the FPS counter.
	 */
	public void updateFPS()
	{
		fpsCount++;
	}

	/**
	 * Updates the UPS counter.
	 */
	public void updateUPS()
	{
		upsCount++;
	}

	/**
	 * Updates FPS and UPS if a whole second has passed.
	 */
	public void update()
	{
		if (timeCount > 1f)
		{
			fps = fpsCount;
			fpsCount = 0;

			ups = upsCount;
			upsCount = 0;

			timeCount -= 1f;
		}
	}

	/**
	 * Getter for the FPS.
	 *
	 * @return Frames per second
	 */
	public int getFPS()
	{
		return fps > 0 ? fps : fpsCount;
	}

	/**
	 * Getter for the UPS.
	 *
	 * @return Updates per second
	 */
	public int getUPS()
	{
		return ups > 0 ? ups : upsCount;
	}

	/**
	 * Getter for the last loop time.
	 *
	 * @return System time of the last loop
	 */
	public double getLastLoopTime()
	{
		return lastLoopTime;
	}

}
