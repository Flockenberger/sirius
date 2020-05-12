package at.flockenberger.sirius.engine.event;

/**
 * <h1>MouseScrollEvent</h1><br>
 * Called when the mouse wheel was used.
 * 
 * @author Florian Wagner
 *
 */
public class MouseScrollEvent extends EventBase
{

	private static final long serialVersionUID = 2828231085968085413L;
	private double scrollX;
	private double scrollY;
	private double deltaScrollX;
	private double deltaScrollY;

	/**
	 * Creates a new {@link MouseScrollEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param scrollX   the current absolute scroll x
	 * @param scrollY   the current absolute scroll y
	 * @param dtX       the delta scroll x
	 * @param dtY       the delta scroll y
	 */
	public MouseScrollEvent(Object src, long timeStamp, double scrollX, double scrollY, double dtX, double dtY)
	{
		super(src, timeStamp);
		this.deltaScrollX = dtX;
		this.deltaScrollY = dtY;
		this.scrollX = scrollX;
		this.scrollY = scrollY;
	}

	/**
	 * @return the current absolute x scroll value
	 */
	public double getScrollX()
	{
		return scrollX;
	}

	/**
	 * @return the current absolute y scroll value
	 */
	public double getScrollY()
	{
		return scrollY;
	}

	/**
	 * @return the delta scroll x value
	 */
	public double getDeltaScrollX()
	{
		return deltaScrollX;
	}

	/**
	 * @return the delta scroll y value
	 */
	public double getDeltaScrollY()
	{
		return deltaScrollY;
	}

}
