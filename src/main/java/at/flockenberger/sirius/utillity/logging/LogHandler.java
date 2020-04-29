package at.flockenberger.sirius.utillity.logging;

import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>LogHandler</h1><br>
 * The LogHandler is the base class for all logging handlers.
 *
 * @author Florian Wagner
 *
 */
public abstract class LogHandler
{

	private volatile LogFormatter formatter = new LogDefaultFormatter();
	private volatile LogLevel logLevel = LogLevel.ALL;

	/**
	 * Called when a {@link LogEntry} should be logged.
	 * 
	 * @param entry the entry to log
	 */
	public abstract void log(LogEntry entry);

	/**
	 * Closes the handler, all writers, buffers etc. should be closed in this method
	 */
	public abstract void close();

	/**
	 * Called to flush all the buffers of a handler
	 */
	public abstract void flush();

	/**
	 * Sets the {@link LogLevel}. Every level and <b>BELOW</b> will be logged.
	 * 
	 * @param lvl the logging level
	 */
	public synchronized void setLevel(LogLevel lvl)
	{
		if(SUtils.checkNull(lvl, "LogLevel"))return;
		this.logLevel = lvl;
	}

	/**
	 * @return the logging level
	 */
	public LogLevel getLevel()
	{
		return this.logLevel;
	}

	/**
	 * Sets the {@link LogFormatter} for this handler.
	 * 
	 * @param frmt the formatter to set
	 */
	public synchronized void setFormatter(LogFormatter frmt)
	{
		if(SUtils.checkNull(frmt, "LogFormatter"))return;
		this.formatter = frmt;
	}

	/**
	 * @return the {@link LogFormatter} of this handler
	 */
	public LogFormatter getFormatter()
	{
		return this.formatter;
	}

	/**
	 * Called to check whether this {@link LogEntry} should be logged based on the
	 * current logging level.
	 * 
	 * @param entry the entry to check
	 * @return true if it should be logged otherwise false
	 */
	boolean shouldLog(LogEntry entry)
	{
		final int llevl = logLevel.getIntLevel();
		if (entry.getLevel().getIntLevel() <= llevl)
			return true;

		return false;
	}

}
