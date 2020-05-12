package at.flockenberger.sirius.engine.event;

/**
 * <h1>WindowPositionEvent</h1><br>
 * Sent when the window position on the monitor has changed.
 * 
 * @author Florian Wagner
 *
 */
public class WindowPositionEvent extends EventBase
{

	private static final long serialVersionUID = 2525531998596885781L;
	private int posX;
	private int posY;

	/**
	 * Creates a new {@link WindowPositionEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param x         the new x position of the window
	 * @param y         the new y position of the window
	 */
	public WindowPositionEvent(Object src, long timeStamp, int x, int y)
	{
		super(src, timeStamp);
		this.posX = x;
		this.posY = y;
	}

	/**
	 * @return the new x position of the window
	 */
	public int getPosX()
	{
		return posX;
	}

	/**
	 * @return the new y position of the window
	 */
	public int getPosY()
	{
		return posY;
	}

}
