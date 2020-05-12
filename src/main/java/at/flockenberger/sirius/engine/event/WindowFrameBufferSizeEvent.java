package at.flockenberger.sirius.engine.event;

/**
 * <h1>WindowFrameBufferSizeEvent</h1><br>
 * Sent when the Framebuffer size was changed.
 * 
 * @author Florian Wagner
 *
 */
public class WindowFrameBufferSizeEvent extends EventBase
{

	private static final long serialVersionUID = 5831876187936121175L;
	private int width;
	private int height;

	/**
	 * Creates a new {@link WindowFrameBufferSizeEvent}.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was sent
	 * @param w         the new width
	 * @param h         the new height
	 */
	public WindowFrameBufferSizeEvent(Object src, long timeStamp, int w, int h)
	{
		super(src, timeStamp);
		this.width = w;
		this.height = h;
	}

	/**
	 * @return the new framebuffer width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return the new framebuffer height
	 */
	public int getHeight()
	{
		return height;
	}

}
