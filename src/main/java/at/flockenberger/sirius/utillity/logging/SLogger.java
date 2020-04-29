package at.flockenberger.sirius.utillity.logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>SLogger</h1><br>
 * The SLogger class is the main logger of the Sirius System.
 * 
 * @author Florian Wagner
 *
 */
public class SLogger {

	private static SLogger SYSTEM_LOGGER;
	private List<LogHandler> handlers;
	private LogConsoleHandler logch;

	/**
	 * @return the CardinalSystem Logger.
	 */
	public static SLogger getSystemLogger() {
		if (SYSTEM_LOGGER == null)
			SYSTEM_LOGGER = new SLogger();
		return SYSTEM_LOGGER;
	}

	private SLogger() {
		handlers = new ArrayList<LogHandler>();

		// add default console handler
		logch = new LogConsoleHandler();
		addDefaultConsoleHandler();
	}

	/**
	 * Removes the default {@link LogConsoleHandler} from this logger.
	 */
	public void removeDefaultConsoleHandler() {
		if (handlers.contains(logch))
			handlers.remove(logch);
	}

	/**
	 * Adds the default {@link LogConsoleHandler} to this logger.
	 */
	public void addDefaultConsoleHandler() {
		if (!handlers.contains(logch))
			handlers.add(logch);
	}

	/**
	 * Adds a new {@link LogHandler} to the Cardinal Logger.
	 * 
	 * @param hndl the handler to add
	 */
	public void addHandler(LogHandler hndl) {
		this.handlers.add(hndl);
	}

	/**
	 * Removes the given {@link LogHandler} from the Cardinal Logger.
	 * 
	 * @param hndl the logger to remove
	 */
	public void removeHandlre(LogHandler hndl) {
		this.handlers.remove(hndl);
	}

	/**
	 * Logs an {@link Exception}.
	 * 
	 * @param e the {@link Exception} to log
	 */
	public void except(Exception e) {
		error(e);
	}

	/**
	 * Logs a {@link StackTraceElement} array.
	 * 
	 * @param trace the stack trace to log
	 */
	public void trace(StackTraceElement[] trace) {
		StackTraceElement[] _trace;
		_trace = Arrays.copyOfRange(trace, 1, trace.length);
		for (StackTraceElement traceElement : _trace)
			error("\tat " + traceElement);
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, String msg) {
		_log(new LogEntry(System.currentTimeMillis(), msg, lvl));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, Object msg) {
		log(lvl, msg.toString());
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, int msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, boolean msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, double msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, float msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, long msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, char msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a given message to the given {@link LogLevel}.
	 * 
	 * @param lvl the {@link LogLevel} to assign this message to
	 * @param msg the message to log
	 */
	public void log(LogLevel lvl, byte msg) {
		log(lvl, String.valueOf(msg));
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(String msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(int msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(long msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(float msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(double msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(boolean msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(byte msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(char msg) {
		log(LogLevel.INFO, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#INFO} level.
	 * 
	 * @param msg the message to log
	 */
	public void info(Object msg) {
		log(LogLevel.INFO, msg.toString());
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(String msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(int msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(long msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(float msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(double msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(boolean msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(char msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(byte msg) {
		log(LogLevel.WARN, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#WARN} level.
	 * 
	 * @param msg the message to log
	 */
	public void warn(Object msg) {
		log(LogLevel.WARN, msg.toString());
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(String msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(Object msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(int msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(double msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(float msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(long msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(boolean msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(char msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#ERROR} level.
	 * 
	 * @param msg the message to log
	 */
	public void error(byte msg) {
		log(LogLevel.ERROR, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(String msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(Object msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(int msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(long msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(double msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(boolean msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(float msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(char msg) {
		log(LogLevel.DEBUG, msg);
	}

	/**
	 * Logs a message with the {@link LogLevel#DEBUG} level.
	 * 
	 * @param msg the message to log
	 */
	public void debug(byte msg) {
		log(LogLevel.DEBUG, msg);
	}

	// caller method to all handlers
	private void _log(LogEntry entry) {
		for (LogHandler h : handlers)
			h.log(entry);
	}

	/**
	 * Closes all log handlers
	 */
	public void free() {
		for (LogHandler h : handlers) {
			h.close();
		}
	}

}
