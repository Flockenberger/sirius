package at.flockenberger.sirius.utillity.exceptions;

public class SiriusRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = -3613195426708110913L;

	public SiriusRuntimeException()
	{
		super("Error during sirius runtime!");
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which typically
	 * contains the class and detail message of <tt>cause</tt>). This constructor is
	 * useful for runtime exceptions that are little more than wrappers for other
	 * throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method). (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 * @since 1.4
	 */
	public SiriusRuntimeException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later
	 *                retrieval by the {@link #getMessage()} method.
	 */
	public SiriusRuntimeException(String message)
	{
		super(message);
	}
}
