package at.flockenberger.sirius.engine.event;

import java.io.Serializable;

/**
 * <h1>EventBase</h1><br>
 * Base class for all Events that are being sent.
 * 
 * @author Florian Wagner
 *
 */
public class EventBase implements Serializable
{

	private static final long serialVersionUID = -5127127644498145678L;
	private boolean consumed;
	private long timeStamp;
	private Object source;

	/**
	 * Constructs a new {@link EventBase} object.
	 * 
	 * @param src       the source object
	 * @param timeStamp the timestamp the event was being sent
	 */
	public EventBase(Object src, long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	/**
	 * Consumes the event.
	 */
	public void consume()
	{
		this.consumed = true;
	}

	/**
	 * @return whether the event has already been consumed or not
	 */
	public boolean isConsumed()
	{
		return this.consumed;
	}

	/**
	 * @return the timestamp the event was sent
	 */
	public long getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @return the source object of the event
	 */
	public Object getSource()
	{
		return this.source;
	}
}
