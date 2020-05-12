package at.flockenberger.sirius.engine.event;

/**
 * <h1>WindowSizeEvent</h1><br>
 * Sent when the window has been resized
 * 
 * @author Florian Wagner
 *
 */
public class WindowSizeEvent extends EventBase
{

	private static final long serialVersionUID = -203322796285879967L;
	private double width;
	private double height;

	/**
	 * Creates a new {@link WindowSizeEvent}
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param w         the new width
	 * @param h         the new height
	 */
	public WindowSizeEvent(Object src, long timeStamp, double w, double h)
	{
		super(src, timeStamp);
		this.width = w;
		this.height = h;

	}

	/**
	 * @return the new width of the window
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * @return the new height of the window
	 */
	public double getHeight()
	{
		return height;
	}

}
