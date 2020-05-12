package at.flockenberger.sirius.engine.event;

/**
 * <h1>MouseMoveEvent</h1><br>
 * Sent when the mouse was moved
 * 
 * @author Florian Wagner
 *
 */
public class MouseMoveEvent extends EventBase
{

	private static final long serialVersionUID = -8160169508882145408L;
	private double x;
	private double y;

	/**
	 * Creates a new {@link MouseMoveEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param x         the current x position of the mouse
	 * @param y         the current y position of the mouses
	 */
	public MouseMoveEvent(Object src, long timeStamp, double x, double y)
	{
		super(src, timeStamp);
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x position of the mouse
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * @return the y position of the mouse
	 */
	public double getY()
	{
		return y;
	}

}
