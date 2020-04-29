package at.flockenberger.sirius.utillity.logging;

import java.io.PrintStream;

/**
 * <h1>LogConsoleHandler</h1><br>
 * The LogConsoleHandler logs everything into the {@link System#out}
 * {@link PrintStream}.
 * 
 * @see LogStreamHandler
 * @author Florian Wagner
 *
 */
public class LogConsoleHandler extends LogStreamHandler
{
	/**
	 * Creates a new {@link LogConsoleHandler}
	 */
	public LogConsoleHandler()
	{
		setOutputStream(System.out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void log(LogEntry entry)
	{
		super.log(entry);
		flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close()
	{
		flush();
	}
}
