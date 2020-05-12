package at.flockenberger.sirius.engine.event;

/**
 * <h1>WindowContentScaleEvent</h1><br>
 * sent when the window was rescaled and the content size changed.
 * 
 * @author Florian Wagner
 *
 */
public class WindowContentScaleEvent extends EventBase
{

	private static final long serialVersionUID = -6424605789071392700L;
	private float scaleX;
	private float scaleY;

	/**
	 * Creates a new {@link WindowContentScaleEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the object was sent
	 * @param x         the x scale
	 * @param y         the y scale
	 */
	public WindowContentScaleEvent(Object src, long timeStamp, float x, float y)
	{
		super(src, timeStamp);
		this.scaleX = x;
		this.scaleY = y;
	}

	/**
	 * @return the x scale
	 */
	public float getScaleX()
	{
		return scaleX;
	}

	/**
	 * @return the y scale
	 */
	public float getScaleY()
	{
		return scaleY;
	}

}
