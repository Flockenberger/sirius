package at.flockenberger.sirius.utillity.logging;

/**
 * <h1>LogFormatter</h1><br>
 * The LogFormatter is used by the {@link SLogger}. It can be used to
 * customize the output of the logger by extending this abstract class.
 * 
 * @author Florian Wagner
 *
 */
public abstract class LogFormatter
{

	/**
	 * This method should be overridden to format a given {@link LogEntry} if
	 * needed.
	 * 
	 * @param entry The entry to format.
	 * @return the formatted entry
	 */
	public abstract String formatLogEntry(LogEntry entry);

	/**
	 * This method is called upon initialization of this formatter and its
	 * corresponding LogHandler. <br>
	 * This method should be overridden if you want to add an HTML Head to your Log
	 * for example. Or just to signalize a start of a log
	 * 
	 * @return the head of the log
	 */
	public abstract String getHead();

	/**
	 * The counterpart to the {@link #getHead()} method. Called after the log has
	 * been finished.
	 * 
	 * @return the tail for the whole log
	 */
	public abstract String getTail();

}
