package at.flockenberger.sirius.utillity.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import sun.reflect.Reflection;

/**
 * <h1>LogDefaultFormatter</h1><br>
 * The default formatter used by the {@link SLogger}.
 * 
 * @author Florian Wagner
 *
 */
public class LogDefaultFormatter extends LogFormatter
{

	private final String NAME = "[SIRIUS]";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String formatLogEntry(LogEntry entry)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(NAME);
		sb.append("[");
		sb.append(calcTime(entry.getTimeStamp()));
		sb.append("]");
		sb.append("[");
		sb.append(entry.getLevel().getName());
		sb.append("]:");
		sb.append(entry.getMessage());
// sb.append(
// entry.getSourceClassName().substring(entry.getSourceClassName().lastIndexOf(".")
// +1)+ "." + entry.getSourceMethodName() + "["
// + entry.getSourceLineNumber() + "]");
		sb.append(System.lineSeparator());
		return sb.toString();
	}

	private String calcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("dd.mm.yyyy HH:mm:ss");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	private String calcTime(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHead()
	{ return NAME + ": LOG STARTED " + calcDate(System.currentTimeMillis()) + System.lineSeparator(); }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTail()
	{ return NAME + ": LOG STOPPED " + calcDate(System.currentTimeMillis()) + System.lineSeparator(); }

}
