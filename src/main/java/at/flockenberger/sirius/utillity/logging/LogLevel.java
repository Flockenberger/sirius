package at.flockenberger.sirius.utillity.logging;

/**
 * <h1>LogLevel</h1><br>
 * The LogLevel enum has the different logger levels used by the
 * {@link SLogger} to differentiate information that is logged.
 * 
 * @author Florian Wagner
 *
 */
public enum LogLevel
{
	/**
	 * This LogLevel is for printing standard information.
	 */
	INFO(0, "Info"),
	/**
	 * Should be used to tell there was something off or potentially could be.
	 * 
	 */
	WARN(1, "Warning"),
	/**
	 * This LogLevel should only be used for error such as Shader Linking or
	 * Compiling errors.
	 */
	ERROR(2, "Error"),
	/**
	 * The System LogLevel is the internal log level. It is used internally.
	 */
	SYSTEM(3, "System"),
	/**
	 * Debug should only be used for debugging.
	 */
	DEBUG(4, "Debug"),
	/**
	 * Used to print each and every log message
	 */
	ALL(5, "All");
	
	private int level;
	private String name;

	LogLevel(int l, String n)
	{
		this.level = l;
		this.name = n;
	}

	/**
	 * @return the level of the logger as {@link Integer}.
	 */
	public int getIntLevel()
	{
		return this.level;
	}

	/**
	 * @return the name of the level as {@link String}
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Finds a {@link LogLevel} that corresponds to the given level with
	 * <code> level </code><br>
	 * Should no {@link Integer} level match, the default {@link #INFO} will be
	 * returned.
	 * 
	 * @param level the level to get as LogLevel
	 * @return the found LogLevel or {@link LogLevel#INFO}
	 */
	public LogLevel getByLevel(int level)
	{
		for (LogLevel l : LogLevel.values())
		{
			if (l.getIntLevel() == level)
				return l;
		}
		return INFO;
	}

}
