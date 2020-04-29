package at.flockenberger.sirius.utillity.logging;

import java.io.Serializable;

/**
 * <h1>LogEntry</h1><br>
 * Every message that is being logged with the {@link SLogger} is converted into
 * a {@link LogEntry}. A LogEntry stores the time stamp, message and the initial
 * {@link LogLevel}.
 * 
 * @author Florian Wagner
 *
 */
public class LogEntry implements Serializable {

	private static final long serialVersionUID = 6188903225366177665L;

	private long timeStamp;
	private String message;
	private LogLevel level;
	private String sourceClassName = "";
	private String sourceMethodName = "";
	private int sourceLineNumber = 0;

	private transient boolean hasSourceParameters = true;

	/**
	 * Creates a new LogEntry.
	 * 
	 * @param time the time stamp of the log message
	 * @param msg  the message that was being logged
	 * @param lvl  the {@link LogLevel} for the message
	 */
	public LogEntry(long time, String msg, LogLevel lvl) {
		this.timeStamp = time;
		this.message = msg;
		this.level = lvl;
	}

	/**
	 * @return the current time <br>
	 * 
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the initial message that was being logged
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the level the message was logged with
	 */
	public LogLevel getLevel() {
		return level;
	}

	/**
	 * @return the class name (with package) of the calling class
	 */
	public String getSourceClassName() {
		if (hasSourceParameters) {
			getSourceParameters();
		}
		return sourceClassName;
	}

	/**
	 * @return the method name of the calling method
	 */
	public String getSourceMethodName() {
		if (hasSourceParameters) {
			getSourceParameters();
		}
		return sourceMethodName;
	}

	/**
	 * @return the line number the log was called
	 */
	public int getSourceLineNumber() {
		if (hasSourceParameters) {
			getSourceParameters();
		}
		return sourceLineNumber;
	}

	protected void setSourceClassName(String sourceClassName) {
		this.sourceClassName = sourceClassName;
		hasSourceParameters = false;
	}

	protected void setSourceMethodName(String sourceMethodName) {
		this.sourceMethodName = sourceMethodName;
		hasSourceParameters = false;
	}

	protected void setSourceLineNumber(int n) {
		this.sourceLineNumber = n;
		hasSourceParameters = false;
	}

	// copied most of this method from the original util.Logger
	private void getSourceParameters() {
		hasSourceParameters = false;
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		boolean lookingForLogger = true;
		for (int ix = 0; ix < stack.length; ix++) {
			StackTraceElement frame = stack[ix];
			String cname = frame.getClassName();

			boolean isLoggerImpl = isLoggerImplFrame(cname);
			if (lookingForLogger) {
				if (isLoggerImpl) {
					lookingForLogger = false;
				}
			} else {
				if (!isLoggerImpl) {
					if (!cname.startsWith("java.lang.reflect.") && !cname.startsWith("sun.reflect.")) {
						setSourceClassName(cname);
						setSourceMethodName(frame.getMethodName());
						setSourceLineNumber(frame.getLineNumber());
						return;
					}
				}
			}
		}
	}

	private boolean isLoggerImplFrame(String cname) {
		return (cname.equals("at.flockenberger.sirius.utillity.logging.SLogger"));
	}
}