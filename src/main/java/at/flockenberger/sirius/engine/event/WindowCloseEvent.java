package at.flockenberger.sirius.engine.event;

/**
 * <h1>WindowCloseEvent</h1><br>
 * Called whenever a window is closed.
 * 
 * @author Florian Wagner
 *
 */
public class WindowCloseEvent extends EventBase
{

	private static final long serialVersionUID = 4209884018226453441L;
	private long id;

	/**
	 * Creates a new {@link WindowCloseEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param windowID  the id of the window that was closed
	 */
	public WindowCloseEvent(Object src, long timeStamp, long windowID)
	{
		super(src, timeStamp);
		this.id = windowID;
	}

	/**
	 * @return the window id of the closed window
	 */
	public long getWindowID()
	{
		return this.id;
	}

}
